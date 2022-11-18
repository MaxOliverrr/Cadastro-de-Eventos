package model.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.dao.CityDao;
import model.entities.City;

@NoArgsConstructor
@AllArgsConstructor

public class CityImpl implements CityDao {
    private Connection conn;

    public void insert(City city) {
        String query = "INSERT INTO city (name) VALUES ('%s')".formatted(city.getName());

        try (Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM city")) {
            while(rs.next()){
                if (city.getName().equals(rs.getString("name"))) {
                    System.out.println("Cidade Selecionada");
                    return;
                }
            }
            System.out.println("Cidade criada");
            st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
