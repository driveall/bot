package org.driveall.telegram.bot.service;

import org.driveall.telegram.bot.dao.EventDao;
import org.driveall.telegram.bot.dao.TravelDao;
import org.driveall.telegram.bot.dao.WakeDao;
import org.driveall.telegram.bot.entity.Event;
import org.driveall.telegram.bot.entity.Travel;
import org.driveall.telegram.bot.entity.Wake;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

        //Unsubscribe from all wake events
        if (msg.equals("unsubscribe")) {
            WakeService.unsubscribeUser(username);
            return username + ", you were unsubscribed from all wake events;";
        }

        //Create new wake event
        if (msg.startsWith("create ")) {
            if (!"Алексей".equals(username)) {
                return "You have not permissions to create a wake event (only boat owner can create it)";
            }
            msg = msg.substring(7);
            LocalDateTime ldt = WakeService.parseEventDate(msg);
            int nextEventNumber = WakeService.getNextEventNumber("w");
            WakeService.createEvent(ldt, nextEventNumber, "wakeboarding");
            return "Wake event #" + nextEventNumber + " on " + Timestamp.valueOf(ldt) + " created;";
        }

        List<Event> evts = EventDao.getWake();
        WakeService.removeOldEvents(evts);

        //Info about future wake events
        if (msg.equals("info")) {
            List<Wake> wks = WakeDao.get();
            StringBuilder out = new StringBuilder("WAKE EVENTS:\n");
            for (Event evt : evts) {
                out.append(evt.getNum()).append(") Wake event '").append(evt.getDescription()).append("' on ").append(evt.getDate()).append(":\n");
                for (Wake w : wks) {
                    if (w.getEvtid().equals(evt.getId())) {
                        out.append("   ").append(w.getName()).append(";\n");
                    }
                }
            }
            return out.toString();
        }

        //Subscribe to nearest wake event
        if (msg.equals("subscribe")) {
            Event evt = getEvent(evts);
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

    private static Event getEvent(List<Event> evts) {
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
        return evt;
    }

    public static String travel(String msg, String username) throws SQLException, ClassNotFoundException {

        if (msg.equals("unsubscribe")) {
            TravelService.unsubscribeUser(username);
            return username + ", you were unsubscribed from all events;";
        }

        if (msg.startsWith("create ")) {
            if (!"Алексей".equals(username)) {
                return "You have not permissions to create an event (only boat owner can create it)";
            }
            msg = msg.substring(7);
            int wsIndex = msg.indexOf(" ");
            String punkt = msg.substring(0, wsIndex);
            msg = msg.substring(wsIndex + 1);
            LocalDateTime ldt = WakeService.parseEventDate(msg);
            int nextEventNumber = WakeService.getNextEventNumber("t");
            WakeService.createEvent(ldt, nextEventNumber, "travel " + punkt);
            return "Event #" + nextEventNumber + " on " + Timestamp.valueOf(ldt) + " created;";
        }

        List<Event> evts = EventDao.getTravel();
        WakeService.removeOldEvents(evts);

        if (msg.equals("info")) {
            List<Travel> trvls = TravelDao.get();
            StringBuilder out = new StringBuilder("TRAVEL EVENTS:\n");
            for (Event evt : evts) {
                out.append(evt.getNum()).append(") Travel event '").append(evt.getDescription()).append("' on ").append(evt.getDate()).append(":\n");
                for (Travel t : trvls) {
                    if (t.getEvtid().equals(evt.getId())) {
                        out.append("   ").append(t.getName()).append(";\n");
                    }
                }
            }
            return out.toString();
        }

        if (msg.equals("subscribe")) {
            Event evt = getEvent(evts);
            if (evt != null) {
                Travel t = new Travel(UUID.randomUUID().toString(), evt.getId(), username);
                TravelDao.add(t);
                return username + ", you are subscribed on event '" + evt.getDescription() + "' on " + evt.getDate().toString() + ';';
            }
        }

        if (msg.startsWith("numSubscribe ")) {
            msg = msg.substring(13);
            int evtNum = Integer.parseInt(msg);
            for (Event e : evts) {
                if (e.getNum() == evtNum) {
                    Travel t = new Travel(UUID.randomUUID().toString(), e.getId(), username);
                    TravelDao.add(t);
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
                "3) 'travel' - info about traveling calendar;\n" +
                "     'travel +' - schedule on traveling on nearest event;\n" +
                "     'travel + 'EVENT_NUMBER'' - schedule on traveling on specified event by number;\n" +
                "     'travel -' - unsubscribe all your travelings;\n" +
                "     'travel create 'DESTINATION' 'YYYY' 'MM' 'DD' 'HH'' - create a travel event;\n\n";
    }
}
