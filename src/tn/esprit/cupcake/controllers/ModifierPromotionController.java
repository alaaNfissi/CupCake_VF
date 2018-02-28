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
import java.time.LocalDate;
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
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.entities.Promotion;
import tn.esprit.cupcake.services.ProduitService;
import tn.esprit.cupcake.services.PromotionService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author Basly
 */
public class ModifierPromotionController implements Initializable {

    @FXML
    private TextField txtLibelle;
    @FXML
    private TextField txtPourcentage;
    @FXML
    private DatePicker txtDateDebut;
    @FXML
    private DatePicker txtDateFin;
    int id;
    @FXML
    private Button btnImport;
    @FXML
    private ImageView imagePromotion;
    @FXML
    private TextArea txtDescription;
    @FXML
    private ComboBox CategorieProduit;
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
    List listCategorie;
    ObservableList options = FXCollections.observableArrayList();
    List<Integer> ListIdProduit;
    List<Produit> ListIdProduitPromo;
    ObservableList<Produit> listViewProduit;
    List<Produit> listProduit;
    ObservableList<Promotion> listViewPromotion;
    List<Promotion> listPromotion;
    @FXML
    private Button modifierPromotion;
    String path = "";
    String image;
    Promotion promot;
    Float PrixPromotion ;
	@FXML
	private JFXButton returnBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("TEEESST11" + id);
        FillCategorie();
    }

    private void setCellValueProduit() throws SQLException {
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle_produit"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prixPromotion"));
        select.setCellValueFactory(new PropertyValueFactory<>("select"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        dateExp.setCellValueFactory(new PropertyValueFactory<>("date_expiration"));
        /********************************************************************************/
        libelle.setStyle( "-fx-alignment: CENTER;");
        select.setStyle( "-fx-alignment: CENTER;");
        quantite.setStyle( "-fx-alignment: CENTER;");
        dateExp.setStyle( "-fx-alignment: CENTER;");
        prix.setStyle( "-fx-alignment: CENTER;");
        /********************************************************************************/
    }

    public void getInfoPromotion(Promotion promo, int id_promotion) throws SQLException {
        id = id_promotion;
        image = promo.getImage();
        promot = promo;
        PromotionService sr = new PromotionService();
        List<Promotion> listPromotion = sr.selectPromotionByID(id);
        for (Promotion promotion : listPromotion) {
            LocalDate dDebut = new java.sql.Date(promotion.getDate_debut().getTime()).toLocalDate();
            LocalDate dFin = new java.sql.Date(promotion.getDate_fin().getTime()).toLocalDate();
            txtLibelle.setText(promotion.getLibelle_promotion());
            txtPourcentage.setText(String.valueOf(promotion.getPourcentage()));
            txtDateDebut.setValue(dDebut);
            txtDateFin.setValue(dFin);
            txtDescription.setText(promotion.getDescription());
            File file = new File(promotion.getImage());
            try {
                Image imagePath = new Image(file.toURI().toURL().toExternalForm());
                imagePromotion.setImage(imagePath);
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
        }
        System.out.println("TEEESST22" + id);
        List<Produit> produits = new ArrayList<>();
        PromotionService sr1 = new PromotionService();
        ProduitService sr2 = new ProduitService();
        listProduit = new ArrayList<>();
        ListIdProduit = sr1.selectProduitPromotion(promo, id);
        for (int id : ListIdProduit) {
            listProduit = sr2.selectProduitById(id);
            for (Produit produit : listProduit) {
                produits.add(produit);
                produit.getSelect().setSelected(true);
                produit.setPrixPromotion((produit.getPrix()*promot.getPourcentage())/100);
                        System.out.println("Priiixx promooo"+produit.getPrixPromotion());
            }
            options = FXCollections.observableArrayList(produits);
            tableProduit.setItems(options);
            setCellValueProduit();
        }
    }

    private void AfficherData() {
        PromotionService sr1 = new PromotionService();
        try {
            ProduitService sr = new ProduitService();
            listProduit = new ArrayList<>();
            if (CategorieProduit.getValue() == "All") {
                System.out.println("OKKKK ALLL");
                listProduit = sr.selectAllProduit();
            } else {
                listProduit = sr.selectProduit(CategorieProduit.getSelectionModel().getSelectedItem().toString());
            }
            for (Produit produit : listProduit) {
                ListIdProduitPromo = sr1.selectProduitPromotion2(promot, id);
                for (Produit produit2 : ListIdProduitPromo) {
                    if (produit.getId_produit() == produit2.getId_produit()) {
                        produit.getSelect().setSelected(true);       
                    } 
                }
            }

            listViewProduit = FXCollections.observableArrayList(listProduit);
            tableProduit.setItems(listViewProduit);
            setCellValueProduit();
        } catch (SQLException ex) {
            System.out.println(ex);
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
    private void showProduit(ActionEvent event) {
        AfficherData();
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

    private List<Integer> getIdProduit() {
        ListIdProduit = new ArrayList<>();
        for (Produit produit : listProduit) {
            if (produit.getSelect().isSelected()) {
                ListIdProduit.add(produit.getId_produit());
            }
        }
        return ListIdProduit;
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
    
    @FXML
    private void modifierPromotion(ActionEvent event) throws SQLException {
        PromotionService sr = new PromotionService();
        if(validateInputs()){
        Date dateDebut = Date.valueOf(txtDateDebut.getValue());
        Date dateFin = Date.valueOf(txtDateFin.getValue());
        int pourcentage = Integer.parseInt(txtPourcentage.getText());
        try {
            if (path == "") {
                path = image;
            }
            ListIdProduit = new ArrayList<>();
            for (Produit produit : listProduit) {
                if (produit.getSelect().isSelected()) {
                    System.out.println("IDDDD PRODUIT  " + id);
                    System.out.println("IDDDD PROMO" + promot.getId_promotion());
                    sr.deleteProduitPromotion(promot.getId_promotion());
                }
                List<Integer> lsproduit = getIdProduit();
                for (int id : lsproduit) {
                //    sr.ajouterProduitPromotion(id, promot.getId_promotion());
                }
            }

            Promotion promotion = new Promotion(txtLibelle.getText(), pourcentage, dateDebut, dateFin, txtDescription.getText(), path);
            sr.modifierPromotion(promotion, id);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
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
