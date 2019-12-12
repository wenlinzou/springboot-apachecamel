###### 1 打包maven
```shell
mvn clean install -Dmaven.test.skip=true 
```
###### 2 运行jar文件
```shell
# 指定运行环境
nohup java -jar springboot-apachecamel.jar --spring.profiles.active=prod &
```


###### camel配置说明
http://camel.apache.org/ftp2.html
```
delay：每次读取时间间隔
filter: 指定文件过滤器
noop：读取后对源文件不做任何处理
recursive：递归扫描子目录，需要在过滤器中允许扫描子目录
readLock：对正在写入的文件的处理机制
passiveMode 指定是否使用被动模式连接。默认是false。（true）修复but no files can be found
```

###### 项目介绍
从ftp中获取每日的文件夹中的文件，将文件复制到本地，同时解析复制的文件，将文件信息入库；

如

1 ftp://txt_info/20191225/下有a.txt,b.txt,c.txt

2 copy到D:/ftpdata/txt/save/20191225/下

3 解析本地文件a.txt，将数据提取批量插入到数据库表中
