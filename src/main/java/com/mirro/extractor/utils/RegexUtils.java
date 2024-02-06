package com.mirro.extractor.utils;

import java.util.regex.Pattern;

public class RegexUtils {

  // Expressão regular para correspondência de informações pessoais
  public static Pattern PERSONAL_INFO_PATTERN = Pattern.compile(
    "Nome: (.+?)\\s+Idade: (\\d+)\\s+Endereço: (.+?)\\s+Cidade: (.+?)\\s+CEP: (\\d{5}-\\d{3})\\s+Email: (.+?)\\s+Telefone: \\((\\d{2})\\) (\\d{4,5}-\\d{4})",
    Pattern.DOTALL
  );
}
