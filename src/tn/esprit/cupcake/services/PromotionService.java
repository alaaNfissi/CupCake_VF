/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.entities.Promotion;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author Basly
 */
public class PromotionService {

    Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;

    public PromotionService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void ajouterPromotion(Promotion promotion) throws SQLException {
        String req = "INSERT INTO promotion (id_promotion,libelle_promotion,pourcentage,date_debut,date_fin,description,image,id_patisserie)values(?,?,?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, promotion.getId_promotion());
        pre.setString(2, promotion.getLibelle_promotion());
        pre.setInt(3, promotion.getPourcentage());
        pre.setDate(4, (Date) promotion.getDate_debut());
        pre.setDate(5, (Date) promotion.getDate_fin());
        pre.setString(6, promotion.getDescription());
        pre.setString(7, promotion.getImage());
        pre.setInt(8, promotion.getId_patisserie());
        pre.executeUpdate();
        System.out.println("Promotion ajouté");
        Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
        alertSucc.setTitle("Succés");
        alertSucc.setContentText("Opération effectuer avec succés");
        alertSucc.setHeaderText(null);
        alertSucc.show();
    }

    public int selectPromotionAjouter(Promotion promotion) throws SQLException {
        String req = "SELECT * FROM promotion where libelle_promotion=? and date_debut=? and date_fin=?  and description=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setString(1, promotion.getLibelle_promotion());
        ste.setDate(2, (Date) promotion.getDate_debut());
        ste.setDate(3, (Date) promotion.getDate_fin());
        ste.setString(4, promotion.getDescription());
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            return rs.getInt("id_promotion");
        }
        return -1;
    }

    public void ajouterProduitPromotion(int id_produit, int id_promotion , Float prix_promotion) throws SQLException {
        String req = "INSERT INTO produit_promotion values(?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id_produit);
        pre.setInt(2, id_promotion);
        pre.setFloat(3,prix_promotion);
        pre.execute();
    }    
    public void deleteProduitPromotion(int id) throws SQLException {
        String req = "DELETE FROM produit_promotion WHERE id_promotion =?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, id);
        ste.executeUpdate();
        System.out.println("DONEEEE DELETED");
    }

    public List<Integer> selectProduitPromotion(Promotion promotion, int id) throws SQLException {
        List<Integer> listIdProduit = new ArrayList<>();
        String req = "SELECT id_produit FROM produit_promotion where id_promotion=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, promotion.getId_promotion());
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listIdProduit.add(
                    rs.getInt("id_produit")
            );
        }
        return listIdProduit;
    }
    public List<Produit> selectProduitPromotion2(Promotion promotion, int id) throws SQLException {
        List<Produit> listIdProduit = new ArrayList<>();
        String req = "SELECT id_produit FROM produit_promotion where id_promotion=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, promotion.getId_promotion());
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listIdProduit.add(new Produit(
                    rs.getInt("id_produit"))
            );
        }
        return listIdProduit;
    }

    public List<Promotion> selectPromotion() throws SQLException {
        List<Promotion> listPromotion = new ArrayList<>();
        String req = "SELECT * FROM promotion where id_patisserie = ?";
        PreparedStatement ste = con.prepareStatement(req);
		PatisserieService ps=new PatisserieService();
		Patisserie p =ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
		ste.setInt(1, p.getId_patisserie());
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listPromotion.add(new Promotion(
                    rs.getInt("id_promotion"),
                    rs.getString("libelle_promotion"),
                    rs.getInt("pourcentage"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getString("description"),
                    rs.getString("image")
            ));
        }
        return listPromotion;
    }
    public List<Promotion> selectPromotionByID(int id) throws SQLException {
        List<Promotion> listPromotion = new ArrayList<>();
        String req = "SELECT * FROM promotion where id_promotion=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, id);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listPromotion.add(new Promotion(
                    rs.getString("libelle_promotion"),
                    rs.getInt("pourcentage"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getString("description"),
                    rs.getString("image")
            ));
        }
        return listPromotion;
    }
    public void deletePromotion(int id) throws SQLException {
        String req = "DELETE FROM promotion WHERE id_promotion =?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, id);
        ste.executeUpdate();
    }
      public void modifierPromotion(Promotion promotion, int id) throws SQLException {
        String req = "UPDATE promotion SET libelle_promotion=?,pourcentage = ? ,date_debut = ? ,date_fin=?,description=? ,image=? WHERE id_promotion = ?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setString(1,promotion.getLibelle_promotion());
        ste.setInt(2,promotion.getPourcentage());
        ste.setDate(3, (Date) promotion.getDate_debut());
        ste.setDate(4, (Date) promotion.getDate_fin());
        ste.setString(5,promotion.getDescription());
        ste.setString(6, promotion.getImage());
        ste.setInt(7, id);
        ste.executeUpdate();
        Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
        alertSucc.setTitle("Succés");
        alertSucc.setContentText("Opération effectuer avec succés");
        alertSucc.setHeaderText(null);
        alertSucc.show();
    }

}
