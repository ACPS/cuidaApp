package android.bitmapfun.provider;

public class Categoria {

	private String id;
	private String name;
	private String url;
	private String icon_normal;
	private String icon_reported;
	private String icon_attended;
	private String icon_repaired;
	
	public Categoria(String id,String name, String url, String icon_normal, String icon_reported,String  icon_attended, String icon_repaired){
		this.id=id;
		this.name=name;
		this.url=url;
		this.icon_normal=icon_normal;
		this.icon_repaired=icon_repaired;
		this.icon_attended=icon_attended;
		this.icon_repaired=icon_repaired;
	}
	
	public Categoria(){
		this.id="";
		this.name="";
		this.url="";
		this.icon_normal="";
		this.icon_repaired="";
		this.icon_attended="";
		this.icon_repaired="";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon_normal() {
		return icon_normal;
	}

	public void setIcon_normal(String icon_normal) {
		this.icon_normal = icon_normal;
	}

	public String getIcon_reported() {
		return icon_reported;
	}

	public void setIcon_reported(String icon_reported) {
		this.icon_reported = icon_reported;
	}

	public String getIcon_attended() {
		return icon_attended;
	}

	public void setIcon_attended(String icon_attended) {
		this.icon_attended = icon_attended;
	}

	public String getIcon_repaired() {
		return icon_repaired;
	}

	public void setIcon_repaired(String icon_repaired) {
		this.icon_repaired = icon_repaired;
	}
	
	
	
	
}
