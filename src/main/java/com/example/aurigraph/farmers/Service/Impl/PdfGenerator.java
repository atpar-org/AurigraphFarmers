package com.example.aurigraph.farmers.Service.Impl;
import com.example.aurigraph.farmers.DTO.AadhaarDetails;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public static MultipartFile saveAsPdf(String name, String xmlContent, String fileName) {
        try {
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("e-Aadhaar Details"));
            document.add(new Paragraph("Name: " + name));
            document.add(new Paragraph("\nRaw XML:\n" + xmlContent));
            document.close();

            // Convert PDF bytes to MultipartFile
            byte[] pdfBytes = outputStream.toByteArray();
            return new MockMultipartFile(fileName, fileName, "application/pdf", new ByteArrayInputStream(pdfBytes));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MultipartFile saveAadhaarAsPdf(AadhaarDetails details, String fileName) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDImageXObject pdImage = LosslessFactory.createFromImage(document, details.photo);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Resize the image if needed
            float imageWidth = 200;
            float imageHeight = 250;
            float startX = (page.getMediaBox().getWidth() - imageWidth) / 2;
            float startY = (page.getMediaBox().getHeight() - imageHeight) / 2;

            contentStream.drawImage(pdImage, startX, startY, imageWidth, imageHeight);
            contentStream.close();

            document.save(outputStream);

            byte[] pdfBytes = outputStream.toByteArray();

            return new MockMultipartFile(
                    fileName,
                    fileName,
                    MediaType.APPLICATION_PDF_VALUE,
                    pdfBytes
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to create Aadhaar PDF with photo", e);
        }
    }

}
