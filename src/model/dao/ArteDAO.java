package model.dao;

import model.entity.Arte;
import model.entity.Artista;
import utility.PollyDatabase;

import javax.swing.*;
import java.sql.*;

public class ArteDAO implements DAO<Arte>{
    public static Artista getArtistByArt(int idArte) {
        String query = "SELECT * FROM Artista WHERE Artista.idArtista = " +
                "(SELECT idArtista FROM Artista_Arte WHERE idArte = ?)" ;

        Artista artista = null;
        try {
            Connection conn = PollyDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, idArte);


            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                artista = new Artista(
                        rs.getInt("idArtista"),
                        rs.getString("nome"),
                        rs.getString("dataNascimento"),
                        rs.getString("contactos"),
                        rs.getString("biografia"),
                        rs.getString("imagem")
                );
            }
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION");
        }

        return artista;
    }

    @Override
    public void create(Arte arte) {
        String query = "call publicar_arte(?, ?, ?, ?, ?, ?, ?)" ;

        try {
            Connection conn = PollyDatabase.getConnection();
            CallableStatement stmt = conn.prepareCall(query);

            stmt.setInt(1, arte.getIdArte()); // idArtista hidden
            stmt.setInt(2, Integer.parseInt(arte.getDataPublicacao())); // idCategoria hidden
            stmt.setString(3, arte.getTitulo());
            stmt.setInt(4, arte.getUnidades());
            stmt.setFloat(5, arte.getPreco());
            stmt.setString(6, arte.getImagem());
            stmt.setString(7, arte.getDescricao());

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION");
        }
    }

    @Override
    public DefaultListModel<Arte> read() {
        DefaultListModel<Arte> artes = new DefaultListModel<>();
        String query = "SELECT * FROM Arte ORDER BY dataPublicacao";
        try {
            Connection conn = PollyDatabase.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                artes.addElement(new Arte(
                        rs.getInt("idArte"),
                        rs.getString("titulo"),
                        rs.getInt("unidades"),
                        rs.getFloat("preco"),
                        rs.getString("imagem"),
                        rs.getString("descricao"),
                        rs.getString("dataPublicacao"),
                        rs.getBoolean("esgotado")
                ));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION");
        }

        return artes;
    }

    @Override
    public void update(Arte arte) {
        String query = "UPDATE Arte SET titulo = ?, unidades = ?, preco = ?, imagem = ?, descricao = ?" +
                " WHERE Arte.idArte = ?";
        try {
            Connection conn = PollyDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, arte.getTitulo());
            stmt.setInt(2, arte.getUnidades());
            stmt.setFloat(3, arte.getPreco());
            stmt.setString(4, arte.getImagem());
            stmt.setString(5, arte.getDescricao());

            stmt.setInt(6, arte.getIdArte());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION");
        }
    }

    @Override
    public void delete(Arte arte) {
        String query = "DELETE FROM Arte WHERE Arte.idArte = ?";

        try {
            Connection conn = PollyDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, arte.getIdArte());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("[EXCEPTION] NO DATA BASE CONNECTION");
        }
    }
}
