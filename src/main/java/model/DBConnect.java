package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
    //  Database credentials
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "IpMan";
    private static Connection connection = null;

    private Statement statement = null;


    public static Connection connect() throws SQLException{
        try{
            Class.forName("org.postgresql.Driver").newInstance();
        }catch(ClassNotFoundException cnfe){
            System.err.println("Error: "+cnfe.getMessage());
        }catch(InstantiationException ie){
            System.err.println("Error: "+ie.getMessage());
        }catch(IllegalAccessException iae){
            System.err.println("Error: "+iae.getMessage());
        }

        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        return connection;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if(connection !=null && !connection.isClosed())
            return connection;
        connect();
        return connection;
    }
    public Connection openConnection(String DBName, String Password) {
        try {
            //login DB
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", DBName, Password);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return connection;
    }

    public void insertPerson(String sql) {
        try {
            statement = connection.createStatement();
            System.out.println(sql);
            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
            connection.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void deletePerson(String sql) {
        try {
            statement = connection.createStatement();
            System.out.println(sql);
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    public void updatePeople(String sql) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.commit();

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
