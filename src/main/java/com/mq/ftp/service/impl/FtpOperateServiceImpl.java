package com.mq.ftp.service.impl;

import com.mq.ftp.dao.ReportFtpInfoDao;
import com.mq.ftp.entity.ReportFtpInfoModel;
import com.mq.ftp.service.FtpOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 将文件数据批量存入数据库表
 *
 * @author wenlinzou
 */
@Slf4j
@Service
public class FtpOperateServiceImpl implements FtpOperateService {

    @Value("${db.insert.max.size}")
    private int maxSize;

    @Resource
    private ReportFtpInfoDao reportFtpInfoDao;

    @Override
    public int process(List<ReportFtpInfoModel> txtList) {
        int saveDbSize = 0;
        if (!CollectionUtils.isEmpty(txtList)) {
            int txtTotalSize = txtList.size();
            if (txtTotalSize <= maxSize) {
                saveDbSize = reportFtpInfoDao.insertByBatch(txtList);
            } else {
                int loop = txtTotalSize / maxSize;
                List<ReportFtpInfoModel> subList = new ArrayList<>(maxSize);
                for (int i = 0; i < loop; i++) {
                    subList = txtList.subList(i * maxSize, (i + 1) * maxSize);
                    saveDbSize += reportFtpInfoDao.insertByBatch(subList);
                }
                subList = txtList.subList(loop * maxSize, txtTotalSize);
                if (!CollectionUtils.isEmpty(subList)) {
                    saveDbSize += reportFtpInfoDao.insertByBatch(subList);
                }
            }

        }
        log.info("save file to db size={}", saveDbSize);
        return saveDbSize;
    }
}
