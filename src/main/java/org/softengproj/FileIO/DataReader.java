package org.softengproj.FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataReader {
    Connection connection = null;
    Statement statement = null;
    private static final Logger logger = LogManager.getLogger(DataReader.class);

    /**
     * Constructor for the DataReader class.
     * Creates a connection to the campaign_data database.
     */
    public DataReader() {
        createDatabase();
        try {
            this.connection = DriverManager
                    .getConnection("jdbc:sqlite:..\\campaign_data.db");
            this.connection.setAutoCommit(false);
            this.statement = connection.createStatement();
            this.statement.setQueryTimeout(30);
        } catch (SQLException e) {
            logger.error("Something went wrong when initializing the connection to the database");
        }
    }

    /**
     * Closes the connection to the campaign_data database.
     * Use when connection is no longer required.
     */
    public void closeConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("Something went wrong when closing the connection");
        }
    }

    /**
     * Adds a new table to the database by reading a csv file.
     * 
     * TODO: make it invulnerable to sql injections
     * 
     * @param path the path to the .csv file
     */
    public void addTableFromCSV(String path) { // check the datatypes
        String name = extractName(path);
        String columnNames = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            columnNames = reader.readLine();
        } catch (Exception e) {
            logger.error("Something went wrong when reading the csv file");
        }
        // create the table and read in the data
        String[] columns = columnNames.split(",");
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = null;
            switch (columns.length) {
                case 3: // clicks
                    this.modify("CREATE TABLE IF NOT EXISTS " + name + " (\n" + //
                            "" + columns[0] + " TEXT,\n" + //
                            "" + columns[1] + " TEXT,\n" + //
                            "" + columns[2] + " REAL\n" + //
                            ");");
                    PreparedStatement stmt = this.connection
                            .prepareStatement("INSERT INTO " + name + " VALUES (?, ?, ?);");
                    int batchSize = 10000; // Experiment with different batch sizes
                    int count = 0;
                    this.modify("BEGIN TRANSACTION;");
                    while ((line = reader.readLine()) != null) {
                        String[] values = line.split(",");
                        stmt.setString(1, values[0]);
                        stmt.setString(2, values[1]);
                        stmt.setString(3, values[2]);
                        stmt.addBatch();

                        if (++count % batchSize == 0) {
                            stmt.executeBatch();
                        }
                    }
                    stmt.executeBatch();
                    this.modify("COMMIT;");
                    ResultSet test = query("select * from " + name + " limit 1;");
                    test.next();
                    System.out.println(test.getString(1));
                    break;
                case 5: // user_activity
                    System.out.println("the input contains user activity data.");
                    this.modify("CREATE TABLE IF NOT EXISTS " + name + " (\n" + //
                            "" + columns[0] + " TEXT,\n" + //
                            "" + columns[1] + " TEXT,\n" + //
                            "" + columns[2] + " TEXT,\n" + //
                            "" + columns[3] + " INTEGER,\n" + //
                            "" + columns[4] + " TEXT\n" + //
                            ");");
                    stmt = this.connection
                            .prepareStatement("INSERT INTO " + name + " VALUES (?, ?, ?, ?, ?);");
                    batchSize = 10000; // Experiment with different batch sizes
                    count = 0;
                    this.modify("BEGIN TRANSACTION;");
                    while ((line = reader.readLine()) != null) {
                        String[] values = line.split(",");
                        stmt.setString(1, values[0]);
                        stmt.setString(2, values[1]);
                        stmt.setString(3, values[2]);
                        stmt.setString(4, values[3]);
                        stmt.setString(5, values[4]);
                        stmt.addBatch();

                        if (++count % batchSize == 0) {
                            stmt.executeBatch();
                        }
                    }
                    stmt.executeBatch();
                    this.modify("COMMIT;");
                    break;
                case 7: // impressions
                    System.out.println("the input contains impression data.");
                    this.modify("CREATE TABLE IF NOT EXISTS " + name + " (\n" + //
                            "" + columns[0] + " TEXT,\n" + //
                            "" + columns[1] + " TEXT,\n" + //
                            "" + columns[2] + " TEXT,\n" + //
                            "" + columns[3] + " INTEGER,\n" + //
                            "" + columns[4] + " TEXT,\n" + //
                            "" + columns[5] + " TEXT,\n" + //
                            "" + columns[6] + " REAL\n" + //
                            ");");
                    stmt = this.connection
                            .prepareStatement("INSERT INTO " + name + " VALUES (?, ?, ?, ?, ?, ?, ?);");
                    batchSize = 10000; // Experiment with different batch sizes
                    count = 0;
                    this.modify("BEGIN TRANSACTION;");
                    while ((line = reader.readLine()) != null) {
                        String[] values = line.split(",");
                        stmt.setString(1, values[0]);
                        stmt.setString(2, values[1]);
                        stmt.setString(3, values[2]);
                        stmt.setString(4, values[3]);
                        stmt.setString(5, values[4]);
                        stmt.setString(6, values[5]);
                        stmt.setString(7, values[6]);
                        stmt.addBatch();

                        if (++count % batchSize == 0) {
                            stmt.executeBatch();
                        }
                    }
                    stmt.executeBatch();
                    this.modify("COMMIT;");
                    break;
                default: // invalid input
                    logger.error(name + ".csv isn't in the required format.");
                    break;
            }
        } catch (Exception e) {
            logger.error("Something went wrong when adding a table from a csv file");
        }
    }

    /**
     * Replaces the current campaign with a new one.
     * This removes all current tables!
     * 
     * @param clickPath        path to click .csv
     * @param impressionPath   path to impression .csv
     * @param userActivityPath path to server .csv
     */
    public void addCampaign(String clickPath, String impressionPath, String userActivityPath) {
        this.purge();
        this.addTableFromCSV(clickPath);
        this.addTableFromCSV(impressionPath);
        this.addTableFromCSV(userActivityPath);
        logger.info("Finished adding campaign");
    }

    /**
     * Modifies the database in some way.
     * 
     * @param code non-query sql code
     */
    public void modify(String code) {
        try {
            this.statement.executeUpdate(code);
        } catch (SQLException e) {
            logger.error("Something went wrong when trying to modify the database");
        }
    }

    /**
     * Queries the database
     * Cannot modify the database
     * 
     * @param code the sql responsible for the query
     */
    public ResultSet query(String code) {
        ResultSet returnSet = null;
        try {
            returnSet = this.statement.executeQuery(code);
        } catch (SQLException e) {
            logger.error("Something went wrong when querying the database");
        }
        return returnSet;
    }

    /**
     * Removes all tables from the database.
     */
    public void purge() {
        try {
            ResultSet tables = this.connection.getMetaData().getTables(null, null, "%", null);
            ArrayList<String> tableNames = new ArrayList<>();
            while (tables.next()) {
                String name = tables.getString(3); // name of the table is at the 3rd position
                tableNames.add(name);
            }
            for (String table : tableNames) {
                this.modify("DROP TABLE " + table + ";");
            }
        } catch (SQLException e) {
            logger.error("Something went wrong when purging the database");
        }
    }

    /**
     * private helper function to extract the name of the file from the path.
     * 
     * @param path the path to extract the name from
     */
    private String extractName(String path) {
        String[] s = path.split("\\\\");
        String x = s[s.length - 1];
        String[] y = x.split("\\.");
        return y[0];
    }

    /**
     * Creating a database if it doesn't already exist
     */
    private void createDatabase() {
        String filePath = "../campaign_data.db";
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            logger.error("Something went wrong when creating the database file");
        }
    }
}
