package cn.cc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFToTXT {

    public static String pdfToTXT(String pdfFilePath) {
        String result = "";
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
//            document = PDDocument.load(new File(pdfFilePath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            result = pdfStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
