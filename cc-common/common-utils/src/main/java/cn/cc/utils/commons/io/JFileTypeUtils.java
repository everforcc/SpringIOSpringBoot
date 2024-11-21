/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-20 18:01
 * Copyright
 */

package cn.cc.utils.commons.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 根据字节判断文件类型
 * 原分类在 MyUtils FileTypeUtils
 */
@Slf4j
public class JFileTypeUtils {

    private static final Map<String, String> file_type_map = new HashMap<>();
    private static final Map<String, String> type_file_map = new HashMap<>();

    /* 为了取出文件真实信息需要4位 */
    private static final int file_min_length = 4;


    static {
        file_type_map.put("jpg", "FFD8FF"); //JPEG (jpg)
        file_type_map.put("png", "89504E47");  //PNG (png)
        file_type_map.put("gif", "47494638");  //GIF (gif)
        file_type_map.put("tif", "49492A00");  //TIFF (tif)
        file_type_map.put("bmp", "424D"); //Windows Bitmap (bmp)
        file_type_map.put("dwg", "41433130"); //CAD (dwg)
        file_type_map.put("html", "68746D6C3E");  //HTML (html)
        file_type_map.put("rtf", "7B5C727466");  //Rich Text Format (rtf)
        file_type_map.put("xml", "3C3F786D6C");
        file_type_map.put("zip", "504B0304");
        file_type_map.put("rar", "52617221");
        file_type_map.put("psd", "38425053");  //Photoshop (psd)
        file_type_map.put("eml", "44656C69766572792D646174653A");  //Email [thorough only] (eml)
        file_type_map.put("dbx", "CFAD12FEC5FD746F");  //Outlook Express (dbx)
        file_type_map.put("pst", "2142444E");  //Outlook (pst)
        file_type_map.put("xls", "D0CF11E0");  //MS Word
        file_type_map.put("doc", "D0CF11E0");  //MS Excel 注意：word 和 excel的文件头一样
        file_type_map.put("mdb", "5374616E64617264204A");  //MS Access (mdb)
        file_type_map.put("wpd", "FF575043"); //WordPerfect (wpd)
        file_type_map.put("eps", "252150532D41646F6265");
        file_type_map.put("ps", "252150532D41646F6265");
        file_type_map.put("pdf", "255044462D312E");  //Adobe Acrobat (pdf)
        file_type_map.put("qdf", "AC9EBD8F");  //Quicken (qdf)
        file_type_map.put("pwl", "E3828596");  //Windows Password (pwl)
        file_type_map.put("wav", "57415645");  //Wave (wav)
        file_type_map.put("avi", "41564920");
        file_type_map.put("ram", "2E7261FD");  //Real Audio (ram)
        file_type_map.put("rm", "2E524D46");  //Real Media (rm)
        file_type_map.put("mpg", "000001BA");  //
        file_type_map.put("mov", "6D6F6F76");  //Quicktime (mov)
        file_type_map.put("asf", "3026B2758E66CF11"); //Windows Media (asf)
        file_type_map.put("mid", "4D546864");  //MIDI (mid)
    }

    static {
        type_file_map.put("38425053", "psd");
        type_file_map.put("252150532D41646F6265", "ps");
        type_file_map.put("424D", "bmp");
        type_file_map.put("E3828596", "pwl");
        type_file_map.put("47494638", "gif");
        type_file_map.put("44656C69766572792D646174653A", "eml");
        type_file_map.put("4D546864", "mid");
        type_file_map.put("49492A00", "tif");
        type_file_map.put("2142444E", "pst");
        type_file_map.put("41564920", "avi");
        type_file_map.put("6D6F6F76", "mov");
        type_file_map.put("3C3F786D6C", "xml");
        type_file_map.put("68746D6C3E", "html");
        type_file_map.put("2E7261FD", "ram");
        type_file_map.put("FFD8FFE0", "jpg");
        type_file_map.put("504B0304", "zip");
        type_file_map.put("52617221", "rar");
        type_file_map.put("7B5C727466", "rtf");
        type_file_map.put("000001BA", "mpg");
        type_file_map.put("89504E47", "png");
        //type_file_map.put("252150532D41646F6265", "eps");
        type_file_map.put("5374616E64617264204A", "mdb");
        type_file_map.put("FF575043", "wpd");
        type_file_map.put("57415645", "wav");
        type_file_map.put("AC9EBD8F", "qdf");
        type_file_map.put("255044462D312E", "pdf");
        type_file_map.put("41433130", "dwg");
        type_file_map.put("3026B2758E66CF11", "asf");
        //type_file_map.put("D0CF11E0", "doc");
        type_file_map.put("CFAD12FEC5FD746F", "dbx");
        type_file_map.put("2E524D46", "rm");
        type_file_map.put("D0CF11E0", "xls");
    }

    public static String getFileRealType(byte[] fileBytes) {
        if (Objects.isNull(fileBytes) || fileBytes.length < file_min_length) {
            return null;
        }

        byte[] fileTypes = new byte[4];
        // 取出头信息
        System.arraycopy(fileBytes, 0, fileTypes, 0, file_min_length);

        String fileType = Hex.encodeHexString(fileTypes, false);

        if (type_file_map.containsKey(fileType)) {
            String v = type_file_map.get(fileType);
            return v;
        }
        return null;
    }

    /**
     * 给map交换 k,v
     */
    private static void changeMap() {
        for (Map.Entry<String, String> entry : file_type_map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("type_file_map.put(\"" + value + "\", \"" + key + "\");");
        }
    }

    public static void main(String[] args) {
        String path = "E:\\filesystem\\project\\Bilibili_craw\\up\\[480974636]_[庚哥有点野]\\短视频\\598595778\\";
        String fileName_1 = "[av598595778]_[7月19深圳外卖小哥发生车祸遭遇二次碾压行车记录仪录像]_[7月19深圳外卖小哥发生车祸遭遇二次碾压行车记录仪录像].flv";
        String fileName_2 = "[av598595778]_[cover].jpg";
        String fileName_3 = "";
        byte[] bytes = RFileUtils.readFileToBytes(path + fileName_2);

        String fileType = getFileRealType(bytes);
        System.out.println("文件类型是 【{}】" + fileType);
    }

}

