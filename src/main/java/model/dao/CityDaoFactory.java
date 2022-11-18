package model.dao;

import model.util.Connection;
import model.dao.impl.CityImpl;
import model.util.exception.DBExcepction;

import java.sql.SQLException;

public class CityDaoFactory {
    public static CityDao createCityDao() {
        try {
            return new CityImpl(Connection.getConnection());
        } catch (SQLException e) {
            throw new DBExcepction(e.getMessage());
        }
    }
}
