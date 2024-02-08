package com.mirro.extractor;

import com.mirro.extractor.database.Database;
import com.mirro.extractor.database.Environment;
import com.mirro.extractor.models.PersonalData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.TypedQuery;

public class BillingsExporter extends Database {

  public void writeCsv(List<List<String>> data, File fileCsv) {
    try {
      Environment.prepare("soutputDir");
    } catch (Exception e) {
      e.printStackTrace();
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileCsv))) {
      // Adicionar títulos
      List<String> titles = Arrays.asList(
        "ID",
        "NOME",
        "IDADE",
        "ENDEREÇO",
        "CIDADE",
        "CEP",
        "E-MAIL",
        "TELEFONE"
      );
      writer.write(String.join(",", titles));
      writer.newLine();

      // Adicionar dados dos usuários
      for (List<String> row : data) {
        StringBuilder csvRow = new StringBuilder();
        for (int i = 0; i < row.size(); i++) {
          // Verifica se o conteúdo da coluna contém uma vírgula
          if (row.get(i).contains(",")) {
            csvRow.append("\"").append(row.get(i)).append("\"");
          } else {
            csvRow.append(row.get(i));
          }
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

  public List<List<String>> generateData() {
    List<List<String>> data = new ArrayList<>();
    try {
      openDatabase();

      TypedQuery<PersonalData> query = entityManager.createQuery(
        "SELECT p FROM PersonalData p",
        PersonalData.class
      );
      List<PersonalData> personalDataList = query.getResultList();

      for (PersonalData personalData : personalDataList) {
        List<String> rowData = Arrays.asList(
          personalData.getId().toString(),
          personalData.getName(),
          String.valueOf(personalData.getAge()),
          personalData.getAddress(),
          personalData.getCity(),
          personalData.getZipCode(),
          personalData.getEmail(),
          personalData.getPhone()
        );
        data.add(rowData);
      }
    } finally {
      closeDatabase();
    }
    return data;
  }
}
