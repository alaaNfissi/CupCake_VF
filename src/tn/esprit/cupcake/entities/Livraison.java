/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import java.sql.Date;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Alaa
 */
public class Livraison {
	private int  id_livraison;
	private Date date_livraison;
	private float prix_livraison;
	private int etat_livraison;
	private int id_commande;
	private String etatLivraison;
	//private ComboBox<String> etatLivraisonComboBox;
	
	public Livraison() {
	}

	public Livraison(int id_livraison,Date date_livraison, float prix_livraison, int etat_livraison, String etatLivraison,int id_commande/*,ComboBox<String> etatLivraisonComboBox*/) {
		this.id_livraison = id_livraison;
		this.date_livraison = date_livraison;
		this.prix_livraison = prix_livraison;
		this.etat_livraison = etat_livraison;
		switch(etat_livraison)
		{
			case 0:
			this.etatLivraison ="La commande est en cours du traitement";
			break;
			case 1:
			this.etatLivraison ="La commande est en route";
			break;
			case 2:
			this.etatLivraison="La Commande est livrée";
			break;
		}
		this.id_commande=id_commande;
		//this.etatLivraisonComboBox.setValue(etatLivraison);
		
	}
	
	public Livraison(Date date_livraison, float prix_livraison, int etat_livraison, int id_commande) {
		this.date_livraison = date_livraison;
		this.prix_livraison = prix_livraison;
		this.etat_livraison = etat_livraison;
		this.id_commande = id_commande;
	}

	public Livraison(int id_livraison, Date date_livraison, float prix_livraison,int etat_livraison, int id_commande) {
		this.id_livraison = id_livraison;
		this.date_livraison = date_livraison;
		this.prix_livraison = prix_livraison;
		this.etat_livraison = etat_livraison;
		this.id_commande = id_commande;
	}
	
	public int getId_livraison() {
		return id_livraison;
	}

	public void setId_livraison(int id_livraison) {
		this.id_livraison = id_livraison;
	}

	public Date getDate_livraison() {
		return date_livraison;
	}

	public void setDate_livraison(Date date_livraison) {
		this.date_livraison = date_livraison;
	}

	public float getPrix_livraison() {
		return prix_livraison;
	}

	public void setPrix_livraison(float prix_livraison) {
		this.prix_livraison = prix_livraison;
	}

	public int getEtat_livraison() {
		return etat_livraison;
	}

	public void setEtat_livraison(int etat_livraison) {
		this.etat_livraison = etat_livraison;
	}

	public int getId_commande() {
		return id_commande;
	}

	public void setId_commande(int id_commande) {
		this.id_commande = id_commande;
	}

	public String getEtatLivraison() {
		return etatLivraison;
	}

	public void setEtatLivraison(String etatLivraison) {
		this.etatLivraison = etatLivraison;
	}

	/*public ComboBox<String> getEtatLivraisonComboBox() {
		return etatLivraisonComboBox;
	}

	public void setEtatLivraisonComboBox(ComboBox<String> etatLivraisonComboBox) {
		this.etatLivraisonComboBox = etatLivraisonComboBox;
	}*/
	
}
