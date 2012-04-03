package abel.wallpaper.global;

public class WallpaperDataConfig {
	private String activado;
	private String paquete;
	private String firsTime;
	
	public WallpaperDataConfig(){}
	
	public WallpaperDataConfig(String activado, String paquete, String firsTime) {
		super();
		this.activado = activado;
		this.paquete = paquete;
		this.firsTime = firsTime;
	}
	public String getActivado() {
		return activado;
	}
	public void setActivado(String activado) {
		this.activado = activado;
	}
	public String getPaquete() {
		return paquete;
	}
	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}
	public String getFirsTime() {
		return firsTime;
	}
	public void setFirsTime(String firsTime) {
		this.firsTime = firsTime;
	}
	
	

}
