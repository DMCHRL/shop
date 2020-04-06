package com.shop.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif","image/jpeg","image/png");

    @Autowired
    private FastFileStorageClient storageClient;

    @Value(value = "${fdfsPath}")
    private String fdfsPath;

    public String uploadImage(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        //文件类型不合法
        String contentType = file.getContentType();
        if(!CONTENT_TYPES.contains(contentType)){
            return null;
        }
        //文件内容
        try{
            BufferedImage read = ImageIO.read(file.getInputStream());
            if(read == null){
                return null;
            }
            String ext = StringUtils.substringAfterLast(originalFilename,".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(),ext,null);
            System.out.println(fdfsPath+storePath.getFullPath());
            return fdfsPath+storePath.getFullPath();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
