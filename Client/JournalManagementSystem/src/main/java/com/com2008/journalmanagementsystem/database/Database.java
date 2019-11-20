package com.com2008.journalmanagementsystem.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

// A database generic interface for java classes. Text and Number supported.
public class Database {
    // Database connection
    private static Connection connection = null;

    /**
     * Disconnect from the database
     * @return Success
     * @throws SQLException
     */
    public static boolean disconnect() throws SQLException {
        if (connection == null)
            return false;
        connection.close();
        return true;
    }

    /**
     * Connect to a database
     * @param url Database url
     * @param username Database username
     * @param password Database password
     * @throws SQLException
     */
    public static void connect(String url, String username, String password) throws SQLException {
        disconnect();
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Insert a new row to a table
     * @param table Table name
     * @param dataRow Data instance you want to insert
     * @throws SQLException
     */
    public static int write(String table, IDataRow dataRow) throws SQLException {
        // Init string builders
        StringBuilder columnsBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();

        // Get fields in the class & build sql elements
        Field[] fields = dataRow.getClass().getDeclaredFields();
        Boolean firstItem = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                String value = (String) field.get(dataRow);

                if (value != null) {
                    if (firstItem) 
                        firstItem = false;
                    else{
                        columnsBuilder.append(",");
                        valuesBuilder.append(",");
                    }
                    columnsBuilder.append(key);
                    if(field.getType() == String.class)
                        valuesBuilder.append("'" + value + "'");
                    else
                        valuesBuilder.append(value);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Build sql
        String sql = "INSERT INTO " + table + " (" + columnsBuilder + ") " + "VALUES (" + valuesBuilder + ");";
        // System.out.println(sql);

        // Execute sql
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }

    /**
     * Select & Read rows form a table
     * @param <T> Data type you want to read
     * @param table Table name
     * @param dataRow A data template you want to match (Null represents unknown)
     * @return A list of instance
     * @throws SQLException
     */
    public static <T extends IDataRow> List<T> read(String table, T dataRow)throws SQLException {
        // Build sql
        String sql = "SELECT * FROM " + table + " WHERE " + buildSQLCondition(dataRow);
        // System.out.println(sql);

        // Execute sql & get results
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        ResultSet resultSet = statement.executeQuery(sql);

        // Get fields in the class
        Class<? extends IDataRow> dataRowClass = dataRow.getClass();
        Field[] fields = dataRowClass.getDeclaredFields();

        // Store results into instances
        List<T> results = new ArrayList<T>();
        while (resultSet.next()) {
            try {
                T result = (T)dataRowClass.getDeclaredConstructor().newInstance();
                for(Field field : fields){
                    field.setAccessible(true);
                    String key = field.getName();
                    field.set(result, resultSet.getObject(key));
                }
                results.add(result);
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (SecurityException e1) {
                e1.printStackTrace();
            }
        }

        return results;
    }


    /**
     * Delete rows in a table 
     * @param table Table name
     * @param dataRow A data template you want to match & delete (Null represents unknown)
     * @throws SQLException
     */
    public static int delete(String table, IDataRow dataRow) throws SQLException{
        // Build sql
        String sql = "DELETE FROM " + table + " WHERE " + buildSQLCondition(dataRow);
        // System.out.println(sql);

        // Execute sql
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }

    /**
     * Update rows in a table
     * @param table Table name
     * @param dataRowOld A data template you want to match & update (Null represents unknown)
     * @param dataRowNew New data
     * @throws SQLException
     */
    public static <T extends IDataRow> int update(String table, T dataRowOld, T dataRowNew, Boolean includeNull) throws SQLException{
        // Init string builders
        StringBuilder setBuilder = new StringBuilder();

        // Get fields in the class & build sql elements
        Field[] fields = dataRowNew.getClass().getDeclaredFields();
        Boolean firstItem = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                String value = (String) field.get(dataRowNew);

                if (includeNull || value != null) {
                    if(firstItem)
                        firstItem = false;
                    else
                        setBuilder.append(",");
                    if(value != null){
                        if(field.getType() == String.class)
                            setBuilder.append(key + "='" + value + "'");
                        else
                            setBuilder.append(key + "=" + value);
                    }
                    else
                        setBuilder.append(key + "=NULL");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Build sql
        String sql = "UPDATE " + table + " SET " + setBuilder + " WHERE " + buildSQLCondition(dataRowOld);
        // System.out.println(sql);

        // Execute sql
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }

    /**
     * Build SQL condition
     * @param dataRow Data template
     * @return Sql condition string
     */
    private static String buildSQLCondition(IDataRow dataRow){
        // Init string builders
        StringBuilder conditionBuilder = new StringBuilder();

        // Get fields in the class & build sql elements
        Class<? extends IDataRow> dataRowClass = dataRow.getClass();
        Field[] fields = dataRowClass.getDeclaredFields();
        Boolean firstItem = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                String value = (String) field.get(dataRow);

                if (value != null) {
                    if (firstItem)
                        firstItem = false;
                    else
                        conditionBuilder.append(" and ");

                    conditionBuilder.append(key + "='" + value + "'");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return conditionBuilder.toString();
    }

    public static void main(String[] args){
        try{
            // Connect
            Database.connect("jdbc:mysql://stusql.dcs.shef.ac.uk/team018", "team018", "9ae70ba0");
            
            // Write & Read
            write("Author", new Author("UoS", "bshan3@sheffield.ac.uk", "Shan", "Boxuan"));
            write("Author", new Author("UoS", "jqi6@sheffield.ac.uk", "Qi", "Jingxiang"));
            List<Author> a = read("Author", new Author("UoS", null, null, null));
            System.out.println("Result count 1: " + a.size());

            // Update & Read
            update("Author", new Author(null, "bshan3@sheffield.ac.uk", null, null), new Author("UoS", "bshan3@sheffield.ac.uk", null, "Boxuan1"), false);
            update("Author", new Author(null, "jqi6@sheffield.ac.uk", null, null), new Author("UoS", "jqi6@sheffield.ac.uk", null, "Jingxiang1"), true);
            List<Author> b = read("Author", new Author("UoS", null, null, null));
            System.out.println("Result count 2: " + b.size());

            // Delete & Read
            delete("Author", new Author("UoS", null, null, null));
            List<Author> c = read("Author", new Author("UoS", null, null, null));
            System.out.println("Result count 3: " + c.size());

            // Disconnect
            Database.disconnect();

            System.out.println("Done!!!");
        }
        catch (SQLException ex) {
		    ex.printStackTrace();
        }
    }
}