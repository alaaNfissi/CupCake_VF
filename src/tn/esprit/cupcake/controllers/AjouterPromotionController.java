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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
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
public class AjouterPromotionController implements Initializable {

    @FXML
    private TableView<Produit> tableProduit;
    @FXML
    private TableColumn<Produit, String> select;
    @FXML
    private TableColumn<Produit, Float> prix;
    @FXML
    private TableColumn<Produit, String> libelle;
    @FXML
    private TableColumn<Produit, Integer> quantite;
    @FXML
    private TableColumn<Produit, Date> dateExp;
    @FXML
    private ComboBox CategorieProduit;
    @FXML
    private TextField txtPourcentage;
    @FXML
    private Button ajouterPromotion;
    @FXML
    private TextField txtLibelle;
    @FXML
    private TextArea txtDescription;
    @FXML
    private DatePicker txtDateDebut;
    @FXML
    private DatePicker txtDateFin;
    @FXML
    private ImageView imagePromotion;
    @FXML
    private Button btnImport;
    List listCategorie;
    ObservableList options = FXCollections.observableArrayList();
    String path = "";
    List<Integer> ListIdProduit;
    ObservableList<Produit> listViewProduit;
    List<Produit> listProduit;
    float PrixPromotion;
	@FXML
	private JFXButton returnBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tableProduit.setEditable(true);
        FillCategorie();
    }

    private void setCellValue() {
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle_produit"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        select.setCellValueFactory(new PropertyValueFactory<>("select"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        dateExp.setCellValueFactory(new PropertyValueFactory<>("date_expiration"));
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

        if ((txtLibelle.getText().isEmpty()) || (txtDescription.getText().isEmpty())
                || (txtPourcentage.getText().isEmpty())
                || (txtDateDebut.getValue() == null)
                || (txtDateFin.getValue() == null)) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez remplir tout les champs");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        }
        if (!isInteger(txtPourcentage.getText())) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez saisir un nombre dans le champ pourcentage");
            alert1.setHeaderText(null);
            alert1.show();
        return false;
        }

        return true;
    }

    private void AfficherData() {
        try {
            ProduitService sr = new ProduitService();
            listProduit = new ArrayList<>();
            if (CategorieProduit.getValue() == "All") {
                System.out.println("OKKKK ALLL");
                listProduit = sr.selectAllProduit();
            } else {
                listProduit = sr.selectProduit(CategorieProduit.getSelectionModel().getSelectedItem().toString());
            }
            listViewProduit = FXCollections.observableArrayList(listProduit);
            tableProduit.setItems(listViewProduit);
            setCellValue();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    private List<Integer> getIdProduit() {
        ListIdProduit = new ArrayList<>();
        for (Produit produit : listProduit) {
            if (produit.getSelect().isSelected()) {
                ListIdProduit.add(produit.getId_produit());
            }
        }
        return ListIdProduit;
    }

    private void FillCategorie() {
        try {
            ProduitService sr = new ProduitService();
            listCategorie = new ArrayList<>();
            listCategorie = sr.selectCategorieProduit();
            listCategorie.add("All");
            options = FXCollections.observableArrayList(listCategorie);
            CategorieProduit.setItems(options);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void showProduit(ActionEvent event) {
        AfficherData();
    }

    @FXML
    private void ajouterPromotion(ActionEvent event) throws SQLException {
        PromotionService sr = new PromotionService();
        if(validateInputs()){
        int pourcentage = Integer.parseInt(txtPourcentage.getText());        
        Date dateDebut = Date.valueOf(txtDateDebut.getValue());
        Date dateFin = Date.valueOf(txtDateFin.getValue());
		PatisserieService ps= new PatisserieService();
		Patisserie p=ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
        Promotion promotion = new Promotion(txtLibelle.getText(), pourcentage, dateDebut, dateFin, txtDescription.getText(), path,p.getId_patisserie());
        sr.ajouterPromotion(promotion);
        List<Integer> lsproduit = getIdProduit();

        for (int id : lsproduit) {
            int idPromotion = sr.selectPromotionAjouter(promotion);
            for (Produit produit : listProduit) {
                if (produit.getId_produit() == id) {
                    if (produit.getSelect().isSelected()) {
                        PrixPromotion = (produit.getPrix() * promotion.getPourcentage() / 100);
                    }
                }
            }
            sr.ajouterProduitPromotion(id, idPromotion, PrixPromotion);
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
            imagePromotion.setImage(image);
        }

    }

	@FXML
	private void returnAction(ActionEvent event) throws IOException {
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
