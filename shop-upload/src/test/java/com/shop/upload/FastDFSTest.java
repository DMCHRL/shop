package com.shop.upload;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FastDFSTest {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Test
    public void testUpload() throws Exception{
        File file = new File("C:\\Users\\DMCHRL\\Desktop\\logo.png");
        StorePath storePath = storageClient.uploadFile(new FileInputStream(file),file.length(),"png",null);
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getPath());
    }

    @Test
    public void testUploadAndCreateThumb() throws Exception{
        File file = new File("C:\\Users\\DMCHRL\\Desktop\\logo.png");
        StorePath storePath = storageClient.uploadFile(new FileInputStream(file),file.length(),"png",null);
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getPath());
        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println(path);
    }
}
