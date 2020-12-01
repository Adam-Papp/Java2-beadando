/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2.beadando;

import java.io.File;
import java.net.URL;
import java.util.List;
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
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;

/**
 *
 * @author AdamPapp
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    public ListView<String> ListViewZeneNev;
    public ListView<String> ListViewZeneHossz;
    
    private static MediaPlayer mediaPlayer;
    
    ObservableList<String> names = FXCollections.observableArrayList("Engineering", "MCA", "MBA", "Graduation", "MTECH", "Mphil", "Phd");
    
    ObservableList<String> zenehosszak = FXCollections.observableArrayList();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        int duration = 0;

        try {
          AudioFile audioFile = AudioFileIO.read(new File("RockAngel.mp3"));
          duration = audioFile.getAudioHeader().getTrackLength();
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        zenehosszak.add(Double.toString(duration));
        
        ListViewZeneHossz.setItems(zenehosszak);
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
