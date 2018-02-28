/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author Basly
 */
public class PreInscriptionController implements Initializable {

    private InscriptionPatissierController InscriptionPatissierController;
    @FXML
    private Button btnGoInscription;
    @FXML
    private RadioButton radioClient;
    @FXML
    private RadioButton radioPatissier;
    @FXML
    private ToggleGroup choixCompte;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
		//HomeViewController.indexTypeCompte =11;
        InscriptionPatissierController = new InscriptionPatissierController();
    }
    @FXML
    private void GoInscription(ActionEvent event) throws Exception {
//       Stage stage = (Stage) btnGoInscription.getScene().getWindow();
//       InscriptionPatissierController.start(stage);
        FXMLLoader loader = null;
        try {
            if (radioPatissier.isSelected()) {
                
				HomeViewController.indexTypeCompte =111;

            } else if (radioClient.isSelected()) {
               // loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/CompteClientFXML.fxml"));
				HomeViewController.indexTypeCompte =1111;

            }
			loader = new FXMLLoader(getClass().getResource(Routes.MAINVIEW));
            Stage stage1 = (Stage) btnGoInscription.getScene().getWindow();
            Stage stage = new Stage();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
}
