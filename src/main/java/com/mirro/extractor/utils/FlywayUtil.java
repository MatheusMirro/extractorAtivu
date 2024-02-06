package com.mirro.extractor.utils;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.jdbc.DriverDataSource;

/**
 * Utility class for managing database migrations using Flyway.
 * Flyway is an open-source database migration tool, which this utility class
 * wraps for ease of use.
 * This class provides static methods to facilitate database migrations,
 * ensuring database schema consistency across different environments.
 * <p>
 * The class includes a method to execute database migrations using Flyway
 * configurations. This method allows for setting up database connections,
 * specifying migration script locations, and handling existing databases that
 * have not been managed by Flyway before.
 * <p>
 * The class is designed with a private constructor to prevent instantiation, as
 * it only contains static utility methods.
 * <p>
 * Usage Example:
 * FlywayUtil.execute("jdbc:database_url", "databaseUsername",
 * "databasePassword");
 * <p>
 * Note: Ensure Flyway and necessary database dependencies are included in the
 * project.
 * <p>
 * For more information on Flyway, visit <a href="https://flywaydb.org">Flyway's
 * official website</a>.
 */
public class FlywayUtil {

    private FlywayUtil() {
    }

    /**
     * Executes the database migration using Flyway.
     * <p>
     * This method initializes and configures a Flyway instance with the specified
     * database connection details.
     * It sets up the data source using the provided JDBC URL, database username,
     * and password.
     * The migration scripts are loaded from two locations: within the classpath and
     * the file system, specifically from 'db/migration' directory.
     * The 'baselineOnMigrate' configuration is set to true, which is useful for
     * managing existing databases that haven't been managed by Flyway before.
     * <p>
     * After configuration, it triggers the migration process, which will apply any
     * pending migrations to the database.
     * <p>
     * Note: This method assumes that all necessary Flyway and database-related
     * dependencies are correctly configured and available.
     *
     * @param databaseJdbcUrl  The JDBC URL for the database connection.
     * @param databaseUsername The database username.
     * @param databasePassword The password for the database username.
     */
    public static void execute(String databaseJdbcUrl, String databaseUsername, String databasePassword) {
        // // Version 9.22.3
        // Flyway flyway = Flyway.configure() //
        // .dataSource(databaseJdbcUrl, databaseUsername, databasePassword) //
        // .locations("classpath:db/migration", "filesystem:db/migration") //
        // .baselineOnMigrate(true) //
        // .load();
        // flyway.migrate();

        // Version 4.2.0
        DriverDataSource dataSource = new DriverDataSource(Thread.currentThread().getContextClassLoader(), null, //
                databaseJdbcUrl, databaseUsername, databasePassword);
        Flyway flywayData = new Flyway();
        flywayData.setLocations("classpath:db/migration", "filesystem:db/migration");
        flywayData.setDataSource(dataSource);
        flywayData.migrate();
    }

}
