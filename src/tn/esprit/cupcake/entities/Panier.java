/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berber
 */
public class Panier {
    private int id_panier;
    private int id_patisserie;
    private long id_utilisateur;
    private int quantite_panier_produit;
    private int id_produit;
    private List<Produit> listeProduit = new ArrayList <>();

    public Panier(int id_patisserie, long id_utilisateur, int quantite_panier_produit, int id_produit) {
        this.id_patisserie = id_patisserie;
        this.id_utilisateur = id_utilisateur;
        this.quantite_panier_produit = quantite_panier_produit;
        this.id_produit = id_produit;
    }
	
	public Panier(int id_panier, int id_patisserie, long id_utilisateur, int quantite_panier_produit, int id_produit) {
        this.id_panier = id_panier;
        this.id_patisserie = id_patisserie;
        this.id_utilisateur = id_utilisateur;
        this.quantite_panier_produit = quantite_panier_produit;
        this.id_produit = id_produit;
    }
    
	public Panier(int id_panier, int id_patisserie, long id_utilisateur) {
        this.id_panier = id_panier;
        this.id_patisserie = id_patisserie;
        this.id_utilisateur = id_utilisateur;
    }
  
   

    public Panier() {
    }

    public List<Produit> getListeProduit() {
        return listeProduit;
    }

    public void setListeProduit(List<Produit> listeProduit) {
        this.listeProduit = listeProduit;
    }
    
    
    

    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }


    public int getId_patisserie() {
        return id_patisserie;
    }

    public void setId_patisserie(int id_patisserie) {
        this.id_patisserie = id_patisserie;
    }

    public long getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(long id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public int getQuantite_panier_produit() {
        return quantite_panier_produit;
    }

    public void setQuantite_panier_produit(int quantite_panier_produit) {
        this.quantite_panier_produit = quantite_panier_produit;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }
    
    public void ajouterProduit(Produit p)
	{
		listeProduit.add(p);
	}

	@Override
	public String toString() {
		String str1="";
		String str= "Panier{" + "id_panier=" + id_panier + ", id_patisserie=" + id_patisserie + ", id_utilisateur=" + id_utilisateur + ", quantite_panier_produit=" + quantite_panier_produit + ", id_produit=" + id_produit +'}';
		for(int i=0;i<listeProduit.size();i++)
		{
			 str1 +=listeProduit.get(i).toString();
		}
		return str+str1;
	}
    
}
