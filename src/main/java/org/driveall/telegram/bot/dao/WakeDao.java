package org.driveall.telegram.bot.dao;

import org.driveall.telegram.bot.entity.Wake;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class WakeDao {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://contac01.mysql.ukraine.com.ua:3306/contac01_prilosh";
    private static final String LOGIN = "contac01_prilosh";
    private static final String PASS = "4h57futz";

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, LOGIN, PASS);
    }

    public static void add(Wake w) throws ClassNotFoundException, SQLException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("INSERT INTO wake VALUES ('" + w.getId() + "', '" + w.getEvtid() + "', '" + w.getName() + "')");
        }
    }

    public static void remove(String id) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM wake WHERE id='" + id + "'");
        }
    }

    public static List<Wake> get() throws SQLException, ClassNotFoundException {
        List<Wake> out = new LinkedList<>();
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM wake");
            while (rs.next()) {
                Wake w = new Wake(rs.getString("id"), rs.getString("evtid"), rs.getString("name"));
                out.add(w);
            }
        }
        return out;
    }

    public static void removeByUsername(String username) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM wake WHERE name='" + username + "'");
        }
    }

}
