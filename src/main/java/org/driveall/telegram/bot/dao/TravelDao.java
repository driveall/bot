package org.driveall.telegram.bot.dao;

import org.driveall.telegram.bot.entity.Travel;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TravelDao {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://contac01.mysql.ukraine.com.ua:3306/contac01_prilosh";
    private static final String LOGIN = "contac01_prilosh";
    private static final String PASS = "4h57futz";

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, LOGIN, PASS);
    }

    public static void add(Travel t) throws ClassNotFoundException, SQLException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("INSERT INTO bot_travel VALUES ('" + t.getId() + "', '" + t.getEvtid() + "', '" + t.getName() + "')");
        }
    }

    public static void remove(String id) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM bot_travel WHERE id='" + id + "'");
        }
    }

    public static List<Travel> get() throws SQLException, ClassNotFoundException {
        List<Travel> out = new LinkedList<>();
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM bot_travel");
            while (rs.next()) {
                Travel t = new Travel(rs.getString("id"), rs.getString("evtid"), rs.getString("name"));
                out.add(t);
            }
        }
        return out;
    }

    public static void removeByUsername(String username) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM bot_travel WHERE name='" + username + "'");
        }
    }
}
