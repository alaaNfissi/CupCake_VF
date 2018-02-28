/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import java.util.Date;

/**
 *
 * @author esprit
 */
public class Catalogue {
    private int id_catalogue;
    private String libelle_catalogue;
    private Date date_debut;
    private Date date_fin;
    private String description;
    private int id_patisserie;

    public int getId_catalogue() {
        return id_catalogue;
    }

    public void setId_catalogue(int id_catalogue) {
        this.id_catalogue = id_catalogue;
    }

    public String getLibelle_catalogue() {
        return libelle_catalogue;
    }

    public void setLibelle_catalogue(String libelle_catalogue) {
        this.libelle_catalogue = libelle_catalogue;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_patisserie() {
        return id_patisserie;
    }

    public void setId_patisserie(int id_patisserie) {
        this.id_patisserie = id_patisserie;
    }

    public Catalogue(int id_catalogue, String libelle_catalogue, Date date_debut, Date date_fin, String description, int id_patisserie) {
        this.id_catalogue = id_catalogue;
        this.libelle_catalogue = libelle_catalogue;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.id_patisserie = id_patisserie;
    }

    public Catalogue(String libelle_catalogue, Date date_debut, Date date_fin, String description, int id_patisserie) {
        this.libelle_catalogue = libelle_catalogue;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.id_patisserie = id_patisserie;
    }

    public Catalogue(String libelle_catalogue) {
        this.libelle_catalogue = libelle_catalogue;
    }
    
    
    
}
