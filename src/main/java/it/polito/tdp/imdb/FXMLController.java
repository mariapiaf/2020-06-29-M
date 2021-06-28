/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Adiacente;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Year> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Year anno = boxAnno.getValue();
    	if(anno == null) {
    		txtResult.appendText("Devi scegliere un anno!");
    		return;
    	}
    	this.model.creaGrafo(anno);
    	txtResult.appendText("GRAFO CREATO\n");
    	txtResult.appendText("# VERTICI: " + this.model.nVertici()+"\n");
    	txtResult.appendText("# ARCHI: " + this.model.nArchi());
    	boxRegista.getItems().addAll(model.getVertici());
    	
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	txtResult.clear();
    	Director partenza = boxRegista.getValue();
    	if(partenza == null) {
    		txtResult.appendText("Devi scegliere un regista!");
    		return;
    	}
    	this.model.getVicini(partenza);
    	for(Adiacente a: model.getVicini(partenza)) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	txtResult.clear();
    	Director partenza = boxRegista.getValue();
    	if(partenza == null) {
    		txtResult.appendText("Devi scegliere un regista!");
    		return;
    	}
    	String sc = txtAttoriCondivisi.getText();
    	int c = -1;
    	try {
    		c = Integer.parseInt(sc);
    	} catch(NumberFormatException n) {
        	txtResult.appendText("Devi inserire un numero intero!");
        	return;
    	}
    	model.getRegistiAffini(partenza, c);
    	for(Director d: model.getRegistiAffini(partenza, c)) {
    		txtResult.appendText(d.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	
    	
    	for(int anno=2004; anno<=2006; anno++) {
    		boxAnno.getItems().add(Year.of(anno)) ;
    	}
    	
    }
    
}
