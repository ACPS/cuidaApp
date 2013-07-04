package cuidaApp.models;

public class Activo {

	private int id;
	private double lon;
	private double lat;
	private String adress;
	private String estado;
	private int posicion_icon;
	private String name_category;
	
	public Activo(int id, double lon, double lat, String adress, String estado) {
		super();
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		this.adress=adress;
		this.estado=estado;
	}
	
	public Activo(int id, double lon, double lat, String adress, String estado, int posicion, String name) {
		super();
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		this.adress=adress;
		this.estado=estado;
		this.posicion_icon=posicion;
		this.name_category=name;
	}
	public Activo() {
		super();
		this.id=0;
		this.lon=0;
		this.lat=0;
		this.adress="";
		this.estado="";
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
	
	public int getPosicion_icon() {
		return posicion_icon;
	}
	
	public void setPosicion_icon(int posicion_icon) {
		this.posicion_icon = posicion_icon;
	}
	
	public String getName_category() {
		return name_category;
	}
	
	public void setName_category(String name_category) {
		this.name_category = name_category;
	}
	
	
}
