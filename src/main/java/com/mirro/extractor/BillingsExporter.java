package com.mirro.extractor;

import com.mirro.extractor.database.Environment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BillingsExporter {

  public static void writeCsv(List<List<String>> data, File fileCsv) {
    try {
      Environment.prepare("soutputDir");
    } catch (Exception e) {
      e.printStackTrace();
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileCsv))) {
      for (List<String> row : data) {
        StringBuilder csvRow = new StringBuilder();
        for (int i = 0; i < row.size(); i++) {
          csvRow.append(row.get(i));
          if (i < row.size() - 1) {
            csvRow.append(",");
          }
        }
        writer.write(csvRow.toString());
        writer.newLine();
      }
      System.out.println("CSV file written successfully!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<List<String>> generateData() {
    List<List<String>> data = new ArrayList<>();
    data.add(Arrays.asList("João", "30", "Rua A"));
    data.add(Arrays.asList("Maria", "25", "Rua B"));
    return data;
  }
}
