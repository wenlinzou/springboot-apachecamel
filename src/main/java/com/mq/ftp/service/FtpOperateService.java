package com.mq.ftp.service;

import com.mq.ftp.entity.ReportFtpInfoModel;

import java.util.List;

public interface FtpOperateService {
    int process(List<ReportFtpInfoModel> txtList);
}
