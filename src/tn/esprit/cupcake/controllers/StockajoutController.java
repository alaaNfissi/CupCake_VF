/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import static tn.esprit.cupcake.controllers.StockmodifierController.isInteger;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.Produit;

import tn.esprit.cupcake.entities.Stock;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.services.StockService;
import tn.esprit.cupcake.test.CupCakeFX;

/**
 * FXML Controller class
 *
 * @author berber
 */
public class StockajoutController implements Initializable {

   
    
    @FXML
    private ComboBox comboNom;
    @FXML
    private DatePicker dateEntre;
    private ObservableList<Produit> data;
    @FXML
    private TextField textqua;
    @FXML
    private Label label;
    @FXML
    private Button btn1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StockService sk = new StockService();
        //id_patisserie static
		PatisserieService ps=new PatisserieService();
		Patisserie pt=null;
		try {
			pt = ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
		} catch (SQLException ex) {
			Logger.getLogger(StockajoutController.class.getName()).log(Level.SEVERE, null, ex);
		}
        List<Produit> list = sk.afficheProduitStock(pt.getId_patisserie());

        data = FXCollections.observableArrayList(list);
        for (int i = 0; i < data.size(); i++) {
            comboNom.getItems().addAll(data.get(i).getLibelle_produit());
        }
        comboNom.setPromptText("Selectionner Votre Produit");

    }

    private boolean controlesaisie() {
       
        if (textqua.getText().isEmpty()) {
           
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez remplir le champ quantité");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (comboNom.getSelectionModel().getSelectedIndex() == -1) {
            
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez selectionner un produit ");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (!isInteger(textqua.getText())) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Erreur dans le champ Quantité veuillez mettre un numéro");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        }
        else if (dateEntre.getValue()==null) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Erreur dans le champ Date_expiration veuillez choisir une date");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        }

        return true;
    }

    @FXML
    private void ajouter(MouseEvent event) throws SQLException {

        if (controlesaisie()) {
            String lib = comboNom.getSelectionModel().getSelectedItem().toString();
            StockService sk = new StockService();
            int id_produit = sk.RecupProduitId(lib);
			PatisserieService ps = new PatisserieService();
			Patisserie pt = ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
           
            Stock st = new Stock(id_produit, pt.getId_patisserie());
            sk.ajouterProduitStock(st);
            //Convertir datappciker to date
            java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateEntre.getValue());
            Produit pro = new Produit(gettedDatePickerDate, Integer.parseInt(textqua.getText()));
            sk.ajouterProduitdepuisStock(pro, id_produit);
            comboNom.getItems().remove(lib);
            //Notification 
            Image img = new Image("/tn/esprit/cupcake/images/cake.png");
            Notifications nf = Notifications.create()
                    .title("*********************Nouvelle Notification Recue***********************")
                    .text("  Le Produit" + " " + lib + " " + "a été ajouté Dans le Stock Avec succés ")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.TOP_RIGHT)
                    
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("Notification vu");
                        }
                    });
            nf.darkStyle();
            
            nf.show();

        }
        

    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }

}
