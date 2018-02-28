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
import tn.esprit.cupcake.entities.Panier;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author berber
 */
public class PanierService {

    Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;

    public PanierService() {
        try {
            stmt = con.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void ajouterPanier(Panier p) throws SQLException {
        String req = "INSERT INTO Panier values(?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, p.getId_panier());
        pre.setInt(3, p.getId_patisserie());
        pre.setLong(4, p.getId_utilisateur());
        pre.executeUpdate();
        System.out.println("Panier ajouté avec succées");
    }

	 public List<String> affichePanier(long id_utilisateur) {
        List<String> listProduit = new ArrayList<>();
        try {
            String req = "SELECT * FROM panier p join produit pro on p.id_produit=pro.id_produit where p.id_utilisateur=?" ;
            PreparedStatement ste = con.prepareStatement(req);
            ste.setLong(1,id_utilisateur);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                listProduit.add(rs.getString("libelle_produit"));
                //int id = rs.getInt("id_produit");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduit;

    }
	 
    public int RecupProduitId (String item) throws SQLException {
            try {
            String req = "SELECT id_produit FROM produit where libelle_produit=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setString(1,item);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getInt("id_produit");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
    }
        public String RecupLibelle (String item) throws SQLException {
            try {
            String req = "SELECT libelle_patisserie FROM patisserie p1 join produit p2 where p2.libelle_produit=? and p1.id_patisserie=p2.id_patisserie";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setString(1,item);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getString("libelle_patisserie");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return "" ;
    }
    
      public int panierutilisateur (long id_utilisateur) throws SQLException {
            try {
            String req = "SELECT id_panier FROM panier where id_utilisateur=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setLong(1,id_utilisateur);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getInt("id_panier");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
        }
	  
	  public void  creationPanier (Panier panier) throws SQLException {

        String req2 = "INSERT INTO Panier values(?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req2);
        pre.setInt(1,panier.getId_panier());
        pre.setInt(2,panier.getId_patisserie());
        pre.setLong(3,panier.getId_utilisateur());
        pre.setInt(4,panier.getQuantite_panier_produit());
        pre.setInt(5,panier.getId_produit());
         pre.executeUpdate();
        System.out.println("Le Produit avec l'id ="+panier.getId_produit()+" "+"a été ajouté avec succés");
       
    }
    public void  ajouterProduitPanier (Panier panier) throws SQLException {
//        List<Produit> listProduit = new ArrayList<>();
//        try {
//            String req = "SELECT id_produit FROM produit where libelle_produit="+item;
//            PreparedStatement ste = con.prepareStatement(req);
//            ResultSet rs = ste.executeQuery();
//            while (rs.next()) {
//                
//                listProduit.add(new Produit(rs.getInt("id_produit"),rs.getInt("id_patisserie")));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        String req2 = "INSERT INTO Panier values(?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req2);
        pre.setInt(1,panier.getId_panier());
        pre.setInt(2,panier.getId_patisserie());
        pre.setLong(3,panier.getId_utilisateur());
        pre.setInt(4,panier.getQuantite_panier_produit());
        pre.setInt(5,panier.getId_produit());
         pre.executeUpdate();
        System.out.println("Le Produit avec l'id ="+panier.getId_produit()+" "+"a été ajouté avec succés");
       
    }
    /*public List<String> afficheProduit() {
        List<String> listProduit = new ArrayList<>();
        try {
            String req = "SELECT * FROM produit";
            PreparedStatement ste = con.prepareStatement(req);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                listProduit.add(rs.getString("libelle_produit"));
                //int id = rs.getInt("id_produit");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduit;

    }*/
      public List<String> afficheProduit( int id_patisserie) {
        List<String> listProduit = new ArrayList<>();
        try {
            String req = "SELECT * FROM produit where id_patisserie=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setInt(1,id_patisserie);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                listProduit.add(rs.getString("libelle_produit"));
                //int id = rs.getInt("id_produit");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduit;

    }
	  
	  public float affichePrixTotal(long item )  throws SQLException {
            try {
            String req = "SELECT SUM(pro.prix*pa.quantite_produit_panier) FROM produit pro join panier pa where pa.id_produit=pro.id_produit and pa.id_utilisateur=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setLong(1,item);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getFloat("SUM(pro.prix*pa.quantite_produit_panier)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
    }   
	  
	   public int RecupQuantitéProduit(String item )  throws SQLException {
            try {
            String req = "SELECT quantite FROM produit where libelle_produit=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setString(1,item);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getInt("quantite");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
    }
	   
    public float affichePrix(String item )  throws SQLException {
            try {
            String req = "SELECT prix FROM produit where libelle_produit=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setString(1,item);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getFloat("prix");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
    }
          public int panierproduitQuantité (int item) throws SQLException {
            try {
            String req = "SELECT quantite_produit_panier FROM panier where id_produit=?";
            PreparedStatement ste = con.prepareStatement(req);
             ste.setInt(1,item);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getInt("quantite_produit_panier");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
        }      
    
    
    public void  supprimerProduitPanier (int item) throws SQLException {
        
        String req = "DELETE FRom Panier where id_produit=?" ;
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,item);
         pre.executeUpdate();
        System.out.println("Le produit id ="+item+" "+"a éte supprimé avec succés");
        
    }
	public float prixTotale(Panier p) throws SQLException
	{
		//String req="select sum(prix*?) from produit where id_produit in (select id_produit from panier where id_panier = ?";
		String req="select pr.prix*pa.quantite_produit_panier as prixTotPr from produit pr join panier pa on pr.id_produit=pa.id_produit where pr.id_produit in (select id_produit from panier where id_panier = ?)";
		PreparedStatement pre = con.prepareStatement(req);
		pre.setInt(1, p.getId_panier());
		ResultSet rs=pre.executeQuery();
		while(rs.next()){
			return 	rs.getFloat("prixTotPr");
		}
		return -1;
	}
	
	public Panier searchById(int id) throws SQLException
	{
		String req ="select * from panier where id_panier= ?";
		PreparedStatement pre= con.prepareStatement(req);
		pre.setInt(1, id);
		ResultSet rs = pre.executeQuery();
		while(rs.next())
		{
			Panier panier =new Panier();
			panier.setId_panier(rs.getInt("id_panier"));
			panier.setId_patisserie(rs.getInt("id_patisserie"));
			panier.setId_utilisateur(rs.getLong("id_utilisateur"));
			String req1= "Select * from produit where id_produit=?";
			PreparedStatement pre1= con.prepareStatement(req1);
			pre1.setInt(1, rs.getInt("id_produit"));
			ResultSet rs1=pre1.executeQuery();
			while(rs1.next())
			{
				Produit p =new Produit();
				p.setId_produit(rs1.getInt("id_produit"));
				p.setLibelle_produit(rs1.getString("libelle_produit"));
				p.setCategorie(rs1.getString("categorie"));
				p.setPrix(rs1.getFloat("prix"));
				p.setDate_expiration(rs1.getDate("date_expiration"));
				p.setQuantite(rs1.getInt("quantite"));
				p.setDescription(rs1.getString("description"));
				p.setNote(rs1.getInt("note"));
				p.setImage(rs1.getString("image"));
				p.setId_patisserie(rs1.getInt("id_patisserie"));
				panier.ajouterProduit(p);
			}
			return panier;
		}
		return  null;
	}
	
	public String RecupLibellePatisserieById(int id_panier) throws SQLException
	{
		String req="select libelle_patisserie as LibellePatisserie from patisserie pr join panier pn on pr.id_patisserie=pn.id_patisserie where pn.id_panier= ?";
		PreparedStatement pre=con.prepareStatement(req);
		pre.setInt(1, id_panier);
		ResultSet rs = pre.executeQuery();
		while(rs.next())
		{
			return  rs.getString("LibellePatisserie");
		}
		return "";
	}
	
	public List<Produit> listeProduitsPanier(Panier p) throws SQLException
	{
		List<Produit> listeProduits = new ArrayList<>();
		String req="Select * from produit where id_produit in (select id_produit from panier where id_panier = ?)";
		PreparedStatement pre=con.prepareStatement(req);
		pre.setInt(1, p.getId_panier());
		ResultSet rs=pre.executeQuery();
		while(rs.next())
		{
			Produit produit= new Produit();
			produit.setId_produit(rs.getInt("id_produit"));
			produit.setLibelle_produit(rs.getString("libelle_produit"));
			produit.setCategorie(rs.getString("categorie"));
			produit.setPrix(rs.getFloat("prix"));
			produit.setDate_expiration(rs.getDate("date_expiration"));
			produit.setQuantite(rs.getInt("quantite"));
			produit.setDescription(rs.getString("description"));
			produit.setNote(rs.getInt("note"));
			produit.setImage(rs.getString("image"));
			produit.setId_patisserie(rs.getInt("id_patisserie"));
			listeProduits.add(produit);
		}
		return listeProduits;
	}
	
	
}
