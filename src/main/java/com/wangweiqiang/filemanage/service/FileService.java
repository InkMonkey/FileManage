package com.wangweiqiang.filemanage.service;

import com.wangweiqiang.filemanage.model.FileInfo;
import java.io.IOException;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public FileInfo saveFile(MultipartFile file) throws IOException;

    public Path getFile(String fileName, String filePath);
}
