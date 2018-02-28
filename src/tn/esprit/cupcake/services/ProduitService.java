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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import tn.esprit.cupcake.entities.*;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author Basly
 */
public class ProduitService {

    Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;

    public ProduitService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
//////////////////////////////SOUHE///////////////////////////////////////////////
	
	
	
	
    public void ajouterProduit(Produit p) {
        try {
            String req = "INSERT INTO produit values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, p.getId_produit());
            pre.setString(2, p.getLibelle_produit());
            pre.setString(3, p.getCategorie());
            pre.setFloat(4, p.getPrix());
            pre.setDate(5, (Date) p.getDate_expiration());
            pre.setInt(6, p.getQuantite());
            pre.setString(7, p.getDescription());
            pre.setInt(8, p.getNote());
            pre.setString(9, p.getImage());
            pre.setInt(10, p.getId_patisserie());
            pre.executeUpdate();
            Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
            alertSucc.setTitle("Succés");
            alertSucc.setContentText("Produit ajouté avec succés");
            alertSucc.setHeaderText(null);
            alertSucc.show();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimerProduit(int id_produit) throws SQLException {
        String req = "DELETE FROM produit WHERE id_produit =?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id_produit);
        pre.executeUpdate();
    }

    public void modifierProduit(Produit p, int id_produit) throws SQLException {
        String req = "UPDATE produit SET libelle_produit=?,categorie=?,prix=?,date_expiration=?,quantite=?,description=?,note=?,image=?,id_patisserie=? WHERE id_produit=?";
        PreparedStatement pre = con.prepareStatement(req);
        //Dans l'ordre
        
        pre.setString(1, p.getLibelle_produit());
        System.out.println("In Method");
        pre.setString(2, p.getCategorie());
         System.out.println("In Method1");
        pre.setFloat(3, p.getPrix());
         System.out.println("In Method2");
        pre.setDate(4, (Date) p.getDate_expiration());
         System.out.println("In Method3");
        pre.setInt(5, p.getQuantite());
         System.out.println("In Method4");
        pre.setString(6, p.getDescription());
         System.out.println("In Method5");
        pre.setInt(7, p.getNote());
         System.out.println("In Method6");
        pre.setString(8, p.getImage());
         System.out.println("In Method7");
        pre.setInt(9, p.getId_patisserie());
         System.out.println("In Method8");
        pre.setInt(10,id_produit);
         System.out.println("In Method9");
        System.out.println(pre.toString());
        pre.executeUpdate();
        Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
        alertSucc.setTitle("Succés");
        alertSucc.setContentText("Produit modifié avec succés");
        alertSucc.setHeaderText(null);
        alertSucc.show();
    }	
	
	
	
	
	
	
	
///////////////////RAHMA//////////////////////////////////////////////////////////
    public List<Produit> selectProduit(String categorie) throws SQLException {
        List<Produit> listProduit = new ArrayList<>();
        String req = "SELECT * FROM produit where categorie=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setString(1, categorie);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listProduit.add(new Produit(
                    rs.getInt("id_produit"),
                    rs.getString("libelle_produit"),
                    rs.getFloat("prix"),
                    rs.getDate("date_expiration"),
                    rs.getInt("quantite")
            ));
        }
        return listProduit;
    }
    public List<Produit> selectAllProduit() throws SQLException {
        List<Produit> listProduit = new ArrayList<>();
        String req = "SELECT * FROM produit";
        PreparedStatement ste = con.prepareStatement(req);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listProduit.add(new Produit(
                    rs.getInt("id_produit"),
                    rs.getString("libelle_produit"),
                    rs.getFloat("prix"),
                    rs.getDate("date_expiration"),
                    rs.getInt("quantite")
            ));
        }
        return listProduit;
    }

    public List<String> selectCategorieProduit() throws SQLException {
        List<String> listProduit = new ArrayList<>();
        String req = "SELECT DISTINCT categorie FROM produit";
        PreparedStatement ste = con.prepareStatement(req);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listProduit.add(
                    rs.getString("categorie")
            );
        }
        return listProduit;
    }
    public List<Produit> selectProduitById(int id) throws SQLException {
        List<Produit> listProduit = new ArrayList<>();
        String req = "SELECT * FROM produit where id_produit=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setInt(1, id);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listProduit.add(new Produit(
                    rs.getInt("id_produit"),
                    rs.getString("libelle_produit"),
                    rs.getFloat("prix"),
                    rs.getDate("date_expiration"),
                    rs.getInt("quantite"),
					rs.getInt("id_patisserie")
            ));
        }
        return listProduit;
    }
}
