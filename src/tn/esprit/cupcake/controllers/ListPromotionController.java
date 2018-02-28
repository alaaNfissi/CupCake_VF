/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.cupcake.entities.*;
import tn.esprit.cupcake.services.*;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author Basly
 */
public class ListPromotionController implements Initializable {

    public static ListPromotionController cont;
    @FXML
    private TableView<Promotion> tablePromotion;
    @FXML
    private TableColumn<Promotion, String> libellePromotion;
    @FXML
    private TableColumn<Promotion, Integer> Pourcentage;
    @FXML
    private TableColumn<Promotion, Date> dateDebut;
    @FXML
    private TableColumn<Promotion, Date> dateFin;
    @FXML
    private TableColumn<Promotion, Button> action;
    @FXML
    private ImageView imagePromotion;
    @FXML
    private TableView<Produit> tableProduit;
    @FXML
    private TableColumn<Produit, String> select;
    @FXML
    private TableColumn<Produit, String> libelleProduit;
    @FXML
    private TableColumn<Produit, Float> prix;
    @FXML
    private TableColumn<Produit, Integer> quantite;
    @FXML
    private TableColumn<Produit, Date> dateExp;
    @FXML
    private TextArea txtDescription;
    ObservableList<Promotion> listViewPromotion;
    List<Promotion> listPromotion;
    List<Integer> ListIdProduit;
    ObservableList<Produit> listViewProduit;
    List<Produit> listProduit;
    ObservableList options = FXCollections.observableArrayList();
    @FXML
    private Button btnModifier;
    String path = "";
    @FXML
    private JFXButton btnGoToAjoutPromo;
	@FXML
    private JFXTextField txtRecherche;
    @FXML
    private JFXButton btnPdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cont = this;
        txtDescription.setEditable(false);
        AfficherDataPromotion();
    }

    private void setCellValuePromotion() {
        libellePromotion.setCellValueFactory(new PropertyValueFactory<>("libelle_promotion"));
        Pourcentage.setCellValueFactory(new PropertyValueFactory<>("pourcentage"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        action.setCellValueFactory(new PropertyValueFactory<>("delete"));
        /**
         * *****************************************************************************
         */
        libellePromotion.setStyle("-fx-alignment: CENTER;");
        Pourcentage.setStyle("-fx-alignment: CENTER;");
        dateDebut.setStyle("-fx-alignment: CENTER;");
        dateFin.setStyle("-fx-alignment: CENTER;");
        action.setStyle("-fx-alignment: CENTER;");
        /**
         * *****************************************************************************
         */
    }

    private void setCellValueProduit() {
        libelleProduit.setCellValueFactory(new PropertyValueFactory<>("libelle_produit"));
        select.setCellValueFactory(new PropertyValueFactory<>("select"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        dateExp.setCellValueFactory(new PropertyValueFactory<>("date_expiration"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prixPromotion"));
        /**
         * *****************************************************************************
         */
        libelleProduit.setStyle("-fx-alignment: CENTER;");
        select.setStyle("-fx-alignment: CENTER;");
        quantite.setStyle("-fx-alignment: CENTER;");
        dateExp.setStyle("-fx-alignment: CENTER;");
        prix.setStyle("-fx-alignment: CENTER;");
        /**
         * *****************************************************************************
         */
    }

    public void AfficherDataPromotion() {
        try {
            PromotionService sr = new PromotionService();
            listPromotion = new ArrayList<>();
            listPromotion = sr.selectPromotion();
            listViewPromotion = FXCollections.observableArrayList(listPromotion);
            tablePromotion.setItems(listViewPromotion);
            setCellValuePromotion();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void showDetails(MouseEvent event) throws SQLException {
        Promotion promotion = tablePromotion.getSelectionModel().getSelectedItem();
        txtDescription.setText(promotion.getDescription());
        File file = null;
        try {
            file = new File(tablePromotion.getSelectionModel().getSelectedItem().getImage());
        } catch (NullPointerException ex) {
            System.out.println("Pas d'image");
        }
        try {
            Image image = new Image(file.toURI().toURL().toExternalForm());
            imagePromotion.setImage(image);
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (NullPointerException ex) {
            System.out.println("Pas d'image");
        }
        List<Produit> produits = new ArrayList<>();
        PromotionService sr1 = new PromotionService();
        ProduitService sr2 = new ProduitService();
        listProduit = new ArrayList<>();
        ListIdProduit = sr1.selectProduitPromotion(promotion, promotion.getId_promotion());
		options.clear();
        for (int id : ListIdProduit) {
            listProduit = sr2.selectProduitById(id);
			System.out.println(listProduit);
            for (Produit produit : listProduit) {
                produits.add(produit);
				System.out.println(produit);
                produit.getSelect().setSelected(true);
				//produit.getSelect().sets
				
                produit.setPrixPromotion((produit.getPrix() * promotion.getPourcentage()) / 100);
            }
            options = FXCollections.observableArrayList(produits);
            tableProduit.setItems(options); 
            setCellValueProduit();
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
    private void GoModifierPromotion(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = null;
        Promotion promotion = tablePromotion.getSelectionModel().getSelectedItem();
        int id_promotion = promotion.getId_promotion();
        loader = new FXMLLoader(getClass().getResource(Routes.MODIFIERPROMOTION));
        Parent root = loader.load();
        ModifierPromotionController modifierPromotionController = loader.getController();
        System.out.println(id_promotion);
        modifierPromotionController.getInfoPromotion(promotion, id_promotion);
        Stage stage = new Stage();
        /*Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
        ((Node) (event.getSource())).getScene().getWindow().hide();
		/***************************/
		//loader = new FXMLLoader(getClass().getResource(Routes.MODIFIEREVENEMENT));
        //Parent root = loader.load();
        //ModifierEvenementController modifEventController = loader.getController();
		//modifierPromotionController.getInfoPromotion(promotion, id_promotion);
		//Stage stage = new Stage();
        /*Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
         ((Node) (event.getSource())).getScene().getWindow().hide();*/
		 
		 //root = FXMLLoader.load(getClass().getResource(Routes.LOGINVIEW));
		 //root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
		 
				 JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);
						
						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						((Node) (event.getSource())).getScene().getWindow().hide();
		/*********************/
    }

    /**
     * ******************************************************************************************
     */
    @FXML
    private void RechercheDynamique(KeyEvent event) {
        FilteredList<Promotion> filteredData = new FilteredList<>(listViewPromotion, e -> true);
        txtRecherche.setOnKeyReleased(e
                -> {
            txtRecherche.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Promotion>) new Predicate<Promotion>() {
                    @Override
                    public boolean test(Promotion promotion) {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (promotion.getLibelle_promotion().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        return false;
                    }
                });

            });
            SortedList<Promotion> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tablePromotion.comparatorProperty());
            tablePromotion.setItems(sortedData);
        });
    }

    /**
     * ******************************************************************************************
     */
    /**
     * ******************************************************************************************
     */
    @FXML
    private void importPDF(ActionEvent event) throws DocumentException, BadElementException, IOException {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("RapportPromotion.pdf"));
            document.open();
            
            
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("CupCake.png");
            image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
            
            
            
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 25.0f, Font.BOLD, BaseColor.BLACK);
            Chunk c = new Chunk("Rapport Promotion", f);
            Paragraph p1 = new Paragraph(c);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setSpacingAfter(10f);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.now();
            Paragraph date = new Paragraph(dtf.format(dateTime));
            date.setAlignment(Paragraph.ALIGN_RIGHT);
           
            
            document.add(date); 
            document.add(image);
            document.add(p1);
            
            PdfPTable table = new PdfPTable(4);
            PdfPCell cell1 = new PdfPCell(new Phrase("Libelle"));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell2 = new PdfPCell(new Phrase("Pourcentage"));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell3 = new PdfPCell(new Phrase("Date debut"));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell4 = new PdfPCell(new Phrase("Date fin"));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            for (int i = 0; i < listViewPromotion.size(); i++) {
                table.addCell(tablePromotion.getItems().get(i).getLibelle_promotion());
                table.addCell("" + tablePromotion.getItems().get(i).getPourcentage());
                table.addCell("" + tablePromotion.getItems().get(i).getDate_debut());
                table.addCell("" + tablePromotion.getItems().get(i).getDate_fin());
            }
            document.add(table);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(e);
        }
    }
       @FXML
    private void GoToAjoutPromo(ActionEvent event) {
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/AjouterPromotionFXML.fxml"));
            Stage stage1 = (Stage) btnGoToAjoutPromo.getScene().getWindow();
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
