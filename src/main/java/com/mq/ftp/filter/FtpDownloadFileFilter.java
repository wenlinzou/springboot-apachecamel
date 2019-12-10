package com.mq.ftp.filter;

import com.mq.ftp.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FTP文件路由过滤器
 * 将只下载当天，且下载目录中不存在的文件
 *
 * @author wenlinzou
 */
@Slf4j
@Component
public class FtpDownloadFileFilter implements GenericFileFilter<Object> {

    /**
     * 将ftp下载到本地文件路径中
     */
    @Value("${ftp.txt.local-save-dir}")
    private String tempPath;


    /**
     * 过滤下载文件
     *
     * @param genericFile
     * @return true下载；false不下载
     */
    @Override
    public boolean accept(GenericFile<Object> genericFile) {

        long lastModified = genericFile.getLastModified();
        String fileName = genericFile.getFileName();
        int index = fileName.indexOf("/");
        if (index > 0) {
            fileName = fileName.substring(index + 1);
        }
        boolean accept = isLatestFile(fileName, lastModified) && !isInLocalDir(fileName) ? true : false;
        log.info("过滤下载文件={}, 需要下载={}", fileName, accept);
        return accept;
    }


    /**
     * 文件是否已在本地目录中
     *
     * @param ftpFileName ftp的文件是否在本地中
     * @return
     */
    private boolean isInLocalDir(String ftpFileName) {
        try {
            //获取本地文件夹中已下载的文件名 + 昨天的文件夹(每日一个文件夹)
            String localPath = tempPath + File.separator + DateUtil.getYesterDay();
            File localFile = new File(localPath);
            if (!localFile.exists()) {
                localFile.mkdir();

            }
            List<String> localFileNames = Files.walk(Paths.get(localPath))
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());

            boolean exsit = localFileNames.contains(ftpFileName) ? true : false;
            log.info("{}在本地目录中={}", ftpFileName, exsit);
            return exsit;
        } catch (Exception e) {
            log.error("get local downloaded files fail !", e);
            return true;
        }
    }

    /**
     * 文件是否为今天的数据
     *
     * @param lastModified
     * @return true=是今天的文件 false=不是今天的文件
     */
    private boolean isLatestFile(String fileName, long lastModified) {
        boolean isTodayFile = DateUtil.isToday(lastModified);
        log.info("{}为今天的数据={}", fileName, isTodayFile);
        return isTodayFile;
    }
}
