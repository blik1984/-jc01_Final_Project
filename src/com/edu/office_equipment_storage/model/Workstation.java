package com.edu.office_equipment_storage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Workstation extends Asset {

	private double hardDiskSize;
	private double RAMSize;
	private Set<WorkstationConnectionType> workstationConnectionType;

	public Workstation() {
	}

	public Workstation(String id, String vendor, String model, Date warrantyDate, String note, AssetType assetType,
			double hardDiskSize, double RAMSize, Set<WorkstationConnectionType> workstationConnectionType) {
		super(id, vendor, model, warrantyDate, assetType, note);
		this.hardDiskSize = hardDiskSize;
		this.RAMSize = RAMSize;
		this.workstationConnectionType = workstationConnectionType;
	}

	@Override
	public String serialize() {

		StringBuilder sb = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sb = sb.append(getId()).append("|").append(getVendor()).append("|").append(getModel()).append("|")
				.append(dateFormat.format(getWarrantyDate())).append("|").append(getNote()).append("|")
				.append(getType()).append("|").append(hardDiskSize).append("|").append(RAMSize).append("|")
				.append(convertSetToString(workstationConnectionType));
		return sb.toString();
	}

	private static String convertSetToString(Set<WorkstationConnectionType> array) {
		StringBuilder result = new StringBuilder();
		for (WorkstationConnectionType type : array) {
			result.append(type).append("|");
		}
		return result.toString();
	}

	@Override
	public Workstation deserialize(String serialized) throws ParseException {
		String[] parts = serialized.split("\\|");
		if (parts.length < 8) {
			throw new IllegalArgumentException("Invalid serialized string format");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Set<WorkstationConnectionType> connections = new HashSet<>();
		String[] connectionParts = parts[7].split("\\|");
		for (String conn : connectionParts) {
			if (!conn.isEmpty()) {
				connections.add(WorkstationConnectionType.valueOf(conn));
			}
		}

		return new Workstation(parts[0], // id
				parts[1], // vendor
				parts[2], // model
				dateFormat.parse(parts[3]), // warrantyDate
				parts[4], // note
				AssetType.valueOf(parts[5]), // assetType
				Double.parseDouble(parts[6]), // hardDiskSize
				Double.parseDouble(parts[7]), // RAMSize
				connections // workstationConnectionType
		);
	}

	public double getHardDiskSize() {
		return hardDiskSize;
	}

	public void setHardDiskSize(double hardDiskSize) {
		this.hardDiskSize = hardDiskSize;
	}

	public double getRAMSize() {
		return RAMSize;
	}

	public void setRAMSize(double rAMSize) {
		RAMSize = rAMSize;
	}

	public Set<WorkstationConnectionType> getWorkstationConnectionType() {
		return workstationConnectionType;
	}

	public void setWorkstationConnectionType(Set<WorkstationConnectionType> workstationConnectionType) {
		this.workstationConnectionType = workstationConnectionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(RAMSize, hardDiskSize, workstationConnectionType);
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
		Workstation other = (Workstation) obj;
		return Double.doubleToLongBits(RAMSize) == Double.doubleToLongBits(other.RAMSize)
				&& Double.doubleToLongBits(hardDiskSize) == Double.doubleToLongBits(other.hardDiskSize)
				&& Objects.equals(workstationConnectionType, other.workstationConnectionType);
	}

	@Override
	public String toString() {
		return super.toString() + "[hardDiskSize=" + hardDiskSize + ", RAMSize=" + RAMSize + ", workstationConnectionType="
				+ workstationConnectionType + "]";
	}
}
