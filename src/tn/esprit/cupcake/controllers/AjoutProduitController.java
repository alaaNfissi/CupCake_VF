/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.scene.control.Alert;
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
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.services.ProduitService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;


/**
 * FXML Controller class
 *
 * @author esprit
 */
public class AjoutProduitController implements Initializable {

    @FXML
    private TextField txtLibelle;
    @FXML
    private TextField txtCategorie;
    @FXML
    private TextField txtPrix;
    @FXML
    private DatePicker txtDate;
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
    private Button btnValider;
    @FXML
    private ImageView imageViewProduit;
	/*@FXML
    private ImageView txtRetour;*/
    // DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy",Locale.FRANCE);
   /* @FXML
    private Button btnAfficher;*/
	
   

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
		txtId_patisserie.setVisible(false);
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
    private void ajouterProduit(ActionEvent event) throws SQLException  {
        System.out.println("h");
     
          ProduitService sv=new ProduitService();
          System.out.println("hné marrokhra");
          if(validateInputs()){
                 java.sql.Date x = java.sql.Date.valueOf(txtDate.getValue());
				 PatisserieService ps = new PatisserieService();
				 Patisserie pt = ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
        Produit p=new Produit(txtLibelle.getText(),txtCategorie.getText(),Float.parseFloat(txtPrix.getText()),x,Integer.parseInt(txtQuantite.getText()),txtDescription.getText(),Integer.parseInt(txtNote.getText()),path,pt.getId_patisserie());
   System.out.println("hnéééé");
       
            sv.ajouterProduit(p);
        
              
               System.out.println("produit ajouté avec succès !! ");}
          
          
            
        
    }
    
     public static boolean isNotInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }
   private boolean validateInputs() 
    {
          // java.sql.Date x = java.sql.Date.valueOf(txtDate.getValue());
              if ((txtLibelle.getText().isEmpty()) || (txtCategorie.getText().isEmpty())
                || ((txtPrix.getText()).isEmpty()) || ((txtDate.getValue()==null))
             
                || (txtQuantite.getText().isEmpty()) || (txtDescription.getText().isEmpty())
                /*|| (txtNote.getText().isEmpty()) ||(path.getText.isEmpty())*//*||(txtId_patisserie.getText().isEmpty())*/) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez remplir tout les champs");
            alert1.setHeaderText(null);
            alert1.show();
            return false;}
              else if (!isNotInteger(txtQuantite.getText())) {
                   Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("erreur dans le champ quantite veiller mettre un numero");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
            
        }
                 else if (!isNotInteger(txtPrix.getText())) {
                   Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("erreur dans le champ prix veiller mettre un numero");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
            
        }
                /* else if (!isNotInteger(txtId_patisserie.getText())) {
                   Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("erreur dans le champ id_patisserie veiller mettre un numero");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
            
        }*/
               else if (!isNotInteger(txtNote.getText())) {
                   Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("erreur dans le champ note veiller mettre un numero");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
            
        }
              
              return true;
        
    }

   /* @FXML
    private void GoAfficher(ActionEvent event)throws Exception {
            FXMLLoader loader = null;
               try {
                    loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/AffichageProduit.fxml"));
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
	
/* @FXML
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
        }*/
    
}
