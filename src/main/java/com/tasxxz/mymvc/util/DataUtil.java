package com.tasxxz.mymvc.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * Created by linshudeng on 2016/11/11.
 */
public class DataUtil {
    public static final String ROOT = "D:/temp/data";
    public static final String SEPARATOR = "/";

    public static String upload(byte[] content, String fileName, String group) throws IOException {
        String path = null;
        if (StringUtils.isBlank(group)) {
            path = ROOT + SEPARATOR + System.currentTimeMillis();
        } else {
            path = ROOT + SEPARATOR + group + SEPARATOR + System.currentTimeMillis();
        }

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        BufferedOutputStream out = null;
        try {
            File file = new File(path + SEPARATOR + fileName);
            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(content);
            return file.getPath();
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 从文件路径中截取文件名
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (filePath == null) {
            return filePath;
        }
        int index = filePath.lastIndexOf("/");
        if (index < 0) {
            index = filePath.lastIndexOf("\\");
        }
        if (index < 0) {
            return filePath;
        }

        return filePath.substring(index + 1);
    }

}
