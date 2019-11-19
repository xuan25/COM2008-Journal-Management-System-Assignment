package com.com2008.journalmanagementsystem.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class Database {

    private static Connection connection = null;

    public static boolean disconnect() throws SQLException {
        if (connection == null)
            return false;
        connection.close();
        return true;
    }

    public static void connect(String url, String username, String password) throws SQLException {
        disconnect();
        connection = DriverManager.getConnection(url, username, password);
    }

    public static void write(String table, IDataRow dataRow) throws SQLException {
        StringBuilder columnsBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();

        Field[] fields = dataRow.getClass().getDeclaredFields();
        Boolean firstItem = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                String value = (String) field.get(dataRow);

                if (value != null) {
                    if (firstItem) {
                        firstItem = false;
                        columnsBuilder.append(key);
                        valuesBuilder.append("'" + value + "'");
                    } else {
                        columnsBuilder.append("," + key);
                        valuesBuilder.append(",'" + value + "'");
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String sql = "INSERT INTO " + table + " (" + columnsBuilder.toString() + ") " + "VALUES ("
                + valuesBuilder.toString() + ");";
        System.out.println(sql);

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    public static <T extends IDataRow> List<T> read(String table, T dataRow)throws SQLException {
        StringBuilder filterBuilder = new StringBuilder();

        Class<? extends IDataRow> dataRowClass = dataRow.getClass();
        Field[] fields = dataRowClass.getDeclaredFields();
        Boolean firstItem = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                String value = (String) field.get(dataRow);

                if (value != null) {
                    if (firstItem) {
                        firstItem = false;
                        filterBuilder.append(key + "='" + value + "'");
                    } else {
                        filterBuilder.append(" and " + key + "='" + value + "'");
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String sql = "SELECT * FROM " + table + " WHERE " + filterBuilder.toString();

        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        ResultSet resultSet = statement.executeQuery(sql);

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

    public static void delete() throws SQLException{

    }

    public static void update() throws SQLException{

    }

    

    public static void main(String[] args){
        try{
            Database.connect("jdbc:mysql://stusql.dcs.shef.ac.uk/team018", "team018", "9ae70ba0");
            
            // write("Author", new Author("UoS", "bshan3@sheffield.ac.uk", "Shan", "Boxuan"));
            // write("Author", new Author("UoS", "jqi6@sheffield.ac.uk", "Qi", "Jingxiang"));

            List<Author> a = read("Author", new Author("UoS", null, null, null));
            List<Author> b = read("Author", new Author(null, null, "Qi", null));

            System.out.println("");

            Database.disconnect();
        }
        catch (SQLException ex) {
		    ex.printStackTrace();
        }
    }
}