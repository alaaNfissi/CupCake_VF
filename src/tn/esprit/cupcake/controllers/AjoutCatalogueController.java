/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.cupcake.entities.Catalogue;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.services.CatalogueService;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class AjoutCatalogueController implements Initializable {

    @FXML
    private TextField txtLibelle;
    @FXML
    private TextField txtDescription;
    @FXML
    private DatePicker txtDate_debut;
    @FXML
    private DatePicker txtDate_fin;
    @FXML
    private Button btnValider;
	@FXML
	private JFXButton affecter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterCatalogue(ActionEvent event) throws SQLException {
         System.out.println("h");
        java.sql.Date x = java.sql.Date.valueOf(txtDate_debut.getValue());
         java.sql.Date y = java.sql.Date.valueOf(txtDate_fin.getValue());
          CatalogueService sv=new CatalogueService();
		  PatisserieService ps = new PatisserieService();
		Patisserie pt = ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
        Catalogue c=new Catalogue(txtLibelle.getText(),x,y,txtDescription.getText(),pt.getId_patisserie());
   System.out.println("hnéééé");
       
            sv.ajouterCatalogue(c);
        
              
               System.out.println("produit ajouté avec succès !! ");
          
    }

    /*@FXML
    private void GoAfficher(ActionEvent event) {
         FXMLLoader loader = null;
               try {
                    loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/AffichageCatalogue.fxml"));
                      Stage stage1 = (Stage)  btnAfficher.getScene().getWindow();
            Stage stage = new Stage();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
               }catch (IOException ex) {
            System.out.println(ex);
        }
    }*/
	
	@FXML
	public void affecterProduitCatalogue(ActionEvent event) throws IOException
	{
		HomeViewController.ajouterProduitbtn=0;
	  HomeViewController.ajouterCataloguebtn=0;
	  HomeViewController.listeCataloguebtn=0;
	  HomeViewController.affecterProduitCatalogue=1;
	  
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
