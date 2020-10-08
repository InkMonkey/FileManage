package com.wangweiqiang.filemanage.util;


import java.io.File;
import java.text.DecimalFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取文件大小，并将文件大小以String返回
 */
public class FileSizeFormatUtil {

    private static final Logger log = LoggerFactory.getLogger(FileSizeFormatUtil.class);
    public static String getSize(File file){
        log.info("com.wangweiqiang.filemanage.util.FileSizeFormat.getSize()---------START");
        if (!file.exists()){
            log.error("文件不存在!");
            log.info("com.wangweiqiang.filemanage.util.FileSizeFormat.getSize()---------END");
            return "";
        }
        long size = file.length();
        if (size == 0L){
            log.error("文件内容为空!");
            log.info("com.wangweiqiang.filemanage.util.FileSizeFormat.getSize()---------END");
            return "0B";
        }
        String resultSize = doFormat(size);
        log.info("文件大小：{}",resultSize);
        log.info("com.wangweiqiang.filemanage.util.FileSizeFormat.getSize()---------END");
        return resultSize;
    }

    public static String doFormat(long fileSize){
        log.info("com.wangweiqiang.filemanage.util.FileSizeFormat.doFormat()---------START");
        log.info("传入参数:{}",fileSize);
        DecimalFormat df = new DecimalFormat("#.00");
        String resultSize;
        if (fileSize < 1024){
            resultSize = df.format(fileSize) + "B";
        }else if (fileSize < 1024*1024){
            resultSize = df.format((double)fileSize/1024) + "KB";
        }else if (fileSize < 1024*1024*1024){
            resultSize = df.format((double)fileSize/(1024*1024)) + "MB";
        }else {
            resultSize = df.format((double)fileSize/(1024*1024*1024)) + "GB";
        }
        log.info("文件大小：{}",resultSize);
        log.info("com.wangweiqiang.filemanage.util.FileSizeFormat.doFormat()---------END");
        return resultSize;
    }
}
