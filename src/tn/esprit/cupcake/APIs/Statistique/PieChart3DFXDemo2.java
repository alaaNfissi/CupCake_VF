/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013-2017, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.object-refinery.com/orsoncharts/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Note that the above terms apply to the demo source only, and not the 
 * Orson Charts library.
 * 
 */

package tn.esprit.cupcake.APIs.Statistique;

import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.TitleAnchor;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.fx.Chart3DViewer;
import com.orsoncharts.label.StandardPieLabelGenerator;
import static com.orsoncharts.label.StandardPieLabelGenerator.PERCENT_TEMPLATE;
import com.orsoncharts.plot.PiePlot3D;
import tn.esprit.cupcake.utils.CupCakeDBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import jdk.nashorn.internal.objects.NativeArray;
import tn.esprit.cupcake.entities.ProduitStat;
import tn.esprit.cupcake.entities.Utilisateur;
import tn.esprit.cupcake.services.ProduitStatService;
import tn.esprit.cupcake.services.UtilisateurService;
/**
 * A 3D pie chart demo for JavaFX.
 */
public class PieChart3DFXDemo2 /*extends Application*/ {
	    Connection con = CupCakeDBConnection.getInstance().getConnection();
		private Statement stmt;
		
    public static Node createDemoNodeClient() throws SQLException {
        PieDataset3D<String> dataset = createDataset();
        Chart3D chart = createChart(dataset);
        Chart3DViewer viewer = new Chart3DViewer(chart);
        return viewer;
    }

    
    private static PieDataset3D<String> createDataset() throws SQLException {
		UtilisateurService us = new UtilisateurService();
		Utilisateur u = null;
		u=us.searchUserById(12);
        StandardPieDataset3D<String> dataset = new StandardPieDataset3D<>();
		ProduitStatService pss= new ProduitStatService();
		List<ProduitStat> listeProduitStats = pss.RegrouperProduitsParPatisserie(u);
		for(int i=0;i<listeProduitStats.size();i++)
		{
			dataset.add(listeProduitStats.get(i).getLibelle_produitS()+" : "+listeProduitStats.get(i).getLibelle_patisserieS(), listeProduitStats.get(i).getQuantiteS());
		}
		/*dataset.add("United States", 4);
        dataset.add("Tunisia", 50);
        dataset.add("New Zealand",5);
        dataset.add("United Kingdom", 1);
        dataset.add("Australia", 3);
        dataset.add("Canada",6);*/
        return dataset; 
    }
  
    /**
     * Creates a pie chart based on the supplied dataset.
     * 
     * @param dataset  the dataset.
     * 
     * @return A pie chart. 
     */
    private static Chart3D createChart(PieDataset3D<String> dataset) throws SQLException {
        final Chart3D chart = Chart3DFactory.createPieChart("Vos Statistiques :", 
            "Les Produits que vous avez commander via notre applications", 
            createDataset());
        chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setLegendLabelGenerator(new StandardPieLabelGenerator(PERCENT_TEMPLATE));
        plot.setSectionLabelGenerator(new StandardPieLabelGenerator(PERCENT_TEMPLATE));
        plot.setSectionColors(Colors.createFancyLightColors());
        return chart;
    }
    
   /* @Override
    public void start(Stage stage) throws Exception,SQLException {
        StackPane sp = new StackPane();
        sp.getChildren().add(createDemoNode());
        Scene scene = new Scene(sp, 768, 512);
        stage.setScene(scene);
        stage.setTitle("Statistiques");
        stage.show();
    }*/
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        launch(args);
    }*/
}
