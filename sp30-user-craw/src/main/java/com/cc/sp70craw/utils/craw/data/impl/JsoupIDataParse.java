package com.cc.sp70craw.utils.craw.data.impl;

import com.cc.sp70craw.utils.craw.data.IDataParse;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import us.codecraft.xsoup.Xsoup;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsoupIDataParse implements IDataParse {

    @Override
    public String parseJsonStr(String json, String pattern) {
        // XSoup
        return null;
    }

    @Override
    public String parseHTMLStr(String html, String pattern) {
        return Xsoup.compile(pattern).evaluate(Jsoup.parse(html)).get();
    }

    @Override
    public List<String> parseJsonList(String json, String pattern) {
        // XSoup
        return null;
    }

    @Override
    public List<String> parseHTMLList(String html, String pattern) {
        return Xsoup.compile(pattern).evaluate(Jsoup.parse(html)).list();
    }

}
