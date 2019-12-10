package com.mq.ftp.config;

import com.mq.ftp.handler.FtpFileProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 文件下载路由设置
 *
 * @author wenlinzou
 */
@Component
public class FtpFileDownloadRoute extends RouteBuilder {

    @Value("${ftp.txt.server-info}")
    private String txtDataUrl;

    @Value("${ftp.txt.local-save-dir}")
    private String localDir;

    @Autowired
    private FtpFileProcessor ftpFileProcessor;

    @Override
    public void configure() throws Exception {
        //数据源
        from(txtDataUrl)
                //目标
                .to("file:" + localDir)
                //过滤器
                .process(ftpFileProcessor);
    }
}
