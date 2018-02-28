

package tn.esprit.cupcake.entities;

/**
 *
 * @author berber
 */
public class Stock {
    
    private int id_produit;
    private int id_patisserie;

    public Stock(int id_produit, int id_patisserie) {
        this.id_produit = id_produit;
        this.id_patisserie = id_patisserie;
    }
    
    
    

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getId_patisserie() {
        return id_patisserie;
    }

    public void setId_patisserie(int id_patisserie) {
        this.id_patisserie = id_patisserie;
    }
    
    
    
    
}
