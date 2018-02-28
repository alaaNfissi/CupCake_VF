/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.cupcake.entities.Catalogue;
import tn.esprit.cupcake.services.CatalogueService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class ModifierCatalogueController implements Initializable {

    @FXML
    private TextField txtId_catalogue;
    @FXML
    private TextField txtLibelle_catalogue;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtId_patisserie;
    @FXML
    private DatePicker txtDate_debut;
    @FXML
    private DatePicker txtDate_fin;
    @FXML
    private Button btnModifier;
    @FXML
    private ImageView txtRetour;
    private Catalogue selectedid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifierCatalogue(ActionEvent event) throws SQLException {
          CatalogueService sv = new CatalogueService();
        java.sql.Date x = java.sql.Date.valueOf(txtDate_debut.getValue());
            java.sql.Date y= java.sql.Date.valueOf(txtDate_fin.getValue());

        Catalogue c = new Catalogue(txtLibelle_catalogue.getText(), x,y , txtDescription.getText(), Integer.parseInt(txtId_patisserie.getText()));

        int id_catalogue = Integer.parseInt(txtId_catalogue.getText());
        System.out.println("hnéééé");
        sv.modifierCatalogue(c, id_catalogue);
        System.out.println("hnéééé2");

        System.out.println("catalogue modifié avec succès !! ");
    }
    
    
    public void recuperer(Catalogue C) {
        selectedid = C;
        txtId_catalogue.setText(Integer.toString(selectedid.getId_catalogue()));
        txtLibelle_catalogue.setText(selectedid.getLibelle_catalogue());
       
        LocalDate ld = new java.sql.Date(selectedid.getDate_debut().getTime()).toLocalDate();
        txtDate_debut.setValue(ld);
         LocalDate ld1 = new java.sql.Date(selectedid.getDate_fin().getTime()).toLocalDate();
        txtDate_fin.setValue(ld1);
        // txtDate.setText(selectedid.getDate_expiration().toString());

  
        txtDescription.setText(selectedid.getDescription());
        
        txtId_patisserie.setText(Integer.toString(selectedid.getId_patisserie()));

    }
	
	 @FXML
    private void retour(MouseEvent event) throws IOException {
           Parent root=null;
		Stage stage = new Stage();
		root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
		JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);
						
						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						((Node) (event.getSource())).getScene().getWindow().hide();
        }
    
}
