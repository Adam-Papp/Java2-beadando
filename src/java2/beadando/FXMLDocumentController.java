/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2.beadando;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author AdamPapp
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    public ListView<String> ListViewZeneNev;
    
    private static MediaPlayer mediaPlayer;
    
    ObservableList<String> names = FXCollections.observableArrayList("Engineering", "MCA", "MBA", "Graduation", "MTECH", "Mphil", "Phd");
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        ListViewZeneNev.setItems(names);
    }    
    
    
    public void playButton(ActionEvent event)
    {
        String source = new File("RockAngel.mp3").toURI().toString();
        Media media = null;
        media = new Media(source);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }
}
