/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.entities;

import com.jfoenix.controls.JFXButton;
import static java.nio.file.Files.*;
import java.sql.Date;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.cupcake.controllers.ListEvenementController;
import tn.esprit.cupcake.services.EvenementService;

/**
 *
 * @author Basly
 */
public class Evenement {

    private int id_evenement;
    private String libelle_evenement;
    private String adresse_evenement;
    private Date date_debut;
    private Date date_fin;
    private String description;
    private int note;
    private String image;
    private int id_patisserie;
    private Double longitude;
    private Double latitude;

    //private Button delete ;
    //Image imageButton = new Image(getClass().getResourceAsStream("/tn/esprit/cupcake/images/delete.png"));
    public Evenement() {
    }

    public Evenement(int id_evenement, String libelle_evenement, String adresse_evenement, Date date_debut, Date date_fin, String description, String image, int id_patisserie, Double longitude, Double latitude) {
        this.id_evenement = id_evenement;
        this.libelle_evenement = libelle_evenement;
        this.adresse_evenement = adresse_evenement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.image = image;
        this.id_patisserie = id_patisserie;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Evenement(String libelle_evenement, String adresse_evenement, Date date_debut, Date date_fin, String description, String image, int id_patisserie, Double longitude, Double latitude) {
        this.libelle_evenement = libelle_evenement;
        this.adresse_evenement = adresse_evenement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.image = image;
        this.id_patisserie = id_patisserie;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Evenement(int id_evenement, String libelle_evenement, String adresse_evenement, Date date_debut, Date date_fin, String description,String image,Double longitude,Double latitude) {
        this.id_evenement = id_evenement;
        this.libelle_evenement = libelle_evenement;
        this.adresse_evenement = adresse_evenement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.image = image;
        this.longitude=longitude;
        this.latitude=latitude;
        
    }
  public Evenement( String libelle_evenement, String adresse_evenement, Date date_debut, Date date_fin,String description, String image,Double longitude,Double latitude) {
        this.libelle_evenement = libelle_evenement;
        this.adresse_evenement = adresse_evenement;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        this.image = image;
        this.longitude=longitude;
        this.latitude=latitude;
        
    }
    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public String getLibelle_evenement() {
        return libelle_evenement;
    }

    public void setLibelle_evenement(String libelle_evenement) {
        this.libelle_evenement = libelle_evenement;
    }

    public String getAdresse_evenement() {
        return adresse_evenement;
    }

    public void setAdresse_evenement(String adresse_evenement) {
        this.adresse_evenement = adresse_evenement;
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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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
        ListEvenementController cont = new ListEvenementController();
        EvenementService sr = new EvenementService();
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("deleting " + libelle_evenement);
                
                try {
                    sr.deleteEvenement(id_evenement);
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                ListEvenementController.cont.AfficherData();
            }
        });
        return b;
    }
}
