/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import tn.esprit.cupcake.entities.*;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author Basly
 */
public class EvenementService {

    Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;

    public EvenementService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void ajouterEvenement(Evenement evenement) throws SQLException {
        String req = "INSERT INTO evenement (id_evenement,libelle_evenement,adresse_evenement,date_debut,date_fin,description,image,id_patisserie,longitude,latitude)values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, evenement.getId_evenement());
        pre.setString(2, evenement.getLibelle_evenement());
        pre.setString(3, evenement.getAdresse_evenement());
        pre.setDate(4, evenement.getDate_debut());
        pre.setDate(5, evenement.getDate_fin());
        pre.setString(6, evenement.getDescription());
        pre.setString(7, evenement.getImage());
        pre.setInt(8, evenement.getId_patisserie());
        pre.setDouble(9, evenement.getLongitude());
        pre.setDouble(10, evenement.getLatitude());
        pre.executeUpdate();
        System.out.println("Evenement ajouté");
    }

    public List<Evenement> selectEvenement() throws SQLException {
        List<Evenement> listEvenement = new ArrayList<>();
        String req = "SELECT * FROM evenement where id_patisserie = ?";
        PreparedStatement ste = con.prepareStatement(req);
		PatisserieService ps = new PatisserieService();
		Patisserie p = ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
		ste.setInt(1, p.getId_patisserie());
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listEvenement.add(new Evenement(
                    rs.getInt("id_evenement"),
                    rs.getString("libelle_evenement"),
                    rs.getString("adresse_evenement"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getDouble("longitude"),
                    rs.getDouble("latitude")
            ));
        }
        return listEvenement;
    }

    public void deleteEvenement(int id) throws SQLException {
        String req = "DELETE FROM evenement WHERE id_evenement =?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, id);
        ste.executeUpdate();
    }
    public List<Evenement> selectEvenementById(int id) throws SQLException {
        List<Evenement> listEvenement = new ArrayList<>();
        String req = "SELECT * FROM evenement where id_evenement=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, id);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listEvenement.add(new Evenement(
                    rs.getInt("id_evenement"),
                    rs.getString("libelle_evenement"),
                    rs.getString("adresse_evenement"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getDouble("longitude"),
                    rs.getDouble("latitude")
            ));
        }
        return listEvenement;
    }
    public void modifierEvenement(Evenement evenement, int id) throws SQLException {
        String req = "UPDATE evenement SET libelle_evenement=?,adresse_evenement = ? ,date_debut = ? ,date_fin=?,description=? ,image=?, longitude=?, latitude=?  WHERE id_evenement = ?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setString(1,evenement.getLibelle_evenement());
        ste.setString(2, evenement.getAdresse_evenement());
        ste.setDate(3, evenement.getDate_debut());
        ste.setDate(4, evenement.getDate_fin());
        ste.setString(5,evenement.getDescription());
        ste.setString(6, evenement.getImage());
        ste.setDouble(7, evenement.getLongitude());
        ste.setDouble(8, evenement.getLatitude());
        ste.setInt(9, id);
        ste.executeUpdate();
        Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
        alertSucc.setTitle("Succés");
        alertSucc.setContentText("Opération effectuer avec succés");
        alertSucc.setHeaderText(null);
        alertSucc.show();
    }
}
