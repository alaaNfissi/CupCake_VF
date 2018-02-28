/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.CheckBox;



/**
 *
 * @author berber
 */
public class Produit {
    private int id_produit;
    private String libelle_produit;
    private String categorie;
    private float prix;
    private Date date_expiration;
    private int quantite;
    private String description;
    private int note;
    private String image;
    private int id_patisserie;
	private CheckBox select ;
	 private Float prixPromotion;
    
    public Produit() {
    }

    public Produit(int id_produit, int id_patisserie) {
        this.id_produit = id_produit;
        this.id_patisserie = id_patisserie;
    }

    public Produit(int id_produit,String libelle_produit, float prix,Date date_expiration,int quantite,Float prixPromotion) {
        this.id_produit=id_produit;
        this.libelle_produit = libelle_produit;
        this.prix = prix;
        this.date_expiration=date_expiration;
        this.quantite=quantite;
		this.prixPromotion=prixPromotion;
        this.select = new CheckBox();
    }
    public Produit(int id_produit, String lebelle_produit, String categorie, float prix, Date date_expiration, int quantite, String description, int note, String image,int id_patisserie) {
        this.id_produit = id_produit;
        this.libelle_produit = lebelle_produit;
        this.categorie = categorie;
        this.prix = prix;
        this.date_expiration = date_expiration;
        this.quantite = quantite;
        this.description = description;
        this.note = note;
        this.image = image;
        this.id_patisserie=id_patisserie;
    }

	public Produit(String libelle_produit, String categorie, float prix, int quantite, String description, int note, String image, int id_patisserie) {
		this.libelle_produit = libelle_produit;
		this.categorie = categorie;
		this.prix = prix;
		this.quantite = quantite;
		this.description = description;
		this.note = note;
		this.image = image;
		this.id_patisserie = id_patisserie;
	}

	public Produit(int id_produit, String libelle_produit, String categorie, float prix, Date date_expiration, int quantite, String description, String image) {
		this.id_produit = id_produit;
		this.libelle_produit = libelle_produit;
		this.categorie = categorie;
		this.prix = prix;
		this.date_expiration = date_expiration;
		this.quantite = quantite;
		this.description = description;
		this.image = image;
	}

	public Produit(int id_produit, String libelle_produit, String categorie, float prix, int quantite, String description, int note, String image, int id_patisserie) {
		this.id_produit = id_produit;
		this.libelle_produit = libelle_produit;
		this.categorie = categorie;
		this.prix = prix;
		this.quantite = quantite;
		this.description = description;
		this.note = note;
		this.image = image;
		this.id_patisserie = id_patisserie;
	}

	public Produit(String libelle_produit, String categorie, float prix, Date date_expiration, int quantite, String description, int note, String image, int id_patisserie) {
		this.libelle_produit = libelle_produit;
		this.categorie = categorie;
		this.prix = prix;
		this.date_expiration = date_expiration;
		this.quantite = quantite;
		this.description = description;
		this.note = note;
		this.image = image;
		this.id_patisserie = id_patisserie;
	}

	public Produit(int id_produit, String libelle_produit, String categorie, float prix, Date date_expiration, int quantite, String description) {
		this.id_produit = id_produit;
		this.libelle_produit = libelle_produit;
		this.categorie = categorie;
		this.prix = prix;
		this.date_expiration = date_expiration;
		this.quantite = quantite;
		this.description = description;
	}

	public Produit(int id_produit, String libelle_produit, String categorie, float prix, Date date_expiration, String description, int id_patisserie) {
		this.id_produit = id_produit;
		this.libelle_produit = libelle_produit;
		this.categorie = categorie;
		this.prix = prix;
		this.date_expiration = date_expiration;
		this.description = description;
		this.id_patisserie = id_patisserie;
	}

	public Produit(int id_produit, String libelle_produit, float prix, Date date_expiration, int quantite) {
		this.id_produit = id_produit;
		this.libelle_produit = libelle_produit;
		this.prix = prix;
		this.date_expiration = date_expiration;
		this.quantite = quantite;
		this.select=new CheckBox();
		
	}
    
	
    public Produit(int id_produit, String libelle_produit) {
        this.id_produit = id_produit;
        this.libelle_produit = libelle_produit;
    }

	public Produit(int id_produit) {
		this.id_produit = id_produit;
	}

	public Produit(String libelle_produit, int id_patisserie) {
		this.libelle_produit = libelle_produit;
		this.id_patisserie = id_patisserie;
	}

	public Produit(Date date_expiration, int quantite) {
		this.date_expiration = date_expiration;
		this.quantite = quantite;
	}

	public Produit(String libelle_produit, String categorie, float prix, Date date_expiration, int quantite, String description) {
		this.libelle_produit = libelle_produit;
		this.categorie = categorie;
		this.prix = prix;
		this.date_expiration = date_expiration;
		this.quantite = quantite;
		this.description = description;
	}
	/*rs.getInt("id_produit"),
                    rs.getString("libelle_produit"),
                    rs.getFloat("prix"),
                    rs.getDate("date_expiration"),
                    rs.getInt("quantite"),
					rs.getInt("id_patisserie");*/

	public Produit(int id_produit, String libelle_produit, float prix, Date date_expiration, int quantite, int id_patisserie) {
		this.id_produit = id_produit;
		this.libelle_produit = libelle_produit;
		this.prix = prix;
		this.date_expiration = date_expiration;
		this.quantite = quantite;
		this.id_patisserie = id_patisserie;
		this.select = new CheckBox();
	}

	
    public Produit(String libelle_produit) {
        this.libelle_produit = libelle_produit;
    }
         

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getLibelle_produit() {
        return libelle_produit;
    }

    public void setLibelle_produit(String libelle_produit) {
        this.libelle_produit = libelle_produit;
    }

    

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Date getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(Date date_expiration) {
        this.date_expiration = date_expiration;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    

    public int getId_patisserie() {
        return id_patisserie;
    }

    public void setId_patisserie(int id_patisserie) {
        this.id_patisserie = id_patisserie;
    }
	 public CheckBox getSelect(){
    return select;
    }
    public void setSelect(CheckBox select)
    {
    this.select=select ;
    }
	public Float getPrixPromotion() {
        return prixPromotion;
    }

    public void setPrixPromotion(Float prixPromotion) {
        this.prixPromotion = prixPromotion;
    }


	@Override
	public String toString() {
		return "Produit{" + "id_produit=" + id_produit + ", libelle_produit=" + libelle_produit + ", categorie=" + categorie + ", prix=" + prix + ", date_expiration=" + date_expiration + ", quantite=" + quantite + ", description=" + description + ", note=" + note + ", image=" + image + ", id_patisserie=" + id_patisserie + '}';
	}

    

   
    
    
}
