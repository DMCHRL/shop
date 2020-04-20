package com.shop.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.common.config.rabbitmq.RabbitExchangeEnum;
import com.shop.common.config.rabbitmq.RabbitKeyEnum;
import com.shop.common.pojo.PageResult;
import com.shop.item.bo.GoodsSearchBO;
import com.shop.item.config.TopicRabbitConfig;
import com.shop.item.convert.SpuConvert;
import com.shop.item.dto.Goods;
import com.shop.item.mapper.SkuMapper;
import com.shop.item.mapper.SpuDetailMapper;
import com.shop.item.mapper.SpuMapper;
import com.shop.item.pojo.*;
import com.shop.item.service.BrandService;
import com.shop.item.service.CategoryService;
import com.shop.item.service.SpecificationService;
import com.shop.item.service.SpuService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpecificationService specificationService;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Override
    public PageResult<Spu> pageList(String key, Boolean saleable, int page, int rows) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        criteria.andEqualTo("saleable",saleable);
        PageHelper.startPage(page,rows);
        List<Spu> spus = spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);
        pageInfo.getList().forEach( t -> {
            List<String> collect = categoryService.getByCidList(Arrays.asList(t.getCid1(), t.getCid2(), t.getCid3())).stream()
                    .map(Category::getName).collect(Collectors.toList());
            t.setCname(StringUtils.join(collect,"/"));
            t.setBname(brandService.findById(t.getBrandId()).getName());
        });
        return PageResult.build(pageInfo.getTotal(),pageInfo.getSize(),pageInfo.getList());
    }

    @Override
    public void saveGoods(Goods goods) {
        Spu spu = SpuConvert.INSTANCE.goods2Spu(goods);
        Date date = new Date();
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(date).setLastUpdateTime(date);
        if(spu.getId() == null){
            spuMapper.insert(spu);
        }else {
            spuMapper.updateByPrimaryKey(spu);
        }
        List<Sku> skus = goods.getSkus();
        skus.forEach( t -> {
            t.setSpuId(spu.getId()).setCreateTime(date).setLastUpdateTime(date);
            if(spu.getId() == null){
                skuMapper.insert(t);
            }else {
                skuMapper.updateByPrimaryKey(t);
            }
        });
        SpuDetail spuDetail = goods.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        if(spu.getId() == null){
            spuDetailMapper.insert(spuDetail);
        }else {
            spuDetailMapper.updateByPrimaryKey(spuDetail);
        }
        sendMsg(RabbitKeyEnum.ITEM_INSERT.getName(),spu.getId());
    }

    @Override
    public SpuDetail getSpuDetails(Long supId) {
       return spuDetailMapper.selectByPrimaryKey(supId);
    }

    private void sendMsg(String type, Long spuId) {
        try{
            rabbitTemplate.convertAndSend(RabbitExchangeEnum.SHEP_ITEM_EXCHANGE.getName(),type,spuId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<GoodsSearchBO> getAllGoodsSearch(Long id) {
        List<GoodsSearchBO> result = new ArrayList<>();
        List<Spu> spus;
        if(id == null){
            spus = spuMapper.selectAll();
        }else{
            spus = new ArrayList<>();
            spus.add(spuMapper.selectByPrimaryKey(id));
        }
        spus.forEach( t -> {
            GoodsSearchBO goodsSearchBO = new GoodsSearchBO();
            goodsSearchBO.setId(t.getId());
            //获取品牌
            List<String> categoryNames = categoryService.getByCidList(Arrays.asList(t.getCid1(), t.getCid2(), t.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            //获取sku
            List<Sku> skus = findBySpuId(t.getId());
            List<Long> prices = new ArrayList<>();
            List<Map<String, Object>> skuList = new ArrayList<>();
            skus.forEach(sku -> {
                prices.add(sku.getPrice());
                Map<String, Object> skuMap = new HashMap<>();
                skuMap.put("id", sku.getId());
                skuMap.put("title", sku.getTitle());
                skuMap.put("price", sku.getPrice());
                skuMap.put("image", StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
                skuList.add(skuMap);
            });
            //类目下的spec
            List<SpecParam> specParams = specificationService.paramByGidOrCid(null, t.getCid3());
            //spu详情
            SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(t.getId());

            // 处理规格参数
            Map<String, Object> genericSpecs = JSON.parseObject(spuDetail.getGenericSpec(),new TypeReference< Map<String, Object>>(){});
            Map<String, Object> specialSpecs = JSON.parseObject(spuDetail.getSpecialSpec(),new TypeReference< Map<String, Object>>(){});

            Map<String, Object> specMap = new HashMap<>();
            specParams.forEach( p -> {
                if (p.getSearching()) {
                    if (p.getGeneric()) {
                        String value = genericSpecs.get(p.getId().toString()).toString();
                        if(p.getNumeric()){
                            value = chooseSegment(value, p);
                        }
                        specMap.put(p.getName(), StringUtils.isBlank(value) ? "其它" : value);
                    } else {
                        specMap.put(p.getName(), specialSpecs.get(p.getId().toString()));
                    }
                }
            });

            goodsSearchBO.setAll(t.getTitle()+" "+StringUtils.join(categoryNames," "));
            goodsSearchBO.setSubTitle(t.getSubTitle());
            goodsSearchBO.setCid1(t.getCid1());
            goodsSearchBO.setCid2(t.getCid2());
            goodsSearchBO.setCid3(t.getCid3());
            goodsSearchBO.setBrandId(t.getBrandId());
            goodsSearchBO.setCreateTime(t.getCreateTime());
            goodsSearchBO.setPrice(prices);
            goodsSearchBO.setSkus(JSON.toJSONString(skuList));
            goodsSearchBO.setSpecs(specMap);
            result.add(goodsSearchBO);
        });
        return result;
    }


    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    @Override
    public List<Sku> findBySpuId(Long spuId) {
        return skuMapper.select(new Sku().setSpuId(spuId));
    }

    @Override
    public Map<String, Object> getGoodsDetails(Long spuId) {
        Map<String, Object> model = new HashMap<>();
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        List<Category> byCidList = categoryService.getByCidList(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        List<Map<String,Object>> categories = new ArrayList<>();
        byCidList.forEach( t -> {
            Map<String,Object> map = new HashMap<>();
            map.put("id",t.getId());
            map.put("name",t.getName());
            categories.add(map);
        });
        Brand brand = brandService.findById(spu.getBrandId());
        List<SpecGroup> specGroups = specificationService.groupsByCid(spu.getCid3());
        List<SpecParam> specParams = specificationService.queryParams(null, spu.getCid3(), false, null);
        Map<Long,String> paramMap = new HashMap<>();
        specParams.forEach( param -> {
            paramMap.put(param.getId(),param.getName());
        });
        List<Sku> skus = findBySpuId(spuId);
        model.put("spu",spu);
        model.put("spuDetail",spuDetail);
        model.put("categories",categories);
        model.put("brand",brand);
        model.put("skus",skus);
        model.put("groups",specGroups);
        model.put("paramMap",paramMap);
        return model;
    }

    @Override
    public Sku findSkuById(Long skuId) {
        return skuMapper.selectByPrimaryKey(skuId);
    }
}
