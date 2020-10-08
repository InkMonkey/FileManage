package com.wangweiqiang.filemanage.controller;

import com.wangweiqiang.filemanage.model.FileInfo;
import com.wangweiqiang.filemanage.model.JsonResult;
import com.wangweiqiang.filemanage.service.FileService;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author WWQ
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Value("${fileRootPath}")
    private String fileRootPath;

    @Autowired
    private FileService fileService;

    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public JsonResult upload(@RequestParam("file") MultipartFile file) {
        log.info("com.wangweiqiang.filemanage.controller.FileController.upload()--------------------START");
        if(file.isEmpty()){
            log.error("上传文件为空");
            return new JsonResult(1,"上传文件为空");
        }
        FileInfo fileInfo = null;
        try {
            fileInfo = fileService.saveFile(file);
        } catch (Exception e) {
            log.error("储存文件失败:",e);
        }
        log.info("com.wangweiqiang.filemanage.controller.FileController.upload()--------------------END");
        return new JsonResult(0,"上传成功",fileInfo);
    }

    @RequestMapping(value = "/download",method = RequestMethod.POST)
    public void download(HttpServletRequest request, HttpServletResponse response){
        log.info("com.wangweiqiang.filemanage.controller.FileController.download()--------------------START");
        String fileName = request.getParameter("fileName");
        String filePath = fileRootPath + fileName;

        if (StringUtils.isEmpty(filePath) || StringUtils.isEmpty(fileName)){
            log.error("文件路径或文件名为空！");
            return;
        }

        Path path = fileService.getFile(fileName, filePath);
        if (path == null){
            log.error("待下载文件不存在");
            return;
        }

        String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
        response.setContentType("application/"+fileSuffix);
        try {
            response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
            Files.copy(path,response.getOutputStream());
        } catch (Exception e) {
            log.error("下载失败:{}",e);
            return;
        }
        log.info("com.wangweiqiang.filemanage.controller.FileController.download()--------------------END");
    }
}
