package cn.cc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * pdf转txt
 */
@Slf4j
public class PDFToTXTTests {

    @Test
    public void transToTXT() {
        String pdfFilePath = "D:\\cache\\BaiduSyncdisk\\sync\\consume\\2024\\12\\发票\\T3出行电子发票\\T3出行行程单-12-13.pdf"; // 替换为你的PDF文件路径
        String result = PDFToTXT.pdfToTXT(pdfFilePath);
        log.info("result: \r\n{}", result);
    }

}
