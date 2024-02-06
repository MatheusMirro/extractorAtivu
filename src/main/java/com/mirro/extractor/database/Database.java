package com.mirro.extractor.database;

import com.mirro.extractor.models.PersonalData;
import com.mirro.extractor.utils.FlywayUtil;
import java.util.Objects;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Database {

  private EntityManagerFactory entityManagerFactory;
  protected EntityManager entityManager;

  public void prepareDatabase(PersonalData user) throws Exception {
    if (System.getProperties().containsKey(Environment.KEY_JDBC_URL)) {
      System.clearProperty(Environment.KEY_JDBC_URL);
    }

    String jdbcTemplate = System.getProperty(Environment.KEY_JDBC_TEMPLATE);

    if (jdbcTemplate != null) {
      System.setProperty(Environment.KEY_JDBC_URL, jdbcTemplate);
    } else {
      System.out.println("JDBC template is null " + jdbcTemplate);
    }

    if (Environment.isH2Active()) {
      FlywayUtil.execute(
        System.getProperty(Environment.KEY_JDBC_URL),
        System.getProperty(Environment.KEY_JDBC_USER),
        System.getProperty(Environment.KEY_JDBC_PASSWORD)
      );
    } else if (Environment.isFirebirdActive()) {
      initializeFirebirdDatabase(user);
    }
  }

  /**
   * Persistir dados pessoais no banco de dados.
   *
   * @param user Os dados do usuário a serem persistidos.
   */
  public void persistUser(PersonalData user) {
    EntityTransaction transaction = null;

    try {
      openDatabase();
      transaction = entityManager.getTransaction();
      transaction.begin();

      // Alteração aqui: Utilizando merge ao invés de persist
      entityManager.merge(user);

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      closeDatabase();
    }
  }

  /**
   * Estabelecer conexão.
   */
  protected void openDatabase() {
    closeDatabase();

    Properties config = new Properties();
    config.setProperty(
      Environment.KEY_JDBC_URL,
      System.getProperty(Environment.KEY_JDBC_URL)
    );
    config.setProperty(
      Environment.KEY_JDBC_USER,
      System.getProperty(Environment.KEY_JDBC_USER)
    );
    config.setProperty(
      Environment.KEY_JDBC_PASSWORD,
      System.getProperty(Environment.KEY_JDBC_PASSWORD)
    );

    entityManagerFactory =
      Persistence.createEntityManagerFactory("myPU", config);
    entityManager = entityManagerFactory.createEntityManager();
  }

  /**
   * Fechar conexão.
   */
  protected void closeDatabase() {
    if (Objects.nonNull(entityManager)) {
      entityManager.close();
    }

    if (Objects.nonNull(entityManagerFactory)) {
      entityManagerFactory.close();
    }
  }

  private void initializeFirebirdDatabase(PersonalData user) {
    // Implemente a inicialização do banco de dados Firebird aqui
  }
}
