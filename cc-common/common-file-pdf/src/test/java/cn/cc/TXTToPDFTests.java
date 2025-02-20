package cn.cc;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class TXTToPDFTests {

    @Test
    public void txtToPDF(){
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Hello, PDFBox!");
                contentStream.endText();
            } catch (Exception e) {
                e.printStackTrace();
            }
            document.save("D:\\cache\\test\\pdf\\ContentPdf.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void txt2ToPDF(){
        // 通过html导出
//        String htmlContent = "<html><body><h1>Hello, World!</h1></body></html>"; // 你的HTML内容，可以是字符串或文件路径

        // 配合 velocity 动态生成模板
        String path ="D:\\code\\gitee\\SpringIOSpringBoot\\cc-common\\common-file-pdf\\src\\test\\java\\cn\\cc\\sn.html";

        File pdfFile = new File("D:\\cache\\test\\pdf\\output_itext-simkai.pdf"); // 输出PDF文件路径
        try {
            byte[] bFile = Files.readAllBytes(Paths.get(path));

            // 本地字体
            // simkai.ttf
            String fontPath = "C:\\Windows\\Fonts\\simkai.ttf";

            // 设置中文
            ConverterProperties converterProperties = new ConverterProperties();
            FontProvider fontProvider = new FontProvider();
            fontProvider.addFont(fontPath);
            converterProperties.setFontProvider(fontProvider);

            // version-5 有这个api
            // 7 没有
            HtmlConverter.convertToPdf(new ByteArrayInputStream(bFile), new FileOutputStream(pdfFile), converterProperties); // 转换并保存PDF文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
