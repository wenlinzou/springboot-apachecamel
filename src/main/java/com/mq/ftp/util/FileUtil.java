package com.mq.ftp.util;

import com.mq.ftp.entity.ReportFtpInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件帮助类
 *
 * @author wenlinzou
 */
@Slf4j
@Component
public class FileUtil {

    @Resource
    FtpDtoUtil ftpDtoUtil;

    public List<ReportFtpInfoModel> getDbListByFileLines(File file) {
        BufferedReader reader = null;
        List<ReportFtpInfoModel> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            int index = -1;
            while ((line = reader.readLine()) != null) {
                index++;
                if (index == 0) {
                    continue;
                }
                ReportFtpInfoModel dto = ftpDtoUtil.strConverDto(line);
                if (null != dto) {
                    list.add(dto);
                }
            }
            reader.close();

        } catch (IOException e) {
            log.error("文件读取时出现错误！");
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    log.error("流关闭异常！");
                }
            }
        }
        return list;
    }


    private List<String> readTextFile(File file, String charset) throws IOException {
        List<String> list = new ArrayList<>();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), charset);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineText;
        int index = -1;
        while ((lineText = bufferedReader.readLine()) != null) {
            index++;
            if (index == 0) {
                list.add(lineText);
            }
        }
        return list;
    }


    /**
     * 以行为单位读取文件，用于读面向行的格式化文件
     */
    private List<String> readFileByLines(File file) {
        BufferedReader reader = null;
        List<String> strList = new ArrayList<>();
        try {
            log.info("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                strList.add(tempString);
            }
            reader.close();

        } catch (IOException e) {
            log.error("文件读取时出现错误！");
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    log.error("流关闭异常！");
                }
            }
        }
        return strList;
    }

    /**
     * 追加文件
     *
     * @param fileName
     * @param content
     */
    private void appendMethod(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> batchReadFile(List<File> files, String charset) throws IOException {
        List<String> textList = new ArrayList<>();
        for (File file : files) {
            List<String> list = readTextFile(file, charset);
            textList.addAll(list);
        }
        return textList;
    }

}
