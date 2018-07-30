package org.driveall.telegram.bot.service;

import org.driveall.telegram.bot.dao.EventDao;
import org.driveall.telegram.bot.dao.WakeDao;
import org.driveall.telegram.bot.entity.Event;
import org.driveall.telegram.bot.entity.Wake;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.lang.Integer.parseInt;

public class MessageService {
    public static String kurs() {
        return "Курс НБУ:\n" +
                CurrencyService.getNbuCurrencies() + "\n" +
                "Курс битка:\n" +
                CurrencyService.getBitcoinCurrency();
    }

    public static String weed() {
        return "Не, все закончилось, спроси позже";
    }

    public static String wake(String msg, String username) throws SQLException, ClassNotFoundException {

        if (msg.equals("unsubscribe")) {
            WakeDao.removeByUsername(username);
            return username + ", you were unsubscribed from all events;";
        }

        if (msg.startsWith("create ")) {
            msg = msg.substring(7);
            LocalDateTime ldt = LocalDateTime.of(
                    parseInt(msg.substring(0, 4)),
                    parseInt(msg.substring(5, 7)),
                    parseInt(msg.substring(8, 10)),
                    parseInt(msg.substring(11)),
                    0);
            List<Event> evts = EventDao.get();
            int num = 0;
            for (Event evt : evts) {
                if (evt.getNum() > num) {
                    num = evt.getNum();
                }
            }
            Event evt = new Event(UUID.randomUUID().toString(), Timestamp.valueOf(ldt), "wakeboarding", num + 1);
            EventDao.add(evt);
            return "Event #" + evt.getNum() + " on " + Timestamp.valueOf(ldt) + " created;";
        }
        List<Event> evts = EventDao.get();

        if (msg.equals("info")) {
            List<Wake> wks = WakeDao.get();
            String out = "EVENTS:\n";
            for (Event evt : evts) {
                out += evt.getNum() + ") Event '" + evt.getDescription() + "' on " + evt.getDate() + ":\n";
                for (Wake w : wks) {
                    if (w.getEvtid().equals(evt.getId())) {
                        out += "  " + w.getName() + ";\n";
                    }
                }
            }
            return out;
        }

        if (msg.equals("subscribe")) {
            Event evt = null;
            if (evts.size() > 0) {
                for (Event e : evts) {
                    if (evt == null) {
                        evt = e;
                        continue;
                    }
                    if (evt.getDate().toLocalDateTime().isAfter(e.getDate().toLocalDateTime())) {
                        evt = e;
                    }
                }
            }
            if (evt != null) {
                Wake w = new Wake(UUID.randomUUID().toString(), evt.getId(), username);
                WakeDao.add(w);
                return username + ", you are subscribed on event '" + evt.getDescription() + "' on " + evt.getDate().toString() + ';';
            }
        }

        if (msg.startsWith("numSubscribe ")) {
            msg = msg.substring(13);
            int evtNum = Integer.parseInt(msg);
            for (Event e : evts) {
                if (e.getNum() == evtNum) {
                    Wake w = new Wake(UUID.randomUUID().toString(), e.getId(), username);
                    WakeDao.add(w);
                    return username + ", you are subscribed on event #" + e.getNum() + " on " + e.getDate().toString() + ';';
                }
            }
        }

        return "No events available;";
    }

    public static String info() {
        return "INFO:\n" +
                "All commands are case insensitive;\n" +
                "To call bot begin message with: '/d ', '/dd ', '/д ', '/дд ';\n" +
                "COMMANDS:\n" +
                "1) 'kurs' or 'курс' - currencies of (USD, EUR and JPY to UAH) and (BTC to USD);\n\n" +
                "2) 'wake' or 'вейк' - info about waking calendar;\n" +
                "     'wake +' - schedule on waking on nearest event;\n" +
                "     'wake + 'EVENT_NUMBER'' - schedule on waking on specified event by number;\n" +
                "     'wake -' - unsubscribe all your wakings;\n" +
                "     'wake create 'YYYY' 'MM' 'DD' 'HH'' - create a wake event;\n\n" +
                "";
    }
}
