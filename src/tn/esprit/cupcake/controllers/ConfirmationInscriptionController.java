/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.cupcake.entities.*;
import tn.esprit.cupcake.services.*;

/**
 * FXML Controller class
 *
 * @author Basly
 */
public class ConfirmationInscriptionController implements Initializable {

    @FXML
    private TableView<Patissier> tableViewPatissier;
    @FXML
    private TableColumn<Patissier, Integer> idPatissier;
    @FXML
    private TableColumn<Patissier, String> nomPatissier;
    @FXML
    private TableColumn<Patissier, String> prenomPatissier;
    @FXML
    private TableColumn<Patissier, String> emailPatissier;
    @FXML
    private Label loadNomPatisserie;
    @FXML
    private Label loadAdressePatisserie;
    @FXML
    private Label loadDatePatisserie;
    @FXML
    private Label loadSpecialitePatisserie;
    @FXML
    private Button btnConfirmer;
    @FXML
    private Button btnSupprimer;

    ObservableList<Patissier> listViewPatissier;
    List<Patissier> listPatissier;

    /**
     * Initializes the controller c
     *
     * @FXML private void AfficherData(ActionEvent event) { } lass.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherData();
    }

    private void setCellValue() {
        idPatissier.setCellValueFactory(new PropertyValueFactory<>("id_utilisateur"));
        nomPatissier.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomPatissier.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailPatissier.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void AfficherData() {
        try {
            PatissierService sr = new PatissierService();
            listPatissier = new ArrayList<>();
            listPatissier = sr.selectPatissierEnAttente();
			System.out.println(listPatissier);
            listViewPatissier = FXCollections.observableArrayList(listPatissier);
            tableViewPatissier.setItems(listViewPatissier);
            setCellValue();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void AfficherInfoPatisserie(MouseEvent event) {

        long idPatissier = tableViewPatissier.getSelectionModel().getSelectedItem().getId_utilisateur();
        PatisserieService sr = new PatisserieService();
        try {
            List<Patisserie> listPatisserie = sr.selectPatisserieDuPatissierEnAttente(idPatissier);
            for (Patisserie patisserie : listPatisserie) {
                loadNomPatisserie.setText(patisserie.getLibelle_patisserie());
                loadAdressePatisserie.setText(patisserie.getAdresse_patisserie());
                loadDatePatisserie.setText(patisserie.getDate_creation().toString());
                loadSpecialitePatisserie.setText(patisserie.getSpecialite());
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void ConfirmerInscription(ActionEvent event) throws SQLException {
        long idPatissier = tableViewPatissier.getSelectionModel().getSelectedItem().getId_utilisateur();
        PatissierService sr = new PatissierService();
        sr.ConfirmerInscription(idPatissier);
        AfficherData();
    }

    @FXML
    private void SupprimerInscription(ActionEvent event) throws SQLException {
        long idPatissier = tableViewPatissier.getSelectionModel().getSelectedItem().getId_utilisateur();
        PatissierService sr1 = new PatissierService();
        PatisserieService sr2 = new PatisserieService();
        sr2.supprimerPatisserie(idPatissier);
        sr1.supprimerPatissier(idPatissier);
        tableViewPatissier.getItems().remove(tableViewPatissier.getSelectionModel().getSelectedItem());
        AfficherData();
    }

}
