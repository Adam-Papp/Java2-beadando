package java2.beadando;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;



public class FXMLDocumentController implements Initializable {
    
    @FXML
    public Label currentPlaying;
    
    public ListView<String> ListViewSongNames;
    public ListView<String> ListViewSongLengths;
    
    private static MediaPlayer mediaPlayer;
    private static Media media;
    private static String source;
    
    ObservableList<String> songNames = FXCollections.observableArrayList();
    
    ObservableList<String> songLengths = FXCollections.observableArrayList();
    
    @FXML
    Slider volumeSlider;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Mappából file-ok kinyerése, zene nevek kiírása
        File[] songs = io.getFilesInFolder("songs");
        for (File f : songs)
        {
            songNames.add(f.getName());
        }
        ListViewSongNames.setItems(songNames);
        
        
        
        // Zene hosszak kinyerése, kiírása 
        int duration = 0;
        int minutesCount = 0;
        String lengthStr = "";
        for (File f : songs)
        {
            
            try {
              AudioFile audioFile = AudioFileIO.read(new File("songs/" + f.getName()));
              duration = audioFile.getAudioHeader().getTrackLength();
            } catch (Exception e) {
              e.printStackTrace();
            }
            while (duration > 59)
            {
                minutesCount++;
                duration -= 60;
            }
            if (duration < 10)
            {
                lengthStr = minutesCount + ":0" + duration;
            }
            else
            {
                lengthStr = minutesCount + ":" + duration;
            }
            
            songLengths.add(lengthStr);
            ListViewSongLengths.setItems(songLengths);
            
            lengthStr = "";
            duration = 0;
            minutesCount = 0;
        }
//        int duration = 0;
//        try {
//          AudioFile audioFile = AudioFileIO.read(new File("songs/RockAngel.mp3"));
//          duration = audioFile.getAudioHeader().getTrackLength();
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//        songLengths.add(Double.toString(duration));
//        ListViewSongLengths.setItems(songLengths);
        
        
        
        // Alapértelmezett 1. szám beállítása, csak hogy ne szálljon el nullexception-nel
//        source = new File("songs/" + songNames.get(0)).toURI().toString();
////                Media media = null;
//        media = new Media(source);
//        mediaPlayer = new MediaPlayer(media);
        
        
        
        ListViewSongNames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentPlaying.setText(newValue);
                
                source = new File("songs/" + newValue).toURI().toString();
//                Media media = null;
                media = new Media(source);
                mediaPlayer = new MediaPlayer(media);
                
                
                mediaPlayer.setVolume(0.2);
                volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                volumeSlider.valueProperty().addListener(new InvalidationListener() {

                    @Override
                    public void invalidated(Observable observable) {
                        mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                    }
                });
            }
        });
        
        
        
        // Zene beállítása lejátszásra
//        String source = new File("songs/RockAngel.mp3").toURI().toString();
//        Media media = null;
//        media = new Media(source);
//        mediaPlayer = new MediaPlayer(media);
        
        
        
        // Hangerőszabályzó beállítása
//        mediaPlayer.setVolume(0.2);
//        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
//        volumeSlider.valueProperty().addListener(new InvalidationListener() {
//            
//            @Override
//            public void invalidated(Observable observable) {
//                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
//            }
//        });
    }
    
    public void playButton(MouseEvent event)
    {
        mediaPlayer.play();
    }
    
    public void pauseButton(MouseEvent event)
    {
        mediaPlayer.pause();
    }
    
    public void stopButton(MouseEvent event)
    {
        mediaPlayer.stop();
    }
}
