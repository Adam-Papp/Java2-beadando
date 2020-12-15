/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2.beadando;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class playList {

    private String playListName;
    private int size;
    private ObservableList<Song> songs = FXCollections.observableArrayList();


    public playList(String playListName) {
        this.playListName = playListName;
        size = 0;
    }

    public String getPlayListName() {
        return playListName;
    }

    public int getSize() {
        return size;
    }

    public ObservableList getSongs() {
        return songs;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public void setSongs(ObservableList songs) {
        this.songs = songs;
        
        this.size = songs.size();
    }

    @Override
    public String toString() {
        return "playList{" + "playListName=" + playListName + ", size=" + size + ", songs=" + songs + '}';
    }
}
