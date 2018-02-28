/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;


import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.cupcake.entities.Panier;
import tn.esprit.cupcake.services.CommandeService;
import tn.esprit.cupcake.services.PanierService;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.services.UtilisateurService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author berber
 */
public class PaniermodiferController implements Initializable {

    @FXML
    private ListView list2;
    @FXML
    private TextField textquan;
    @FXML
    private TextField prixtotal;
    @FXML
    private TextField textcpt;
    @FXML
    private ImageView btnCpt;
    @FXML
    private JFXButton btnsup;
	@FXML
	private JFXButton commanderPanierBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PanierService pn = new PanierService();
        List<String> list = new ArrayList<>();
        //Id_utilisateur du panier statique
        list = pn.affichePanier(CupCakeFX.user.getId_utilisateur());
        ObservableList<String> names = FXCollections.observableArrayList(list);
        list2.setItems(names);
        //affichage prixtotale  avec id_utilisateur
      
            
         
        try {
         float   prixtotalpanier = pn.affichePrixTotal(CupCakeFX.user.getId_utilisateur());
            prixtotal.setText(Float.toString(prixtotalpanier)+"DT");
        } catch (SQLException ex) {
            Logger.getLogger(PaniermodiferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    

    @FXML
    private void supprimerProduitPanier(MouseEvent event) throws SQLException {
        String selectedProduit = list2.getSelectionModel().getSelectedItem().toString();
        PanierService pn = new PanierService();
        int idProduit = pn.RecupProduitId(selectedProduit);
        Panier panier = new Panier();
        pn.supprimerProduitPanier(idProduit);
        list2.getItems().remove(list2.getSelectionModel().getSelectedItem());
        System.out.println(selectedProduit + " a été supprimer avec succés de votre panier");
        //supprimer produit du panier changement prix total
        try {
         float   prixtotalpanier = pn.affichePrixTotal(CupCakeFX.user.getId_utilisateur());
            prixtotal.setText(Float.toString(prixtotalpanier)+"DT");
        } catch (SQLException ex) {
            Logger.getLogger(PaniermodiferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        textcpt.setText(Integer.toString(Integer.parseInt(textcpt.getText())-1));
        
        
    }

    @FXML
    private void selectquan(MouseEvent event) throws SQLException {
        String selectedProduit = list2.getSelectionModel().getSelectedItem().toString();
        PanierService sv = new PanierService();
        int id_produit = sv.RecupProduitId(selectedProduit);
        int quantite =sv.panierproduitQuantité(id_produit);
        textquan.setText( Integer.toString(quantite));
        sv.affichePrix(selectedProduit);
        
        
    }
    
    public void recupererCpt(String text)
    {
        textcpt.setText(text);
    }

    @FXML
    private void EnvoyerCpt(MouseEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/Panierajouter.fxml"));
        Parent root = loader.load();
        //enovyer la valeur du compteur a l'autre controller
        PanierajouterController pn = loader.getController();
        pn.recupererCpt(textcpt.getText());
        Stage stage1 = (Stage) btnCpt.getScene().getWindow();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/tn/esprit/cupcake/views/bootstrap3.css").toString());
        stage1.setScene(scene);
        stage1.show();
    }
	@FXML
	 public void versCommande(ActionEvent event) throws IOException, SQLException
	{
		PatisserieService ps=new PatisserieService();
		UtilisateurService us =new UtilisateurService();
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmer votre mode de payement");
		alert.setHeaderText("Choisissez un mode parmis ces deux .");
		alert.setContentText("Choose your option.");

		ButtonType buttonTypeOne = new ButtonType("à la livraison");
		/*ButtonType buttonTypeTwo = new ButtonType("En ligne");*/
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne,/* buttonTypeTwo,*/ buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
			System.out.println("à la livraison");
			CommandeService cs = new CommandeService();
			System.out.println("**********************************************");
			cs.ajouterCommande(ps.searchById(CupCakeFX.patisserie.getId_patisserie()), us.searchUserById(CupCakeFX.user.getId_utilisateur()));
			Parent root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
                        Scene newScene= new Scene(root);
                        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        window.setScene(newScene);
                        window.show();
		} /*else if (result.get() == buttonTypeTwo) {
			System.out.println("En ligne");
		}*/ else {
			Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setTitle("Alert !");
			alert1.setHeaderText("Pas de choix selectionné");
			alert1.setContentText("Vous devez choisir un mode de payement !");

			alert1.showAndWait();
		}
		
	} 
}
