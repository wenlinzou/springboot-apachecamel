package com.mq.ftp.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ftp上报数据
 *
 * @author wenlinzou
 */
@Data
public class ReportFtpInfoModel implements Serializable {

    private String id;

    private String name;

    private String content;

    private Date createTime;

    private Date updateTime;
}
