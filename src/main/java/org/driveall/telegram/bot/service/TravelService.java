package org.driveall.telegram.bot.service;

import org.driveall.telegram.bot.dao.TravelDao;

import java.sql.SQLException;

class TravelService {
    static void unsubscribeUser(String username) throws SQLException, ClassNotFoundException {
        TravelDao.removeByUsername(username);
    }
}
