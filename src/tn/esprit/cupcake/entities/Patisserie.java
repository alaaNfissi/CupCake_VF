/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Date;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Basly
 */
public class Patisserie {

    private int id_patisserie;
    private String libelle_patisserie;
    private String adresse_patisserie;
    private Date date_creation;
    private String specialite;
    private String compte_facebook;
    private String compte_instagram;
    private String description;
    private String image;
    private long Patissier;
	private ImageView imageV;

    public Patisserie(int id_patisserie, String libelle_patisserie, String adresse_patisserie, Date date_creation, String specialite, String compte_facebook, String compte_instagram, String description, String image, long Patissier) {
        this.id_patisserie = id_patisserie;
        this.libelle_patisserie = libelle_patisserie;
        this.adresse_patisserie = adresse_patisserie;
        this.date_creation = date_creation;
        this.specialite = specialite;
        this.compte_facebook = compte_facebook;
        this.compte_instagram = compte_instagram;
        this.description = description;
        this.image = image;
        this.Patissier = Patissier;
    }

    public Patisserie(String libelle_patisserie, String adresse_patisserie, Date date_creation, String specialite, String compte_facebook, String compte_instagram, String description, String image, long Patissier) {
        this.libelle_patisserie = libelle_patisserie;
        this.adresse_patisserie = adresse_patisserie;
        this.date_creation = date_creation;
        this.specialite = specialite;
        this.compte_facebook = compte_facebook;
        this.compte_instagram = compte_instagram;
        this.description = description;
        this.image = image;
        this.Patissier = Patissier;
    }
/*p.setLibelle_patisserie(rs.getString("libelle_patisserie"));
			p.setAdresse_patisserie(rs.getString("adresse_patisserie"));
			p.setSpecialite(rs.getString("specialite"));
			p.setDescription(rs.getString("description"));
			p.setImage(rs.getString("image"));
			listePatisseries.add(p);*/

	public Patisserie(int id_patisserie, String libelle_patisserie, String adresse_patisserie, String specialite, String description, String image,ImageView imageV) throws MalformedURLException {
		this.id_patisserie = id_patisserie;
		this.libelle_patisserie = libelle_patisserie;
		this.adresse_patisserie = adresse_patisserie;
		this.specialite = specialite;
		this.description = description;
		this.image = image;
		//imageV=getImageV();
	}
			
    public Patisserie(String libelle_patisserie,String adresse_patisserie, Date date_creation, String specialite, String compte_facebook, String compte_instagram, String description, String image) {
        this.libelle_patisserie = libelle_patisserie;
        this.adresse_patisserie = adresse_patisserie ;
        this.date_creation = date_creation;
        this.specialite = specialite;
        this.compte_facebook = compte_facebook;
        this.compte_instagram = compte_instagram;
        this.description = description;
        this.image = image;
    }

    public Patisserie(String libelle_patisserie, String adresse_patisserie, Date date_creation, String specialite) {
        this.libelle_patisserie = libelle_patisserie;
        this.adresse_patisserie = adresse_patisserie;
        this.date_creation = date_creation;
        this.specialite = specialite;
    }

    
    
    public Patisserie() {
    }

    public int getId_patisserie() {
        return id_patisserie;
    }

    public void setId_patisserie(int id_patisserie) {
        this.id_patisserie = id_patisserie;
    }

    public String getLibelle_patisserie() {
        return libelle_patisserie;
    }

    public void setLibelle_patisserie(String libelle_patisserie) {
        this.libelle_patisserie = libelle_patisserie;
    }

    public String getAdresse_patisserie() {
        return adresse_patisserie;
    }

    public void setAdresse_patisserie(String adresse_patisserie) {
        this.adresse_patisserie = adresse_patisserie;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getCompte_facebook() {
        return compte_facebook;
    }

    public void setCompte_facebook(String compte_facebook) {
        this.compte_facebook = compte_facebook;
    }

    public String getCompte_instagram() {
        return compte_instagram;
    }

    public void setCompte_instagram(String compte_instagram) {
        this.compte_instagram = compte_instagram;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPatissier() {
        return Patissier;
    }

    public void setPatissier(long Patissier) {
        this.Patissier = Patissier;
    }
	/*public ImageView getImageV() throws MalformedURLException
    {
		File file=new File(image);
		Image i = new Image(file.toURI().toURL().toExternalForm());
		imageV.setImage(i);
		return imageV;
    }*/
	/*public void setImageV(String image) throws MalformedURLException
	{
		File file=new File(image);
		Image i = new Image(file.toURI().toURL().toExternalForm());
		System.out.println(i);
		imageV.setImage(i);
		/*Image i=new Image(image);
		this.imageV.setImage(i);*/
	//}

	@Override
	public String toString() {
		return "Patisserie{" + "id_patisserie=" + id_patisserie + ", libelle_patisserie=" + libelle_patisserie + ", adresse_patisserie=" + adresse_patisserie + ", date_creation=" + date_creation + ", specialite=" + specialite + ", compte_facebook=" + compte_facebook + ", compte_instagram=" + compte_instagram + ", description=" + description + ", image=" + image + ", Patissier=" + Patissier + '}';
	}

}
