package com.tasxxz.mymvc.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lsd on 2017/7/4.
 */

public class DataUtilTest {

    @Test
    public void testGetFileName() {
        String filePath = "/test/aaa.txt";
        Assert.assertEquals("error", "aaa.txt", DataUtil.getFileName(filePath));
    }
}
