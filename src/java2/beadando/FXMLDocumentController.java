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
    
    
    ObservableList<Song> songs = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Mappából file-ok kinyerése
        File[] songFiles = io.getFilesInFolder("songs");
        
        
        
        // Zene hosszak kinyerése, kiírása 
        int duration = 0;
        int minutesCount = 0;
        String lengthStr = "";
        for (File f : songFiles)
        {
            songNames.add(f.getName());
            
            
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
            
            songs.add(new Song(f, f.getName(), lengthStr));
            
            lengthStr = "";
            duration = 0;
            minutesCount = 0;
        }
        
        ListViewSongNames.setItems(songNames);
        ListViewSongLengths.setItems(songLengths);
        
        
        
        // Real time listview figyelő
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
        
        
        
        for (Song s : songs)
        {
            System.out.println(s.toString());
        }
        
        
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
