package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

public class listController {
	@FXML
	ListView<String> listView;
	public static ArrayList<Song> mainList = new ArrayList<Song>();
	private ObservableList<String> obsList;

	@FXML
	TextField songName, artist, year, albumName;
	
	@FXML
	Label songLabel,artistLabel, yearLabel,albumLabel,error;
	
	
	public void intitialize(){
		songName.setText(null);
		artist.setText(null);
		year.setText(null);
		albumName.setText(null);
		
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				System.out.println("Selected item: " + newValue);
			}
		});
	}
	
	
	
	@FXML
	public void addSong() throws IOException{
		
		if(songName.getText().trim().isEmpty() || artist.getText().trim().isEmpty()){
			Alert fail= new Alert(AlertType.ERROR);
	        fail.setHeaderText("Must Enter Song Name and Artist");
	        fail.setContentText("Both Fields Are Empty");
	        fail.showAndWait();
	        return;
		}
		else{
			for(Song s: mainList){
				if(s.getSong().equals(songName.getText().trim()) && s.getArtist().equals(artist.getText().trim())){
					Alert fail= new Alert(AlertType.ERROR);
			        fail.setHeaderText("Song and/or Artist Already Exist");
			        fail.setContentText("Cannot Have Duplicate Entries");
				    fail.showAndWait();
					return;
				}
			}
		}

		if(songName.getText() != null && artist.getText() != null && isInt(year.getText())==true){
			Song song = new Song(songName.getText(),artist.getText(), year.getText(),albumName.getText());
			
			confirmBox confirm = new confirmBox();
			
			if(confirm.display("Would You Like To Add Song?") == true){
				mainList.add(song);
				obsList = FXCollections.observableArrayList();
				addToList(mainList);
				songName.setText("");
				artist.setText("");
				year.setText("");
				albumName.setText("");
			
			}
			else{
				return;
			}
		}
		else if(isInt(year.getText())==false){
			Alert fail= new Alert(AlertType.ERROR);
	        fail.setHeaderText("Year is not a valid!");
	        fail.setContentText("Please put the year as a number.");
		    fail.showAndWait();
			year.setText("");

		}
		toEdit(true);
	}

	public void addToList(ArrayList<Song> mainList){
		int pos1 = 0;
		int pos2 = 0;
		for(int i = 0; i < mainList.size(); i++) {
			obsList.add(mainList.get(i).song);
			pos1 = obsList.indexOf(mainList.get(i).song);
			FXCollections.sort(obsList);
			pos2 = obsList.indexOf(mainList.get(i).song);

		}
		switchPos(pos1,pos2);
		listView.setItems(obsList);
		listView.getSelectionModel().select(pos2);
		if(listView.getSelectionModel().isSelected(pos2)){
			try {
				view2();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

    
	@FXML
	public void editSong() throws IOException{
		if(listView.getSelectionModel().isEmpty() || songName.getText().isEmpty()){
			System.out.println("Please Select a Song!");
			return;
		}
		else if(isInt(year.getText())==false){
			Alert fail= new Alert(AlertType.ERROR);
	        fail.setHeaderText("Year is not a valid!");
	        fail.setContentText("Please put the year as a number.");
		    fail.showAndWait();
			year.setText("");
			return;
		}
		else{
			int x = listView.getSelectionModel().getSelectedIndex();
			mainList.get(x).setSong(songName.getText());
			mainList.get(x).setArtist(artist.getText());
			mainList.get(x).setYear(year.getText());	
			mainList.get(x).setAlbum(albumName.getText());
			
			obsList.set(x, songName.getText());
			int pos1 = listView.getSelectionModel().getSelectedIndex();
			FXCollections.sort(obsList);
			int pos2 = listView.getSelectionModel().getSelectedIndex();
	
			switchPos(pos1,pos2);
			listView.getSelectionModel().select(pos2);
			if(listView.getSelectionModel().isSelected(pos2)){
				try {
					view2();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		songName.setText("");
		artist.setText("");
		year.setText("");
		albumName.setText("");
		FXCollections.sort(obsList);
	}
	public void view2() throws IOException{
		if(mainList.size()==0){
			return;
		}
		songLabel.setText(listView.getSelectionModel().getSelectedItem());
		artistLabel.setText(mainList.get(listView.getSelectionModel().getSelectedIndex()).getArtist());
		yearLabel.setText(mainList.get(listView.getSelectionModel().getSelectedIndex()).getYear());
		albumLabel.setText(mainList.get(listView.getSelectionModel().getSelectedIndex()).getAlbum());

	}
	@FXML
	public void view() throws IOException{
		if(mainList.size()==0){
			return;
		}
		view2();
		songName.setText(listView.getSelectionModel().getSelectedItem());
		artist.setText(mainList.get(listView.getSelectionModel().getSelectedIndex()).getArtist());
		year.setText(mainList.get(listView.getSelectionModel().getSelectedIndex()).getYear());
		albumName.setText(mainList.get(listView.getSelectionModel().getSelectedIndex()).getAlbum());
	}
	@FXML
	public void deleteSong() throws IOException{
		confirmBox confirm = new confirmBox();
		int x = 0;
		if(confirm.display("Would You Like To Delete?") == true){
			x = (listView.getSelectionModel().getSelectedIndex());
			obsList.remove((listView.getSelectionModel().getSelectedIndex()));
			mainList.remove(x);
			FXCollections.sort(obsList);
		}
		
		if(x-1 == -1){
			listView.getSelectionModel().select(x);
		}
		else{
			listView.getSelectionModel().select(x-1);
		}
		if(!listView.getSelectionModel().isEmpty())
		view2();
		else{
			songLabel.setText("");
			artistLabel.setText("");
			yearLabel.setText("");
			albumLabel.setText("");
			
		}
		songName.setText("");
		artist.setText("");
		year.setText("");
		albumName.setText("");
	}
	
	
	@FXML
	public void toEdit(Boolean h){
		songName.setEditable(h);
		artist.setEditable(h);
		year.setEditable(h);
		albumName.setEditable(h);
	}
	
	public Boolean isInt(String s){
		boolean q = true;
		for(int i = 0;i<s.length()-1;i++){
			char w = s.charAt(i);
			if(!Character.isDigit(w)){
				q = false;
			}			
		}
		return q;
	}

	public void switchPos(int pos1, int pos2){
		String temp = "";
		temp = mainList.get(pos1).getYear();
		mainList.get(pos1).setYear(mainList.get(pos2).getYear());
		mainList.get(pos2).setYear(temp);

		temp = mainList.get(pos1).getArtist();
		mainList.get(pos1).setArtist(mainList.get(pos2).getArtist());
		mainList.get(pos2).setArtist(temp);

		temp = mainList.get(pos1).getAlbum();
		mainList.get(pos1).setAlbum(mainList.get(pos2).getAlbum());
		mainList.get(pos2).setAlbum(temp);
	}
}
