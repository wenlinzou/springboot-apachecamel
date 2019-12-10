package com.mq.ftp.util;

import com.mq.ftp.dto.ReportFtpDto;
import com.mq.ftp.entity.ReportFtpInfoModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 字符串转dto
 *
 * @author wenlinzou
 */
@Component
public class FtpDtoUtil {

    public ReportFtpInfoModel strConverDto(String line) {
        if (StringUtils.isEmpty(line)) {
            return null;
        }
        // |和$需要转义
        String[] arrs = line.split("\\|\\$");
        if (arrs.length > 0) {
            ReportFtpDto dto = new ReportFtpDto(
                    arrs[0], arrs[1]
            );
            ReportFtpInfoModel ftpInfoModel = new ReportFtpInfoModel();
            BeanUtils.copyProperties(dto, ftpInfoModel);
            ftpInfoModel.setId(UUID.randomUUID().toString().replace("-", ""));
            return ftpInfoModel;
        }
        return null;
    }
}
