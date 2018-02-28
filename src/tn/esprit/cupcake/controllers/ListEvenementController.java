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
import java.time.LocalDate;
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
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import tn.esprit.cupcake.entities.*;
import tn.esprit.cupcake.services.*;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author Basly
 */
public class ListEvenementController implements Initializable {

    public static ListEvenementController cont;
    @FXML
    private TableView<Evenement> tableEvent;
    @FXML
    private TableColumn<Evenement, String> libelle;
    @FXML
    private TableColumn<Evenement, String> adresse;
    @FXML
    private TableColumn<Evenement, Date> dateDebut;
    @FXML
    private TableColumn<Evenement, Date> dateFin;
    @FXML
    private TableColumn<Evenement, Button> action;
    ObservableList<Evenement> listViewEvenement;
    List<Evenement> listEvenement;
    @FXML
    private TextArea txtDescription;
    @FXML
    private ImageView imageEvent;
    @FXML
    private Button btnModif;
    @FXML
    private JFXTextField txtRecherche;
	@FXML
    private JFXButton btnGoToAjoutEvent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
		System.out.println("initialize evenement");
        tableEvent.setEditable(true);
        cont = this;
        AfficherData();
    }

    private void setCellValue() {
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle_evenement"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse_evenement"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        action.setCellValueFactory(new PropertyValueFactory<>("delete"));
        /********************************************************************************/
        libelle.setStyle( "-fx-alignment: CENTER;");
        adresse.setStyle( "-fx-alignment: CENTER;");
        dateDebut.setStyle( "-fx-alignment: CENTER;");
        dateFin.setStyle( "-fx-alignment: CENTER;");
        action.setStyle( "-fx-alignment: CENTER;");
        /********************************************************************************/
    }

    public void AfficherData() {
        //System.out.println("reloading data");
        try {
            EvenementService sr = new EvenementService();
            listEvenement = new ArrayList<>();
            listEvenement = sr.selectEvenement();
			System.out.println(listEvenement);
            listViewEvenement = FXCollections.observableArrayList(listEvenement);
            tableEvent.setItems(listViewEvenement);
            setCellValue();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void showImage(MouseEvent event) {
        txtDescription.setText(tableEvent.getSelectionModel().getSelectedItem().getDescription());
        File file = new File(tableEvent.getSelectionModel().getSelectedItem().getImage());
        try {
            Image image = new Image(file.toURI().toURL().toExternalForm());
            imageEvent.setImage(image);
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void GoModifierEvenement(ActionEvent event) throws IOException {
		
        //FXMLLoader loader = null;
		FXMLLoader loader2 = null;
		//Parent root=null;
        Evenement evenement = tableEvent.getSelectionModel().getSelectedItem();
        String id_evenement = Integer.toString(evenement.getId_evenement());
        String libelle_evenement = evenement.getLibelle_evenement();
        String adresse_evenement = evenement.getAdresse_evenement();
        Date dateDebut = evenement.getDate_debut();
        Date dateFin = evenement.getDate_fin();
        String description = evenement.getDescription();
        String image = evenement.getImage();
        Double longitude = evenement.getLongitude();
        Double latitude = evenement.getLatitude();
		
        loader2 = new FXMLLoader(getClass().getResource(Routes.MODIFIEREVENEMENT));
        //Parent root = loader.load();
		Parent root2=loader2.load();
        ModifierEvenementController modifEventController = loader2.getController();
        System.out.println(id_evenement);
        modifEventController.getInfoEvenement(id_evenement,libelle_evenement,adresse_evenement,dateDebut,dateFin,description,image,longitude,latitude);
		Stage stage = new Stage();
        /*Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
         ((Node) (event.getSource())).getScene().getWindow().hide();*/
		 
		 //root = FXMLLoader.load(getClass().getResource(Routes.LOGINVIEW));
		 //root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
		 
				 JFXDecorator decorator = new JFXDecorator(stage, root2, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);
						
						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						((Node) (event.getSource())).getScene().getWindow().hide();
    }
        /*********************************************************************************************/
    @FXML
    private void RechercheDynamique(KeyEvent event) {
        
              FilteredList<Evenement> filteredData = new FilteredList<>(listViewEvenement, e -> true);
        txtRecherche.setOnKeyReleased(e
                -> {
            txtRecherche.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Evenement>) new Predicate<Evenement>() {
                    @Override
                    public boolean test(Evenement evenement) {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (evenement.getLibelle_evenement().toLowerCase().contains(lowerCaseFilter)) {
                            return true;}
                        else if (evenement.getAdresse_evenement().toLowerCase().contains(lowerCaseFilter)){
                            return true ; 
                        }
                        
                        return false;
                    }
                });

            });
            SortedList<Evenement> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableEvent.comparatorProperty());
            tableEvent.setItems(sortedData);
        });
    }
            /*********************************************************************************************/
        /**
     * ******************************************************************************************
     */
    @FXML
    private void importPDF(ActionEvent event) throws DocumentException, BadElementException, IOException {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("RapportEvenement.pdf"));
            document.open();
            
            
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("CupCake.png");
            image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
            
            
            
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 25.0f, Font.BOLD, BaseColor.BLACK);
            Chunk c = new Chunk("Rapport Evenement", f);
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
            PdfPCell cell2 = new PdfPCell(new Phrase("Adresse"));
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
            for (int i = 0; i < listViewEvenement.size(); i++) {
                table.addCell(tableEvent.getItems().get(i).getLibelle_evenement());
                table.addCell("" + tableEvent.getItems().get(i).getAdresse_evenement());
                table.addCell("" + tableEvent.getItems().get(i).getDate_debut());
                table.addCell("" + tableEvent.getItems().get(i).getDate_fin());
            }
            document.add(table);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(e);
        }
    }
    /**
     * ******************************************************************************************
     */
	@FXML
    private void GoToAjoutEvent(ActionEvent event) {
        FXMLLoader loader = null;
        try {
                loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/AjouterEvenementFXML.fxml"));
            Stage stage1 = (Stage) btnGoToAjoutEvent.getScene().getWindow();
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
