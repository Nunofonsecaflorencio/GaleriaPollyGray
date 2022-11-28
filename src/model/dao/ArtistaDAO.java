package model.dao;

import model.valueobjects.Artista;
import utility.PollyDatabase;

import javax.swing.*;
import java.sql.*;

public class ArtistaDAO implements DAO<Artista>{

    @Override
    public void create(Artista artista) {
        String query = "INSERT INTO Artista (nome,dataNascimento,contactos,biografia,imagem)" +
                " VALUES (?,?,?,?,?)";
        try {
            Connection conn = PollyDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, artista.getNome());
            stmt.setDate(2, Date.valueOf(artista.getDataNascimento()));
            stmt.setString(3, artista.getContactos());
            stmt.setString(4, artista.getBiografia());
            stmt.setString(5, artista.getImagem());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION (from ArtistaDAO.create)");
        }
    }

    @Override
    public DefaultListModel<Artista> read() {
        DefaultListModel<Artista> artistas = new DefaultListModel<>();
        String query = "SELECT * FROM Artista";
        try {
            Connection conn = PollyDatabase.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                artistas.addElement(new Artista(
                        rs.getInt("idArtista"),
                        rs.getString("nome"),
                        rs.getString("dataNascimento"),
                        rs.getString("contactos"),
                        rs.getString("biografia"),
                        rs.getString("imagem")
                ));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION (from ArtistaDAO.read)");
        }

        return artistas;
    }

    @Override
    public void update(Artista artista) {
        String query = "UPDATE Artista SET nome = ?,dataNascimento = ?, contactos = ?, biografia = ?, imagem = ?" +
                " WHERE Artista.idArtista = ?";
        try {
            Connection conn = PollyDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, artista.getNome());
            stmt.setDate(2, Date.valueOf(artista.getDataNascimento()));
            stmt.setString(3, artista.getContactos());
            stmt.setString(4, artista.getBiografia());
            stmt.setString(5, artista.getImagem());

            stmt.setInt(6, artista.getIdArtista());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION (from ArtistaDAO.update)");
        }
    }

    @Override
    public void delete(Artista artista) {
        String query = "DELETE FROM Artista WHERE idArtista = ?";
        try {
            Connection conn = PollyDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, artista.getIdArtista());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION (from ArtistaDAO.delete)");
        }
    }
}
