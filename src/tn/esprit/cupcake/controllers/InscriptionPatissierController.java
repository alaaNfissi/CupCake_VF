/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.esprit.cupcake.entities.*;
import tn.esprit.cupcake.services.*;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author Basly
 */
public class InscriptionPatissierController implements Initializable {

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTel;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private TextField txtPseudo;
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
    private Button btnInscrire;
    @FXML
    private DatePicker txtDateCreation;
    @FXML
    private Button btnImportImage;
    @FXML
    private ImageView imageViewPatisserie;

    String path = "";
    @FXML
    private JFXPasswordField txtCfPassword;
    @FXML
    private VBox VBoxInfoPersonel;
    @FXML
    private JFXDatePicker txtDate;
    @FXML
    private VBox VBoxInfoPatisserie;

    @FXML
    private VBox VBoxMdp1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
   

    @FXML
    private void importImage(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file = fileChooser.showOpenDialog(null);
        path = file.getAbsolutePath();
        Image image = new Image(file.toURI().toURL().toString());
        imageViewPatisserie.setImage(image);
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

      } else if (!(txtCfPassword.getText().equals(txtPassword.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre mot de passe");
            alert2.setHeaderText(null);
            alert2.show();
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
    private void ajouterPatissier(ActionEvent event) throws SQLException, IOException, InterruptedException {
        long id_patissier = 0;
        PatissierService sr1 = new PatissierService();
        PatisserieService sr2 = new PatisserieService();
        
        try {
            if(validateInputs()){
                Date dateNaiss = Date.valueOf(txtDate.getValue());
                Patissier patissier = new Patissier(txtPassword.getText(), txtEmail.getText(), txtPseudo.getText(),0, Integer.parseInt(txtTel.getText()), txtNom.getText(), txtPrenom.getText(),dateNaiss, 1);
				UtilisateurService us=new UtilisateurService();
				if(us.verifierPseudo().contains(txtPseudo.getText()))
				{
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Erreur");
					alert.setContentText("Votre Pseudo déjà existe, veuillez changer votre Pseudo !");
					alert.setHeaderText(null);
					alert.show();
					alert.wait();
				}
				sr1.ajouterPatissier(patissier);
            id_patissier = sr1.selectPatissierAjouter(patissier);
            Date dateCreation = Date.valueOf(txtDateCreation.getValue());
            Patisserie patisserie = new Patisserie(txtLibPatisserie.getText(), txtAddPatisserie.getText(),dateCreation, txtSpecialite.getText(), txtCompteFb.getText(), txtCompteInsta.getText(), txtDescription.getText(), path, id_patissier);
            sr2.ajouterPatisserie(patisserie);
            
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.setContentText("Operation effectuée avec succée !");
            alert.show();
            alert.setOnHidden(e -> {
                if (alert.getResult() == ButtonType.YES) {
                    Stage stage = new Stage();
						Parent root = null;
					try {
						root = FXMLLoader.load(getClass().getResource(Routes.LOGINVIEW));
					} catch (IOException ex) {
						Logger.getLogger(InscriptionClientController.class.getName()).log(Level.SEVERE, null, ex);
					}
						JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);

						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						((Node) (event.getSource())).getScene().getWindow().hide();
                } else {
                    System.out.println("canceled");
                }
            });
			}
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (NumberFormatException ex) 
        {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez remplir tout les champs");
            alert1.setHeaderText(null);
            alert1.show();
        }
    }

}
