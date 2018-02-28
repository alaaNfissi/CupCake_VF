/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.views;

import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.cupcake.controllers.HomeViewController;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class ListePatisseriesController implements Initializable {

	@FXML
	private TableView<Patisserie> tablePatisseries;
	@FXML
	private TableColumn<Patisserie, String> libelleCol;
	@FXML
	private TableColumn<Patisserie, String> adresseCol;
	@FXML
	private TableColumn<Patisserie, String> specialiteCol;
	@FXML
	private TableColumn<Patisserie, String> descriptionCol;
	/*@FXML
	private TableColumn<Patisserie, ImageView> imageCol;*/
	private ObservableList<Patisserie> listePatisseries = FXCollections.observableArrayList();
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			afficherDetailsPatisseries();
		} catch (SQLException | MalformedURLException ex) {
			Logger.getLogger(ListePatisseriesController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void afficherDetailsPatisseries() throws SQLException, MalformedURLException
	{
		//List<Patisserie> listePatisseries= new ArrayList<>();
		PatisserieService pts= new PatisserieService();
		listePatisseries.clear();
		listePatisseries.addAll(pts.retourneTTPatisseries());
		libelleCol.setCellValueFactory(new PropertyValueFactory<Patisserie,String>("libelle_patisserie"));
		adresseCol.setCellValueFactory(new PropertyValueFactory<Patisserie,String>("adresse_patisserie"));
		specialiteCol.setCellValueFactory(new PropertyValueFactory<Patisserie,String>("specialite"));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<Patisserie,String>("description"));
		//imageCol.setCellValueFactory(new PropertyValueFactory<Patisserie,ImageView>("imageV"));
		tablePatisseries.setItems(listePatisseries);
		tablePatisseries.getSelectionModel().selectedItemProperty().addListener((ObservableList,oldSelection,newSelection)->{
				System.out.println(newSelection);
				MouseEvent event = null;
				if(newSelection!=null)
				{
					PatisserieService pts1=new PatisserieService();
					Patisserie pt;
					try {
						pt = pts1.searchById(tablePatisseries.getSelectionModel().getSelectedItem().getId_patisserie());
						CupCakeFX.patisserie=pt;
					} catch (SQLException ex) {
						Logger.getLogger(ListePatisseriesController.class.getName()).log(Level.SEVERE, null, ex);
					}
						HomeViewController.selectedPatisserie=1;
						Parent root=null;
						Stage stage = new Stage();
					try {
						root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
					} catch (IOException ex) {
						Logger.getLogger(ListePatisseriesController.class.getName()).log(Level.SEVERE, null, ex);
					}
						JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);
						
						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						//((Node) (event.getSource())).getScene().getWindow().hide();
				}
			}
			);
	}
	
	/*public void GoPatisserie(ActionEvent event) throws IOException
	{
		tablePatisseries.addEventHandler(EventType.ROOT, eventHandler);
		CupCakeFX.patisserie.setId_patisserie(tablePatisseries.getSelectionModel().getSelectedItem().getId_patisserie());
		HomeViewController.selectedPatisserie=1;
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
