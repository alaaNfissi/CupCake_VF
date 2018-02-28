/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import tn.esprit.cupcake.entities.Catalogue;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author esprit
 */
public class CatalogueService {
       Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;

    public CatalogueService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void ajouterCatalogue(Catalogue c) {
        try {
            String req = "INSERT INTO catalogue values(?,?,?,?,?,?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1,c.getId_catalogue());
            pre.setString(2, c.getLibelle_catalogue());
            pre.setDate(3,(java.sql.Date) (Date) c.getDate_debut());
            
            pre.setDate(4, (java.sql.Date) (Date) c.getDate_fin());
            pre.setString(5, c.getDescription());
            pre.setInt(6, c.getId_patisserie());
           
         
            pre.executeUpdate();
            Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
            alertSucc.setTitle("Succés");
            alertSucc.setContentText("catalogue ajouté avec succés");
            alertSucc.setHeaderText(null);
            alertSucc.show();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimerCatalogue(int id_catalogue) throws SQLException {
        String req = "DELETE FROM catalogue WHERE id_catalogue =?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id_catalogue);
        pre.executeUpdate();
    }

    public void modifierCatalogue(Catalogue c, int id_catalogue) throws SQLException {
        String req = "UPDATE `catalogue` SET `libelle_catalogue`=?,`date_debut`=?,`date_fin`=?,`description`=?,`id_patisserie`=? WHERE `id_catalogue`=?";
        PreparedStatement pre = con.prepareStatement(req);
        //Dans l'ordre
        
        pre.setString(1, c.getLibelle_catalogue());
       
        pre.setDate(2, (java.sql.Date) c.getDate_debut());
        pre.setDate(3, (java.sql.Date) c.getDate_fin());
    
        
        pre.setString(4, c.getDescription());
         
        pre.setInt(5, c.getId_patisserie());
        
         pre.setInt(6,id_catalogue);
     
        System.out.println(pre.toString());
        pre.executeUpdate();
        Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
        alertSucc.setTitle("Succés");
        alertSucc.setContentText("catalogue modifié avec succés");
        alertSucc.setHeaderText(null);
        alertSucc.show();
    }
    
}
