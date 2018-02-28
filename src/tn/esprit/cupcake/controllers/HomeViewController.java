/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import tn.esprit.cupcake.utils.Routes;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import tn.esprit.cupcake.APIs.Statistique.BarChart3DFXDemo2;
import static tn.esprit.cupcake.APIs.Statistique.BarChart3DFXDemo2.createDemoNodePatissier;
import static tn.esprit.cupcake.APIs.Statistique.PieChart3DFXDemo2.createDemoNodeClient;
import tn.esprit.cupcake.entities.TypeRole;
import tn.esprit.cupcake.entities.Utilisateur;
import tn.esprit.cupcake.test.CupCakeFX;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class HomeViewController implements Initializable {

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private JFXDrawer drawer;
	
	@FXML 
	private StackPane holderStackPane;
    @FXML
    private Label txtCurrentWindow;
	public static int indexTypeCompte=0;
	public static int listeCataloguebtn=0;
	public static int ajouterProduitbtn=0;
	public static int ajouterCataloguebtn=0;
	public static int affecterProduitCatalogue=0;
	public static int stockajoutPass=0;
	public static int stockafficherBtn=0;
	public static int confirmerPatissier=0;
	public static int selectedPatisserie=0;
	public static int versPanier=0;
	
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		if(CupCakeFX.user.getRole()==TypeRole.Administrateur.ordinal())
		{
			if(confirmerPatissier!=0)
			{
				try {
				AnchorPane confirmerP = FXMLLoader.load(getClass().getResource(Routes.CONFIRMERPATISSIER));
				setNode(confirmerP);
				confirmerPatissier=0;
			} catch (IOException ex) {
				Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
			}
			}
			else
			{
				try {
				AnchorPane listePatissiers = FXMLLoader.load(getClass().getResource(Routes.ADMINISTRATION));
				setNode(listePatissiers);
			} catch (IOException ex) {
				Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
			}
			}
			
		}
		else if(CupCakeFX.user!=null)
	   {
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
				if (drawer.isShown()) {
					drawer.close();
				} else {
					drawer.open();
				}
        });
        try {
            VBox sidePane = FXMLLoader.load(getClass().getResource("/tn/esprit/cupcake/views/Drawer.fxml"));
            //AnchorPane doctorsPane = FXMLLoader.load(getClass().getResource(Routes.DOCTORSVIEW));
            //AnchorPane login = FXMLLoader.load(getClass().getResource(Routes.LOGINVIEW));
            AnchorPane listeCommandes = FXMLLoader.load(getClass().getResource(Routes.LISTECOMMANDES));
			AnchorPane choisirTypeCompte =FXMLLoader.load(getClass().getResource(Routes.CHOISIRTYPECOMPTE));
			AnchorPane modifierCompteClient=FXMLLoader.load(getClass().getResource(Routes.MODIFIERCOMPTECLIENT));
			AnchorPane modifierComptePatissier =FXMLLoader.load(getClass().getResource(Routes.MODIFIERCOMPTEPATISSIER));
			AnchorPane listeCatalogues =FXMLLoader.load(getClass().getResource(Routes.AFFICHERCATALOGUE));
			/*if (CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal()) {
				AnchorPane ajouterPromotion =FXMLLoader.load(getClass().getResource(Routes.AJOUTERPROMOTION));
			    AnchorPane ajouterEvenement =FXMLLoader.load(getClass().getResource(Routes.AJOUTEREVENEMENT));
				}*/
			
			
			
			
			/*StackPane sp = new StackPane();
			sp.getChildren().add(createDemoNode());
			
			StackPane StatistiquePatissier = sp;*/
            //AnchorPane appointment = FXMLLoader.load(getClass().getResource(Routes.APPOINTMENTSVIEW));
            //AnchorPane welcome = FXMLLoader.load(getClass().getResource(Routes.WELCOMEVIEW));
            //setNode(welcome);
            drawer.setSidePane(sidePane);
			//PreInscriptionController prc = new PreInscriptionController();
			
            for (Node node : sidePane.getChildren()) {
                if (node.getAccessibleText() != null) {
					if(!compteComplet()){
							System.out.println("compte incomplet !!!");
							setNode(choisirTypeCompte);
						}
					/*if(CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal())
						{
							setNode(listeProduits);
						}*/
					if(CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal())
					{
					if(listeCataloguebtn!=0)
					{
						
						setNode(listeCatalogues);
						listeCataloguebtn=0;
					}
					else if(affecterProduitCatalogue!=0)
					{
						AnchorPane affecterProduitCatalogueA=FXMLLoader.load(getClass().getResource(Routes.CATALOGUEPRODUIT));
						setNode(affecterProduitCatalogueA);
						affecterProduitCatalogue=0;
					}
					else if(ajouterProduitbtn!=0)
					{
						AnchorPane ajouterProduit = FXMLLoader.load(getClass().getResource(Routes.AJOUTERPRODUITS));
						setNode(ajouterProduit);
						ajouterProduitbtn=0;
					}
					else if(ajouterCataloguebtn!=0)
					{
						AnchorPane ajouterCatalogue=FXMLLoader.load(getClass().getResource(Routes.AJOUTCATALOGUE));
						setNode(ajouterCatalogue);
						ajouterCataloguebtn=0;
					}
					else if(stockafficherBtn!=0)
					{
						AnchorPane stockAffichage = FXMLLoader.load(getClass().getResource(Routes.STOCKAFFICHAGE));
						setNode(stockAffichage);
						stockafficherBtn=0;
					}
					else if(stockajoutPass!=0)
					{
						AnchorPane stockAjout=FXMLLoader.load(getClass().getResource(Routes.STOCKAJOUT));
						setNode(stockAjout);
						stockajoutPass=0;
					}
					}
					if(CupCakeFX.user.getRole()==TypeRole.Client.ordinal())
					{
						if(selectedPatisserie!=0)
						{
							AnchorPane panierAjouter=FXMLLoader.load(getClass().getResource(Routes.PANIERAJOUTER));
						setNode(panierAjouter);
						selectedPatisserie=0;
						}
						/*else if(versPanier!=0)
						{
							AnchorPane panierModifier=FXMLLoader.load(getClass().getResource(Routes.PANIERMODIFIER));
						setNode(panierModifier);
						versPanier=0;
						}*/
					}
					System.out.println(listeCatalogues);
					/********************************************/
						System.out.println(indexTypeCompte);
					if(CupCakeFX.user.getRole()==0){
						if(indexTypeCompte==1111)
						{
							setNode(modifierCompteClient);
						}
						else if(indexTypeCompte == 111)
						{
							setNode(modifierComptePatissier);
						}
					
					}
					/*if(CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal())
								{
									setNode(listeProduits);
								}*/
					
                    node.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ev) -> {
                        switch (node.getAccessibleText()) {
                            case "homeMenu":
                                drawer.close();
								if(CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal())
								{
									AnchorPane listeProduits;
							try {
								listeProduits = FXMLLoader.load(getClass().getResource(Routes.AFFICHERPRODUITS));
								setNode(listeProduits);
							} catch (IOException ex) {
								Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
							}
									
								}
								else if(CupCakeFX.user.getRole()==TypeRole.Client.ordinal())
								{
									AnchorPane listePatisseries;
									try {
										 listePatisseries=(AnchorPane)FXMLLoader.load(getClass().getResource(Routes.LISTETTPATISSERIES));
										setNode(listePatisseries);
									} catch (IOException ex) {
										Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
									}
								}
                                break;
                            case "listecommande":
                                drawer.close();                               
                                setNode(listeCommandes);
                                break;
							case "mesPromotions":
								if (CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal()) {
									AnchorPane listePromotions;
							try {
								listePromotions = FXMLLoader.load(getClass().getResource(Routes.LISTPROMOTION));
								setNode(listePromotions);
							} catch (IOException ex) {
								Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
							}
								}
								else
								{
									try {
										AnchorPane notfound = FXMLLoader.load(getClass().getResource(Routes.NOTFOUND));
										setNode(notfound);
									} catch (IOException ex) {
										Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
									}
								}
								
								drawer.close();  
								break;
							case "mesEvenements":
								if (CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal()) {
									AnchorPane listeEvenements;
							try {
								listeEvenements = FXMLLoader.load(getClass().getResource(Routes.LISTEVENEMENT));
								setNode(listeEvenements);
							} catch (IOException ex) {
								Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
							}
								}
								else
								{
									try {
										AnchorPane notfound = FXMLLoader.load(getClass().getResource(Routes.NOTFOUND));
										setNode(notfound);
									} catch (IOException ex) {
										Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
									}
								}
								//setNode(listeEvenements);
								drawer.close();  
								break;
							/*case "monStock":
								if (CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal()) {
									AnchorPane stockAffichage;
							try {
								stockAffichage = FXMLLoader.load(getClass().getResource(Routes.STOCKAFFICHAGE));
								setNode(stockAffichage);
							} catch (IOException ex) {
								Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
							}
								}
								else
								{
									try {
										AnchorPane notfound = FXMLLoader.load(getClass().getResource(Routes.NOTFOUND));
										setNode(notfound);
									} catch (IOException ex) {
										Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
									}
								}
								//setNode(listeEvenements);
								drawer.close();  
								break;*/
                            case "statistique":
                                drawer.close();
								System.out.println("test1");
							try {
								if(CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal()){
								setNodeStack(createDemoNodePatissier());	
								}
								else if(CupCakeFX.user.getRole()==TypeRole.Client.ordinal())
								{
									setNodeStack(createDemoNodeClient());
								}
								System.out.println("test2");
							} catch (SQLException ex) {
								Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
							}
                                break;
                            case "modifierCompteClient":
                                drawer.close();    
								
								if(compteComplet() && CupCakeFX.user.getRole()==TypeRole.Client.ordinal())
								{
									System.out.println("modifier compte client");
									setNode(modifierCompteClient);
								}
								else if(compteComplet() && CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal())
								{
									System.out.println("modifier compte patissier");
									setNode(modifierComptePatissier);
								}
                                
                                break;                                
                        }
                    });
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
	   }
	   else
	   {
		   hamburger.setVisible(false);
		   try {
			   AnchorPane choisirTypeCompte =FXMLLoader.load(getClass().getResource(Routes.CHOISIRTYPECOMPTE));
			   AnchorPane inscriptionClient =FXMLLoader.load(getClass().getResource(Routes.INSCRIPTIONCLIENT));
			   AnchorPane inscriptionPatissier =FXMLLoader.load(getClass().getResource(Routes.INSCRIPTIONPATISSIER));
			   setNode(choisirTypeCompte);
			   if(indexTypeCompte == 1111)
			   {
				   setNode(inscriptionClient);
			   }
			   else if(indexTypeCompte == 111)
			   {
				   setNode(inscriptionPatissier);
			   }
		   } catch (IOException ex) {
			   Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
		   }
	   }
    }

    private void setNode(Node node) {
        holderPane.getChildren().clear();
		holderStackPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
    }
	private void setNodeStack(Node node)
	{
		holderPane.getChildren().clear();
		holderPane.getChildren().add(holderStackPane);
		holderStackPane.getChildren().clear();
		holderStackPane.getChildren().add((Node) node);
		System.out.println("node set");
	}
	
	public boolean compteComplet()
	{
		Utilisateur u = CupCakeFX.user;
		if(u.getEmail().isEmpty() || u.getPseudo().isEmpty() || u.getNum_tel() == 0 || u.getNom().isEmpty() || u.getPrenom().isEmpty() || u.getDate_naissance().toString().isEmpty() || u.getPassword().isEmpty())
		{
			return false;
		}
		return true;
	}

}
