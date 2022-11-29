package model.dao;

import model.valueobjects.Artista;
import model.valueobjects.Compra;
import utility.PollyConstants;
import utility.PollyDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompraDAO implements DAO<Compra>{

    @Override
    public void create(Compra obj) {

    }

    @Override
    public DefaultTableModel read() {
        DefaultTableModel compras = new DefaultTableModel();
        compras.setColumnIdentifiers(new String[]{
            "NOME DO CLIENTE", "ID DA ARTE", "DATA", "UNIDADES", "PREÇO TOTAL"
        });
        

        String query = "SELECT Cliente.nome, idArte, data, unidades, precoTotal " +
                "FROM Compra, Cliente WHERE Compra.idCliente = Cliente.idCliente";
        try {
            Connection conn = PollyDatabase.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                compras.addRow(new String[]{
                        rs.getString("nome"),
                        rs.getInt("idArte")+"",
                        PollyConstants.reformatDate(rs.getDate("data").toString()),
                        rs.getInt("unidades")+"",
                        rs.getFloat("precoTotal")+""
                });
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION");
        }

        return compras;
    }

    @Override
    public void update(Compra obj) {

    }

    @Override
    public void delete(Compra obj) {

    }
}
