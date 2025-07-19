package com.edu.office_equipment_storage.model;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public abstract class Asset {

	private String id = "1";
	private String vendor;
	private String model;
	private Date warrantyDate;
	private String note;
	private AssetType type;

	public Asset() {
	}

	public Asset(String id, String vendor, String model, Date warrantyDate, AssetType type) {
		this.id = id;
		this.vendor = vendor;
		this.model = model;
		this.warrantyDate = warrantyDate;
		this.type = type;
		this.note = null;

	}
	public Asset(String id, String vendor, String model, Date warrantyDate, AssetType type, String note) {
		this.id = id;
		this.vendor = vendor;
		this.model = model;
		this.warrantyDate = warrantyDate;
		this.type = type;
		this.note = note;
	}
	
	public abstract String serialize();
	public abstract Asset deserialize(String asset) throws ParseException;
	

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getId() {
		return id;
	}

	public AssetType getType() {
		return type;
	}

	public void setType(AssetType type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, model, note, type, vendor, warrantyDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asset other = (Asset) obj;
		return Objects.equals(id, other.id) && Objects.equals(model, other.model) && Objects.equals(note, other.note)
				&& type == other.type && Objects.equals(vendor, other.vendor)
				&& Objects.equals(warrantyDate, other.warrantyDate);
	}

	@Override
	public String toString() {
		return "Asset [id=" + id + ", vendor=" + vendor + ", model=" + model + ", warrantyDate=" + warrantyDate
				+ ", note=" + note + ", type=" + type + "]";
	}

}
