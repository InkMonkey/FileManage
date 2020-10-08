package com.wangweiqiang.filemanage.service.Impl;

import com.wangweiqiang.filemanage.model.FileInfo;
import com.wangweiqiang.filemanage.service.FileService;
import com.wangweiqiang.filemanage.util.FileSizeFormatUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${fileRootPath}")
    private String fileRootPath;

    @Override
    public FileInfo saveFile(MultipartFile file) {

        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeStamp);
        String fileUploadTime = formatter.format(date);

        String originalFilename = file.getOriginalFilename();
        String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = fileNamePrefix+ timeStamp+fileNameSuffix;
        log.info("新文件名:{}",newFileName);

        long size = file.getSize();
        String fileSize = FileSizeFormatUtil.doFormat(size);

        String md5Hex = null;
        String filePath = fileRootPath+newFileName;
        try {
            md5Hex = DigestUtils.md5Hex(file.getInputStream());
        } catch (IOException e) {
            log.error("获取文件md5值失败",e);
        }
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error("文件保存失败",e);
        }

        FileInfo fileInfo = new FileInfo(originalFilename, md5Hex, filePath, fileSize, fileUploadTime);
        log.info("储存文件信息:{}",fileInfo.toString());
        return fileInfo;
    }

    @Override
    public Path getFile(String fileName, String filePath) {

        if (!Files.exists(Paths.get(filePath))){
            log.error("{}下文件{}不存在!",filePath,fileName);
            return null;
        }
        return Paths.get(filePath);
    }
}
