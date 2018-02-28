/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;
import tn.esprit.cupcake.entities.Utilisateur;
import tn.esprit.cupcake.entities.*;
/**
 *
 * @author Basly
 */
public class Patissier extends Utilisateur{
 
    //List <Patisserie> ListPatisserie = new ArrayList <>();

    public Patissier() {
    }

    public Patissier(long id_utilisateur, String password, String email, String pseudo, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance, int role) {
        super(id_utilisateur, password, email, pseudo, etat_compte, num_tel, nom, prenom, date_naissance, role);
    }

	public Patissier(long id_utilisateur, String email, String pseudo, int etat_compte) {
		super(id_utilisateur, email, pseudo, etat_compte);
	}

     public Patissier(String password, String email, String pseudo, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance,int role) {
        super(password, email, pseudo, etat_compte, num_tel, nom, prenom, date_naissance, role);
    }
     public Patissier(String password, String email, String pseudo, int num_tel, String nom, String prenom, Date date_naissance,int role) {
        super(password, email, pseudo, num_tel, nom, prenom, date_naissance,role);
    } 
	 public Patissier(String password, String email, String pseudo, int num_tel, String nom, String prenom, Date date_naissance) {
        super(password, email, pseudo, num_tel, nom, prenom, date_naissance);
    } 
    public Patissier(long id_utilisateur, String email, int etat_compte, int num_tel, String nom, String prenom, Date date_naissance) {
        super(id_utilisateur, email, etat_compte, num_tel, nom, prenom, date_naissance);
    }

     

   /* public List<Patisserie> getListPatisserie() {
        return ListPatisserie;
    }

    public void setListPatisserie(List<Patisserie> ListPatisserie) {
        this.ListPatisserie = ListPatisserie;
    }*/
    
    

    
    
}
