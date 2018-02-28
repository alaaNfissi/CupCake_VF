/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.cupcake.entities.Catalogue;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.services.CatalogueService;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.test.CupCakeFX;

import tn.esprit.cupcake.utils.CupCakeDBConnection;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class AffichageCatalogueController implements Initializable {

    @FXML
    private TableColumn<Catalogue, Integer> txtId_catalogue;
    @FXML
    private TableColumn<Catalogue, String> txtLibelle_catalogue;
    @FXML
    private TableColumn<Catalogue, Date> txtDate_debut;
    @FXML
    private TableColumn<Catalogue, Date> txtDate_fin;
    @FXML
    private TableColumn<Catalogue, String> txtDescription;
    @FXML
    private TableColumn<Catalogue, Integer> txtId_patisserie;
    @FXML
    private Button btnSuivant;
    @FXML
    private Button txtSupprimer;
    @FXML
    private Button btnLoad;
	@FXML
	private JFXButton ajouterCatalogueBtn;
    @FXML
    private TableView<Catalogue> tableCat;
      ObservableList<Catalogue> data;

    private Connection con;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void recupererId(ActionEvent event) {
           FXMLLoader loader = null;
               try {
                    loader = new FXMLLoader(getClass().getResource("/tn/esprit/cupcake/views/ModifierCatalogue.fxml"));
                      Stage stage1 = (Stage)  btnSuivant.getScene().getWindow();
                     Parent root = loader.load();
                      ModifierCatalogueController mod = loader.getController();
                      mod.recuperer(tableCat.getSelectionModel().getSelectedItem());
                      System.out.println(tableCat.getSelectionModel().getSelectedItem());
                      System.out.println("here");
                 //     ModifierProduitController md=loader.getController();
                  //   md.recuperer(tableProd.getSelectionModel().getSelectedItem());
                    
            Stage stage = new Stage();
            
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("/tn/esprit/cupcake/views/bootstrap3.css").toString());
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
               }catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void supprimerProduit(ActionEvent event) throws SQLException {
          CatalogueService sv=new CatalogueService();
         ObservableList<Catalogue> productSelected, allProducts;      
        allProducts = tableCat.getItems();
        productSelected = tableCat.getSelectionModel().getSelectedItems();

        int id_catalogue =productSelected.get(0).getId_catalogue();
        System.out.println(""+id_catalogue);
        sv.supprimerCatalogue(id_catalogue);
       
        productSelected.forEach(allProducts::remove);
                System.out.println("catalogue supprimé avec succès !! ");
    }

    @FXML
    private void loadDataFromDatabase(ActionEvent event) {
        
         con = CupCakeDBConnection.getInstance().getConnection();
        System.out.println("test");

        try {

            // Connection co=dc.getInstance().getConnection();
            data = FXCollections.observableArrayList();
            System.out.println("test");
			PatisserieService ps = new PatisserieService();
			Patisserie pt=ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
			String req="SELECT * FROM catalogue where id_patisserie=?";
			PreparedStatement pre=con.prepareStatement(req);
			pre.setInt(1, pt.getId_patisserie());
            ResultSet rs = pre.executeQuery();
			
            System.out.println("test2");
         
            while (rs.next()) {
          
                data.add(new Catalogue(rs.getInt("id_catalogue"),
                        rs.getString("libelle_catalogue"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),                    
                        rs.getString("description"),
                        rs.getInt("id_patisserie")
                ));
                System.out.println(rs.getInt("id_patisserie"));
                

            }

            txtId_catalogue.setCellValueFactory(new PropertyValueFactory<>("id_catalogue"));
            txtLibelle_catalogue.setCellValueFactory(new PropertyValueFactory<>("libelle_catalogue"));
            txtDate_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));

            txtDate_fin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
         
            txtDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    
            txtId_patisserie.setCellValueFactory(new PropertyValueFactory<>("id_patisserie"));
            //tablePat.setItems(null);
            tableCat.setItems(data);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
	public void GoAjouterCatalogue(ActionEvent event) throws IOException
	{
		HomeViewController.ajouterCataloguebtn=1;
		HomeViewController.ajouterProduitbtn=0;
		HomeViewController.listeCataloguebtn=0;
		HomeViewController.affecterProduitCatalogue=0;
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
