package com.mirro.extractor;

import java.io.FileWriter;
import java.io.IOException;

public class BillingsTxtExtractor {

  //Transform the PDF content in a txt file.
  public void writeTextFile(String text, String string) throws IOException {
    try (FileWriter fileWriter = new FileWriter(string)) {
      fileWriter.write(text);
      System.out.println("Conteúdo foi gravado em: " + string);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
