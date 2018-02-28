/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.services;

import java.awt.image.RescaleOp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.scene.control.ComboBox;
import tn.esprit.cupcake.entities.Commande;
import tn.esprit.cupcake.entities.EtatLivraison;
import tn.esprit.cupcake.entities.Livraison;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author Alaa
 */
public class LivraisonService {
	Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;
	
	public LivraisonService() {
	}
	
	public void ajouterLivraison(int id_commande,EtatLivraison etatLivraison) throws SQLException{
		String req="Insert into livraison(date_livraison,prix_livraison,etat_livraison,id_commande) values(?,?,?,?)";
		PreparedStatement pre=con.prepareStatement(req);
		pre.setDate(1, java.sql.Date.valueOf(LocalDate.now().plusDays(2)));
		CommandeService cs= new CommandeService();
		pre.setFloat(2, (float)(cs.searchById(id_commande).getPrix_totale() *0.1));
		pre.setInt(3, etatLivraison.ordinal());
		pre.setInt(4, id_commande);
		pre.executeUpdate();
	}
	
	public Livraison searchById(int id_livraison) throws SQLException
	{
		String req="select * from livraison where id_livraison=?";
		PreparedStatement pre= con.prepareStatement(req);
		pre.setInt(1, id_livraison);
		ResultSet rs = pre.executeQuery();
		while(rs.next())
		{
			Livraison livraison = new Livraison();
			livraison.setId_livraison(rs.getInt("id_livraison"));
			livraison.setDate_livraison(rs.getDate("date_livraison"));
			livraison.setPrix_livraison(rs.getFloat("prix_livraison"));
			livraison.setEtat_livraison(rs.getInt("etat_livraison"));
			livraison.setId_commande(rs.getInt("id_commande"));
			return livraison;
		}
		return null;
	}
	public String EtatLivraison(int etat_livraison)
	{
		
		switch(etat_livraison)
		{
			case 0:
			return "La commande est en cours du traitement";
			case 1:
			return "La commande est en route";
			case 2:
			return "La Commande est livrée";
			default:
				return "";
		}
	}
	public Livraison searchByCommandeId(int id_commande) throws SQLException
	{
		String req="select * from livraison where id_commande = ?";
		PreparedStatement pre= con.prepareStatement(req);
		pre.setInt(1, id_commande);
		ResultSet rs = pre.executeQuery();
		System.out.println(rs);
		while(rs.next())
		{
			System.out.println("test1");
			Livraison livraison = new Livraison();
			livraison.setId_livraison(rs.getInt("id_livraison"));
			livraison.setDate_livraison(rs.getDate("date_livraison"));
			livraison.setPrix_livraison(rs.getFloat("prix_livraison"));
			livraison.setEtat_livraison(rs.getInt("etat_livraison"));
			livraison.setEtatLivraison(EtatLivraison(rs.getInt("etat_livraison")));
			livraison.setId_commande(rs.getInt("id_commande"));
			/*ComboBox<String> cmb=new ComboBox<>();
			cmb.setValue(EtatLivraison(rs.getInt("etat_livraison")));
			livraison.setEtatLivraisonComboBox(cmb);*/
			return livraison;
		}
		return null;
	}
	public void changerEtatLivraison(Livraison l,int i) throws SQLException
	{
		String req ="UPDATE livraison SET etat_livraison = ? WHERE id_livraison = ?";
		PreparedStatement pre = con.prepareStatement(req);
		pre.setInt(1, i);
		pre.setInt(2, l.getId_livraison());
		pre.executeUpdate();
	}
}
