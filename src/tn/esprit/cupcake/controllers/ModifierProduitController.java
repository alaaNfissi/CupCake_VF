/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXDecorator;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.services.ProduitService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.CupCakeDBConnection;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class ModifierProduitController implements Initializable {

    private Produit selectedid;

    @FXML
    private TextField txtId_produit;
    @FXML
    private TextField txtLibelle_produit;
    @FXML
    private TextField txtCategorie;
    @FXML
    private TextField txtPrix;
    @FXML
    private TextField txtQuantite;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtNote;
    @FXML
    private TextField txtId_patisserie;
    @FXML
    private Button btnImportImage;
    String path = "";
    @FXML
    private ImageView imageViewProduit;
    @FXML
    private Button btnModifier;
    @FXML
    private DatePicker txtDate;
    private Connection con;
    @FXML
    private ImageView txtRetour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId_patisserie.setVisible(false);
		txtId_produit.setVisible(false);
		
    }

    @FXML
    private void importImage(ActionEvent event) throws MalformedURLException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            path = selectedFile.getAbsolutePath();
            String path2 = selectedFile.toURI().toURL().toString();
            Image image = new Image(path2);
            imageViewProduit.setImage(image);
        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("NoData");
        }
    }

    @FXML
    private void modifierProduit(ActionEvent event) throws SQLException {
        //  java.sql.Date x = java.sql.Date.valueOf(txtDate.getValue());
        // con = CupCakeDBConnection.getInstance().getConnection();

        ProduitService sv = new ProduitService();
        java.sql.Date x = java.sql.Date.valueOf(txtDate.getValue());

        Produit p = new Produit(txtLibelle_produit.getText(), txtCategorie.getText(), Float.parseFloat(txtPrix.getText()), x, Integer.parseInt(txtQuantite.getText()), txtDescription.getText(), Integer.parseInt(txtNote.getText()), path, Integer.parseInt(txtId_patisserie.getText()));

        int id_produit = Integer.parseInt(txtId_produit.getText());
        System.out.println("hnéééé");
        sv.modifierProduit(p, id_produit);
        System.out.println("hnéééé2");

        System.out.println("produit modifié avec succès !! ");
    }

    public void recuperer(Produit p) {
        selectedid = p;
        txtId_produit.setText(Integer.toString(selectedid.getId_produit()));
        txtLibelle_produit.setText(selectedid.getLibelle_produit());
        txtCategorie.setText(selectedid.getCategorie());
        txtPrix.setText(Float.toString(selectedid.getPrix()));
        LocalDate ld = new java.sql.Date(selectedid.getDate_expiration().getTime()).toLocalDate();
        txtDate.setValue(ld);
        // txtDate.setText(selectedid.getDate_expiration().toString());

        txtQuantite.setText(Integer.toString(selectedid.getQuantite()));
        txtDescription.setText(selectedid.getDescription());
        txtNote.setText(Integer.toString(selectedid.getNote()));
        txtId_patisserie.setText(Integer.toString(selectedid.getId_patisserie()));
        System.out.println(selectedid.getId_produit());
       // imageViewProduit.setImage(selectedid.);
                //  Image i = new Image("file:" + selectedid.getImage());
        //ImageView image = new ImageView(i);
        // imageViewProduit = image;
        File file = new File(selectedid.getImage());

                try {
                    Image image = new Image(file.toURI().toURL().toExternalForm());
                    imageViewProduit.setImage(image);
                } catch (MalformedURLException ex) {
                    System.out.println(ex);
                }

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
