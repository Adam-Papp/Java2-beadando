package java2.beadando;

import java.io.File;


public class Song {
    
    private File songFile;
    private String songName;
    private String songLength;
    private int playCount;
    
    
    
    // Konstruktor
    public Song(File songFile, String songName, String songLength) {
        this.songFile = songFile;
        this.songName = songName;
        this.songLength = songLength;
        
        playCount = 0;
    }

    public Song(Song s) {
        this.songFile = s.songFile;
        this.songName = s.songName;
        this.songLength = s.songLength;
        
        playCount = 0;
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

    public int getPlayCount() {
        return playCount;
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

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    
    
    // toString
    @Override
    public String toString() {
        return "Song{" + "songFile=" + songFile + ", songName=" + songName + ", songLength=" + songLength + ", playCount=" + playCount + '}';
    }
    
}
