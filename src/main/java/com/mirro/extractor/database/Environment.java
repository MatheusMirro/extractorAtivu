package com.mirro.extractor.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import org.yaml.snakeyaml.Yaml;

public class Environment {

  public static final String KEY_DATABASE_ACTIVE = "database.active";
  public static final String KEY_DATABASE_FILE_EXTENSION =
    "database.fileExtension";
  public static final String KEY_JDBC_URL = "javax.persistence.jdbc.url";
  public static final String KEY_JDBC_USER = "javax.persistence.jdbc.user";
  public static final String KEY_JDBC_PASSWORD =
    "javax.persistence.jdbc.password";
  public static final String KEY_JDBC_TEMPLATE = "database.jdbcTemplate";

  public static boolean isH2Active() {
    return getKeyDatabaseActive().equalsIgnoreCase("h2");
  }

  public static boolean isFirebirdActive() {
    return getKeyDatabaseActive().equalsIgnoreCase("firebird");
  }

  private static String getKeyDatabaseActive() {
    return System.getProperty(KEY_DATABASE_ACTIVE);
  }

  public static void prepare(String soutputDir) throws IOException {
    try (
      InputStream inputStream = Environment.class.getResourceAsStream(
          "/config.yaml"
        )
    ) {
      assert inputStream != null;
      Yaml yaml = new Yaml();
      Map<String, Object> yamlMaps = yaml.load(inputStream);

      @SuppressWarnings("unchecked")
      Map<String, Object> databaseMaps = (Map<String, Object>) yamlMaps.get(
        "database"
      );
      String active = (String) databaseMaps.get("active");
      @SuppressWarnings("unchecked")
      Map<String, Object> profileMaps = (Map<String, Object>) databaseMaps.get(
        "profile"
      );
      @SuppressWarnings("unchecked")
      Map<String, Object> propertiesMaps = (Map<String, Object>) profileMaps.get(
        active
      );

      System.setProperty(KEY_JDBC_USER, (String) propertiesMaps.get("user"));

      if (Objects.nonNull(propertiesMaps.get("password"))) {
        System.setProperty(
          KEY_JDBC_PASSWORD,
          (String) propertiesMaps.get("password")
        );
      } else if (Objects.isNull(propertiesMaps.get("password"))) {
        System.setProperty(KEY_JDBC_PASSWORD, "");
      }

      System.setProperty(
        "javax.persistence.jdbc.driver",
        (String) propertiesMaps.get("driver")
      );
      System.setProperty(
        "hibernate.dialect",
        (String) propertiesMaps.get("dialect")
      );
      System.setProperty("hibernate.hbm2ddl.auto", "update");
      System.setProperty("hibernate.show_sql", "false");

      System.setProperty(KEY_DATABASE_ACTIVE, active);
      System.setProperty(
        KEY_JDBC_TEMPLATE,
        (String) propertiesMaps.get("jdbcTemplate")
      );
      System.setProperty(
        KEY_DATABASE_FILE_EXTENSION,
        (String) propertiesMaps.get("fileExtension")
      );
    }
  }
}
