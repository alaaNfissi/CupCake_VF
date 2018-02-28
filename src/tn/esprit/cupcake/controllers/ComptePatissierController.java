/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import tn.esprit.cupcake.entities.*;
import tn.esprit.cupcake.services.*;
import tn.esprit.cupcake.test.CupCakeFX;

/**
 *
 * @author Basly
 */
public class ComptePatissierController implements Initializable {

    @FXML
    private Button btnInfo;
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
    private DatePicker txtDate;
    @FXML
    private TextField txtTel;
    @FXML
    private JFXPasswordField txtPassActuel;
    @FXML
    private JFXPasswordField txtPassNv;
    @FXML
    private JFXPasswordField txtPassConf;
    @FXML
    private TextField txtLibPatisserie;
    @FXML
    private TextField txtAddPatisserie;
    @FXML
    private TextField txtSpecialite;
    @FXML
    private TextField txtCompteInsta;
    @FXML
    private TextField txtCompteFb;
    @FXML
    private TextArea txtDescription;
    @FXML
    private DatePicker txtDateCreation;
    @FXML
    private Button btnConfirmer;
    @FXML
    private Button btnImportImage;
    @FXML
    private Label loadEmail;
    @FXML
    private Label loadPseudo;
    @FXML
    private Label loadNumTel;
    @FXML
    private Label loadDateNaiss;
    @FXML
    private Label loadNomPatisserie;
    @FXML
    private Label loadCptFbPatisserie;
    @FXML
    private Label loadSpecialitePatisserie;
    @FXML
    private Label loadDateCreationPatisserie;
    @FXML
    private Label loadAdressePatisserie;
    @FXML
    private Label loadCptInstaPatisserie;
    @FXML
    private ImageView imageViewPatisserieModif;
    @FXML
    private VBox VBoxInfoPersonel;
    @FXML
    private VBox VBoxMdp;
    @FXML
    private Label loadNomPrenom;
    @FXML
    private VBox VBoxInfoPatisserie;
    @FXML
    private AnchorPane VBoxInfo;
    String savePassword = "";
    String path = "";
    @FXML
    private JFXButton btnCommande;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private ImageView imageViewPatisserieInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        VBoxInfo.setVisible(true);
        VBoxInfoPatisserie.setVisible(false);
        VBoxInfoPersonel.setVisible(false);
        VBoxMdp.setVisible(false);
        txtPassActuel.setEditable(false);
		btnConfirmer.setVisible(false);
        try {
            detailPatissier();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComptePatissierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ComptePatissierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void detailPatissier() throws FileNotFoundException, MalformedURLException {
        PatissierService sr1 = new PatissierService();
        PatisserieService sr2 = new PatisserieService();
        try {
            List<Patissier> listPatissier = sr1.selectPatissier();
            for (Patissier patissier : listPatissier) {
                loadEmail.setText(patissier.getEmail());
                loadPseudo.setText(patissier.getPseudo());
                loadNumTel.setText(Integer.toString(patissier.getNum_tel()));
                loadNomPrenom.setText(patissier.getNom() + " " + patissier.getPrenom());
                loadDateNaiss.setText(patissier.getDate_naissance().toString());

            }
            List<Patisserie> listPatisserie = sr2.selectPatisserie();
            for (Patisserie Patisserie : listPatisserie) {
                loadNomPatisserie.setText(Patisserie.getLibelle_patisserie());
                loadAdressePatisserie.setText(Patisserie.getAdresse_patisserie());
                loadDateCreationPatisserie.setText(Patisserie.getDate_creation().toString());
                loadSpecialitePatisserie.setText(Patisserie.getSpecialite());
                loadCptFbPatisserie.setText(Patisserie.getCompte_facebook());
                loadCptInstaPatisserie.setText(Patisserie.getCompte_instagram());
                File file = new File(Patisserie.getImage());

                try {
                    Image image = new Image(file.toURI().toURL().toExternalForm());
                    imageViewPatisserieInfo.setImage(image);
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
        VBoxInfo.setVisible(true);
        VBoxInfoPatisserie.setVisible(false);
        VBoxInfoPersonel.setVisible(false);
        VBoxMdp.setVisible(false);
		btnConfirmer.setVisible(false);
        detailPatissier();
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

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }

    private boolean validateInputs() {
        if ((txtNom.getText().isEmpty()) || (txtPrenom.getText().isEmpty())
                || (txtEmail.getText().isEmpty()) || (txtTel.getText().isEmpty())
                || (txtDate.getValue()==null)
                || (txtPseudo.getText().isEmpty())
                || (txtLibPatisserie.getText().isEmpty()) || (txtAddPatisserie.getText().isEmpty())
                || (txtSpecialite.getText().isEmpty()) || (txtDescription.getText().isEmpty())
                || (txtCompteFb.getText().isEmpty()) || (txtCompteInsta.getText().isEmpty())
                || (txtDateCreation.getValue()== null)) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez remplir tout les champs");
            alert1.setHeaderText(null);
            alert1.show();
            return false;

        } else if (!(validate(txtEmail.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre email");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if ((txtTel.getText().trim().length() > 8) || ((txtTel.getText().trim().length() < 8)) || (!isInteger(txtTel.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre numéro de téléphone");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }
        return true;
    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @FXML
    private void ModifierCompte(ActionEvent event) {
        VBoxInfo.setVisible(false);
        VBoxInfoPatisserie.setVisible(true);
        VBoxInfoPersonel.setVisible(true);
        VBoxMdp.setVisible(true);
		btnConfirmer.setVisible(true);
        PatissierService sr1 = new PatissierService();
        PatisserieService sr2 = new PatisserieService();
        try {
            List<Patissier> listPatissier = sr1.selectPatissier();
            for (Patissier patissier : listPatissier) {
                txtPassActuel.setText(patissier.getPassword());
                txtNom.setText(patissier.getNom());
                txtPrenom.setText(patissier.getPrenom());
                txtEmail.setText(patissier.getEmail());
                txtPseudo.setText(patissier.getPseudo());
                txtTel.setText(Integer.toString(patissier.getNum_tel()));
                LocalDate dNaiss = new java.sql.Date(patissier.getDate_naissance().getTime()).toLocalDate();
                txtDate.setValue(dNaiss);
            }
            List<Patisserie> listPatisserie = sr2.selectPatisserie();
            for (Patisserie patisserie : listPatisserie) {
                txtLibPatisserie.setText(patisserie.getLibelle_patisserie());
                txtAddPatisserie.setText(patisserie.getLibelle_patisserie());
                txtSpecialite.setText(patisserie.getSpecialite());
                txtCompteInsta.setText(patisserie.getCompte_instagram());
                txtCompteFb.setText(patisserie.getCompte_facebook());
                txtDescription.setText(patisserie.getDescription());
                LocalDate dCreation = new java.sql.Date(patisserie.getDate_creation().getTime()).toLocalDate();
                txtDateCreation.setValue(dCreation);
                File file = new File(patisserie.getImage());

                try {
                    Image image = new Image(file.toURI().toURL().toExternalForm());
                    imageViewPatisserieModif.setImage(image);
                } catch (MalformedURLException ex) {
                    System.out.println(ex);
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void ModifierInformation(ActionEvent event) throws SQLException {
        PatissierService sr1 = new PatissierService();
        PatisserieService sr2 = new PatisserieService();
        if (confirmPassword() && validateInputs()) {
            int tel = Integer.parseInt(txtTel.getText());
            savePassword = testPassword();
                Date dateNaiss = Date.valueOf(txtDate.getValue());
                Patissier patissier = new Patissier(savePassword, txtEmail.getText(), txtPseudo.getText(), tel, txtNom.getText(), txtPrenom.getText(), dateNaiss,TypeRole.Patisssier.ordinal());
                Date dateCreation = Date.valueOf(txtDateCreation.getValue());
               if(path=="")
				{
					Patisserie p=sr2.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
					path=p.getImage();
				}
				Patisserie patisserie = new Patisserie(txtLibPatisserie.getText(), txtAddPatisserie.getText(), dateCreation, txtSpecialite.getText(), txtCompteFb.getText(), txtCompteInsta.getText(), txtDescription.getText(), path);
				Patisserie patisserie1 = new Patisserie(txtLibPatisserie.getText(), txtAddPatisserie.getText(), dateCreation, txtSpecialite.getText(), txtCompteFb.getText(), txtCompteInsta.getText(), txtDescription.getText(), path,CupCakeFX.user.getId_utilisateur());
				sr1.modifierPatissier(patissier, CupCakeFX.user.getId_utilisateur());
				System.out.println(CupCakeFX.user.getId_utilisateur());
				if(sr2.searchByIdPatissier(CupCakeFX.user.getId_utilisateur())!=null)
				{
					System.out.println(sr2.searchByIdPatissier(CupCakeFX.user.getId_utilisateur()));
					System.out.println("Patisserie existe");
					sr2.modifierPatisserie(patisserie,CupCakeFX.user.getId_utilisateur());
				}
				else
				{
					System.out.println("Patisserie n'existe pas");
					sr2.ajouterPatisserie(patisserie1);
				}
				
        }
    }

    @FXML
    private void importImage(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            path = file.getAbsolutePath();
            String path2 = file.toURI().toURL().toString();
            Image image = new Image(path2);
            imageViewPatisserieModif.setImage(image);
        }
    }

    @FXML
    private void showCommande(ActionEvent event) {
    }


}
