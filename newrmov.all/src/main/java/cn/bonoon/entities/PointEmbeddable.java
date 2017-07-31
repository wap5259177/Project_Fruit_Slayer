package cn.bonoon.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PointEmbeddable {
	//经度
	@Column(name = "C_LONGITUDE")
	private double longitude;
	//纬度
	@Column(name = "C_LATITUDE")
	private double latitude;
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
