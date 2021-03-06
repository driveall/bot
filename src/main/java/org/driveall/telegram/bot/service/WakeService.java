package org.driveall.telegram.bot.service;

import org.driveall.telegram.bot.dao.EventDao;
import org.driveall.telegram.bot.dao.WakeDao;
import org.driveall.telegram.bot.entity.Event;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.lang.Integer.parseInt;

class WakeService {
    static void unsubscribeUser(String username) throws SQLException, ClassNotFoundException {
        WakeDao.removeByUsername(username);
    }

    static LocalDateTime parseEventDate(String dateToParse) {
        return LocalDateTime.of(
                parseInt(dateToParse.substring(0, 4)),
                parseInt(dateToParse.substring(5, 7)),
                parseInt(dateToParse.substring(8, 10)),
                parseInt(dateToParse.substring(11)),
                0);
    }

    static int getNextEventNumber(String type) throws SQLException, ClassNotFoundException {
        int num = 0;
        List<Event> evts = null;
        switch (type) {
            case "w":
                evts = EventDao.getWake();
                break;
            case "t":
                evts = EventDao.getTravel();
                break;
        }
        for (Event evt : evts) {
            if (evt.getNum() > num) {
                num = evt.getNum();
            }
        }
        return num + 1;
    }

    static void createEvent(LocalDateTime date, int number, String type) throws SQLException, ClassNotFoundException {
        Event evt = new Event(UUID.randomUUID().toString(), Timestamp.valueOf(date), type, number);
        EventDao.add(evt);
    }

    static void removeOldEvents(List<Event> events) {
        for (Event e : events) {
            if (e.getDate().before(Timestamp.valueOf(LocalDateTime.now()))) {
                events.remove(e);
            }
        }
    }

}
