

package tn.esprit.cupcake.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import tn.esprit.cupcake.entities.Catalogue;
import tn.esprit.cupcake.entities.Patisserie;
import tn.esprit.cupcake.entities.Produit;
import tn.esprit.cupcake.entities.ProduitCatalogue;
import tn.esprit.cupcake.test.CupCakeFX;
import tn.esprit.cupcake.utils.CupCakeDBConnection;

/**
 *
 * @author esprit
 */
public class ProduitCatalogueService {
    Connection con = CupCakeDBConnection.getInstance().getConnection();
    private Statement stmt;

    public ProduitCatalogueService() {
        try {
            if (con != null) {
                stmt = con.createStatement();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public List<Produit> afficheProduit() throws SQLException {
        List<Produit> listProduit = new ArrayList<>();
        try {
            String req = "Select * from produit where id_patisserie=? ";
			PatisserieService ps = new PatisserieService();
			Patisserie pt = ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
            PreparedStatement ste = con.prepareStatement(req);
            ste.setInt(1, pt.getId_patisserie());
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                listProduit.add(new Produit(rs.getInt("id_produit"),
                        rs.getString("libelle_produit"),
                        rs.getString("categorie"),
                        rs.getFloat("prix"),
                        rs.getDate("date_expiration"),
                        rs.getInt("quantite"),
                        rs.getString("description")));
               
            }
    
}catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduit;
    }
    
    
     public List<Catalogue> afficheCatalogue() throws SQLException {
        List<Catalogue> listCatalogue = new ArrayList<>();
        try {
            String req = "Select * from catalogue where id_patisserie=?";
			PatisserieService ps = new PatisserieService();
			Patisserie pt = ps.searchByIdPatissier(CupCakeFX.user.getId_utilisateur());
            PreparedStatement ste = con.prepareStatement(req);
            ste.setInt(1, pt.getId_patisserie());
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                listCatalogue.add(new Catalogue( rs.getString("libelle_catalogue")));
               
            }
    
}catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCatalogue;
    }
     
     
     
     
      public int RecupCatalogueId (String item) throws SQLException {
            try {
            String req = "SELECT id_catalogue FROM catalogue where libelle_catalogue=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setString(1,item);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getInt("id_catalogue");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
    }




 public int RecupProduitId (String item) throws SQLException {
            try {
            String req = "SELECT id_produit FROM produit where libelle_produit=?";
            PreparedStatement ste = con.prepareStatement(req);
            ste.setString(1,item);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                return rs.getInt("id_produit");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupCakeDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1 ;
    }



 public void ajouterProduitCatalogue(ProduitCatalogue pc)    throws SQLException {
        String req = "INSERT INTO produit_catalogue values(?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, pc.getId_catalogue());
        pre.setInt(2, pc.getId_produit());
        pre.executeUpdate();
        System.out.println("Produit id="+""+pc.getId_produit()+" Catalogue id="+""+pc.getId_catalogue()+""+"a ajouté dans le produit_catalogue avec succés");
           Alert alertSucc = new Alert(Alert.AlertType.CONFIRMATION);
            alertSucc.setTitle("Succés");
            alertSucc.setContentText("Produit ajouté au catalogue avec succés");
            alertSucc.setHeaderText(null);
            alertSucc.show();
        
    }
 
 
   public void supprimerProduitCatalogue(int id_catalogue) throws SQLException {
        String req = "DELETE FROM produit_catalogue WHERE id_catalogue =?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id_catalogue);
        pre.executeUpdate();
    }
}
