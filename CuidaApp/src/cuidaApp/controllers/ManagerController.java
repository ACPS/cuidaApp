package cuidaApp.controllers;



import java.util.LinkedList;
import java.util.List;

import cuidaApp.models.Activo;

import android.bitmapfun.provider.Categoria;
import android.graphics.Bitmap;

/**
 * @author Luster
 * 
 */
public class ManagerController {

	public final String PhoneModel = android.os.Build.MODEL;
	public final String AndroidVersion = android.os.Build.VERSION.RELEASE;
	private Categoria selectedCategory;
	private Activo selectedActivo;
	private Bitmap imageTemp;
	private List<Bitmap> images;
	private double latitude;
	private double longitude;
	private static ManagerController instance;
	private String description;
	private boolean responseEvent;
	private boolean previewing;
	private String city;
	

	public static ManagerController getInstance() {
		if (instance == null) {
			instance = new ManagerController();
		}
		return instance;
	}

	private ManagerController() {
		this.images = new LinkedList<Bitmap>();
		this.selectedCategory = null;
		this.previewing = false;
		this.longitude = 0;
		this.latitude = 0;
		this.city="";
	}

	public void reset() {
		//this.longitude = 0;
		//this.latitude = 0;
		this.clearImages();
		this.previewing = false;
		this.selectedCategory = null;
		this.description = "";
		imageTemp=null;
		this.city="";
	}

	public List<Bitmap> getImages() {
		return images;
	}

	public void addImage() {
		if (imageTemp != null)
			this.images.add(this.imageTemp);
		//imageTemp = null;
	}

	public Bitmap getLastImage() {
		return this.images.get(this.images.size() - 1);
	}

	public void clearImages() {
		  for(Bitmap image : this.images){
		   image.recycle();
		  }
		  this.images.clear();
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	
	public Categoria getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Categoria selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public boolean isResponseEvent() {
		return responseEvent;
	}

	public void setResponseEvent(boolean responseEvent) {
		this.responseEvent = responseEvent;
	}

	/**
	 * @return the imageTemp
	 */
	public Bitmap getImageTemp() {
		return imageTemp;
	}

	/**
	 * @param imageTemp
	 *            the imageTemp to set
	 */
	public void setImageTemp(Bitmap imageTemp) {
		this.imageTemp = imageTemp;
	}

	/**
	 * @return the previewing
	 */
	public boolean isPreviewing() {
		return previewing;
	}

	/**
	 * @param previewing
	 *            the previewing to set
	 */
	public void setPreviewing(boolean previewing) {
		this.previewing = previewing;
	}

	public Activo getSelectedActivo() {
		return selectedActivo;
	}
	
	public void setSelectedActivo(Activo selectedActivo) {
		this.selectedActivo = selectedActivo;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
}
