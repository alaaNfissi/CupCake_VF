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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jfree.base.Library;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.services.StockService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author berber
 */
public class StockmodifierController implements Initializable {

    @FXML
    private TextField textlib;
    @FXML
    private TextField textid;

    @FXML
    private TextField textprix;
    @FXML
    private DatePicker textdate;
    @FXML
    private TextField textquan;
    @FXML
    private TextField textdesc;
    @FXML
    private TextField textcat;
    @FXML
    private ImageView btnRetour;
    @FXML
    private JFXButton btnmodification;
    @FXML
    private JFXButton btnreset;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private boolean controlesaisie() {

        if (textlib.getText().isEmpty() || textprix.getText().isEmpty() || textquan.getText().isEmpty()
                || textdesc.getText().isEmpty() || textcat.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez remplir tous les champs");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (isInteger(textlib.getText())) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Erreur dans le champ Libelle veuillez mettre une chaine");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (textdate.getValue() == null) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Erreur dans le champ Date_expiration veuillez choisir une date");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (isInteger(textdesc.getText())) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Erreur dans le champ Description veuillez mettre une chaine");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (isInteger(textcat.getText())) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Erreur dans le champ categorie veuillez mettre une chaine");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (!isInteger(textquan.getText())) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Erreur dans le champ quantité veuillez mettre une numéro");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (!isFloat(textprix.getText())) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Erreur dans le champ prix veuillez mettre un numéro");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        }

        return true;
    }

    public void Functionparam(String text1, String text2, String text3, String text4, LocalDate text5, String text6, String text7) {
        textid.setText(text1);
        textlib.setText(text2);
        textcat.setText(text3);
        textprix.setText(text4);
        textdate.setValue(text5);
        textquan.setText(text6);
        textdesc.setText(text7);

    }

    @FXML
    private void modificationProduitStock(MouseEvent event) throws SQLException {
        if (controlesaisie()) {
            StockService sv = new StockService();
            Produit p = new Produit(
                    textlib.getText(),
                    textcat.getText(),
                    Float.parseFloat(textprix.getText()),
                    Date.valueOf(textdate.getValue()),
                    Integer.parseInt(textquan.getText()),
                    textdesc.getText());
            int item = Integer.parseInt(textid.getText());
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation Dialog");
            alert1.setContentText("Etes vous sur de vouloir modifier le produit ");
            alert1.setHeaderText(null);
            Optional<ButtonType> action = alert1.showAndWait();
            if (action.get() == ButtonType.OK) {
                sv.modifierProduitStock(p, item);

            }
            System.out.println("Produit" + " " + textlib.getText()+" " + "modifier avec succés");

        }

    }

    @FXML
    private void reset(MouseEvent event) {
        
        textlib.setText("");
        textcat.setText("");
        textprix.setText("");
        textquan.setText("");
        textdesc.setText("");

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

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }
    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }

}
