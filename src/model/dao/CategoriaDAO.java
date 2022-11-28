package model.dao;

import model.valueobjects.Categoria;
import utility.PollyDatabase;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoriaDAO implements DAO<Categoria> {
    @Override
    public void create(Categoria obj) {

    }

    @Override
    public DefaultComboBoxModel<Categoria> read() {
        String query = "SELECT * FROM Categoria";
        DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();

        try {
            Connection conn = PollyDatabase.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                model.addElement(new Categoria(
                        rs.getInt("idCategoria"),
                        rs.getString("nome"),
                        rs.getInt("quantidadeArtes")
                ));
            }

            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return model;
    }

    @Override
    public void update(Categoria obj) {

    }

    @Override
    public void delete(Categoria obj) {

    }
}
