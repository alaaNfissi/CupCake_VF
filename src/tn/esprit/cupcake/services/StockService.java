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
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.entities.Stock;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author berber
 */
public class StockService {
    Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;

    public StockService() {
        try {
            stmt = con.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void ajouterProduitStock(Stock stock)    throws SQLException {
        String req = "INSERT INTO Stock values(?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, stock.getId_produit());
        pre.setInt(2, stock.getId_patisserie());
        pre.executeUpdate();
        System.out.println("Produit id="+""+stock.getId_produit()+" "+ "a ajouté dans le stock avec succés");
    }
    
    
    
    

    
    public List<Produit> afficheProduitStock( int id_patisserie) {
        List<Produit> listProduit = new ArrayList<>();
        try {
            String req = "Select * from produit where id_produit not in (select id_produit from stock ) and id_patisserie=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setInt(1,id_patisserie);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                listProduit.add(new Produit(rs.getInt("id_produit"),
                        rs.getString("libelle_produit"),
                        rs.getString("categorie"),
                        rs.getFloat("prix"),
                        rs.getDate("date_expiration"),
                        rs.getInt("quantite"),
                        rs.getString("description")));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduit;

    }
    
    
    public int RecupProduitId (String item) throws SQLException {
            try {
            String req = "SELECT id_produit FROM produit where libelle_produit=? and id_patisserie=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setString(1,item);
			PatisserieService ps=new PatisserieService();
			Patisserie pt= ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
			ste.setInt(2,pt.getId_patisserie());
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getInt("id_produit");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
    }
    public int RecupPatissierId (String item) throws SQLException {
            try {
            String req = "SELECT id_patisserie FROM produit where libelle_produit=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setString(1,item);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getInt("id_patisserie");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
    }
        public void  supprimerProduitStock (int item) throws SQLException {
        
        String req = "DELETE FRom Produit where id_produit=?" ;
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,item);
         pre.executeUpdate();
        System.out.println("Le produit id ="+item+" "+"a éte supprimé avec succés");
        
    }
    
    
    
     public List<Produit> afficheTousProduitStock( int id_patisserie) {
        List<Produit> listProduit = new ArrayList<>();
        try {
            String req = "SELECT * FROM produit p join stock s where s.id_produit=p.id_produit and p.id_patisserie=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setInt(1,id_patisserie);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                listProduit.add(new Produit(rs.getInt("id_produit"),
                        rs.getString("libelle_produit"),
                        rs.getString("categorie"),
                        rs.getFloat("prix"),
                        rs.getDate("date_expiration"),
                        rs.getInt("quantite"),
                        rs.getString("description"),
                        rs.getString("image")));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduit;

    }
    
     
     
     
     
    ///// test produit 
    
    
     public void modifierProduitStock(Produit p,int item) throws SQLException {
        String req = "UPDATE produit p join stock s on p.id_produit=s.id_produit SET libelle_produit = ? , categorie = ?, prix = ?,date_expiration=?, quantite = ?, description = ? where p.id_produit=?";
        PreparedStatement pre = con.prepareStatement(req);
        
        pre.setString(1, p.getLibelle_produit());
        
        pre.setString(2, p.getCategorie());
         
        pre.setFloat(3, p.getPrix());
         
         pre.setDate(4, p.getDate_expiration());
         
        pre.setInt(5, p.getQuantite());
         
        pre.setString(6, p.getDescription());
         
         pre.setInt(7,item);
         
       
        pre.executeUpdate();
        
    }
    
     
     public void ajouterProduitdepuisStock(Produit p,int item) throws SQLException {
        String req = "UPDATE produit SET date_expiration=?, quantite = ? where id_produit=?";
        PreparedStatement pre = con.prepareStatement(req);
         pre.setDate(1,p.getDate_expiration());
         pre.setInt(2,p.getQuantite());
         pre.setInt(3,item);
         System.out.println(pre.toString());
        pre.executeUpdate();
     
     
     
    
    }
    

}