/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.services;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.Patissier;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author Basly
 */
public class PatisserieService {

    Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;

    public PatisserieService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void ajouterPatisserie(Patisserie patisserie) throws SQLException {
        String req = "INSERT INTO patisserie values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, patisserie.getId_patisserie());
        pre.setString(2, patisserie.getLibelle_patisserie());
        pre.setString(3, patisserie.getAdresse_patisserie());
        pre.setDate(4, patisserie.getDate_creation());
        pre.setString(5, patisserie.getSpecialite());
        pre.setString(6, patisserie.getCompte_facebook());
        pre.setString(7, patisserie.getCompte_instagram());
        pre.setString(8, patisserie.getDescription());
        pre.setString(9, patisserie.getImage());
        pre.setLong(10, patisserie.getPatissier());
        pre.executeUpdate();
        System.out.println("Patisserie ajouté");
    }
    
    public void modifierPatisserie(Patisserie patisserie, long id) throws SQLException {
        String req = "UPDATE patisserie SET libelle_patisserie=?,adresse_patisserie = ? ,date_creation = ? ,specialite=?,compte_facebook=?, compte_instagram=?, description=?,image=?  WHERE 	id_utilisateur = ?";
        PreparedStatement ste = con.prepareStatement(req);
        //Dans l'ordre
        ste.setString(1,patisserie.getLibelle_patisserie());
        ste.setString(2,patisserie.getAdresse_patisserie() );
        ste.setDate(3,patisserie.getDate_creation() );
        ste.setString(4, patisserie.getSpecialite());
        ste.setString(5,patisserie.getCompte_facebook());
        ste.setString(6,patisserie.getCompte_instagram());
        ste.setString(7,patisserie.getDescription());
        ste.setString(8,patisserie.getImage());
        ste.setLong(9, id);
        ste.executeUpdate();
        Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
        alertSucc.setTitle("Succés");
        alertSucc.setContentText("Opération effectuer avec succés");
        alertSucc.setHeaderText(null);
        alertSucc.show();
    }
    
    
    public List<Patisserie> selectPatisserie() throws SQLException {
        List<Patisserie> listPatisserie = new ArrayList<>();
        String req = "SELECT * FROM patisserie where id_utilisateur=?";
        PreparedStatement ste = con.prepareStatement(req);
		ste.setLong(1, CupCakeFX.user.getId_utilisateur());
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listPatisserie.add(new Patisserie(
                    rs.getString("libelle_patisserie"),
                    rs.getString("adresse_patisserie"),
                    rs.getDate("date_creation"),
                    rs.getString("specialite"),
                    rs.getString("compte_facebook"),
                    rs.getString("compte_instagram"),
                    rs.getString("description"),
                    rs.getString("image")
                    ));
        }
        return listPatisserie;
    }
    
    public List<Patisserie> selectPatisserieDuPatissierEnAttente(long id) throws SQLException {
        List<Patisserie> listPatisserie = new ArrayList<>();
        String req = "SELECT * FROM patisserie where id_utilisateur=?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setLong(1,id);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            listPatisserie.add(new Patisserie(
                   rs.getString("libelle_patisserie"),
                    rs.getString("adresse_patisserie"),
                    rs.getDate("date_creation"),
                    rs.getString("specialite")
                    ));
        }
        return listPatisserie;
    }
    public  void supprimerPatisserie(long id)throws SQLException  {
        String req = "DELETE FROM patisserie WHERE id_utilisateur =?";
        PreparedStatement ste = con.prepareStatement(req);
        ste.setLong(1, id);
        ste.executeUpdate();
    }
    
    public Patisserie searchById(int id) throws SQLException
	{
		String req="select * from patisserie where id_patisserie =?";
		PreparedStatement pre=con.prepareStatement(req);
		pre.setInt(1,id);
		ResultSet rs= pre.executeQuery();
		while(rs.next())
		{
			Patisserie patisserie=new Patisserie();
			patisserie.setId_patisserie(rs.getInt("id_patisserie"));
			patisserie.setLibelle_patisserie(rs.getString("libelle_patisserie"));
			patisserie.setAdresse_patisserie(rs.getString("adresse_patisserie"));
			patisserie.setDate_creation(rs.getDate("date_creation"));
			patisserie.setSpecialite(rs.getString("specialite"));
			patisserie.setCompte_facebook(rs.getString("compte_facebook"));
			patisserie.setCompte_instagram(rs.getString("compte_instagram"));
			patisserie.setDescription(rs.getString("description"));
			patisserie.setImage(rs.getString("image"));
			return patisserie;
		}
		return null;
	}
	
	public String LibellePatisserie(int id_patisserie) throws SQLException
	{
		String req= "Select libelle_patisserie as libellePatisserie from patisserie where id_patisserie =?";
		PreparedStatement pre= con.prepareStatement(req);
		pre.setInt(1,id_patisserie);
		ResultSet rs = pre.executeQuery();
		while(rs.next())
		{
			return rs.getString("libellePatisserie");
		}
		return "";
	}
	
	public Patisserie searchByIdPatissier(long id_patissier) throws SQLException
	{
		Patisserie patisserie = null;
		String req ="select * from patisserie where id_utilisateur = ?";
		PreparedStatement pre = con.prepareStatement(req);
		pre.setLong(1, id_patissier);
		ResultSet rs = pre.executeQuery();
		while(rs.next())
		{
			patisserie=new Patisserie();
			patisserie.setId_patisserie(rs.getInt("id_patisserie"));
			patisserie.setLibelle_patisserie(rs.getString("libelle_patisserie"));
			patisserie.setAdresse_patisserie(rs.getString("adresse_patisserie"));
			patisserie.setDate_creation(rs.getDate("date_creation"));
			patisserie.setSpecialite(rs.getString("specialite"));
			patisserie.setCompte_facebook(rs.getString("compte_facebook"));
			patisserie.setCompte_instagram(rs.getString("compte_instagram"));
			patisserie.setDescription(rs.getString("description"));
			patisserie.setImage(rs.getString("image"));
			patisserie.setPatissier(rs.getLong("id_utilisateur"));
		}
		return patisserie;
	}
    public List<Patisserie> retourneTTPatisseries() throws SQLException, MalformedURLException
	{
		List<Patisserie> listePatisseries=new ArrayList<>();
		String req="select * from patisserie";
		PreparedStatement pre=con.prepareStatement(req);
		ResultSet rs=pre.executeQuery();
		while(rs.next()){
			Patisserie p=new Patisserie();
			p.setId_patisserie(rs.getInt("id_patisserie"));
			p.setLibelle_patisserie(rs.getString("libelle_patisserie"));
			p.setAdresse_patisserie(rs.getString("adresse_patisserie"));
			p.setSpecialite(rs.getString("specialite"));
			p.setDescription(rs.getString("description"));
			//p.setImage(rs.getString("image"));
			listePatisseries.add(p);
		}
		return listePatisseries;
	}

}
