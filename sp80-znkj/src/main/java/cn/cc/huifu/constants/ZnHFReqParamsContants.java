package cn.cc.huifu.constants;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 请求参数常量
 */
public class ZnHFReqParamsContants {

    public static final String REQ_SEQ_ID = new Random().nextLong() % 1000000000000000000L + 1000000000000000000L + "";

    public static final String REQ_DATE = new SimpleDateFormat("yyyyMMdd").format(new Date());

}