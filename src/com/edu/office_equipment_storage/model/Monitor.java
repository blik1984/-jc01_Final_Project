package com.edu.office_equipment_storage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Monitor extends Asset {

	private double monitorDiagonal;
	private MonitorConnectionType connectionType;

	public Monitor() {
	}

	public Monitor(String id, String vendor, String model, Date warrantyDate, String note, AssetType assetType,
			double monitorDiagonal, MonitorConnectionType connectionType) {
		super(id, vendor, model, warrantyDate, assetType, note);
		this.monitorDiagonal = monitorDiagonal;
		this.connectionType = connectionType;
	}

	@Override
	public String serialize() {

		StringBuilder sb = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sb = sb.append(getId()).append("|").append(getVendor()).append("|").append(getModel()).append("|")
				.append(dateFormat.format(getWarrantyDate())).append("|").append(getNote()).append("|").append(getType()).append("|")
				.append(monitorDiagonal).append("|").append(connectionType);
		return sb.toString();
	}

	@Override
	public Monitor deserialize(String serialized) throws ParseException {
		String[] parts = serialized.split("\\|");
		if (parts.length < 8) {
			throw new IllegalArgumentException("Invalid serialized string format");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return new Monitor(parts[0], // id
				parts[1], // vendor
				parts[2], // model
				dateFormat.parse(parts[3]), // warrantyDate
				parts[4], // note
				AssetType.valueOf(parts[5]), // assetType
				Double.parseDouble(parts[6]), // monitorDiagonal
				MonitorConnectionType.valueOf(parts[7]) // connectionType
		);
	}

	public double getMonitorDiagonal() {
		return monitorDiagonal;
	}

	public void setMonitorDiagonal(double monitorDiagonal) {
		this.monitorDiagonal = monitorDiagonal;
	}

	public MonitorConnectionType getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(MonitorConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(connectionType, monitorDiagonal);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Monitor other = (Monitor) obj;
		return connectionType == other.connectionType
				&& Double.doubleToLongBits(monitorDiagonal) == Double.doubleToLongBits(other.monitorDiagonal);
	}

	@Override
	public String toString() {
		return super.toString() +"[monitorDiagonal=" + monitorDiagonal + ", connectionType=" + connectionType + "]";
	}
}
