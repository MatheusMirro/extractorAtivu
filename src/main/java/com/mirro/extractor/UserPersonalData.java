package com.mirro.extractor;

import com.mirro.extractor.database.Database;
import com.mirro.extractor.database.Environment;
import com.mirro.extractor.models.PersonalData;
import com.mirro.extractor.utils.RegexUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserPersonalData {

  private final String outFilePath;

  public UserPersonalData(String outFilePath) {
    this.outFilePath = outFilePath;
  }

  public void parseUserData() {
    // Carregar as configurações do ambiente
    try {
      Environment.prepare("soutputDir");
    } catch (Exception e) {
      e.printStackTrace();
    }

    try (BufferedReader br = new BufferedReader(new FileReader(outFilePath))) {
      String line;
      int userCount = 1;

      System.out.println("Iniciando o parsing dos dados dos usuários...");
      while ((line = br.readLine()) != null) {
        if (line.trim().startsWith("Nome:")) {
          StringBuilder userData = new StringBuilder();
          userData.append(line).append("\n"); // Adiciona a primeira linha

          // Lê as próximas linhas até encontrar uma linha em branco ou um separador
          while (
            (line = br.readLine()) != null &&
            !line.trim().isEmpty() &&
            !line.trim().startsWith("---")
          ) {
            userData.append(line).append("\n");
          }

          // Processa os dados do usuário
          printUserData(userData.toString(), userCount++, userCount);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void printUserData(String user, int userCount, int totalUsers) {
    Pattern pattern = RegexUtils.PERSONAL_INFO_PATTERN;

    Matcher matcher = pattern.matcher(user);
    System.out.println("-----------------------------------------------");

    // Verifica se houve uma correspondência
    if (matcher.find()) {
      PersonalData userData = new PersonalData();

      userData.setName(matcher.group(1));
      userData.setAge(Integer.parseInt(matcher.group(2)));
      userData.setAddress(matcher.group(3));
      userData.setCity(matcher.group(4));
      userData.setZipCode(matcher.group(5));
      userData.setEmail(matcher.group(6));
      userData.setPhone("(" + matcher.group(7) + ") " + matcher.group(8));

      // Exibe a mensagem de progresso
      float progress = ((float) userCount / totalUsers) * 100;
      System.out.println("Progresso: " + String.format("%.2f", progress) + "%");

      // Cria uma instância do Database e insere os dados
      Database database = new Database();

      try {
        database.prepareDatabase(userData);
        database.persistUser(userData);

        System.out.println("Dados inseridos com sucesso!");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Não foi possível encontrar os dados do usuário.");
    }
  }
}
