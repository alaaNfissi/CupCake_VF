/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import java.net.URL;
import tn.esprit.cupcake.APIs.Mail.Mail;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;
import com.sun.javafx.font.FontConstants;
import java.net.URL;
import tn.esprit.cupcake.entities.Client;
import tn.esprit.cupcake.entities.EtatCompte;
import tn.esprit.cupcake.entities.TypeRole;
import tn.esprit.cupcake.entities.Utilisateur;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import tn.esprit.cupcake.services.ClientService;
import tn.esprit.cupcake.services.UtilisateurService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;




/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class AuthentificationController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	@FXML
	private JFXButton seConnecterButton;
	@FXML
	private JFXButton facebookButton;
	@FXML 
	private JFXTextField pseudoTextField;
	@FXML 
	private JFXPasswordField passwordTexField;
	
	@FXML
    private ImageView imgProgress;
	
	@FXML
    private ImageView imgAlert;
	@FXML
	private JFXButton creerComptebtn;
	@FXML
	private Label passwordOub;
	//@FXML Label message;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		imgProgress.setVisible(false);
		imgAlert.setVisible(false);
		passwordOub.setVisible(false);
	}
	
	 @FXML
    private void loginAction(ActionEvent event) throws IOException{
        imgProgress.setVisible(true);
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(5));
        pauseTransition.setOnFinished(ev -> {
            System.out.println("Complte one");
			try {
				SeConnecter();
			} catch (IOException | SQLException ex) {
				Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
			}
            System.out.println("Complte two");
        });
        pauseTransition.play();

    }
	
	 @FXML
    private void loginActionFb(ActionEvent event) throws IOException{
        imgProgress.setVisible(true);
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(5));
        pauseTransition.setOnFinished(ev -> {
            System.out.println("Complte one");
			try {
				seConnecterAvecFacebook();
			} catch (IOException | SQLException | ParseException ex) {
				Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
			}
            System.out.println("Complte two");
        });
        pauseTransition.play();
    }
	
	@FXML
	public void SeConnecter() throws IOException , SQLException{
		imgProgress.setVisible(false);
		if(validateInputs()){
		UtilisateurService us = new UtilisateurService();
		String pseudo = pseudoTextField.getText();
		String password = passwordTexField.getText();
		System.out.println("*******test******");
		Utilisateur u = us.searchByPseudoPass(pseudo, password);
		System.out.println(u);
		System.out.println("OK !");
		if(u!=null)
		{
			u.setEtat_compte(EtatCompte.Actif.ordinal());
			if (u.getRole()==TypeRole.Patisssier.ordinal()) {
				CupCakeFX.user=u;
				/*Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/cupcake/views/HomeClient.fxml"));
                        Scene newScene= new Scene(root);
                        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        window.setScene(newScene);
                        window.show();*/
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
                JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
                decorator.setCustomMaximize(false);
                decorator.setBorder(Border.EMPTY);

                Scene scene = new Scene(decorator);
                scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
				stage.show();
				seConnecterButton.getScene().getWindow().hide();
			}
			else if (u.getRole()==TypeRole.Client.ordinal()) {
				Client c =new Client();
				ClientService cs =new ClientService();
				c=cs.findById(u.getId_utilisateur());
				CupCakeFX.user=c;
				System.out.println(c);
				System.out.println("je suis un client");
				/*Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/cupcake/views/HomeClient.fxml"));
                        Scene newScene= new Scene(root);
                        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        window.setScene(newScene);
                        window.show();*/
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
                JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
                decorator.setCustomMaximize(false);
                decorator.setBorder(Border.EMPTY);

                Scene scene = new Scene(decorator);
                scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
				stage.show();
				seConnecterButton.getScene().getWindow().hide();
			}
			else {
				CupCakeFX.user=u;
				/*Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/cupcake/views/HomeClient.fxml"));
                        Scene newScene= new Scene(root);
                        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        window.setScene(newScene);
                        window.show();*/
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
                JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
                decorator.setCustomMaximize(false);
                decorator.setBorder(Border.EMPTY);

                Scene scene = new Scene(decorator);
                scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
				stage.show();
				seConnecterButton.getScene().getWindow().hide();
			}
			/*Task sendingMessage = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    
                    // ClientAbonne conduct;
                    //conduct=cas.findById(Table_demande.getSelectionModel().getSelectedItem().getId_ut());
                    //String destinataire = conduct.getEmail();
                   Mail mail = new Mail();
                    String submail = "Authentification faite avec succès";
                    String contentmail = "L mail hetha bel CupCake :D hhhhhhhhhhhhh Votre authentification a été faite avec succès, bienvenue à CupCake "+u.getNom()+" "+u.getPrenom();
                    System.out.println("mail test");
					mail.sendMail("alaa.nfissi@gmail.com", "Nfissi2014", u.getEmail(), submail, contentmail);
					System.out.println("mail sent");
                    return null;
                }
            };
            new Thread(sendingMessage).start();*/
            
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Votre authentification a été faite avec succès ;) ");
            alert.showAndWait();*/
		}
		else
		{
		 imgAlert.setVisible(true);
		 passwordOub.setVisible(true);
		}
		}
	}
	
	public void seConnecterAvecFacebook() throws UnsupportedEncodingException, SQLException, IOException, ParseException{
		imgProgress.setVisible(true);
		String domain ="https://localhost";
		String appId = "563976137303485";
		String appSecret ="42e3ac786b4dfd94b4599aa2e33d61dc";
		//String appSecret ="42e3ac786b4dfd94b4599aa2e33d61dc";
		//String authUrl ="https://graph.facebook.com/me/access_token?client_id="+appId+"&redirect_uri="+URLEncoder.encode("http://"+appId+".appspot.com/signin_fb.do", "UTF-8")+"&scope=public_profile,email,user_birthday" ;
		String authUrl ="https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+appId+"&redirect_uri="+domain+"&scope=user_birthday,"
				+"user_religion_politics, user_relationships, user_relationship_details, user_hometown, user_location, user_likes,"
				+" user_education_history, user_work_history, user_website, user_events, user_photos, user_videos, user_friends, user_about_me,"
				+" user_status, user_games_activity, user_tagged_places, user_posts, rsvp_event, email, read_insights, publish_actions,"
				+" read_custom_friendlists, user_actions.video, user_actions.news, user_actions.fitness, user_actions.books, user_actions.music,"
				+" user_managed_groups, manage_pages, pages_manage_cta, pages_manage_instant_articles, pages_show_list, publish_pages,"
				+" read_page_mailboxes, ads_management, ads_read, business_management, pages_messaging, pages_messaging_phone_number,"
				+" pages_messaging_subscriptions, instagram_basic, instagram_manage_comments, instagram_manage_insights, public_profile";
               /* + "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_activities,user_birthday,user_education_history,"
                + "user_events,user_photos,user_friends,user_games_activity,user_groups,user_hometown,user_interests,user_likes,user_location,user_photos,user_relationship_details,"
                + "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email,"
                + "manage_notifications,manage_pages,publish_actions,read_insights,read_mailbox,read_page_mailboxes,read_stream,rsvp_event" ;*/
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		//System.getProperties();
		//oauth
	
		WebDriver driver = new ChromeDriver();
		driver.get(authUrl);
		//driver.navigate().to(authUrl);
		//driver.navigate().to("https://localhost/");
		//"&client_secret="+appSecret+
		
		System.out.println(driver.getCurrentUrl());
		String accessToken;
		//String aToken ="EAAIA7u1TZAb0BABIBOg5b6PbNt0hbTbiat5SnDSFxZBjJZCdcbSpqQatMGKtKIOypUJBmy4jkzcZB5uX4ULMLfDUeZAziZAb7Sne8jlSrwqTFUnljXbZBLXPDmk0SE2IZAs2ZBZALwndojZCQQJWiZBOCsynQPXi7UGGsqJtZBfFZCRlMAugZDZD";
				/*FacebookClient fbClient1 = new DefaultFacebookClient(aToken);
		AccessToken exAccessToken = fbClient1.obtainExtendedAccessToken(appId,appSecret);
		System.out.println(exAccessToken.getAccessToken());
		System.out.println(exAccessToken.getExpires());*/
			boolean b =true;
		while (b) {		
			if (!driver.getCurrentUrl().contains("facebook.com")) {
				//String url = driver.getCurrentUrl();
				//accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$l");
				//System.out.println(accessToken);
				String url = driver.getCurrentUrl();
				accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
				System.out.println("test");
				
				
				driver.quit();
				b = false;
				//FacebookClient fbClient1 = new DefaultFacebookClient;
				FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
				String fields ="name,first_name,last_name,age_range,birthday,email,gender,address";
				User user = fbClient.fetchObject("me", User.class,Parameter.with("fields", fields));
				//driver.get("https://www.facebook.com/esprit20162017/");
				System.out.println(user.getName());
				System.out.println(user.toString());
				//message.setText(user.getName()+" "+user.getFirstName()+" "+user.getLastName()+" "+user.getEmail()+" "+user.getBirthday()+" "+user.getId());
				UtilisateurService us =new UtilisateurService();
				/*try {
					int i =Integer.parseInt(user.getId());
					System.out.println(i);
					Utilisateur u = us.searchUserById(i);
				}catch (NumberFormatException e) {
				  //e.getStackTrace();
				  System.out.println("error");
			  }*/
					
				if(us.searchUserById(Long.parseLong(user.getId()))!=null)
				{
					Utilisateur u =us.searchUserById(Long.parseLong(user.getId()));
					u.setEtat_compte(EtatCompte.Actif.ordinal());
					if (u.getRole()==TypeRole.Patisssier.ordinal()) {
						CupCakeFX.user=u;
						/*Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/cupcake/views/HomeClient.fxml"));
								Scene newScene= new Scene(root);
								Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
								window.setScene(newScene);
								window.show();*/
						Stage stage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
						JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);

						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						facebookButton.getScene().getWindow().hide();
					}
					else if (u.getRole()==TypeRole.Client.ordinal()) {
						Client c =new Client();
						ClientService cs =new ClientService();
						c=cs.findById(u.getId_utilisateur());
						CupCakeFX.user=c;
						/*Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/cupcake/views/HomeClient.fxml"));
								Scene newScene= new Scene(root);
								Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
								window.setScene(newScene);
								window.show();*/
						Stage stage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
						JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);

						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						facebookButton.getScene().getWindow().hide();
					}
					else {
						CupCakeFX.user=u;
						/*Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/cupcake/views/HomeClient.fxml"));
								Scene newScene= new Scene(root);
								Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
								window.setScene(newScene);
								window.show();*/
						Stage stage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
						JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);

						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						facebookButton.getScene().getWindow().hide();
					}
					
				}
				else
				{
					java.sql.Date sqlDate=null;
					if(user.getBirthday()!=null){
						java.util.Date dtJ=(Date) new SimpleDateFormat("MM/DD/YYYY").parse(user.getBirthday());
						sqlDate = new java.sql.Date(dtJ.getTime());
						Utilisateur u=new Utilisateur(Long.parseLong(user.getId()),user.getId()+"cupcake", user.getEmail(), user.getFirstName().toLowerCase()+"_"+user.getLastName().toLowerCase(), EtatCompte.Actif.ordinal(), user.getLastName(), user.getFirstName(),sqlDate);
						CupCakeFX.user=u;
						us.ajouterUtilisateur(u);
					}
				    
					//Date sqlDate = Date.valueOf(user.getBirthday());
					else
					{
						Utilisateur u=new Utilisateur(Long.parseLong(user.getId()),user.getId()+"cupcake", user.getEmail(), user.getFirstName().toLowerCase()+"_"+user.getLastName().toLowerCase(), EtatCompte.Actif.ordinal(), user.getLastName(), user.getFirstName(),sqlDate);
						CupCakeFX.user=u;
						us.ajouterUtilisateur(u);
					}
					
					
					/*u.setId_utilisateur(Long.parseLong(user.getId()));
					u.setPassword(user.getId()+"cupcake");
					u.setEmail(user.getEmail());
					u.setPseudo(user.getFirstName()+"_"+user.getLastName());
					u.setEtat_compte(EtatCompte.Actif.ordinal());
					u.setNom(user.getLastName());
					u.setPrenom(user.getFirstName());
					u.setDate_naissance(user.getBirthday());*/
					
					/*Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/cupcake/views/HomeClient.fxml"));
                        Scene newScene= new Scene(root);
                        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        window.setScene(newScene);
                        window.show();*/
						Stage stage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
						JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);

						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						facebookButton.getScene().getWindow().hide();
				}
			  }
			
		}
		
	}
	
	private boolean validateInputs() throws SQLException {
		/*UtilisateurService us=new UtilisateurService();
		Utilisateur u =us.searchByPseudoPass(pseudoTextField.getText(), passwordTexField.getText());
		if(u.getEtat_compte()==EtatCompte.Bloqué.ordinal() || u.getEtat_compte()==EtatCompte.Désactivé.ordinal())
		{
			return false;
		}*/
        if (pseudoTextField.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez saisir votre Pseudo");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        }
		if(passwordTexField.getText().isEmpty())
		{
			Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez saisir votre Password");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
		}
        return true;
    }
	@FXML
	public void CreerCompte(ActionEvent event) throws IOException
	{
		Stage stage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource(Routes.MAINVIEW));
						JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
						decorator.setCustomMaximize(false);
						decorator.setBorder(Border.EMPTY);

						Scene scene = new Scene(decorator);
						scene.getStylesheets().add(CupCakeFX.class.getResource("/tn/esprit/cupcake/stylesheets/styles.css").toExternalForm());
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.show();
						creerComptebtn.getScene().getWindow().hide();
	}
	@FXML
	public void passwordOublie()
	{
		if(!pseudoTextField.getText().isEmpty() && pseudoTextField.getText().contains("@"))
		{
			Task sendingMessage = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
					UtilisateurService us=new UtilisateurService();
					Utilisateur u = us.searchByEmail(pseudoTextField.getText());
                   Mail mail = new Mail();
                    String submail = "Authentification faite avec succès";
                    String contentmail = "Votre mot de passe est : "+u.getPassword() ;
                    System.out.println("mail test");
					mail.sendMail("alaa.nfissi@gmail.com", "Nfissi2014", u.getEmail(), submail, contentmail);
					System.out.println("mail sent");
                    return null;
                }
            };
            new Thread(sendingMessage).start();
		}
		
	}
}
