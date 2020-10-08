package com.wangweiqiang.filemanage.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 上传文件信息
 */
@Data
@AllArgsConstructor
@ToString
public class FileInfo implements Serializable {
    private static final long serialVersionUID = -4225378572420197762L;

    private String fileName;

    //文件md5值
    private String fileHash;

    private String filePath;

    private String fileSize;

    private String fileUploadTime;
}
