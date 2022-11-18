package model.dao;

import model.util.Connection;
import model.dao.impl.EventImpl;
import model.util.exception.DBExcepction;

import java.sql.SQLException;

public class EventDaoFactory {
    public static EventDao createEventDao(){
        try {
            return new EventImpl(Connection.getConnection());
        } catch (SQLException e) {
            throw new DBExcepction(e.getMessage());
        }
    }
}
