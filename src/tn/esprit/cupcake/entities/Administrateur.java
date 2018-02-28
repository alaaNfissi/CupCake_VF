/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

/**
 *
 * @author Basly
 */
public class Administrateur extends Utilisateur{

    public Administrateur() {
    }

    public Administrateur(long id_utilisateur, String password, String email, String pseudo, int etat_compte, int num_tel, String nom, String prenom, java.sql.Date date_naissance, int role) {
        super(id_utilisateur, password, email, pseudo, etat_compte, num_tel, nom, prenom, date_naissance, role);
    }

    
}
