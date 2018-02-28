/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.services;


//import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import tn.esprit.cupcake.entities.Patisserie;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.cupcake.entities.Commande;
import tn.esprit.cupcake.entities.EtatLivraison;
import tn.esprit.cupcake.entities.Panier;
import tn.esprit.cupcake.entities.Utilisateur;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author Alaa
 */
public class CommandeService {
	 Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;
	
	public void ajouterCommande(Patisserie p,Utilisateur u) throws SQLException
	{
		System.out.println(u);
		System.out.println(p);
		String req = "INSERT INTO commande(num_commande,date_commande,prix_totale,id_panier) values(?,?,?,?)";
		String req1="Select count(*) as numeroCommande from commande where id_panier in (select id_panier from panier where id_patisserie = ?)";
		String req2="Select Max(id_panier) as IdPanier from panier where id_patisserie = ? and id_utilisateur = ? ";
		//String req2="Select sum(prix*?) from produit where id_produit in (select id_produit from panier where id_patisserie = ?)";
		PreparedStatement pre = con.prepareStatement(req);
		PreparedStatement pre1 = con.prepareStatement(req1);
		PreparedStatement pre2 = con.prepareStatement(req2);
		pre1.setInt(1, p.getId_patisserie());
		pre2.setInt(1, p.getId_patisserie());
		pre2.setLong(2, u.getId_utilisateur());
		 ResultSet i = pre1.executeQuery();
		 ResultSet j = pre2.executeQuery();
		 /*System.out.println(j.getInt("IdPanier"));
		 System.out.println(i.getInt("numeroCommande"));*/
		 while(i.next() && j.next())
		 {
			 pre.setInt(1, i.getInt("numeroCommande")+1);
			 pre.setDate(2,java.sql.Date.valueOf(LocalDate.now()));
			 PanierService ps=new PanierService();
			 pre.setFloat(3,ps.prixTotale(ps.searchById(j.getInt("IdPanier"))));
			 pre.setInt(4, j.getInt("IdPanier"));
			 
		 }
		//pre2.setInt(p, );
		pre.executeUpdate();
		String req3="select id_commande from commande where id_panier = ?";
		PreparedStatement pre3= con.prepareStatement(req3);
		pre3.setInt(1, j.getInt("IdPanier"));
		ResultSet rs= pre3.executeQuery();
		while(rs.next())
		{
			LivraisonService ls = new LivraisonService();
			ls.ajouterLivraison(rs.getInt("id_commande"), EtatLivraison.Traitement);
		}
	}
	
	public List<Commande> listeCommandePatisserie(Patisserie p) throws SQLException
	{
		List<Commande> listeCommandes=new ArrayList<>();
		String req="select DISTINCT(cd.id_commande),cd.* from commande cd join panier pn on cd.id_panier=pn.id_panier where pn.id_patisserie = ? ";
		PreparedStatement pre= con.prepareStatement(req);
		pre.setInt(1, p.getId_patisserie());
		PanierService pns=new PanierService();
		ResultSet rs = pre.executeQuery();
		while(rs.next())
		{
			Commande cd = new Commande();
			cd.setId_commande(rs.getInt("id_commande"));
			cd.setNum_commande(rs.getInt("num_commande"));
			cd.setDate_commande(rs.getDate("date_commande"));
			cd.setPrix_totale(rs.getFloat("prix_totale"));
			cd.setId_panier(rs.getInt("id_panier"));
			cd.setLibelle_patisserie(pns.RecupLibellePatisserieById(rs.getInt("id_panier")));
			listeCommandes.add(cd);
		}
		return listeCommandes;
	}
	
	public List<Commande> listeCommandeClient(Utilisateur u) throws SQLException
	{
		List<Commande> listeCommandes=new ArrayList<>();
		String req="select DISTINCT(cd.id_commande), cd.* from commande cd join panier pn on cd.id_panier=pn.id_panier where pn.id_utilisateur = ? order by cd.num_commande desc";
		PreparedStatement pre= con.prepareStatement(req);
		pre.setLong(1, u.getId_utilisateur());
		ResultSet rs = pre.executeQuery();
		PanierService pns=new PanierService();
		while(rs.next())
		{
			Commande cd = new Commande();
			cd.setId_commande(rs.getInt("id_commande"));
			cd.setNum_commande(rs.getInt("num_commande"));
			cd.setDate_commande(rs.getDate("date_commande"));
			cd.setPrix_totale(rs.getFloat("prix_totale"));
			cd.setId_panier(rs.getInt("id_panier"));
			cd.setLibelle_patisserie(pns.RecupLibellePatisserieById(rs.getInt("id_panier")));
			listeCommandes.add(cd);
		}
		return listeCommandes;
	}
	
	public void supprimerCommande(int id_commande) throws SQLException
	{
		String req = "Delete from commande where id_commande= ?";
		PreparedStatement pre=con.prepareStatement(req);
		pre.setInt(1, id_commande);
		pre.executeUpdate();
		System.out.println("commande supprimée !!!");
	}
	
	public Commande searchById(int id) throws SQLException
	{
		String req="select * from commande where id_commande = ?";
		PreparedStatement pre=con.prepareStatement(req);
		pre.setInt(1,id);
		ResultSet rs = pre.executeQuery();
		while(rs.next())
		{
			Commande cd = new Commande();
			cd.setId_commande(rs.getInt("id_commande"));
			cd.setNum_commande(rs.getInt("num_commande"));
			cd.setDate_commande(rs.getDate("date_commande"));
			cd.setPrix_totale(rs.getFloat("prix_totale"));
			cd.setId_panier(rs.getInt("id_panier"));
			return cd;
		}
		return  null;
	}
	
}


