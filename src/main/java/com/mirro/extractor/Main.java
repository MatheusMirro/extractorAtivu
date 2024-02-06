package com.mirro.extractor;

import java.io.File;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    File file = new File("files/in/pessoas.pdf");
    File outFilePath = new File("files/out/out-pessoas.txt");
    File outCsvFilePath = new File("files/out/csv/dados.csv");

    try {
      BillingsPdfExtractor pdfExtractor = new BillingsPdfExtractor();
      String extractedText = pdfExtractor.extractTextFromPdf(file);

      BillingsTxtExtractor txtExtractor = new BillingsTxtExtractor();
      txtExtractor.writeTextFile(extractedText, outFilePath.getAbsolutePath());

      UserPersonalData userInfoParser = new UserPersonalData(
        outFilePath.getAbsolutePath()
      );
      userInfoParser.parseUserData();

      List<List<String>> data = BillingsExporter.generateData();
      BillingsExporter.writeCsv(data, outCsvFilePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
