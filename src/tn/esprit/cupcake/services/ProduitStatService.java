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
import tn.esprit.cupcake.entities.ProduitStat;
import tn.esprit.cupcake.entities.Utilisateur;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author Alaa
 */
public class ProduitStatService {
	Connection con = CupCakeDBConnection.getInstance().getConnection();
	private Statement stmt;

	public ProduitStatService() 
	{
		try {
            stmt = con.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
	}
	
	public List<ProduitStat> RegrouperProduitsParPatisserie(Utilisateur u) throws SQLException
	{
		List<ProduitStat> listeProduitStats=new ArrayList<>();
		String req="SELECT pr.libelle_produit as libelle_produit ,pt.libelle_patisserie as libelle_patisserie,sum(pn.quantite_produit_panier) as quantite FROM panier pn JOIN patisserie pt ON pn.id_patisserie=pt.id_patisserie JOIN produit pr on pn.id_produit=pr.id_produit WHERE pn.id_utilisateur = ? GROUP by pn.id_produit";
		PreparedStatement pre =  con.prepareStatement(req);
		pre.setLong(1, u.getId_utilisateur());
		ResultSet rs=pre.executeQuery();
		while(rs.next())
		{
			ProduitStat ps=new ProduitStat();
			ps.setLibelle_patisserieS(rs.getString("libelle_patisserie"));
			ps.setLibelle_produitS(rs.getString("libelle_produit"));
			ps.setQuantiteS(rs.getFloat("quantite"));
			listeProduitStats.add(ps);
		}
		return listeProduitStats;
	}
	
}
