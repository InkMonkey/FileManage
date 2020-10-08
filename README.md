# filemanage

Java搭建的简单文件系统，支持单文件的上传下载功能。配合项目[SaveEvidence](https://github.com/InkMonkey/SaveEvidence.git)使用。

###部署

1.使用IDEA中maven工具将源码编译打包。

2.将`target`目录下`filemanage-0.0.1-SNAPSHOT-bin.zip`上传至服务器。

3.使用`unzip`命令进行解压缩。

4.在`config`目录修改配置文件`application.yml`,配置端口号及文件存放路径。

5.在`static`目录下修改`index.html`,更改ajax请求路径为当前服务器域名/ip地址。

6.在项目根目录下修改`run.sh`文件,配置脚本。通过`./run.sh start`启动程序。

###注意事项

1.在linux环境下初次使用时,请执行`dos2unix`命令更改`run.sh`文件格式

2.`run.sh`文件中`APP_NAME`后需更改为程序jar包名称。

3.如果没有权限启动,使用`chmod`命令赋予脚本权限。
