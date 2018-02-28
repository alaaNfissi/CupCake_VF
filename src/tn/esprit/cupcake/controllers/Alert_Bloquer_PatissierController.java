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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DefaultStringConverter;
import tn.esprit.cupcake.APIs.Mail.Mail;
import tn.esprit.cupcake.entities.Commande;
import tn.esprit.cupcake.entities.Livraison;
import tn.esprit.cupcake.entities.Panier;
import tn.esprit.cupcake.entities.Patissier;
import tn.esprit.cupcake.entities.Utilisateur;
import tn.esprit.cupcake.services.LivraisonService;
import tn.esprit.cupcake.services.PanierService;
import tn.esprit.cupcake.services.PatissierService;
import tn.esprit.cupcake.services.UtilisateurService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class Alert_Bloquer_PatissierController implements Initializable {

	@FXML
	private TableView<Patissier> TableUtilisateurs;
	@FXML
	private TableColumn<Patissier, Long> id_utilisateurCol;
	@FXML
	private TableColumn<Patissier, String> emailCol;
	@FXML
	private TableColumn<Patissier, String> PseudoCol;
	@FXML
	private TableColumn<Patissier, String> etat_compteCol;
	@FXML
	private JFXButton confirmerPatissierBtn;
	ObservableList<Patissier> listePatissiers=FXCollections.observableArrayList();
	ObservableList<String> comboBoxValues =  FXCollections.observableArrayList(
        "Bloquer",
        "Désactivé"
    );

	/**
	 * Initializes the controller class.
	 */
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		afficherDetailsPatissiers();
	}
	
	public void afficherDetailsPatissiers()
	{
		PatissierService pats=new PatissierService();
		List<Patissier> lp=new ArrayList<>();
			try {
				 lp=pats.searchByEmailReclamation();
			} catch (SQLException ex) {
				Logger.getLogger(Alert_Bloquer_PatissierController.class.getName()).log(Level.SEVERE, null, ex);
			}
			listePatissiers.clear();
			listePatissiers.addAll(lp);
			id_utilisateurCol.setCellValueFactory(new PropertyValueFactory<Patissier,Long>("id_utilisateur"));
			emailCol.setCellValueFactory(new PropertyValueFactory<Patissier,String>("email"));
			PseudoCol.setCellValueFactory(new PropertyValueFactory<Patissier,String>("pseudo"));
			etat_compteCol.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), comboBoxValues));
			TableUtilisateurs.setItems(listePatissiers);
			TableUtilisateurs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			TableUtilisateurs.setEditable(true);
			etat_compteCol.setText("changer ici Etat Compte !!!");
			//etat_compteCol.setCellValueFactory(value);
			etat_compteCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Patissier, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Patissier, String> event) {
				try {
					Patissier selectedPatissier=null ;
					selectedPatissier=TableUtilisateurs.getSelectionModel().getSelectedItem();
					pats.changerEtatCompte(selectedPatissier, comboBoxValues.indexOf(event.getNewValue()));
					Task sendingMessage = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
					Patissier selectedPatissier=null ;
					selectedPatissier=TableUtilisateurs.getSelectionModel().getSelectedItem();
					
				
                    // ClientAbonne conduct;
                    //conduct=cas.findById(Table_demande.getSelectionModel().getSelectedItem().getId_ut());
                    //String destinataire = conduct.getEmail();
                    Mail mail = new Mail();
                    String submail = "Changement d'état du Compte";
                    String contentmail = "L'état de votre compte est:"+selectedPatissier.getEtat_compte()+2;
                    System.out.println("mail test");
					mail.sendMail("alaa.nfissi@gmail.com" , "Nfissi2014", selectedPatissier.getEmail(), submail, contentmail);
					System.out.println("mail sent");
                    return null;
                }
            };
            new Thread(sendingMessage).start();
				} catch (SQLException ex) {
					Logger.getLogger(Alert_Bloquer_PatissierController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
	}
	
	public void ActionConfirmerPatissier(ActionEvent event) throws IOException
	{
		HomeViewController.confirmerPatissier=1;
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
