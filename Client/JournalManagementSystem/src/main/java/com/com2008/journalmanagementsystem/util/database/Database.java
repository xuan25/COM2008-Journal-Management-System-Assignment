package com.com2008.journalmanagementsystem.util.database;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.*;

/**
 * A database generic interface for java classes. String, Interger, Long, InputStream are supported.
 * Written by Xuan525    02 Dec 2019
 * For COM2008 project
 */
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
     * @param url      Database url
     * @param username Database username
     * @param password Database password
     * @throws SQLException
     */
    public static void connect(String url, String username, String password) throws SQLException {
        disconnect();
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Run INSERT, UPDATE, DELETE
     * @param sql   SQL
     * @return      Count
     * @throws SQLException
     */
    public static int executeUpdate(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }

    /**
     * Run SELECT
     * @param sql   SQL
     * @return      Result set
     * @throws SQLException
     */
    public static ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        return statement.executeQuery(sql);
    }

    /**
     * Download a document to the database
     * @param tableName Table name
     * @param filepath  Document's local absolute path
     * @return          Generated document ID to locate documents in the database.
     * @throws SQLException
     * @throws IOException
     */
    public static String uploadDocument(String tableName, String filepath) throws SQLException, IOException {
        InputStream fileStream = new FileInputStream(filepath);
        while(true){
            try {
                PreparedStatement statment = connection.prepareStatement("INSERT INTO " + tableName + " VALUES(?,?)");
                UUID uuid = UUID.randomUUID();
                statment.setString(1, uuid.toString());
                statment.setBlob(2, fileStream);
                statment.executeUpdate();
                fileStream.close();
                return uuid.toString();
            } 
            catch (SQLException e) {
                if(e.getErrorCode() != 2601)
                    throw e;
            }
        }
    }

    /**
     * Download a document in database by its ID
     * @param tableName Table name
     * @param uuid      ID
     * @return          A InputStream of the document.
     * @throws SQLException
     * @throws IOException
     */
    public static InputStream downloadDocument(String tableName, String uuid) throws SQLException, IOException {
        String uuidColumnName = connection.createStatement().executeQuery("SELECT * FROM " + tableName + " LIMIT 0").getMetaData().getColumnName(1);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE " + uuidColumnName + "='" + uuid + "'");
        if(resultSet.next()){
            Blob document = resultSet.getBlob(2);
            InputStream stream = document.getBinaryStream();
            return stream;
        }
        return null;
    }

    /**
     * Delete a document in database by its ID
     * @param tableName Table name
     * @param uuid      ID
     * @return          Count
     * @throws SQLException
     */
    public static int deleteDocument(String tableName, String uuid) throws SQLException{
        String uuidColumnName = connection.createStatement().executeQuery("SELECT * FROM " + tableName + " LIMIT 0").getMetaData().getColumnName(1);
        return connection.createStatement().executeUpdate("DELETE FROM " + tableName + " WHERE " + uuidColumnName + "='" + uuid + "'");
    }

    /**
     * Insert a new row to a table
     * @param table     Table name
     * @param dataRow   Data instance you want to insert
     * @throws SQLException
     */
    public static int write(String table, IDataRow dataRow) throws SQLException {
        // Init string builders
        StringBuilder columnsBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();
        List<Object> values = new ArrayList<Object>();

        // Get fields in the class & build sql elements
        Field[] fields = dataRow.getClass().getDeclaredFields();
        Boolean firstItem = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                Object obj = field.get(dataRow);

                if (obj != null) {
                    values.add(obj);
                    if (firstItem) 
                        firstItem = false;
                    else{
                        columnsBuilder.append(",");
                        valuesBuilder.append(",");
                    }
                    columnsBuilder.append(key);
                    valuesBuilder.append("?");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Statement
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + table + " (" + columnsBuilder + ") " + "VALUES (" + valuesBuilder + ");");
        for(int i = 0; i < values.size(); i++){
            Object obj = values.get(i);
            Class<? extends Object> cl = obj.getClass();
            if(cl == String.class){
                statement.setString(i+1, (String)obj);
            }
            else if(cl == Integer.class){
                statement.setInt(i+1, (Integer)obj);
            }
            else if(cl == Long.class){
                statement.setLong(i+1, (Long)obj);
            }
            else if(InputStream.class.isAssignableFrom(cl)){
                statement.setBlob(i+1, (InputStream)obj);
            }
        }
        return statement.executeUpdate();
    }

    /**
     * Select & Read rows form a table
     * @param <T>       Data type you want to read
     * @param table     Table name
     * @param dataRow   A data template you want to match (Null represents wildcard)
     * @return          A list of instance
     * @throws SQLException
     */
    public static <T extends IDataRow> List<T> read(String table, T dataRow)throws SQLException {
        // Read data from the database
        PreparedStatement statement = prepareConditionalStatement("SELECT * FROM " + table, dataRow);
        ResultSet resultSet = statement.executeQuery();

        // Get fields in the class
        Class<? extends IDataRow> dataRowClass = dataRow.getClass();
        Field[] fields = dataRowClass.getDeclaredFields();

        // Store results into instances
        List<T> results = new ArrayList<T>();
        while (resultSet.next()) {
            try {
                // Warning Note : 
                // dataRowClass must be <T extends IDataRow> because it is the class of dataRow which type is T.
                // Which means (Class<? extends IDataRow> dataRowClass), where ? is T. See line 195.
                // Therefore the cast will be success.
                T result = (T)dataRowClass.getDeclaredConstructor().newInstance();
                for(Field field : fields){
                    field.setAccessible(true);
                    String key = field.getName();
                    Class<?> cl = field.getType();
                    if(InputStream.class.isAssignableFrom(cl)){
                        Blob document = resultSet.getBlob(key);
                        InputStream stream = document.getBinaryStream();
                        field.set(result, stream);
                    }
                    else{
                        Object obj = resultSet.getObject(key);
                        field.set(result, obj);
                    }
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
     * @param table     Table name
     * @param dataRow   A data template you want to match & delete (Null represents wildcard)
     * @throws SQLException
     */
    public static int delete(String table, IDataRow dataRow) throws SQLException{
        PreparedStatement statement = prepareConditionalStatement("DELETE FROM " + table, dataRow);
        return statement.executeUpdate();
    }

    /**
     * Update rows in a table
     * @param table         Table name
     * @param dataRowOld    A data template you want to match & update (Null represents wildcard)
     * @param dataRowNew    New data  (Null represents not change)
     * @throws SQLException
     */
    public static <T extends IDataRow> int update(String table, T dataRowOld, T dataRowNew, Boolean includeNull) throws SQLException{
        // TODO : Remove param includeNull after everything is done.
        if(includeNull)
            throw new InvalidParameterException("includeNull=true is no longer supported. Please use includeNull=false for updating database.");
        PreparedStatement statement = prepareUpdateStatement(table, dataRowOld, dataRowNew);
        return statement.executeUpdate();
    }

    private static PreparedStatement prepareConditionalStatement(String prefix, IDataRow dataRow) throws SQLException {
        return prepareConditionalStatement(prefix, dataRow, 0);
    }

    private static PreparedStatement prepareConditionalStatement(String prefix, IDataRow dataRow, int offset) throws SQLException {
        // Init string builders
        StringBuilder conditionBuilder = new StringBuilder();
        // Init value list
        List<Object> values = new ArrayList<Object>();

        // Get fields in the class
        Class<? extends IDataRow> dataRowClass = dataRow.getClass();
        Field[] fields = dataRowClass.getDeclaredFields();
        // Build sql condition & value list
        Boolean firstItem = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                Object obj = field.get(dataRow);

                if (obj != null) {
                    if (firstItem) 
                        firstItem = false;
                    else{
                        conditionBuilder.append(" and ");
                    }
                    conditionBuilder.append(key + "=?");
                    values.add(obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Build full sql
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(prefix);
        
        if(!conditionBuilder.toString().isEmpty())
            sqlBuilder.append(" WHERE " + conditionBuilder.toString());

        sqlBuilder.append(";");

        // Prepare statement & set values
        PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
        for(int i = 0; i < values.size(); i++){
            Object obj = values.get(i);
            Class<? extends Object> cl = obj.getClass();
            if(cl == String.class){
                statement.setString(i+1+offset, (String)obj);
            }
            else if(cl == Integer.class){
                statement.setInt(i+1+offset, (Integer)obj);
            }
            else if(cl == Long.class){
                statement.setLong(i+1+offset, (Long)obj);
            }
            else if(InputStream.class.isAssignableFrom(cl)){
                statement.setBlob(i+1, (InputStream)obj);
            }
        }

        return statement;
    }

    private static PreparedStatement prepareUpdateStatement(String table, IDataRow dataRowOld, IDataRow dataRowNew) throws SQLException {
        // Init string builders
        StringBuilder conditionBuilder = new StringBuilder();
        // Init value list
        List<Object> values = new ArrayList<Object>();

        // Get fields in the class
        Class<? extends IDataRow> dataRowClass = dataRowNew.getClass();
        Field[] fields = dataRowClass.getDeclaredFields();
        // Build sql condition & value list
        Boolean firstItem = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                Object obj = field.get(dataRowNew);

                if (obj != null) {
                    if (firstItem) 
                        firstItem = false;
                    else{
                        conditionBuilder.append(", ");
                    }
                    conditionBuilder.append(key + "=?");
                    values.add(obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Build full sql
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE " + table);
        
        if(!conditionBuilder.toString().isEmpty())
            sqlBuilder.append(" SET " + conditionBuilder.toString());

        sqlBuilder.append(" ");

        PreparedStatement statement = prepareConditionalStatement(sqlBuilder.toString(), dataRowOld, values.size());

        // Prepare statement & set values
        // PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
        for(int i = 0; i < values.size(); i++){
            Object obj = values.get(i);
            Class<? extends Object> cl = obj.getClass();
            if(cl == String.class){
                statement.setString(i+1, (String)obj);
            }
            else if(cl == Integer.class){
                statement.setInt(i+1, (Integer)obj);
            }
            else if(cl == Long.class){
                statement.setLong(i+1, (Long)obj);
            }
            else if(InputStream.class.isAssignableFrom(cl)){
                statement.setBlob(i+1, (InputStream)obj);
            }
        }

        return statement;
    }

    public static void main(String[] args){
        try{
            // Connect
            Database.connect("jdbc:mysql://stusql.dcs.shef.ac.uk/team018", "team018", "9ae70ba0");
            
            // Write & Read
            write("Account", new com.com2008.journalmanagementsystem.model.Account("bshan3@sheffield.ac.uk", "Mr.", "Boxuan", "Shan", "UoS"));
            write("Account", new com.com2008.journalmanagementsystem.model.Account("jqi6@sheffield.ac.uk", "Mr.", "Jingxiang", "Qi", "UoS"));
            List<com.com2008.journalmanagementsystem.model.Account> a = read("Account", new com.com2008.journalmanagementsystem.model.Account(null, null, null, null, "UoS"));
            System.out.println("Result count 1: " + a.size());

            // Update & Read
            update("Account", new com.com2008.journalmanagementsystem.model.Account("bshan3@sheffield.ac.uk", null,null, null, null), new com.com2008.journalmanagementsystem.model.Account("bshan3@sheffield.ac.uk", null, "Boxuan1", null, "UoS"), false);
            update("Account", new com.com2008.journalmanagementsystem.model.Account("jqi6@sheffield.ac.uk", null, null, null, null), new com.com2008.journalmanagementsystem.model.Account("jqi6@sheffield.ac.uk", null, "Jingxiang1", null, "UoS"), true);
            List<com.com2008.journalmanagementsystem.model.Account> b = read("Account", new com.com2008.journalmanagementsystem.model.Account(null, null, null, null, "UoS"));
            System.out.println("Result count 2: " + b.size());

            // Delete & Read
            delete("Account", new com.com2008.journalmanagementsystem.model.Account(null, null, null, null, "UoS"));
            List<com.com2008.journalmanagementsystem.model.Account> c = read("Account", new com.com2008.journalmanagementsystem.model.Account(null, null, null, null, "UoS"));
            System.out.println("Result count 3: " + c.size());


            // // Test Document
            // String documentFolder = "/Users/boxuanshan/Documents/GitHub/COM2008Project/";
            // String filename = "dummy.pdf";

            // // Document upload
            // String docID = uploadDocument("Document", documentFolder + filename);

            // // Document download
            // InputStream downloadStream = downloadDocument("Document", docID);
            // OutputStream out = new FileOutputStream(documentFolder + "dounload.pdf");
            // byte[] buffer = new byte[1024];
            // int len = 0;
            // while((len = downloadStream.read(buffer)) != -1){
            //     out.write(buffer, 0, len);
            // }
            // out.close();

            // // Document delete
            // deleteDocument("Document", docID);



            // Disconnect
            Database.disconnect();

            System.out.println("Done!!!");
        }
        catch (SQLException ex) {
		    ex.printStackTrace();
        }
        // catch (IOException ex) {
		//     ex.printStackTrace();
        // }
    }
}