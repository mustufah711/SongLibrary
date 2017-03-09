package view;

public class Song {
	
	String song; 
	String artist;
	String year;
	String album;
	
	public Song(String song, String artist, String year, String album){
		this.song = song;
		this.artist = artist;
		this.year = year;
		this.album = album;
	}
	
	public String getSong(){
		return song;
	}
	
	public String getArtist(){
		
		return artist;
	}
	
	public String getYear(){
		return year;
	}
	
	public String getAlbum(){
		return album;
	}
	
	public void setSong(String songs){
		this.song = songs;
	}
	
	public void setArtist(String artists){
		this.artist = artists;
	}
	
	public void setYear(String years){
		this.year=years;
	}
	
	public void setAlbum(String albums){
		this.album = albums;
	}
}