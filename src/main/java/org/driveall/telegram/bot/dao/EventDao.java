package org.driveall.telegram.bot.dao;

import org.driveall.telegram.bot.entity.Event;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventDao {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://contac01.mysql.ukraine.com.ua:3306/contac01_prilosh";
    private static final String LOGIN = "contac01_prilosh";
    private static final String PASS = "4h57futz";

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, LOGIN, PASS);
    }

    public static void add(Event e) throws ClassNotFoundException, SQLException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("INSERT INTO bot_event VALUES ('" + e.getId() + "', '" + e.getDate() + "', '" + e.getDescription() + "', '" + e.getNum() + "')");
        }
    }

    public static void remove(String id) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM bot_event WHERE id='" + id + "'");
        }
    }

    public static List<Event> getWake() throws SQLException, ClassNotFoundException {
        List<Event> out = new CopyOnWriteArrayList<>();
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM bot_event WHERE description='wakeboarding'");
            while (rs.next()) {
                Timestamp d = rs.getTimestamp("date");
                Event evt = new Event(
                        rs.getString("id"),
                        d,
                        rs.getString("description"),
                        rs.getInt("num"));
                out.add(evt);
            }
        }
        return out;
    }

    public static List<Event> getTravel() throws SQLException, ClassNotFoundException {
        List<Event> out = new CopyOnWriteArrayList<>();
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM bot_event WHERE description!='wakeboarding'");
            while (rs.next()) {
                Timestamp d = rs.getTimestamp("date");
                Event evt = new Event(
                        rs.getString("id"),
                        d,
                        rs.getString("description"),
                        rs.getInt("num"));
                out.add(evt);
            }
        }
        return out;
    }
}
