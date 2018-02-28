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
import com.sun.rowset.internal.Row;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.management.Notification;
import org.apache.poi.hssf.usermodel.HSSFRow;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.services.ProduitService;
import tn.esprit.cupcake.utils.CupCakeDBConnection;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Row;
import org.controlsfx.control.Notifications;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class AffichageProduitController implements Initializable {
  private Produit selectedid;
    @FXML
    private TableView<Produit> tableProd;
    @FXML
    private TableColumn<Produit, Integer> txtId_produit;
    @FXML
    private TableColumn<Produit, String> txtLibelle_produit;
    @FXML
    private TableColumn<Produit, String> txtCategorie;
    @FXML
    private TableColumn<Produit, Float> txtPrix;
    @FXML
    private TableColumn<Produit, Date> txtDate_expiration;
    @FXML
    private TableColumn<Produit, Integer> txtQuantite;
    @FXML
    private TableColumn<Produit, String> txtDescription;
    @FXML
    private TableColumn<Produit, Integer> txtNote;
    @FXML
    private TableColumn<Produit, String> txtImage;
    @FXML
    private TableColumn<Produit, Integer> txtId_patisserie;
    @FXML
    private Button btnLoad;
	@FXML
	private JFXButton ajouterBtn;
    ObservableList<Produit> data;

    private Connection con;
    //  private Connection dc;
    @FXML
    private Button txtSupprimer;
    @FXML
    private Button btnSuivant;
    @FXML
    private TextField searchField;
    @FXML
    private Button btnExport;
    @FXML
    private ImageView imageViewProduit;
    @FXML
    private Button txtPdf;
	@FXML
	private JFXButton ajouterCatalogueBtn;
	@FXML
	private JFXButton gererStockbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId_patisserie.setVisible(false);
    }

    @FXML
    private void loadDataFromDatabase(ActionEvent event) {
        con = CupCakeDBConnection.getInstance().getConnection();
        System.out.println("test");

        try {

            // Connection co=dc.getInstance().getConnection();
            data = FXCollections.observableArrayList();
            System.out.println("test");
			PatisserieService ps=new PatisserieService();
			Patisserie p=ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
			String req="SELECT * FROM produit where id_patisserie = ?";
			PreparedStatement pre=con.prepareStatement(req);
			pre.setInt(1, p.getId_patisserie());
            ResultSet rs = pre.executeQuery();
            System.out.println("test2");
            //  String req = "SELECT * FROM produit";
            //  PreparedStatement pre = con.prepareStatement(req) ;
            // ResultSet rs = pre.executeQuery();
            while (rs.next()) {
               // data.add(new Produit(rs.getInt("id_produit")));
                data.add(new Produit(rs.getInt("id_produit"),
                        rs.getString("libelle_produit"),
                        rs.getString("categorie"),
                        rs.getFloat("prix"),
                        rs.getDate("date_expiration"),
                        rs.getInt("quantite"),
                        rs.getString("description"),
                        rs.getInt("note"),
                        rs.getString("image"),
                        rs.getInt("id_patisserie")
                ));
                System.out.println(rs.getInt("id_patisserie"));
                

            }

            txtId_produit.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
            txtLibelle_produit.setCellValueFactory(new PropertyValueFactory<>("libelle_produit"));
            txtCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

            txtPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));

            txtDate_expiration.setCellValueFactory(new PropertyValueFactory<>("date_expiration"));
            txtQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            txtDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            txtNote.setCellValueFactory(new PropertyValueFactory<>("note"));
            txtImage.setCellValueFactory(new PropertyValueFactory<>("image"));
            txtId_patisserie.setCellValueFactory(new PropertyValueFactory<>("id_patisserie"));
            //tablePat.setItems(null);
            tableProd.setItems(data);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    @FXML
    private void supprimerProduit(ActionEvent event) throws SQLException {
         ProduitService sv=new ProduitService();
         ObservableList<Produit> productSelected, allProducts;      
        allProducts = tableProd.getItems();
        productSelected = tableProd.getSelectionModel().getSelectedItems();

        int id_produit =productSelected.get(0).getId_produit();
        System.out.println(""+id_produit);
        sv.supprimerProduit(id_produit);
       
        productSelected.forEach(allProducts::remove);
                System.out.println("produit supprimé avec succès !! ");
      

    }

    @FXML
    private void recupererId(ActionEvent event) throws Exception {
           FXMLLoader loader = null;
               try {
                    loader = new FXMLLoader(getClass().getResource(Routes.MODIFIERPRODUITS));
                      Stage stage1 = (Stage)  btnSuivant.getScene().getWindow();
                     Parent root = loader.load();
                      ModifierProduitController mod = loader.getController();
                      mod.recuperer(tableProd.getSelectionModel().getSelectedItem());
                      System.out.println(tableProd.getSelectionModel().getSelectedItem());
                      System.out.println("here");
                 //     ModifierProduitController md=loader.getController();
                  //   md.recuperer(tableProd.getSelectionModel().getSelectedItem());
                    
            Stage stage = new Stage();
            
            Scene scene = new Scene(root);
             //scene.getStylesheets().add(getClass().getResource("/tn/esprit/cupcake/views/bootstrap3.css").toString());
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
               }catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void rechercher(ActionEvent event) {
        FilteredList<Produit> filteredData=new FilteredList<>(data,e->true);
        searchField.setOnKeyReleased(e->
        {
            searchField.textProperty().addListener((ObservableValue,oldValue,newValue)->{
                filteredData.setPredicate((Predicate<? super Produit>) new Predicate<Produit>() {
                    @Override
                    public boolean test(Produit Produit) {
                        if(newValue==null|| newValue.isEmpty()){
                            return true;
                        }
                        String lowerCaseFilter=newValue.toLowerCase();
                        if(Produit.getLibelle_produit().toLowerCase().contains(lowerCaseFilter)){
                            return true;
                        }
                        else if (Produit.getCategorie().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        return false;
                    }
                });
                       
            });
            SortedList<Produit>sortedData=new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableProd.comparatorProperty());
            tableProd.setItems(sortedData);
        });
        //if(Produit.getId_produit().contains(newValue)
    }
    private void saveXLSFile(File file)
    {
        try {
            FileOutputStream fileOut;
            fileOut=new FileOutputStream(file);
            HSSFWorkbook workbook=new HSSFWorkbook();
            HSSFSheet workSheet=workbook.createSheet("sheet 0");
            HSSFRow row1=workSheet.createRow((short)0);
            row1.createCell(0).setCellValue("id_produit");
            row1.createCell(1).setCellValue("libelle_produit");
            row1.createCell(2).setCellValue("categorie");
            row1.createCell(3).setCellValue("prix");
            row1.createCell(4).setCellValue("date_expiration");
            row1.createCell(5).setCellValue("quantite");
            row1.createCell(6).setCellValue("description");
            row1.createCell(7).setCellValue("note");
            row1.createCell(8).setCellValue("image");
            row1.createCell(9).setCellValue("id_patisserie");
            
             HSSFRow row2;
            /*con = CupCakeDBConnection.getInstance().getConnection();
           ResultSet rs = con.createStatement().executeQuery("SELECT * FROM produit ");*/
		   PatisserieService ps=new PatisserieService();
			Patisserie p=ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
			String req="SELECT * FROM produit where id_patisserie = ?";
			PreparedStatement pre=con.prepareStatement(req);
			pre.setInt(1, p.getId_patisserie());
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int a =rs.getRow();
                row2=workSheet.createRow((short) a);
                row2.createCell(0).setCellValue(rs.getInt(1));
                row2.createCell(1).setCellValue(rs.getString(2));
                row2.createCell(2).setCellValue(rs.getString(3));
                row2.createCell(3).setCellValue(rs.getFloat(4));
                row2.createCell(4).setCellValue(rs.getDate(5));
                row2.createCell(5).setCellValue(rs.getInt(6));
                row2.createCell(6).setCellValue(rs.getString(7));
                row2.createCell(7).setCellValue(rs.getInt(8));
                row2.createCell(8).setCellValue(rs.getString(9));
                row2.createCell(9).setCellValue(rs.getInt(10));
                
                
            }
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            rs.close();
            TrayNotification tn=new TrayNotification("NEW EXCEL FILE","specified successfully generated",NotificationType.SUCCESS);
            tn.showAndWait();
            
        } catch (SQLException|IOException e) {
            TrayNotification tn=new TrayNotification("NEW EXCEL FILE","specified successfully generated",NotificationType.ERROR);
            tn.showAndWait();
            System.err.println(e);
        }
    
    }

    @FXML
    private void exportAction(ActionEvent event) {
        FileChooser chooser=new FileChooser();
        //set extension filter
        FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("Excel Files(*.xls)","*.xls");
        chooser.getExtensionFilters().add(filter);
        //show save dialog
        File file=chooser.showSaveDialog(btnExport.getScene().getWindow());
        if(file!=null)
        {
            saveXLSFile(file);
        }
        
    }

    @FXML
    private void afficherImage(MouseEvent event) {
      
  
  selectedid=(Produit) tableProd.getSelectionModel().getSelectedItem();
  File file = new File(selectedid.getImage());
  
                try {
                    Image image = new Image(file.toURI().toURL().toExternalForm());
                    imageViewProduit.setImage(image);
                } catch (MalformedURLException ex) {
                    System.out.println(ex);
                }
    }

    /*@FXML
    private void generation(ActionEvent event) {
        
          Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Rapport.pdf"));
            document.open();

            for (int i = 0; i < data.size(); i++) {
                int text1 = tableProd.getItems().get(i).getId_produit();
                String text2 = tableProd.getItems().get(i).getLibelle_produit();
                String text3 = tableProd.getItems().get(i).getCategorie();
                 float text4 = tableProd.getItems().get(i).getPrix();
                 Date text5 = tableProd.getItems().get(i).getDate_expiration();
                    int text6 = tableProd.getItems().get(i).getQuantite();
                       String text7 = tableProd.getItems().get(i).getDescription();
                      int text8 = tableProd.getItems().get(i).getNote();  
                      int text9 = tableProd.getItems().get(i).getId_patisserie();
                       /*  File file = new File(selectedid.getImage());

                try {
                    Image image = new Image(file.toURI().toURL().toExternalForm());
                    imageViewProduit.setImage(image);
                } catch (MalformedURLException ex) {
                    System.out.println(ex);
                }
                String text10 = "Id_produit" + "  " + text1 + "  " + "Libelle_produit" + "  " + text2 + "  " + "Categorie" + " " + text3+ "  " + "Prix" +" "+text4+
                   "  " + "Date_expiration" + "  " + text5 +" "+"Quantite"+" "+text6+" "+"Description"+" "+text7+" "+"Note"+" "+text8+"Id_patisserie"+" "+text9      ;
                //Paragraph p1 = new Paragraph(text1);
                //Paragraph p2 = new Paragraph(text2);
                //Paragraph p3 = new Paragraph(text3);
                Paragraph p4 = new Paragraph(text10);
                //document.add(p1);
                //document.add(p2);
                //document.add(p3);
                document.add(p4);
                System.out.println("lllalalalaal");
            }
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(e);
        }
        document.close();
    }*/
	   @FXML
    private void generation(ActionEvent event)throws DocumentException, BadElementException, IOException  {
              
               Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("RapportProduit.pdf"));
            document.open();
            
            
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("CupCake.jpg");
            image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
            
            
            
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 25.0f, Font.BOLD, BaseColor.BLACK);
            Chunk c = new Chunk("Rapport Produit", f);
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
            
            PdfPTable table = new PdfPTable(7);
            PdfPCell cell1 = new PdfPCell(new Phrase("id_produit"));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell2 = new PdfPCell(new Phrase("libelle_produit"));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell3 = new PdfPCell(new Phrase("categorie"));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell cell4 = new PdfPCell(new Phrase("prix"));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
             PdfPCell cell5 = new PdfPCell(new Phrase("date_expiration"));
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
             PdfPCell cell6 = new PdfPCell(new Phrase("Quantite"));
            cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
               PdfPCell cell7 = new PdfPCell(new Phrase("description"));
            cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
        
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
        
            for (int i = 0; i < data.size(); i++) {
                table.addCell(""+tableProd.getItems().get(i).getId_produit());
                table.addCell("" + tableProd.getItems().get(i).getLibelle_produit());
                table.addCell("" + tableProd.getItems().get(i).getCategorie());
                table.addCell("" + tableProd.getItems().get(i).getPrix());
                table.addCell("" + tableProd.getItems().get(i).getDate_expiration());
                table.addCell("" + tableProd.getItems().get(i).getQuantite());
                  table.addCell("" + tableProd.getItems().get(i).getDescription());
                 
            }
            document.add(table);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(e);
        }
    }
	
	@FXML
	public void GoAjouterProduit(ActionEvent event) throws IOException
	{
		HomeViewController.listeCataloguebtn=0;
		HomeViewController.ajouterProduitbtn=1;
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
  public void GoListeCatalogues(ActionEvent event) throws IOException
  {
	  HomeViewController.ajouterProduitbtn=0;
	  HomeViewController.ajouterCataloguebtn=0;
	  HomeViewController.affecterProduitCatalogue=0;
	  HomeViewController.listeCataloguebtn=1;
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
  
  public void GoGererStock(ActionEvent event) throws IOException
  {
	  HomeViewController.ajouterProduitbtn=0;
	  HomeViewController.ajouterCataloguebtn=0;
	  HomeViewController.affecterProduitCatalogue=0;
	  HomeViewController.listeCataloguebtn=0;
	  HomeViewController.stockajoutPass=0;
	  HomeViewController.stockafficherBtn=1;
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
