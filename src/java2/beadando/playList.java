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
    private ObservableList songs;


    public playList(String playListName) {
        this.playListName = playListName;
        size = 0;
//        if (songs == null) {
//         this.songs = FXCollections.observableArrayList();
//      } else {
//         this.songs = songs;
//      }
    }
//   public playList(ObservableList list) {
//      if (list == null) {
//         this.songs = FXCollections.observableArrayList();
//      } else {
//         this.songs = list;
//      }
//
//   }

   public ObservableList getPersonTableList() {
      return this.songs;
   } 

   public void setPersonTableList(ObservableList list) {
      this.songs = list;
   }
}
