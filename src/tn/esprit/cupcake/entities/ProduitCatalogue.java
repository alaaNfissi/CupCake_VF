/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

/**
 *
 * @author esprit
 */
public class ProduitCatalogue {
    private int id_produit;
    private int id_catalogue;

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getId_catalogue() {
        return id_catalogue;
    }

    public void setId_catalogue(int id_catalogue) {
        this.id_catalogue = id_catalogue;
    }

    public ProduitCatalogue(int id_produit, int id_catalogue) {
        this.id_produit = id_produit;
        this.id_catalogue = id_catalogue;
    }
    
    
    
}
