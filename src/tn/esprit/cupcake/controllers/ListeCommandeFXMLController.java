/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.util.converter.DefaultStringConverter;
import jdk.nashorn.internal.objects.NativeArray;
import sun.plugin.com.Dispatch;
import tn.esprit.cupcake.APIs.Mail.Mail;
import tn.esprit.cupcake.entities.Client;
import tn.esprit.cupcake.entities.Commande;
import tn.esprit.cupcake.entities.Livraison;
import tn.esprit.cupcake.entities.Panier;
import tn.esprit.cupcake.entities.Patissier;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.entities.TypeRole;
import tn.esprit.cupcake.entities.Utilisateur;
import tn.esprit.cupcake.services.CommandeService;
import tn.esprit.cupcake.services.LivraisonService;
import tn.esprit.cupcake.services.PanierService;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.services.UtilisateurService;
import tn.esprit.cupcake.test.CupCakeFX;

/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class ListeCommandeFXMLController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	private Produit selectedid;
	@FXML
	private TableView<Commande> tableCommandes;
	@FXML
	private TableColumn<Commande,Integer> numCommande;
	@FXML
	private TableColumn<Commande,Date>dateCommande;
	@FXML
	private TableColumn<Commande,String> libellePatisserie;
	@FXML
	private TableColumn<Commande,Float> prixTotale;
	@FXML
	private TableView<Produit> tableProduits;
	@FXML
	private TableColumn<Produit,String> LibelleProduit;
	@FXML
	private TableColumn<Produit,String> categorieProduit ;
	@FXML
	private TableColumn<Produit,Float> prixProduit ;
	@FXML
	private TableColumn<Produit,Date> dateExpiration;
	@FXML
	private TableColumn<Produit,String>description ;
	@FXML
	private TableColumn<Produit,Integer>note ;
	@FXML 
	private TableColumn<Produit,String>lienImageProduit;
	@FXML
	private ImageView imageProduit;
	@FXML
	private TableView<Livraison> tableLivraison;
	@FXML 
	private TableColumn<Livraison,String> etatLivraison;
	@FXML
	private TableColumn<Livraison,Date> dateLivraison;
	@FXML
	private TableColumn<Livraison,Float> prixLivraison;
	@FXML
	private Button refreshbtn;
	/*private TableColumn<Livraison,ComboBox<String>> comboBoxColumn;
	@FXML*/
	ObservableList<String> comboBoxValues =  FXCollections.observableArrayList(
        "La commande est en cours du traitement",
        "La commande est en route",
        "La Commande est livrée"
    );
	//private ComboBox<String> etatLivraisoncomboBoxTable = new ComboBox<String>();*/
	private ObservableList<Commande> listeCommandes = FXCollections.observableArrayList();
	private ObservableList<Livraison> listeLivraisons =FXCollections.observableArrayList();
	private ObservableList<Produit> listeProduits = FXCollections.observableArrayList();
	
	private Button btnButton;
	//private TranslateTransition translateImage = new TranslateTransition(Duration.millis(1000));
	//listeCommandes.add
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	afficherDetailsCommande();
			
	}
	
	public void afficherDetailsCommande()
	{
			UtilisateurService us = new UtilisateurService();
			PatisserieService ps = new PatisserieService();
			CommandeService cs = new CommandeService();
			Utilisateur u = new Utilisateur();
			List<Commande> liste_Commandes = new ArrayList<>();
			try {
				//u = us.searchUserById(12);
				u=CupCakeFX.user;
				if(u.getRole() == TypeRole.Client.ordinal()){
				liste_Commandes = cs.listeCommandeClient(u);	
				}
				else if(u.getRole() == TypeRole.Patisssier.ordinal())
				{
					System.out.println(ps.searchByIdPatissier(u.getId_utilisateur()));
					liste_Commandes = cs.listeCommandePatisserie(ps.searchByIdPatissier(u.getId_utilisateur()));
				}

			} catch (SQLException ex) {
				Logger.getLogger(ListeCommandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
			}
			listeCommandes.clear();
			listeCommandes.addAll(liste_Commandes);
			numCommande.setCellValueFactory(new PropertyValueFactory<Commande,Integer>("num_commande"));
			dateCommande.setCellValueFactory(new PropertyValueFactory<Commande,Date>("date_commande"));
			//PatisserieService ps = new PatisserieService();
			//PanierService pns=new PanierService();
			libellePatisserie.setCellValueFactory(new PropertyValueFactory<Commande,String>("libelle_patisserie"));
			//System.out.println(libellePatisserie.);
			prixTotale.setCellValueFactory(new PropertyValueFactory<Commande,Float>("prix_totale"));
			tableCommandes.setItems(listeCommandes);
			tableCommandes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
			Commande selectedCommande =null;
			selectedCommande = tableCommandes.getSelectionModel().getSelectedItem();
			System.out.println(selectedCommande);
			tableCommandes.getSelectionModel().selectedItemProperty().addListener((ObservableList,oldSelection,newSelection)->{
				System.out.println(newSelection);
				if(newSelection!=null)
				{
					try {
						imageProduit.setImage(null);
						//translateImage.setFromX(0);
						afficherDetailsPanier(newSelection.getId_panier());
						afficherDetailsLivraison(newSelection);
					} catch (SQLException ex) {
						Logger.getLogger(ListeCommandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
			);
			tableProduits.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			tableProduits.getSelectionModel().selectedItemProperty().addListener((ObservableList,oldSelection,newSelection)->{
				System.out.println(newSelection);
				MouseEvent event = null;
				if(newSelection!=null)
				{
					afficherImage(event);
				}
			}
			);
			
	}
	
	public void afficherDetailsLivraison(Commande selectedCommande) throws SQLException
	{
		System.out.println("dans afficherDetailsLivraison");
		Livraison l = null;
		LivraisonService ls = new LivraisonService();
		try {
			l=ls.searchByCommandeId(selectedCommande.getId_commande());
		} catch (SQLException ex) {
			Logger.getLogger(ListeCommandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
		}
		listeLivraisons.clear();
		listeLivraisons.add(l);
		etatLivraison.setCellValueFactory(new PropertyValueFactory<Livraison,String>("etatLivraison"));
		dateLivraison.setCellValueFactory(new PropertyValueFactory<Livraison,Date>("date_livraison"));
		prixLivraison.setCellValueFactory(new PropertyValueFactory<Livraison,Float>("prix_livraison"));
		etatLivraison.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), comboBoxValues));
		//dateLivraison.setCellFactory(DatePickerCell );
		etatLivraison.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Livraison, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Livraison, String> event) {
				try {
					Livraison l1 =null;
					
					
					LivraisonService ls1= new LivraisonService();
					l1=ls1.searchByCommandeId(selectedCommande.getId_commande());
					
					ls1.changerEtatLivraison(l1, comboBoxValues.indexOf(event.getNewValue()));
					//System.out.println(etatLivraison.getCellObservableValue(l1));
					//System.out.println(etatLivraison.getCellData(l1));
					//System.out.println(etatLivraison.getCellD);
					Task sendingMessage = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
					Livraison l2=new Livraison();
					Panier p=new Panier();
					LivraisonService ls2= new LivraisonService();
					l2=ls2.searchByCommandeId(selectedCommande.getId_commande());
                    UtilisateurService us=new UtilisateurService();
					PanierService ps= new PanierService();
					p=ps.searchById(selectedCommande.getId_panier());
                    // ClientAbonne conduct;
                    //conduct=cas.findById(Table_demande.getSelectionModel().getSelectedItem().getId_ut());
                    //String destinataire = conduct.getEmail();
                    Mail mail = new Mail();
                    String submail = "Changement d'état de la Livraison";
                    String contentmail = "L'état de votre livraison est:"+l2.getEtatLivraison();
                    System.out.println("mail test");
					mail.sendMail("alaa.nfissi@gmail.com" , "Nfissi2014", us.searchUserById(p.getId_utilisateur()).getEmail(), submail, contentmail);
					System.out.println("mail sent");
                    return null;
                }
            };
            new Thread(sendingMessage).start();
					System.out.println(event.getNewValue());
					//tableLivraison.refresh();
					//comboBoxValues.indexOf(etatLivraison.getCellData(l.getEtat_livraison()));
					/*String etatS =l.getEtatLivraison();
					int etat=comboBoxValues.indexOf(etatLivraison.getCellData();
					ls.changerEtatLivraison(l,etat);*/
					//ls.changerEtatLivraison(l, comboBoxValues.indexOf(etatLivraison.getText()));
					
					System.out.println("Etat livraison changé ");
				} catch (SQLException ex) {
					Logger.getLogger(ListeCommandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		if(CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal())
		{
			tableLivraison.setEditable(true);
		}
			
		else
		{
			tableLivraison.setEditable(false);
		}
		
		
		//comboBoxColumn.setCellValueFactory(new PropertyValueFactory<Livraison,ComboBox<String>>("etatLivraisonComboBox"));
		
		tableLivraison.setItems(listeLivraisons);
		
	}
	
	public void afficherDetailsPanier(int id_panier) throws SQLException
	{
		Panier panier=null;
		PanierService pns = new PanierService();
		panier=pns.searchById(id_panier);
		System.out.println(panier);
		listeProduits.clear();
		listeProduits.addAll(pns.listeProduitsPanier(panier));
		LibelleProduit.setCellValueFactory(new PropertyValueFactory<Produit,String>("libelle_produit"));
		categorieProduit.setCellValueFactory(new PropertyValueFactory<Produit,String>("categorie"));
		prixProduit.setCellValueFactory(new PropertyValueFactory<Produit,Float>("prix"));
		dateExpiration.setCellValueFactory(new PropertyValueFactory<Produit,Date>("date_expiration"));
		description.setCellValueFactory(new PropertyValueFactory<Produit,String>("description"));
		note.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("note"));
		lienImageProduit.setCellValueFactory(new PropertyValueFactory<Produit,String>("image"));
		tableProduits.setItems(listeProduits);
	}

	@FXML
	public void afficherImage(MouseEvent event) {
		System.out.println("imaaaaaaaaaaaaage");
		 selectedid=(Produit) tableProduits.getSelectionModel().getSelectedItem();
         File file = new File(selectedid.getImage());
  
                try {
                    Image image = new Image(file.toURI().toURL().toExternalForm());
                    imageProduit.setImage(image);
                } catch (MalformedURLException ex) {
                    System.out.println(ex);
                }
	}
	
	public void rafraichirTableCommandes(ActionEvent event)
	{
		afficherDetailsCommande();
		tableCommandes.refresh();
		System.out.println("refresh");
	}
}
