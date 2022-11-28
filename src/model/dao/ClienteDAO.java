package model.dao;

import model.valueobjects.Artista;
import model.valueobjects.Cliente;
import utility.PollyDatabase;

import java.sql.*;

public class ClienteDAO implements DAO<Cliente>{


    @Override
    public void create(Cliente cliente) {
        String query = "INSERT INTO Cliente (nome,endereco,contacto)" +
                " VALUES (?,?,?)";
        try {
            Connection conn = PollyDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getContacto());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION");
        }
    }

    @Override
    public Object read() {
        return null;
    }

    @Override
    public void update(Cliente obj) {

    }

    @Override
    public void delete(Cliente obj) {

    }


    public static Cliente getClienteByName(String nomeCliente) {
        String query = "SELECT * FROM Cliente WHERE LOWER(REPLACE(nome, ' ', '')) = ?";

        Cliente cliente = null;
        try {
            Connection conn = PollyDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, nomeCliente.toLowerCase().replace(" ", ""));


            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("contacto")
                );
            }

            stmt.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION");
        }

        return cliente;
    }
}
