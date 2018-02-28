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
public class Utilisateur {
	private long id_utilisateur ;
    private String password ;
    private String email ;
    private String pseudo ;
    private int etat_compte ;
    private int num_tel;
    private String nom ;
    private String prenom;
    private Date date_naissance ;
    private int role ;



    public Utilisateur() {
    }

    public Utilisateur(long id_utilisateur, String password, String email, String pseudo, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance, int role) {
        this.id_utilisateur = id_utilisateur;
        this.password = password;
        this.email = email;
        this.pseudo = pseudo;
        this.etat_compte = etat_compte;
        this.num_tel = num_tel;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.role = role;
    }
    //password, email, pseudo, num_tel, nom, prenom, date_naissance,

	public Utilisateur(String password, String email, String pseudo, int num_tel, String nom, String prenom, Date date_naissance, int role) {
		this.password = password;
		this.email = email;
		this.pseudo = pseudo;
		this.num_tel = num_tel;
		this.nom = nom;
		this.prenom = prenom;
		this.date_naissance = date_naissance;
		this.role = role;
	}

	public Utilisateur(long id_utilisateur, String email, String pseudo, int etat_compte) {
		this.id_utilisateur = id_utilisateur;
		this.email = email;
		this.pseudo = pseudo;
		this.etat_compte = etat_compte;
	}

	public Utilisateur(long id_utilisateur, String password, String email, String pseudo, int etat_compte) {
		this.id_utilisateur = id_utilisateur;
		this.password = password;
		this.email = email;
		this.pseudo = pseudo;
		this.etat_compte = etat_compte;
	}
    
    public Utilisateur(String password, String email, String pseudo, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance, int role) {
        this.password = password;
        this.email = email;
        this.pseudo = pseudo;
        this.etat_compte = etat_compte;
        this.num_tel = num_tel;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.role = role;
    }
//(savePassword, txtEmail.getText(), txtPseudo.getText(), tel, txtNom.getText(), txtPrenom.getText(),dateNaiss,TypeRole.Client.ordinal(), txtAdresse.getText(), valueRadio,path)
    public Utilisateur(String email, String pseudo, int num_tel, String nom, String prenom, Date date_naissance) {
        this.email = email;
        this.pseudo = pseudo;
        this.num_tel = num_tel;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
    }
     public Utilisateur(String password ,String email, String pseudo, int num_tel, String nom, String prenom, Date date_naissance) {
        this.password = password;
        this.email = email;
        this.pseudo = pseudo;
        this.num_tel = num_tel;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
    }
	 
	 //id_utilisateur,email,pseudo,etat_compte,nom,prenom,date_naissance
	 
    public Utilisateur(long id_utilisateur,String password, String email, String pseudo, int etat_compte, String nom, String prenom, Date date_naissance) {
        this.id_utilisateur = id_utilisateur;
		this.password=password;
        this.email = email;
        this.pseudo = pseudo;
        this.etat_compte = etat_compte;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
    }

	public Utilisateur(long id_utilisateur, String email, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance) {
		this.id_utilisateur = id_utilisateur;
		this.email = email;
		this.etat_compte = etat_compte;
		this.num_tel = num_tel;
		this.nom = nom;
		this.prenom = prenom;
		this.date_naissance = date_naissance;
	}
     
    
   

    public long getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(long id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getEtat_compte() {
        return etat_compte;
    }

    public void setEtat_compte(int etat_compte) {
        this.etat_compte = etat_compte;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id_utilisateur=" + id_utilisateur + ", password=" + password + ", email=" + email + ", pseudo=" + pseudo + ", etat_compte=" + etat_compte + ", num_tel=" + num_tel + ", nom=" + nom + ", prenom=" + prenom + ", date_naissance=" + date_naissance + ", role=" + role + '}';
    }
}
