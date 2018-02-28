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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.DefaultListModel;
import tn.esprit.cupcake.entities.Panier;
import tn.esprit.cupcake.services.PanierService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;
import tn.esprit.cupcake.views.ListePatisseriesController;

/**
 * FXML Controller class
 *
 * @author berber
 */
public class PanierajouterController implements Initializable {


    /**
     * Initializes the controller class.
     */
    DefaultListModel dim = new DefaultListModel();

    @FXML
    private ListView list1;
    @FXML
    private TextField textprix;
    
    private TextField textptixtotal;
    @FXML
    private ImageView btnmodifier;
    @FXML
    private TextField textquan;
    int Cpt=0;
    private TextField nrbproduit;
    @FXML
    private TextField textCpt;
    @FXML
    private JFXButton bntajouter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PanierService pn = new PanierService();
        List<String> list = new ArrayList<>();
        //Id_patisserie statique
        list = pn.afficheProduit(CupCakeFX.patisserie.getId_patisserie());
        ObservableList<String> names = FXCollections.observableArrayList(list);
        list1.setItems(names);
        textprix.setText("0");
        textCpt.setText("0");
        
    }

    @FXML
    private void ajouterPanier(MouseEvent event) throws SQLException {

       
        String selectedProduit = list1.getSelectionModel().getSelectedItem().toString();
        int quantiteSouhaite = Integer.parseInt(textquan.getText());
        PanierService pn = new PanierService();
        int quantitéProduit = pn.RecupQuantitéProduit(selectedProduit);
        if (pn.panierutilisateur(CupCakeFX.user.getId_utilisateur())==-1)
        {
            if ((quantitéProduit!=0)&&(quantitéProduit>quantiteSouhaite))
            {
        int idProduit = pn.RecupProduitId(selectedProduit);
        // id utilisteur et id_patisserie statique
        
        Panier panier = new Panier(CupCakeFX.patisserie.getId_patisserie(), CupCakeFX.user.getId_utilisateur(),quantiteSouhaite, idProduit);
        pn.creationPanier(panier);
        Cpt++;
        System.out.println(selectedProduit + " a été ajouter avec succés dans votre Panier");
        list1.getItems().remove(selectedProduit);
        }
        
            else 
            {
              Alert alert1 = new Alert(Alert.AlertType.ERROR);
              alert1.setContentText("Désole Stock épuisé");
              alert1.show();
               
              
            }
        }
         
        else 
        {  if((quantitéProduit!=0)&&(quantitéProduit>quantiteSouhaite))
        {
            int idProduit = pn.RecupProduitId(selectedProduit);
            Panier panier = new Panier(CupCakeFX.patisserie.getId_patisserie(),CupCakeFX.user.getId_utilisateur(),quantiteSouhaite, idProduit);
           int  numpanier = pn.panierutilisateur(CupCakeFX.user.getId_utilisateur());
            pn.ajouterProduitPanier(panier);
            list1.getItems().remove(selectedProduit);
            Cpt++;
            
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
              alert1.setContentText("Désole le produit est épuisé");
              alert1.show();     
        } 
    }
         textCpt.setText(Integer.toString(Cpt));
}


    

    @FXML
    private void prixProduit(MouseEvent event) throws SQLException {

        String selectedProduit = list1.getSelectionModel().getSelectedItem().toString();
        PanierService pn1PanierService = new PanierService();
        float a = 0;
        try {
            a = pn1PanierService.affichePrix(selectedProduit);

        } catch (SQLException ex) {
            Logger.getLogger(PanierajouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        textprix.setText(Float.toString(a) + "DT");

    }
    
    public void recupererCpt(String text)
    {
       textCpt.setText(text);
    }

    @FXML
    private void monpanier(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/Paniermodifer.fxml"));
        
        Parent root = loader.load();
        //enovyer la valeur du compteur a l'autre controller
        PaniermodiferController pc = loader.getController();
        pc.recupererCpt(Integer.toString(Cpt));
        Stage stage1 = (Stage) btnmodifier.getScene().getWindow();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/tn/esprit/cupcake/views/bootstrap3.css").toString());
        stage1.setScene(scene);
        stage1.show();
		//HomeViewController.versPanier=1;
		/*Parent root=null;
						Stage stage = new Stage();
					try {
						root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
					} catch (IOException ex) {
						Logger.getLogger(ListePatisseriesController.class.getName()).log(Level.SEVERE, null, ex);
					}
						JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);
						
						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						//((Node) (event.getSource())).getScene().getWindow().hide();*/
    }


}
