/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tn.esprit.cupcake.entities.Evenement;
import tn.esprit.cupcake.services.EvenementService;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.Routes;

/**
 *
 * @author Basly
 */
public class ModifierEvenementController extends JavascriptObject implements Initializable, MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    @FXML
    private TextField addressTextField;

    private GoogleMap map;

    private GeocodingService geocodingService;

    private StringProperty address = new SimpleStringProperty();
    @FXML
    private TextField txtLibelle;
    @FXML
    private DatePicker txtDateDebut;
    @FXML
    private DatePicker txtDateFin;
    @FXML
    private TextArea txtDescription;
    @FXML
    private Button btnImport;
    @FXML
    private ImageView imageEvent;
	@FXML
	private JFXButton returnBtn;
    int id;
    String adresseEvent = "";
    Double longitude;
    Double latitude;
    LatLong latLong = null;
    String path = "";
    @FXML
    private Label txtLongitude;
    @FXML
    private Label txtLatitude;
    @FXML
    private Button btnModifier;
    @FXML
    private Label txtImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        address.bind(addressTextField.textProperty());
        txtLatitude.setVisible(false);
        txtLongitude.setVisible(false);
        txtImage.setVisible(false);
    }

    @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(36.8189700, 10.1657900))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);
        map = mapView.createMap(mapOptions);
        Double lat = Double.parseDouble(txtLatitude.getText());
        Double longi = Double.parseDouble(txtLongitude.getText());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLong(lat, longi))
                .visible(Boolean.TRUE)
                .title("My Marker");
        Marker marker = new Marker(markerOptions);
        map.addMarker(marker);
        System.out.println("TEEEEEEEEEEEESTSTTT" + id);
    }

    @FXML
    public void addressTextFieldAction(ActionEvent event) {
        MarkerOptions markerOptions = new MarkerOptions();
        geocodingService.geocode(address.get(), (GeocodingResult[] results, GeocoderStatus status) -> {
            if (status == GeocoderStatus.ZERO_RESULTS) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                alert.show();
                return;
            } else if (results.length > 1) {
                Alert alertM = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.");
                alertM.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            longitude = latLong.getLongitude();
            latitude = latLong.getLatitude();
            markerOptions.position(new LatLong(latitude, longitude))
                    .visible(Boolean.TRUE)
                    .title("My Marker");
            Marker marker = new Marker(markerOptions);
            map.addMarker(marker);
            map.setCenter(latLong);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.setContentText("Voulez-vous confirmer cette adresse?");
            alert.show();
            alert.setOnHidden(e -> {
                if (alert.getResult() == ButtonType.YES) {
                    System.out.println("Ok pressed");
                    //adresseEvent = address.get();

                } else {
                    System.out.println("canceled");
                }
            });
        });
    }

    private boolean validateInputs() {
        if ((txtLibelle.getText().isEmpty()) || (txtDescription.getText().isEmpty())
                || (addressTextField.getText().isEmpty())
                ||(txtDateDebut.getValue()==null)
                ||(txtDateFin.getValue()==null)) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez remplir tout les champs");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        }
        return true;
    }

    public void getInfoEvenement(String id_evenement, String libelle_evenement, String adresse_evenement, Date dateDebut, Date dateFin, String description, String image, Double longitude, Double latitude) {
        id = Integer.parseInt(id_evenement);
        txtLibelle.setText(libelle_evenement);
		addressTextField.setText(adresse_evenement);
        LocalDate dDebut = new java.sql.Date(dateDebut.getTime()).toLocalDate();
        LocalDate dFin = new java.sql.Date(dateFin.getTime()).toLocalDate();
        txtDateDebut.setValue(dDebut);
        txtDateFin.setValue(dFin);
        txtDescription.setText(description);
        txtLongitude.setText(String.valueOf(longitude));
        txtLatitude.setText(String.valueOf(latitude));
        txtImage.setText(image);
        File file = new File(image);
        try {
            Image imagePath = new Image(file.toURI().toURL().toExternalForm());
            imageEvent.setImage(imagePath);
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private String showMarker(MouseEvent event) throws MalformedURLException, IOException {
        MarkerOptions markerOptions = new MarkerOptions();
        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            latLong = new LatLong((JSObject) obj.getMember("latLng"));
            System.out.println("Test**********" + latLong.toString());
            longitude = latLong.getLongitude();
            latitude = latLong.getLatitude();
            markerOptions.position(new LatLong(latLong.getLatitude(), latLong.getLongitude()))
                    .visible(Boolean.TRUE)
                    .title("My Marker");
            Marker marker = new Marker(markerOptions);

            map.addMarker(marker);
        });

        URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="
                + latitude + "," + longitude + "&sensor=true");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String formattedAddress = "";
 
        try {
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String result, line = reader.readLine();
            result = line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
 
            JSONParser parser = new JSONParser();
            JSONObject rsp = (JSONObject) parser.parse(result);
 
            if (rsp.containsKey("results")) {
                JSONArray matches = (JSONArray) rsp.get("results");
                JSONObject data = (JSONObject) matches.get(0); //TODO: check if idx=0 exists
                formattedAddress = (String) data.get("formatted_address");
            }
 
            return "";
        } finally {
            urlConnection.disconnect();
            System.out.println("Teeeeeeeess"+formattedAddress);
            addressTextField.setText(formattedAddress);
            return formattedAddress;
        }
        

    }

    @FXML
    private void ImportImage(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            path = file.getAbsolutePath();
            String path2 = file.toURI().toURL().toString();
            Image image = new Image(path2);
            imageEvent.setImage(image);
        } else {
            path = txtImage.getText();
        }
    }

    @FXML
    private void modifierEvenement(ActionEvent event) {
        if ((longitude == null) && (latitude == null)) {
            longitude = Double.parseDouble(txtLongitude.getText());
            latitude = Double.parseDouble(txtLatitude.getText());
        }
        if (adresseEvent == "") {
            adresseEvent = addressTextField.getText();
        }
        if (path == "") {
            path = txtImage.getText();
        }
        EvenementService sr = new EvenementService();
        Date dateDebut = Date.valueOf(txtDateDebut.getValue());
        Date dateFin = Date.valueOf(txtDateFin.getValue());
        try {
            Evenement evenement = new Evenement(txtLibelle.getText(), addressTextField.getText(), dateDebut, dateFin, txtDescription.getText(), path, longitude, latitude);
            if (validateInputs()) {
                sr.modifierEvenement(evenement, id);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
	
	@FXML
	public void returnAction(ActionEvent event) throws IOException
	{
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
