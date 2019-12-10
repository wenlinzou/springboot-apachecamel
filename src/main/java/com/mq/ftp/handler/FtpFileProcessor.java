package com.mq.ftp.handler;


import com.mq.ftp.entity.ReportFtpInfoModel;
import com.mq.ftp.service.FtpOperateService;
import com.mq.ftp.util.DateUtil;
import com.mq.ftp.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFileMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * ftp文件路由解析器
 * 解析从ftp服务器路由下来的数据文件
 *
 * @author wenlinzou
 */
@Slf4j
@Component
public class FtpFileProcessor implements Processor {

    @Value("${ftp.txt.local-save-dir}")
    private String localDir;

    @Resource
    FileUtil fileUtil;

    @Resource
    FtpOperateService ftpOperateService;

    @Override
    public void process(Exchange exchange) {
        log.info("+++++++++++++开始解析ftp上下载的txt文件+++++++++++++");
        GenericFileMessage<RandomAccessFile> inFileMessage = (GenericFileMessage<RandomAccessFile>) exchange.getIn();

        String ftpFileName = inFileMessage.getGenericFile().getFileName();
        log.info("ftp upload localPath ftpFilename={}", ftpFileName);
        int index = ftpFileName.indexOf("/");
        if (index > 0) {
            ftpFileName = ftpFileName.substring(index + 1);
        }


        String localFtpFilePath = localDir + File.separator + DateUtil.getYesterDay() + File.separator + ftpFileName;
        log.info("LocalFtpFile={}", localFtpFilePath);
        File saveDbFile = new File(localFtpFilePath);
        if (saveDbFile.exists()) {
            saveFile2Db(saveDbFile);
        }


    }

    private int saveFile2Db(File file) {
        log.info("++++++++++++ save start localFile={} ++++++++++++", file.getName());
        List<ReportFtpInfoModel> txtList = fileUtil.getDbListByFileLines(file);
        int rows = ftpOperateService.process(txtList);
        log.info("++++++++++++ save end localFile={} dbSize={} ++++++++++++", file.getName(), rows);
        return rows;
    }

}
