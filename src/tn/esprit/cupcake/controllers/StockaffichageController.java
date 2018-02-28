/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.entities.Stock;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.services.StockService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author berber
 */
public class StockaffichageController implements Initializable {

    @FXML
    private Button ajoutstock;
    @FXML
    private Button supprimerstock;
    @FXML
    private Button modifierstock;
    @FXML
    private Button btnref;

    private Stock selectedid;
    @FXML
    private ImageView imageViewstock;
    @FXML
    private Button btnpie;
    @FXML
    private Button btnpdf;
    @FXML
    private TableView<Produit> tablestock;
    @FXML
    private TableColumn<Produit, Integer> id;
    @FXML
    private TableColumn<Produit, String> libelle;
    @FXML
    private TableColumn<Produit, String> cat;
    @FXML
    private TableColumn<Produit, Float> prix;
    @FXML
    private TableColumn<Produit, Date> datex;
    @FXML
    private TableColumn<Produit, Integer> quan;
    @FXML
    private TableColumn<Produit, String> desc;

    private ObservableList<Produit> data;
    @FXML
    private JFXTextField recherchetext;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/InscriptionPatissierFXML.fxml"));
        // TODO

        StockService sv = new StockService();
        // id patisserie static
		PatisserieService ps = new PatisserieService();
		try {
			Patisserie pt=ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
			List<Produit> list1 = sv.afficheTousProduitStock(pt.getId_patisserie());
			data = FXCollections.observableArrayList(list1);
		} catch (SQLException ex) {
			Logger.getLogger(StockaffichageController.class.getName()).log(Level.SEVERE, null, ex);
		}
        id.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle_produit"));
        cat.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        datex.setCellValueFactory(new PropertyValueFactory<>("date_expiration"));
        quan.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tablestock.setItems(data);

    }

    @FXML
    private void ajouterProduitStock(MouseEvent event) throws IOException {

        HomeViewController.ajouterCataloguebtn=0;
		HomeViewController.ajouterProduitbtn=0;
		HomeViewController.listeCataloguebtn=0;
		HomeViewController.affecterProduitCatalogue=0;
		HomeViewController.stockafficherBtn=0;
		HomeViewController.stockajoutPass=1;
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

    @FXML
    private void supprimerProduitStock(MouseEvent event) throws SQLException {

        Produit produit = tablestock.getSelectionModel().getSelectedItem();
        int a = produit.getId_produit();
        StockService sk = new StockService();
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation Dialog");
        alert1.setContentText("Etes vous sur de vouloir supprimer le produit du stock");
        alert1.setHeaderText(null);
        Optional<ButtonType> action = alert1.showAndWait();
        if (action.get() == ButtonType.OK) {
            sk.supprimerProduitStock(a);
            tablestock.getItems().remove(produit);
            Image img = new Image("/tn/esprit/cupcake/images/supprimerprod.png");
            Notifications nf = Notifications.create()
                    .title("**************************Nouvelle Notification Recue***********************")
                    .text(" Produit Supprimer avec succés ")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("Notification vu");
                        }
                    });
            nf.darkStyle();
            nf.show();
        }

    }

    @FXML
    private void modifier(MouseEvent event) throws SQLException, IOException {

        Produit produit = tablestock.getSelectionModel().getSelectedItem();
        String a = Integer.toString(produit.getId_produit());
        String b = produit.getLibelle_produit();
        String c = produit.getCategorie();
        String d = Float.toString(produit.getPrix());
        LocalDate e = new java.sql.Date(produit.getDate_expiration().getTime()).toLocalDate();
        String f = Integer.toString(produit.getQuantite());
        String g = produit.getDescription();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/Stockmodifier.fxml"));
        Stage stage1 = (Stage) modifierstock.getScene().getWindow();
        Parent root = loader.load();
        StockmodifierController mo = loader.getController();
        System.out.println(a);
        mo.Functionparam(a, b, c, d, e, f, g);
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/tn/esprit/cupcake/views/bootstrap3.css").toString());
        stage1.setScene(scene);
        stage1.show();

    }

    @FXML
    private void refreshStock(MouseEvent event) {

        StockService sv = new StockService();
		PatisserieService ps = new PatisserieService();
		try {
			Patisserie pt=ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
			List<Produit> list1 = sv.afficheTousProduitStock(pt.getId_patisserie());
			data = FXCollections.observableArrayList(list1);
		} catch (SQLException ex) {
			Logger.getLogger(StockaffichageController.class.getName()).log(Level.SEVERE, null, ex);
		}
        id.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle_produit"));
        cat.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        datex.setCellValueFactory(new PropertyValueFactory<>("date_expiration"));
        quan.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));

        tablestock.setItems(data);
    }

    @FXML
    private void afficherImage(MouseEvent event) {

        Produit selecteditem = tablestock.getSelectionModel().getSelectedItem();
        File file = new File(selecteditem.getImage());

        try {
            Image image = new Image(file.toURI().toURL().toExternalForm());
            imageViewstock.setImage(image);
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void afficherpie(MouseEvent event) {

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (int i = 0; i < data.size(); i++) {
            pieDataset.setValue(tablestock.getItems().get(i).getLibelle_produit() + "  " + tablestock.getItems().get(i).getQuantite(), tablestock.getItems().get(i).getQuantite());
        }
        JFreeChart chart = ChartFactory.createPieChart("Quantité Des Produits Dans Le Stock", pieDataset, true, true, true);
        PiePlot p = (PiePlot) chart.getPlot();
        ChartFrame frame = new ChartFrame("Quantité Des Produits ", chart);
        frame.setVisible(true);
        frame.setSize(800, 600);

    }

    @FXML
    private void generation(MouseEvent event) throws BadElementException, IOException {

        /* Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Rapport.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(4);

            for (int i = 0; i < data.size(); i++) {
                table.addCell("Libelle:" + "  " + tablestock.getItems().get(i).getLibelle_produit());
                table.addCell("Categorie:" + "  " + tablestock.getItems().get(i).getCategorie());
                table.addCell("Quantité" + "  " + tablestock.getItems().get(i).getQuantite());
                table.addCell("Date_Expiration" + "  " + tablestock.getItems().get(i).getDate_expiration());

            }
            document.add(table);

            document.close();

            
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(e);
        }*/
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("RapportStockProduit.pdf"));
            document.open();

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("stock.jpg");
            image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 25.0f, Font.BOLD, BaseColor.BLACK);
            Chunk c = new Chunk("Rapport Stock", f);
            Paragraph p1 = new Paragraph(c);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setSpacingAfter(10f);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.now();
            Paragraph date = new Paragraph(dtf.format(localDate));
            date.setAlignment(Paragraph.ALIGN_RIGHT);

            document.add(date);
            document.add(image);
            document.add(p1);

            PdfPTable table = new PdfPTable(6);
            PdfPCell cell1 = new PdfPCell(new Phrase("Libelle"));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell2 = new PdfPCell(new Phrase("Categorie"));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell3 = new PdfPCell(new Phrase("Prix"));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell4 = new PdfPCell(new Phrase("Date_expiration"));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell5 = new PdfPCell(new Phrase("Quantité"));
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell6 = new PdfPCell(new Phrase("Description"));
            cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            for (int i = 0; i < data.size(); i++) {
                table.addCell(tablestock.getItems().get(i).getLibelle_produit());
                table.addCell(tablestock.getItems().get(i).getCategorie());
                table.addCell("" + tablestock.getItems().get(i).getPrix());
                table.addCell("" + tablestock.getItems().get(i).getDate_expiration());
                table.addCell("" + tablestock.getItems().get(i).getQuantite());
                table.addCell(tablestock.getItems().get(i).getDescription());
            }
            document.add(table);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succés");
            alert.setHeaderText(null);
            alert.setContentText("Votre Rapport a été génerer avec succés");
            alert.show();
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void search(MouseEvent event) {
        FilteredList<Produit> filteredData = new FilteredList<>(data, e -> true);
        recherchetext.setOnKeyReleased(e
                -> {
            recherchetext.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Produit>) new Predicate<Produit>() {
                    @Override
                    public boolean test(Produit Produit) {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (Produit.getLibelle_produit().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (Produit.getCategorie().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        return false;
                    }
                });

            });
            SortedList<Produit> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tablestock.comparatorProperty());
            tablestock.setItems(sortedData);
        });

    }
}
