/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import java.sql.Date;



/**
 *
 * @author Basly
 */
public class Client extends Utilisateur {
    private String adresse ;
    private String sexe ;
    private String image;

    public Client() {
    }

    public Client(long id_utilisateur, String password, String email, String pseudo, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance, int role) {
        super(id_utilisateur, password, email, pseudo, etat_compte, num_tel, nom, prenom, date_naissance, role);
    }

//    public Client(String adresse, String sexe, String image) {
//        this.adresse = adresse;
//        this.sexe = sexe;
//        this.image = image;
//    }
    

    public Client(String adresse, String sexe, String image, int id_utilisateur, String password, String email, String pseudo, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance, int role) {
        super(id_utilisateur, password, email, pseudo, etat_compte, num_tel, nom, prenom, date_naissance, role);
        this.adresse = adresse;
        this.sexe = sexe;
        this.image = image;
    }
      public Client(String password, String email, String pseudo, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance,int role, String adresse, String sexe, String image) {
        super(password, email, pseudo, etat_compte, num_tel, nom, prenom, date_naissance, role);
        this.adresse = adresse;
        this.sexe = sexe;
        this.image = image;
    }

    public Client(String password,String email, String pseudo, int num_tel, String nom, String prenom, Date date_naissance,String adresse,String sexe) {
        super(password,email, pseudo, num_tel, nom, prenom, date_naissance);
        this.adresse = adresse;
        this.sexe=sexe ;

    }
    //(savePassword, txtEmail.getText(), txtPseudo.getText(), tel, txtNom.getText(), txtPrenom.getText(),dateNaiss,TypeRole.Client.ordinal(), txtAdresse.getText(), valueRadio,path)

	public Client(String password, String email, String pseudo, int num_tel, String nom, String prenom, Date date_naissance, int role,String adresse, String sexe, String image) {
		super(password, email, pseudo, num_tel, nom, prenom, date_naissance, role);
		this.adresse = adresse;
		this.sexe = sexe;
		this.image = image;
	}
    
	public Client(String email, String pseudo, int num_tel, String nom, String prenom, Date date_naissance,String adresse,String sexe) {
        super(email, pseudo, num_tel, nom, prenom, date_naissance);
        this.adresse = adresse;
        this.sexe = sexe ;
    }
  public Client(String password,String email, String pseudo, int num_tel, String nom, String prenom, Date date_naissance,String adresse,String sexe,String image) {
        super(password,email, pseudo, num_tel, nom, prenom, date_naissance);
        this.adresse = adresse;
        this.sexe = sexe ;
        this.image = image ;
    }

	public Client(long id_utilisateur, String password, String email, String pseudo, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance, int role,String adresse, String sexe, String image) {
		super(id_utilisateur, password, email, pseudo, etat_compte, num_tel, nom, prenom, date_naissance, role);
		this.adresse = adresse;
		this.sexe = sexe;
		this.image = image;
	}



    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Client{" + "adresse=" + adresse + ", sexe=" + sexe + ", image=" + image + '}';
    }

    
    
    
    
}
