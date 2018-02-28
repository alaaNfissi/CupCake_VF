/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.cupcake.services;

//import tn.esprit.cupcake.entities.TypeRole;
import tn.esprit.cupcake.entities.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author Alaa
 */
public class UtilisateurService {
	Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;

	public UtilisateurService() {
			try {
				if (con != null) {
					stmt = con.createStatement();
				}
			} catch (SQLException ex) {
				System.out.println(ex);
		}
	}
	
	public Utilisateur searchByPseudoPass(String pseudo,String password) throws SQLException
	{
		Utilisateur u=null;
		String req="SELECT * FROM `utilisateur` WHERE ((pseudo = ? OR email = ?) AND password = ?)";
		PreparedStatement pre = con.prepareStatement(req);
		pre.setString(1, pseudo);
		pre.setString(2, pseudo);
		pre.setString(3, password);
		ResultSet rs = pre.executeQuery();
		while(rs.next())
		{
				u=new Utilisateur();
				u.setId_utilisateur(rs.getLong("id_utilisateur"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setPseudo(rs.getString("pseudo"));
				u.setEtat_compte(rs.getInt("etat_compte"));
				u.setNum_tel(rs.getInt("num_tel"));
				u.setNom(rs.getString("nom"));
				u.setPrenom(rs.getString("prenom"));
				u.setDate_naissance(rs.getDate("date_naissance"));
				u.setRole(rs.getInt("role"));
			/*if(rs.getInt("role")==0){
				Administrateur a= new Administrateur();
				a.setPassword(rs.getString("password"));
				a.setEmail("email");
				a.setPseudo(rs.getString("pseudo"));
				a.setEtat_compte(rs.getInt("etat_compte"));
				a.setNum_tel(rs.getInt("num_tel"));
				a.setNom(rs.getString("nom"));
				a.setPrenom(rs.getString("prenom"));
				a.setDate_naissance(rs.getString("date_naissance"));
				a.setRole(rs.getInt("role"));
				u=(Administrateur)a;
			}
			else if(rs.getInt("role")==1)
			{
				Patissier p = new Patissier();
				p.setPassword(rs.getString("password"));
				p.setEmail("email");
				p.setPseudo(rs.getString("pseudo"));
				p.setEtat_compte(rs.getInt("etat_compte"));
				p.setNum_tel(rs.getInt("num_tel"));
				p.setNom(rs.getString("nom"));
				p.setPrenom(rs.getString("prenom"));
				p.setDate_naissance(rs.getString("date_naissance"));
				p.setRole(rs.getInt("role"));
				u=(Patissier)p;
			}
			else if (rs.getInt("role")==2) {
				Client c =  new Client();
				c.setPassword(rs.getString("password"));
				c.setEmail("email");
				c.setPseudo(rs.getString("pseudo"));
				c.setEtat_compte(rs.getInt("etat_compte"));
				c.setNum_tel(rs.getInt("num_tel"));
				c.setNom(rs.getString("nom"));
				c.setPrenom(rs.getString("prenom"));
				c.setDate_naissance(rs.getString("date_naissance"));
				c.setRole(rs.getInt("role"));
				c.setAdresse(rs.getString("adresse"));
				c.setSexe(rs.getString("sexe"));
				c.setImage("image");
				u=(Client)c;
			}*/
		}
		System.out.println("Utilisateur trouvé !");
		return u;
	}
	
	public Utilisateur searchUserById(long id) throws SQLException{
		Utilisateur u=null;
		String req="SELECT * FROM `utilisateur` WHERE id_utilisateur = ?";
		PreparedStatement pre = con.prepareStatement(req);
		pre.setLong(1,id);
		ResultSet rs = pre.executeQuery();
		while(rs.next()) 
			{
				u=new Utilisateur();
				u.setId_utilisateur(rs.getLong("id_utilisateur"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setPseudo(rs.getString("pseudo"));
				u.setEtat_compte(rs.getInt("etat_compte"));
				u.setNum_tel(rs.getInt("num_tel"));
				u.setNom(rs.getString("nom"));
				u.setPrenom(rs.getString("prenom"));
				u.setDate_naissance(rs.getDate("date_naissance"));
				u.setRole(rs.getInt("role"));
				System.out.println("Utilisateur trouvé !");
			}
		return u;
	}
	
	  public void ajouterUtilisateur(Utilisateur utilisateur) throws SQLException {
        String req = "INSERT INTO utilisateur (id_utilisateur,password,email,pseudo,etat_compte,nom,prenom,date_naissance) values(?,?,?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setLong(1,utilisateur.getId_utilisateur());
		pre.setString(2, utilisateur.getPassword());
        pre.setString(3,utilisateur.getEmail());
        pre.setString(4,utilisateur.getPseudo());
        pre.setInt(5,utilisateur.getEtat_compte());
        pre.setString(6,utilisateur.getNom());
        pre.setString(7,utilisateur.getPrenom());
        pre.setDate(8,utilisateur.getDate_naissance());   
        pre.executeUpdate();
        System.out.println("Utilisateur ajouté");
    }
	
	public void changeEtatCompte(Utilisateur utilisateur,int etatCompte) throws SQLException{
		String req = "UPDATE `utilisateur` SET `etat_compte` = ? WHERE `id_utilisateur` = ?";
		//String req ="INSERT INTO `utilisateur` (etat_compte) VALUES (?) WHERE `id_utilisateur` = ?";
		PreparedStatement pre = con.prepareStatement(req);
		pre.setInt(1,etatCompte);
		pre.setLong(2,utilisateur.getId_utilisateur());
		pre.executeUpdate();
		System.out.println("etat changer dans la base");
	}
		
	public List<String> verifierPseudo() throws SQLException
	{
		List<String> listePseudo= new ArrayList<>();
		String pseudo;
		String req="Select pseudo as Pseudo from utilisateur";
		PreparedStatement pre=con.prepareStatement(req);
		ResultSet rs = pre.executeQuery();
		while(rs.next())
		{
			pseudo=rs.getString("Pseudo");
			listePseudo.add(pseudo);
		}
		return listePseudo;
	}
	public Utilisateur searchByEmail(String email) throws SQLException
	{
		Utilisateur u =new Utilisateur();
		String req="select * from utilisateur where email like ?";
		PreparedStatement pre=con.prepareStatement(req);
		pre.setString(1, email);
		ResultSet rs=pre.executeQuery();
		while(rs.next())
		{
			 u = new Utilisateur(rs.getLong("id_utilisateur"),rs.getString("password"), rs.getString("email"), rs.getString("pseudo"), rs.getInt("etat_compte"));
		}
		return u; 
	}
	

}
