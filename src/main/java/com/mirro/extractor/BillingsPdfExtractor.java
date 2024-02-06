package com.mirro.extractor;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class BillingsPdfExtractor {

  //Extracts PDF content.
  public String extractTextFromPdf(File file) throws IOException {
    try (PDDocument document = Loader.loadPDF(file)) {
      PDFTextStripper textStripper = new PDFTextStripper();
      return textStripper.getText(document);
    }
  }
}
