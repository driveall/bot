package org.driveall.telegram.bot.service;

import org.driveall.telegram.bot.dao.EventDao;
import org.driveall.telegram.bot.dao.WakeDao;
import org.driveall.telegram.bot.entity.Event;
import org.driveall.telegram.bot.entity.Wake;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
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
            LocalDateTime d = LocalDateTime.of(
                    parseInt(msg.substring(0, 4)),
                    parseInt(msg.substring(5, 7)),
                    parseInt(msg.substring(8, 10)),
                    parseInt(msg.substring(11)),
                    0);
            Event evt = new Event(UUID.randomUUID().toString(), d, "wakeboarding");
            EventDao.add(evt);
            return "Event on " + d.toString() + " created;";
        }
        List<Event> evts = EventDao.get();
        if (msg.equals("info")) {
            List<Wake> wks = WakeDao.get();
            String out = "EVENTS:\n";
            for (Event evt : evts) {
                out += "Event on " + evt.getDate().toString() + ":\n";
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
                    if (evt.getDate().isAfter(e.getDate())) {
                        evt = e;
                    }
                }
            }
            if (evt != null) {
                Wake w = new Wake(UUID.randomUUID().toString(), evt.getId(), username);
                WakeDao.add(w);
                return username + ", you are subscribed on event on " + evt.getDate().toString() + ';';
            }
        }
        if (msg.startsWith("dateSubscribe ")) {
            msg = msg.substring(14);
            Date d = new Date(Long.parseLong(msg));
            for (Event e : evts) {
                if (d.equals(e.getDate())) {
                    Wake w = new Wake(UUID.randomUUID().toString(), e.getId(), username);
                    WakeDao.add(w);
                    return username + ", you are subscribed on event on " + e.getDate().toString() + ';';
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
                "'kurs' or 'курс' - currencies of (USD, EUR and JPY to UAH) and (BTC to USD);\n\n" +
                "'wake' or 'вейк' - info about waking calendar;\n" +
                "'wake +' - schedule on waking on nearest date-time;\n" +
                "'wake + 'YYYY' 'MM' 'DD' 'HH'' - schedule on waking on specified date-time;\n" +
                "'wake -' - unsubscribe your all wakings;\n" +
                "'wake create 'YYYY' 'MM' 'DD' 'HH'' - create a wake event;\n\n" +
                "";
    }
}
