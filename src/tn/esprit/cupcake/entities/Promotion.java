/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import com.jfoenix.controls.JFXButton;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.cupcake.controllers.ListEvenementController;
import tn.esprit.cupcake.controllers.ListPromotionController;
import tn.esprit.cupcake.services.PromotionService;

/**
 *
 * @author Basly
 */
public class Promotion {

    private int id_promotion;
    private String libelle_promotion;
    private int pourcentage;
    private Date date_debut;
    private Date date_fin;
    private String description;
    private String image;
    private int id_patisserie;
    List <Produit> listProduit = new ArrayList<>();

    public Promotion() {}

    public Promotion(int id_promotion, String libelle_promotion, int pourcentage, Date date_debut, Date date_fin, String description, String image, int id_patisserie) {
        this.id_promotion = id_promotion;
        this.libelle_promotion = libelle_promotion;
        this.pourcentage = pourcentage;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.image = image;
        this.id_patisserie = id_patisserie;
    }
    
     public Promotion(int id_promotion, String libelle_promotion, int pourcentage, Date date_debut, Date date_fin, String description, String image) {
        this.id_promotion = id_promotion;
        this.libelle_promotion = libelle_promotion;
        this.pourcentage = pourcentage;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.image = image;
    }

    public Promotion(String libelle_promotion, int pourcentage, Date date_debut, Date date_fin, String description,String image ,int id_patisserie) {
        this.libelle_promotion = libelle_promotion;
        this.pourcentage = pourcentage;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.image=image;
        this.id_patisserie = id_patisserie;
    }
public Promotion(String libelle_promotion, int pourcentage, Date date_debut, Date date_fin, String description,String image) {
        this.libelle_promotion = libelle_promotion;
        this.pourcentage = pourcentage;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.image=image;
    }
    public Promotion(String libelle_promotion, int pourcentage, String description, int id_patisserie) {
        this.libelle_promotion = libelle_promotion;
        this.pourcentage = pourcentage;
        this.description = description;
        this.id_patisserie = id_patisserie;
    }
    
    

    public int getId_promotion() {
        return id_promotion;
    }

    public void setId_promotion(int id_promotion) {
        this.id_promotion = id_promotion;
    }

    public String getLibelle_promotion() {
        return libelle_promotion;
    }

    public void setLibelle_promotion(String libelle_promotion) {
        this.libelle_promotion = libelle_promotion;
    }

    public int getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(int pourcentage) {
        this.pourcentage = pourcentage;
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

    public List<Produit> getListProduit() {
        return listProduit;
    }

    public void setListProduit(List<Produit> listProduit) {
        this.listProduit = listProduit;
    }
    
    public JFXButton getDelete() {
        JFXButton b = new JFXButton();
        /*********************************************************************************************/
        Image image = new Image(getClass().getResourceAsStream("/tn/esprit/cupcake/views/icons/trash3.png"));
        ImageView img = new ImageView();
        img.setImage(image);
        img.setFitWidth(20);
        img.setFitHeight(20);
        b.setGraphic(img);
        /*********************************************************************************************/
        ListPromotionController cont = new ListPromotionController();
        PromotionService sr = new PromotionService();
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("deleting " + libelle_evenement);
                
                try {
                    sr.deletePromotion(id_promotion);
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                ListPromotionController.cont.AfficherDataPromotion();
            }
        });
        return b;
    }

}
