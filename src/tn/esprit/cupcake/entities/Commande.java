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
public class Commande {
	private int id_commande;
	private int num_commande;
	private Date date_commande;
	private float prix_totale;
	private int id_panier;
	private String libelle_patisserie;// for internal use

	public Commande() {
	}

	public Commande(int num_commande, Date date_commande, float prix_totale, String libelle_patisserie) {
		this.num_commande = num_commande;
		this.date_commande = date_commande;
		this.prix_totale = prix_totale;
		this.libelle_patisserie = libelle_patisserie;
	}

	public Commande(int num_commande, Date date_commande, float prix_totale, int id_panier, String libelle_patisserie) {
		this.num_commande = num_commande;
		this.date_commande = date_commande;
		this.prix_totale = prix_totale;
		this.id_panier = id_panier;
		this.libelle_patisserie = libelle_patisserie;
	}

	public Commande(int id_commande, int num_commmande, Date date_commande, float prix_totale, int id_panier) {
		this.id_commande = id_commande;
		this.num_commande = num_commmande;
		this.date_commande = date_commande;
		this.prix_totale = prix_totale;
		this.id_panier = id_panier;
	}

	public Commande(int num_commmande, Date date_commande, float prix_totale, int id_panier) {
		this.num_commande = num_commmande;
		this.date_commande = date_commande;
		this.prix_totale = prix_totale;
		this.id_panier = id_panier;
	}

	public int getId_commande() {
		return id_commande;
	}

	public void setId_commande(int id_commande) {
		this.id_commande = id_commande;
	}

	public int getNum_commande() {
		return num_commande;
	}

	public void setNum_commande(int num_commande) {
		this.num_commande = num_commande;
	}

	public Date getDate_commande() {
		return date_commande;
	}

	public void setDate_commande(Date date_commande) {
		this.date_commande = date_commande;
	}

	public float getPrix_totale() {
		return prix_totale;
	}

	public void setPrix_totale(float prix_totale) {
		this.prix_totale = prix_totale;
	}

	public int getId_panier() {
		return id_panier;
	}

	public void setId_panier(int id_panier) {
		this.id_panier = id_panier;
	}

	public String getLibelle_patisserie() {
		return libelle_patisserie;
	}

	public void setLibelle_patisserie(String libelle_patisserie) {
		this.libelle_patisserie = libelle_patisserie;
	}
	
	

	@Override
	public String toString() {
		return "Commande{" + "id_commande=" + id_commande + ", num_commmande=" + num_commande + ", date_commande=" + date_commande + ", prix_totale=" + prix_totale + ", id_panier=" + id_panier + '}';
	}
	
	
}
