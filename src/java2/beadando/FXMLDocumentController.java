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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;



public class FXMLDocumentController implements Initializable {
    
    @FXML
    public Label currentPlaying;
    
    @FXML
    private TableView<Song> TableViewSongs = new TableView<Song>();
    
    @FXML
    private TableColumn<Song, String> nameColumn = new TableColumn<>();
    
    @FXML
    private TableColumn<Song, String> lengthColumn = new TableColumn<>();
    
    @FXML
    private TableColumn<Song, Integer> playCountColumn = new TableColumn<>();
    
    private static MediaPlayer mediaPlayer;
    private static Media media;
    private static String source;
    
    ObservableList<String> songNames = FXCollections.observableArrayList();
    
    ObservableList<String> songLengths = FXCollections.observableArrayList();
    
    @FXML
    Slider volumeSlider;
    
    //PLAYLIST

    
    
    ObservableList<Song> songs = FXCollections.observableArrayList();
    
    boolean isPlaying;
    
    @FXML
    private TableView<playList> TableViewPlayList = new TableView<playList>();
    private TableColumn<playList, String> playListNameColumn = new TableColumn<>();
    private TableColumn<playList, String> playListSizeColumn = new TableColumn<>();
    ObservableList<playList> playLists = FXCollections.observableArrayList();

    public TextField playListField;
 


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        isPlaying = false;
        
        
        
        // Mappából file-ok kinyerése
        File[] songFiles = io.getFilesInFolder("songs");
        
        
        
        // Zene hosszak kinyerése, kiírása 
        int duration = 0;
        int minutesCount = 0;
        String lengthStr = "";
        for (File f : songFiles)
        {
            songNames.add(f.getName().substring(0, f.getName().length()-4));
            
            
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
            
            songs.add(new Song(f, f.getName().substring(0, f.getName().length()-4), lengthStr));
            
            lengthStr = "";
            duration = 0;
            minutesCount = 0;
        }
        
        
        for (Song s : songs)
        {
            System.out.println(s.toString());
        }
        
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("songName"));
        
        
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("songLength"));
        
        
        playCountColumn.setCellValueFactory(new PropertyValueFactory<>("playCount"));
        
        TableViewSongs.setItems(songs);
        
        
        
        //  Cella szerinti listener
//        TableViewSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//        @Override
//        public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
//            //Check whether item is selected and set value of selected item to Label
//            if(TableViewSongs.getSelectionModel().getSelectedItem() != null) 
//            {    
//               TableViewSelectionModel selectionModel = TableViewSongs.getSelectionModel();
//               ObservableList selectedCells = selectionModel.getSelectedCells();
//               TablePosition tablePosition = (TablePosition) selectedCells.get(0);
//               Object val = tablePosition.getTableColumn().getCellData(newValue);
//               System.out.println("Selected Value" + val);
//             }
//             }
//        });
        
        
        
        // Real time listview figyelő
        TableViewSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                if (isPlaying)
                {
                    mediaPlayer.stop();
                }
                
                isPlaying = false;
                
                Song tempSong = (Song) observable.getValue();
                
                playSong(tempSong);
            }
        });
        
        
        
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
        playListNameColumn.setCellValueFactory(new PropertyValueFactory<>("playListName"));
        playListSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        TableViewPlayList.setItems(playLists);
    }
    
    
    
    public void playSong(Song tempSong)
    {
        source = null;
        
        currentPlaying.setText(tempSong.getSongName());
                
        if (!tempSong.getSongName().endsWith(".mp3"))
            tempSong.setSongName(tempSong.getSongName() + ".mp3");
                
        source = new File("songs/" + tempSong.getSongName()).toURI().toString();
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
           
        
        
        if (!isPlaying)
        {
            mediaPlayer.play();
            isPlaying = true;

            String replace = media.getSource().replace("%20", " ");

            for (Song s : songs)
            {
    //            if (s.getSongName().equals(replace.substring(0, replace.length()-4)))
                if (s.getSongFile().getName().equals(replace))
                {
                    System.out.println(replace);
                    int playCount = s.getPlayCount();
                    s.setPlayCount(++playCount);
                    System.out.println(s.toString());
                }
            }
        }
    }
        
    
    public void playButton(MouseEvent event)
    {
        if (!isPlaying)
            mediaPlayer.play();
    }
    
    public void pauseButton(MouseEvent event)
    {
        mediaPlayer.pause();
        isPlaying = false;
    }
    
    public void stopButton(MouseEvent event)
    {
        mediaPlayer.stop();
        isPlaying = false;
    }
    
    public void addNewList(MouseEvent event)
    {
        if(!playListField.getText().equals("")){
              
            playLists.add(new playList(playListField.getText()));
            TableViewPlayList.setItems(playLists);
//              playListView.getItems().add(playListField.getText());
              playListField.clear();
              
          }  
    }
}
