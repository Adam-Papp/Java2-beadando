package java2.beadando;

import java.io.File;


public class Song {
    
    private File songFile;
    private String songName;
    private String songLength;

    
    
    // Konstruktor
    public Song(File songFile, String songName, String songLength) {
        this.songFile = songFile;
        this.songName = songName;
        this.songLength = songLength;
    }

    
    
    // Getterek
    public File getSongFile() {
        return songFile;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongLength() {
        return songLength;
    }

    
    
    // Setterek
    public void setSongFile(File songFile) {
        this.songFile = songFile;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }

    
    
    // toString
    @Override
    public String toString() {
        return "Song{" + "songFile=" + songFile + ", songName=" + songName + ", songLength=" + songLength + '}';
    }
}
