package com.example.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileProvider {

    @RequestMapping(path = "/text", method = RequestMethod.GET)
    public ResponseEntity<String> text(@RequestParam(required = false) String name) {
        if (name == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Hello " + name);
    }

    @RequestMapping(path = "/pdf", method = RequestMethod.GET)
    public ResponseEntity<Resource> pdf(@RequestParam(required = false) String name) throws IOException {
        if (name == null) {
            return ResponseEntity.notFound().build();
        }

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA_BOLD;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Hello " + name);
        contentStream.endText();

        // Make sure that the content stream is closed:
        contentStream.close();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
