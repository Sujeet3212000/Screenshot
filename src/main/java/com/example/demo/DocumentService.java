package com.example.demo;


import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
public class DocumentService {
    private XWPFDocument document;

    public DocumentService() {
        document = new XWPFDocument();
    }

    public void addScreenshotToDocument(File screenshotFile, String comment) {
        try {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            if (comment != null && !comment.isEmpty()) {
                run.setText(comment);
                run.addBreak();
            }

            FileInputStream inputStream = new FileInputStream(screenshotFile);
            run.addPicture(inputStream, Document.PICTURE_TYPE_PNG, screenshotFile.getName(), Units.toEMU(400), Units.toEMU(300));
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDocument(String outputPath) {
        try (FileOutputStream out = new FileOutputStream(outputPath)) {
            document.write(out);
            System.out.println("Document saved to " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
