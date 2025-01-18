package cn.cc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFToPic {

    public static void transToPic(String pdfPath, String pngPath) throws IOException {
        File file = new File(pdfPath);
        PDDocument doc = PDDocument.load(file);
        PDFRenderer reader = new PDFRenderer(doc);
        int pageNo = doc.getNumberOfPages();

        for (int i = 0; i < pageNo; i++) {
            //1.将pdf转为图片
            BufferedImage bfi = reader.renderImageWithDPI(i, 120);
            //2. 文件路径
            File file2 = new File(pngPath + i + ".png");
            ImageIO.write(bfi, "PNG", file2);
        }
    }

}
