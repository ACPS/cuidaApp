package cuidaApp.models;

public class Activo {

	private int id;
	private double lon;
	private double lat;
	private String adress;
	private String estado;
	
	public Activo(int id, double lon, double lat, String adress, String estado) {
		super();
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		this.adress=adress;
		this.estado=estado;
	}
	public Activo() {
		super();
		this.id=0;
		this.lon=0;
		this.lat=0;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
	
	
}
