package com.mq.ftp.dao;


import com.mq.ftp.entity.ReportFtpInfoModel;

import java.util.List;

public interface ReportFtpInfoDao {

    int insertByBatch(List<ReportFtpInfoModel> list);
}
