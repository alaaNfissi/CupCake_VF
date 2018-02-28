/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import tn.esprit.cupcake.entities.Catalogue;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.entities.ProduitCatalogue;
import tn.esprit.cupcake.entities.TypeRole;
import tn.esprit.cupcake.services.ProduitCatalogueService;
import tn.esprit.cupcake.test.CupCakeFX;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class CatalgueProduitController implements Initializable {

    @FXML
    private ComboBox comboProduit;
    @FXML
    private ComboBox comboCatalogue;
    @FXML
    private Button btnValider;
    private ObservableList<Produit>data;
    private ObservableList<Catalogue>data2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal())
		{
        ProduitCatalogueService sk = new ProduitCatalogueService();
        List<Produit> list = null;
        try {
            list = sk.afficheProduit();
        } catch (SQLException ex) {
            Logger.getLogger(CatalgueProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     data = FXCollections.observableArrayList(list);
        for (int i = 0; i < data.size(); i++) {
            comboProduit.getItems().addAll(data.get(i).getLibelle_produit());      
        }
        comboProduit.setPromptText("Selectionner Votre Produit");
        
         
        List<Catalogue> list1 = null;
        try {
            list1 = sk.afficheCatalogue();
        } catch (SQLException ex) {
            Logger.getLogger(CatalgueProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     data2 = FXCollections.observableArrayList(list1);
        for (int i = 0; i < data2.size(); i++) {
            comboCatalogue.getItems().addAll(data2.get(i).getLibelle_catalogue());      
        }
        comboCatalogue.setPromptText("Selectionner Votre Catalogue");
        
        
        // TODO
		}
    }    

    @FXML
    private void ajouterPC(ActionEvent event) throws SQLException {
        String selectedProduit = comboProduit.getSelectionModel().getSelectedItem().toString();
         String selectedCatalogue = comboCatalogue.getSelectionModel().getSelectedItem().toString();
        
        ProduitCatalogueService sk = new ProduitCatalogueService();
       int  idp=sk.RecupProduitId(selectedProduit);
       int idc=sk.RecupCatalogueId(selectedCatalogue);
        

		ProduitCatalogue pc = new ProduitCatalogue(idc,idp);
		sk.ajouterProduitCatalogue(pc);
    }
    } 

