package uet.oop.dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Test {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite")
             ; PreparedStatement st = connection.prepareStatement("SELECT * FROM words LIMIT 5");){
            st.execute();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
