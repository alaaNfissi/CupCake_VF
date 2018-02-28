/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import java.sql.Date;

/**
 *
 * @author Alaa
 */
public class ProduitStatPatisserie {
	private String libelle_produit;
	private int quantite;
	private Date dateVente;

	public ProduitStatPatisserie() {
	}

	public String getLibelle_produit() {
		return libelle_produit;
	}

	public void setLibelle_produit(String libelle_produit) {
		this.libelle_produit = libelle_produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Date getDateVente() {
		return dateVente;
	}

	public void setDateVente(Date dateVente) {
		this.dateVente = dateVente;
	}

	@Override
	public String toString() {
		return "ProduitStatPatisserie{" + "libelle_produit=" + libelle_produit + ", quantite=" + quantite + ", dateVente=" + dateVente + '}';
	}
	
	
}
