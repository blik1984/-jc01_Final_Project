package com.edu.office_equipment_storage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Switch extends Asset {

	private int numberLanPorts;
	private Set<WiFiRange> WiFiRanges;

	public Switch() {
	}

	public Switch(String id, String vendor, String model, Date warrantyDate, String note, AssetType assetType,
			int numberLanPorts, Set<WiFiRange> WiFiRanges) {
		super(id, vendor, model, warrantyDate, assetType, note);
		this.numberLanPorts = numberLanPorts;
		this.WiFiRanges = WiFiRanges;
	}

	@Override
	public String serialize() {

		StringBuilder sb = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sb = sb.append(getId()).append("|").append(getVendor()).append("|").append(getModel()).append("|")
				.append(dateFormat.format(getWarrantyDate())).append("|").append(getNote()).append("|")
				.append(getType()).append("|").append(numberLanPorts).append("||")
				.append(convertSetToString(WiFiRanges));
		return sb.toString();
	}

	private static String convertSetToString(Set<WiFiRange> array) {
		StringBuilder result = new StringBuilder();
		for (WiFiRange type : array) {
			result.append(type).append("|");
		}
		return result.toString();
	}
	
	@Override
	public Switch deserialize(String serialized) throws ParseException {
		String[] mainParts = serialized.split("\\|\\|");
		if (mainParts.length != 2) {
			throw new IllegalArgumentException("Invalid format - missing '||' separator");
		}

		String[] assetParts = mainParts[0].split("\\|");
		if (assetParts.length < 7) {
			throw new IllegalArgumentException("Not enough elements in main part");
		}

		Set<WiFiRange> wifiRanges = new HashSet<>();
		if (!mainParts[1].isEmpty()) {
			String[] wifiParts = mainParts[1].split("\\|");
			for (String wifi : wifiParts) {
				if (!wifi.isEmpty()) {
					wifiRanges.add(WiFiRange.valueOf(wifi));
				}
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return new Switch(assetParts[0], // id
				assetParts[1], // vendor
				assetParts[2], // model
				dateFormat.parse(assetParts[3]), // warrantyDate
				assetParts[4], // note
				AssetType.valueOf(assetParts[5]), // assetType
				Integer.parseInt(assetParts[6]), // numberLanPorts
				wifiRanges // WiFiRanges
		);
	}

	public Switch(Set<WiFiRange> WiFiRanges) {
		this.numberLanPorts = 0;
		this.WiFiRanges = WiFiRanges;
	}

	public Switch(int numberLanPorts) {
		this.numberLanPorts = numberLanPorts;
		this.WiFiRanges = null;
	}

	public int getNumberLanPorts() {
		return numberLanPorts;
	}

	public void setNumberLanPorts(int numberLanPorts) {
		this.numberLanPorts = numberLanPorts;
	}

	public Set<WiFiRange> getWiFiRanges() {
		return WiFiRanges;
	}

	public void setWiFiRanges(Set<WiFiRange> wiFiRanges) {
		WiFiRanges = wiFiRanges;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(WiFiRanges, numberLanPorts);
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
		Switch other = (Switch) obj;
		return Objects.equals(WiFiRanges, other.WiFiRanges) && numberLanPorts == other.numberLanPorts;
	}

	@Override
	public String toString() {
		return super.toString() + "[numberLanPorts=" + numberLanPorts + ", WiFiRanges=" + WiFiRanges + "]";
	}
}
