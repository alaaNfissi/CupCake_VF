/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.sun.java.swing.plaf.windows.resources.windows;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.TokenType;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
import tn.esprit.cupcake.entities.Client;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.TypeRole;
import tn.esprit.cupcake.entities.Utilisateur;
import tn.esprit.cupcake.services.ClientService;
import tn.esprit.cupcake.services.PatisserieService;
import tn.esprit.cupcake.test.CupCakeFX;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class DrawerController implements Initializable {

    @FXML
    private JFXButton homeBtn;
	@FXML
	private JFXButton mesCommandesbtn;
    @FXML
    private JFXButton mesPromotionsbtn;
	@FXML
    private JFXButton mesEvenementsbtn;
    @FXML
    private JFXButton statistiquebtn;
    @FXML
    private JFXButton logoutBtn;
    @FXML
    private JFXButton exitBtn;
	@FXML
	private JFXButton monComptebtn;
	@FXML
	private ImageView avatarImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		
		if (CupCakeFX.user.getRole()==TypeRole.Client.ordinal()) {
			/*mesEvenementsbtn.setVisible(false);
			mesPromotionsbtn.setVisible(false);*/
			ClientService cs = new ClientService();
			try {
				Client client = cs.findById(CupCakeFX.user.getId_utilisateur());
				File file = new File(client.getImage());
				Image image = new Image(file.toURI().toURL().toExternalForm());
				 System.out.println("client");
                 avatarImg.setImage(image);
				 System.out.println("avatar client");
			} catch (SQLException | MalformedURLException ex) {
				Logger.getLogger(DrawerController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else if(CupCakeFX.user.getRole()==TypeRole.Patisssier.ordinal())
		{
			PatisserieService ps =new PatisserieService();
			try {
				Patisserie p = ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
				File file = new File(p.getImage());
				Image image = new Image(file.toURI().toURL().toExternalForm());
				avatarImg.setImage(image);
			} catch (SQLException | MalformedURLException ex) {
				Logger.getLogger(DrawerController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else{
			File file = new File("C:\\Users\\Alaa\\Documents\\NetBeansProjects\\CupCake_1.0\\src\\tn\\esprit\\cupcake\\views\\icons\\avatar.gif");
			Image image;
			try {
				image = new Image(file.toURI().toURL().toExternalForm());
				avatarImg.setImage(image);
			} catch (MalformedURLException ex) {
				Logger.getLogger(DrawerController.class.getName()).log(Level.SEVERE, null, ex);
			}	
		}
		/*if(compteComplet())
		{
			modifierProfile.arm();
		}*/
    }    

    @FXML
    private void logOut(ActionEvent event) {
        try {
			System.out.println(CupCakeFX.user);
			CupCakeFX.user=null;
			/*HomeViewController.indexTypeCompte=0;
			HomeViewController.listeCataloguebtn=0;
			HomeViewController.ajouterProduitbtn=0;
			HomeViewController.ajouterCataloguebtn=0;
			HomeViewController.affecterProduitCatalogue=0;*/
			System.out.println(CupCakeFX.user);
            Stage window=(Stage) exitBtn.getScene().getWindow();
            CupCakeFX homeClient=new CupCakeFX();
            homeClient.start(new Stage());
            window.close();
        } catch (Exception ex) {
            Logger.getLogger(DrawerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }
    
	/*public boolean compteComplet()
	{
		Utilisateur u = CupCakeFX.user;
		if(u.getEmail().isEmpty() || u.getPseudo().isEmpty() || u.getNum_tel() == 0 || u.getNom().isEmpty() || u.getPrenom().isEmpty() || u.getDate_naissance().toString().isEmpty() || u.getPassword().isEmpty())
		{
			return false;
		}
		return true;
	}*/
}
