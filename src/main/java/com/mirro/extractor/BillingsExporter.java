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
      System.out.println("Dados escritos no CSV com sucesso!");
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

        // Print data to console
        System.out.println("ID: " + personalData.getId());
        System.out.println("Nome: " + personalData.getName());
        System.out.println("Idade: " + personalData.getAge());
        System.out.println("Endereço: " + personalData.getAddress());
        System.out.println("Cidade: " + personalData.getCity());
        System.out.println("CEP: " + personalData.getZipCode());
        System.out.println("E-mail: " + personalData.getEmail());
        System.out.println("Telefone: " + personalData.getPhone());
        System.out.println("----------------------------------------");
      }
    } finally {
      closeDatabase();
    }
    return data;
  }
}
