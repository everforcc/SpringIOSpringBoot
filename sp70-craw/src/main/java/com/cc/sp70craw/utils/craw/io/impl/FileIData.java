package com.cc.sp70craw.utils.craw.io.impl;

import com.cc.sp70craw.utils.craw.io.IData;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileIData implements IData {
    @Override
    public boolean save(String path, String content) {
        try {
            FileUtils.writeStringToFile(new File("E:/filesystem/craw/文本/www.3diyibanzhu.xyz/" + path),content, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }
}
