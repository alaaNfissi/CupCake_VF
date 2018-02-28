/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import tn.esprit.cupcake.entities.Client;
import tn.esprit.cupcake.entities.TypeRole;
import tn.esprit.cupcake.services.ClientService;
import tn.esprit.cupcake.test.CupCakeFX;

/**
 * FXML Controller class
 *
 * @author Basly
 */
public class CompteClientController implements Initializable {

    @FXML
    private Button btnInfo;
    private AnchorPane anchorInformation;
    @FXML
    private Label loadEmail;
    @FXML
    private Label loadPseudo;
    @FXML
    private Label loadNumTel;
    @FXML
    private Label loadDateNaiss;
    @FXML
    private Label loadAdresse;
    @FXML
    private ImageView imageClientModif;
    @FXML
    private Button btnCompte;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPseudo;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtAdresse;
    @FXML
    private TextField txtTel;
    @FXML
    private RadioButton radioHomme;
    @FXML
    private RadioButton radioFemme;
    String valueRadio;
    @FXML
    private JFXPasswordField txtPassActuel;
    @FXML
    private JFXPasswordField txtPassNv;
    @FXML
    private JFXPasswordField txtPassConf;
    String savePassword = "";
    @FXML
    private JFXButton btnCommande;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private VBox VBoxInfoPersonel;
    @FXML
    private JFXButton btnModifier;
    @FXML
    private VBox VBoxInfo;
    @FXML
    private ImageView imageClientInfo;
    @FXML
    private VBox VBoxMdp;
    @FXML
    private Label loadNomPrenom;
    @FXML
    private JFXDatePicker txtDate;
    String path="";
    @FXML
    private ToggleGroup gender;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VBoxInfoPersonel.setVisible(false);
        btnModifier.setVisible(false);
        VBoxMdp.setVisible(false);
        VBoxInfo.setVisible(true);
        txtPassActuel.setEditable(false);
        try {
            detailClient();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompteClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(CompteClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validateInputs() {
        if ((txtNom.getText().isEmpty()) || (txtPrenom.getText().isEmpty())
                || (txtEmail.getText().isEmpty()) || (txtTel.getText().isEmpty())
                || (txtDate.getValue()==null) || (txtAdresse.getText().isEmpty())
                || (txtPseudo.getText().isEmpty()) || (txtPassActuel.getText().isEmpty())
                || ((!radioFemme.isSelected()) && !(radioHomme.isSelected()))) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez remplir tout les champs  viiiiiiiiideeeee");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        }
        return true;
    }

    private String testPassword() {
        if ((txtPassNv.getText().isEmpty()) || (txtPassConf.getText().isEmpty())) {
            return savePassword = txtPassActuel.getText();
        } else if ((txtPassConf.getText().equals(txtPassNv.getText()))) {
            return savePassword = txtPassNv.getText();
        }
        return savePassword;
    }

    private boolean confirmPassword() {
        if (!(txtPassConf.getText().equals(txtPassNv.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre mot de passe");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }
        return true;
    }

    private void detailClient() throws FileNotFoundException, MalformedURLException {
        ClientService sr = new ClientService();
        try {
            List<Client> ls = sr.selectClient();
            for (Client c : ls) {
                loadEmail.setText(c.getEmail());
                loadPseudo.setText(c.getPseudo());
                loadNumTel.setText(Integer.toString(c.getNum_tel()));
                loadNomPrenom.setText(c.getNom() + " " + c.getPrenom());
                loadDateNaiss.setText(c.getDate_naissance().toString());
                loadAdresse.setText(c.getAdresse());
                File file = new File(c.getImage());

                try {
                    Image image = new Image(file.toURI().toURL().toExternalForm());
                    imageClientInfo.setImage(image);
                } catch (MalformedURLException ex) {
                    System.out.println(ex);
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void afficherDetails(ActionEvent event) throws FileNotFoundException, MalformedURLException {
        VBoxInfoPersonel.setVisible(false);
        btnModifier.setVisible(false);
        VBoxMdp.setVisible(false);
        VBoxInfo.setVisible(true);
        detailClient();
    }

    @FXML
    private void ModifierCompte(ActionEvent event) throws FileNotFoundException, MalformedURLException {
        VBoxInfoPersonel.setVisible(true);
        btnModifier.setVisible(true);
        VBoxMdp.setVisible(true);
        VBoxInfo.setVisible(false);
        ClientService sr = new ClientService();
        try {
            List<Client> ls = sr.selectClient();
            for (Client c : ls) {
                txtPassActuel.setText(c.getPassword());
                txtNom.setText(c.getNom());
                txtPrenom.setText(c.getPrenom());
                txtEmail.setText(c.getEmail());
                txtPseudo.setText(c.getPseudo());
                txtTel.setText(Integer.toString(c.getNum_tel()));
                LocalDate dNaiss = new java.sql.Date(c.getDate_naissance().getTime()).toLocalDate();
                txtDate.setValue(dNaiss);
                txtAdresse.setText(c.getAdresse());
                if (c.getSexe().equals("Femme")) {
                    radioFemme.setSelected(true);
                } else if (c.getSexe().equals("Homme")) {
                    radioHomme.setSelected(true);
                }
                File file = new File(c.getImage());
                Image image = new Image(file.toURI().toURL().toExternalForm());
                imageClientModif.setImage(image);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void ModifierInformation(ActionEvent event) throws SQLException {
        VBoxInfoPersonel.setVisible(true);
        btnModifier.setVisible(true);
        VBoxMdp.setVisible(true);
        VBoxInfo.setVisible(false);
        ClientService sr = new ClientService();
        if (radioHomme.isSelected()) {
            valueRadio = "Homme";
        } else if (radioFemme.isSelected()) {
            valueRadio = "Femme";
        }
		if(path=="")
		{
			ClientService cs = new ClientService();
			Client c =cs.findById(CupCakeFX.user.getId_utilisateur());
			path=c.getImage();
		}
        int tel = Integer.parseInt(txtTel.getText());
        savePassword = testPassword();
        try {
            Date dateNaiss = Date.valueOf(txtDate.getValue());
            Client client = new Client(savePassword, txtEmail.getText(), txtPseudo.getText(), tel, txtNom.getText(), txtPrenom.getText(),dateNaiss,TypeRole.Client.ordinal(), txtAdresse.getText(), valueRadio,path);
            if ((validateInputs()) && (confirmPassword())) {
                sr.modifierClient(client, CupCakeFX.user.getId_utilisateur());
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void showCommande(ActionEvent event) {
    }

    @FXML
    private void importImage(ActionEvent event) throws MalformedURLException, SQLException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            path = file.getAbsolutePath();
            String path2 = file.toURI().toURL().toString();
            Image image = new Image(path2);
            imageClientModif.setImage(image);
        }
        
    }
}
//Vérifier probléme radiobutton
//Afficher image
//Confirmation mdp
