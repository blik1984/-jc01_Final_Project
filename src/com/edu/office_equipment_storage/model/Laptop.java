package com.edu.office_equipment_storage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Laptop extends Asset {

	private double diagonal;
	private double hardDiskSize;
	private double RAMSize;
	private Set<LaptopConnectionType> laptopConnectionType;

	public Laptop() {
	}

	public Laptop(String id, String vendor, String model, Date warrantyDate, String note, AssetType assetType,
			double diagonal, double hardDiskSize, double RAMSize, Set<LaptopConnectionType> laptopConnectionType) {
		super(id, vendor, model, warrantyDate, assetType, note);
		this.diagonal = diagonal;
		this.hardDiskSize = hardDiskSize;
		this.RAMSize = RAMSize;
		this.laptopConnectionType = laptopConnectionType;
	}

	public Laptop deserialize(String asset) throws ParseException {
		String[] parts = asset.split("\\|\\|");
		if (parts.length < 2) {
			throw new IllegalArgumentException("Invalid serialized format");
		}

		String[] arguments = parts[0].split("\\|");
		if (arguments.length < 9) {
			throw new IllegalArgumentException("Not enough arguments");
		}

		Set<LaptopConnectionType> connectionType = new HashSet<>();
		if (!parts[1].isEmpty()) {
			for (String s : parts[1].split("\\|")) {
				if (!s.isEmpty()) {
					connectionType.add(LaptopConnectionType.valueOf(s));
				}
			}
		}

		return new Laptop(arguments[0], // id
				arguments[1], // vendor
				arguments[2], // model
				new SimpleDateFormat("yyyy-MM-dd").parse(arguments[3]), // warrantyDate
				arguments[4], // note
				AssetType.valueOf(arguments[5]), // assetType
				Double.parseDouble(arguments[6]), // diagonal
				Double.parseDouble(arguments[7]), // hardDiskSize
				Double.parseDouble(arguments[8]), // RAMSize
				connectionType);
	}

	@Override
	public String serialize() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sb = new StringBuilder();
		sb = sb.append(getId()).append("|").append(getVendor()).append("|").append(getModel()).append("|")
				.append(dateFormat.format(getWarrantyDate())).append("|").append(getNote()).append("|")
				.append(getType()).append("|").append(diagonal).append("|").append(hardDiskSize).append("|")
				.append(RAMSize).append("||").append(convertSetToString(laptopConnectionType));
		return sb.toString();
	}

	private static String convertSetToString(Set<LaptopConnectionType> array) {
		StringBuilder result = new StringBuilder();
		for (LaptopConnectionType type : array) {
			result.append(type).append("|");
		}
		return result.toString();
	}

	public double getDiagonal() {
		return diagonal;
	}

	public void setDiagonal(double diagonal) {
		this.diagonal = diagonal;
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

	public Set<LaptopConnectionType> getLaptopConnectionType() {
		return laptopConnectionType;
	}

	public void setLaptopConnectionType(Set<LaptopConnectionType> laptopConnectionType) {
		this.laptopConnectionType = laptopConnectionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(RAMSize, diagonal, hardDiskSize, laptopConnectionType);
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
		Laptop other = (Laptop) obj;
		return Double.doubleToLongBits(RAMSize) == Double.doubleToLongBits(other.RAMSize)
				&& Double.doubleToLongBits(diagonal) == Double.doubleToLongBits(other.diagonal)
				&& Double.doubleToLongBits(hardDiskSize) == Double.doubleToLongBits(other.hardDiskSize)
				&& Objects.equals(laptopConnectionType, other.laptopConnectionType);
	}

	@Override
	public String toString() {
		return super.toString() +"[diagonal=" + diagonal + ", hardDiskSize=" + hardDiskSize + ", RAMSize=" + RAMSize
				+ ", laptopConnectionType=" + laptopConnectionType + "]";
	}
}
