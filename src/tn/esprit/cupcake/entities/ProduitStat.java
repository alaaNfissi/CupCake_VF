/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

/**
 *
 * @author Alaa
 */
public class ProduitStat {
	private String libelle_patisserieS;
	private String libelle_produitS;
	private float quantiteS;

	public ProduitStat() {
	}
	
	public String getLibelle_patisserieS() {
		return libelle_patisserieS;
	}

	public void setLibelle_patisserieS(String libelle_patisserieS) {
		this.libelle_patisserieS = libelle_patisserieS;
	}

	public String getLibelle_produitS() {
		return libelle_produitS;
	}

	public void setLibelle_produitS(String libelle_produitS) {
		this.libelle_produitS = libelle_produitS;
	}

	public float getQuantiteS() {
		return quantiteS;
	}

	public void setQuantiteS(float quantiteS) {
		this.quantiteS = quantiteS;
	}

	@Override
	public String toString() {
		return "ProduitStat{" + "libelle_patisserieS=" + libelle_patisserieS + ", libelle_produitS=" + libelle_produitS + ", quantiteS=" + quantiteS + '}';
	}
	
	
}
