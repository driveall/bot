package org.driveall.telegram.bot.dao;

import org.driveall.telegram.bot.entity.Event;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
            st.executeUpdate("INSERT INTO event VALUES ('" + e.getId() + "', '" + e.getDate() + "', '" + e.getDescription() + "')");
        }
    }

    public static void remove(String id) throws SQLException, ClassNotFoundException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM event WHERE id='" + id + "'");
        }
    }

    public static List<Event> get() throws SQLException, ClassNotFoundException {
        List<Event> out = new LinkedList<>();
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM event");
            while (rs.next()) {
                Date d = rs.getDate("date");
                LocalDateTime ldt = LocalDateTime.of(d.getYear(), d.getMonth(), d.getDay(), 0, 0);
                Event evt = new Event(
                        rs.getString("id"),
                        ldt,
                        rs.getString("description"));
                out.add(evt);
            }
        }
        return out;
    }
}