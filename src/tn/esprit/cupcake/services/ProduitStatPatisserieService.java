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
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.ProduitStatPatisserie;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author Alaa
 */
public class ProduitStatPatisserieService {
	Connection con = CupCakeDBConnection.getInstance().getConnection();
	private Statement stmt;

	public ProduitStatPatisserieService() {
		try {
            stmt = con.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
	}
	
	public List<ProduitStatPatisserie> produitsVenduParMois(Patisserie p) throws SQLException
	{
		List<ProduitStatPatisserie> listeProduitStatPatisseries=new ArrayList<>();
		String req="select pr.libelle_produit as libelle_produit, SUM(pn.quantite_produit_panier) as quantite,cd.date_commande dateCd FROM panier pn JOIN produit pr on pn.id_produit=pr.id_produit JOIN commande cd ON cd.id_panier=pn.id_panier WHERE pn.id_patisserie = ? GROUP by pn.id_produit";
		PreparedStatement pre=con.prepareStatement(req);
		pre.setInt(1, p.getId_patisserie());
		ResultSet rs=pre.executeQuery();
		while(rs.next())
		{
			ProduitStatPatisserie prsp=new ProduitStatPatisserie();
			prsp.setLibelle_produit(rs.getString("libelle_produit"));
			prsp.setQuantite(rs.getInt("quantite"));
			prsp.setDateVente(rs.getDate("dateCd"));
			listeProduitStatPatisseries.add(prsp);
		}
		return listeProduitStatPatisseries;
	}
	
}
