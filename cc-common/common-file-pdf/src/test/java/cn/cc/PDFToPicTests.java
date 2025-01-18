package cn.cc;


import org.junit.Test;

import java.io.IOException;

/**
 * pdf转图片
 */
public class PDFToPicTests {

    @Test
    public void transToPic() {
        String pdfPath = "D:\\cache\\test\\pdf\\111.pdf";
        String pngPath = "D:\\cache\\test\\pdf\\pdf2png";
        try {
            PDFToPic.transToPic(pdfPath, pngPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
