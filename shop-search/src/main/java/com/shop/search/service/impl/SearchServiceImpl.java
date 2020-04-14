package com.shop.search.service.impl;

import com.shop.item.bo.GoodsSearchBO;
import com.shop.item.pojo.Brand;
import com.shop.item.pojo.Category;
import com.shop.item.pojo.SpecParam;
import com.shop.search.bo.SearchResult;
import com.shop.search.client.BrandClient;
import com.shop.search.client.CategoryClient;
import com.shop.search.client.GoodsClient;
import com.shop.search.client.SpecificationClient;
import com.shop.search.dto.GoodsSearchDTO;
import com.shop.search.pojo.GoodsSearch;
import com.shop.search.repository.GoodsRepository;
import com.shop.search.service.SearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Override
    public SearchResult search(GoodsSearchDTO goodsSearchDTO) {
        if(StringUtils.isBlank(goodsSearchDTO.getKey())){
            return null;
        }
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //查询关键字
        //QueryBuilder baseQuery = QueryBuilders.matchQuery("all", goodsSearchDTO.getKey()).operator(Operator.AND);
        BoolQueryBuilder baseQuery = QueryBuilders.boolQuery();
        baseQuery.must( QueryBuilders.matchQuery("all", goodsSearchDTO.getKey()).operator(Operator.AND));
        for (Map.Entry<String, Object> entry : goodsSearchDTO.getFilter().entrySet()) {
            String key = entry.getKey();
            if(StringUtils.equals("品牌",key)){
                key = "brandId";
            }else if(StringUtils.equals("分类",key)){
                key = "cid3";
            }else {
                key = "specs."+key+".keyword";
            }
            baseQuery.filter(QueryBuilders.termQuery(key,entry.getValue()));
        }

        nativeSearchQueryBuilder.withQuery(baseQuery);
        //分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(goodsSearchDTO.getPage()-1,goodsSearchDTO.getSize()));
        //过滤只显示字段
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","skus","subTitle"},null));

        String categoryAggName = "categories";
        String brandAggName = "brands";
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3")); //聚合三级分类Id
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId")); //聚合品牌Id
        AggregatedPage<GoodsSearch> search = ( AggregatedPage<GoodsSearch>)goodsRepository.search(nativeSearchQueryBuilder.build());

        //获取分类结果集
        List<Map<String,Object>> categories =  getCategoryResult(search.getAggregation(categoryAggName));
        //品牌结果集
        List<Brand> brands = getBrandResult(search.getAggregation(brandAggName));

        //只有一个分类才做规格参数聚合
        List<Map<String,Object>> spec = null;
        if(!CollectionUtils.isEmpty(categories) && categories.size() == 1){
            spec = getSpecResult((Long)categories.get(0).get("id"),baseQuery);
        }


        return new SearchResult(search.getTotalElements(),search.getTotalPages(),search.getContent(),categories,brands,spec);
    }

    /**
     * 根据查询条件聚合规格参数
     * @param id
     * @param baseQuery
     * @return
     */
    private List<Map<String, Object>> getSpecResult(Long id, QueryBuilder baseQuery) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(baseQuery);

        List<SpecParam> specParams = specificationClient.queryParams(null, id, null,true);

        specParams.forEach( param -> {
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs."+param.getName()+".keyword"));
        });
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));
        AggregatedPage<GoodsSearch> search = (AggregatedPage<GoodsSearch>)goodsRepository.search(nativeSearchQueryBuilder.build());
        //获取所有聚合
        Map<String, Aggregation> stringAggregationMap = search.getAggregations().asMap();

        List<Map<String, Object>> specs = new ArrayList<>();
        for (Map.Entry<String, Aggregation> entry : stringAggregationMap.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("k",entry.getKey());
            StringTerms stringTerms = (StringTerms)entry.getValue();
            map.put("options", stringTerms.getBuckets().stream().map(StringTerms.Bucket ::getKeyAsString).collect(Collectors.toList()));
            specs.add(map);
        }
        return specs;

    }

    private List<Brand> getBrandResult(Aggregation aggregation) {
        List<Brand> brands = new ArrayList<>();
        LongTerms terms = (LongTerms)aggregation;
        //获取桶
        terms.getBuckets().forEach( t -> {
            brands.add(brandClient.getById(t.getKeyAsNumber().longValue()));
        });
        return brands;
    }

    private List<Map<String, Object>> getCategoryResult(Aggregation aggregation) {
        List<Map<String, Object>> categories = new ArrayList<>();
        LongTerms terms = (LongTerms)aggregation;
        //获取桶
        terms.getBuckets().forEach( t -> {
            Category byId = categoryClient.getById(t.getKeyAsNumber().longValue());
            Map<String, Object> map = new HashMap<>();
            map.put("id",byId.getId());
            map.put("name",byId.getName());
            categories.add(map);
        });
        return categories;
    }

    @Override
    public void updateGoodsIndex(Long spuId) {
        List<GoodsSearch> goodsSearchList = new ArrayList<>();
        List<GoodsSearchBO> allGoodsSearch = goodsClient.getAllGoodsSearch(spuId);
        allGoodsSearch.forEach( t -> {
            GoodsSearch goodsSearch = new GoodsSearch();
            goodsSearch.setId(t.getId());
            goodsSearch.setAll(t.getAll());
            goodsSearch.setSubTitle(t.getSubTitle());
            goodsSearch.setCid1(t.getCid1());
            goodsSearch.setCid2(t.getCid2());
            goodsSearch.setCid3(t.getCid3());
            goodsSearch.setBrandId(t.getBrandId());
            goodsSearch.setCreateTime(t.getCreateTime());
            goodsSearch.setPrice(t.getPrice());
            goodsSearch.setSkus(t.getSkus());
            goodsSearch.setSpecs(t.getSpecs());
            goodsSearchList.add(goodsSearch);
        });
        goodsRepository.saveAll(goodsSearchList);
    }
}
