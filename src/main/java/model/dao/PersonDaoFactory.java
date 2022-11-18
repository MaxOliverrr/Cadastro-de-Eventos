package model.dao;

import model.util.Connection;
import model.dao.impl.PersonImpl;
import model.util.exception.DBExcepction;

import java.sql.SQLException;

public class PersonDaoFactory {
    public PersonDaoFactory() {
    }
    public static PersonDao createPersonDao()  {
        try {
            return new PersonImpl(Connection.getConnection());
        } catch (SQLException e) {
            throw new DBExcepction(e.getMessage());
        }
    }
}
