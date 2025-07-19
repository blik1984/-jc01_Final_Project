package com.edu.office_equipment_storage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Printer extends Asset {

	private PrinterType printerType;
	private PrinterColorSupport colorSupport;
	private Set<PrinterConnectionType> connectionType;

	public Printer() {
	}

	public Printer(String id, String vendor, String model, Date warrantyDate, String note, AssetType assetType,
			PrinterType printerType, Set<PrinterConnectionType> connectionType, PrinterColorSupport colorSupport) {
		super(id, vendor, model, warrantyDate, assetType, note);
		this.printerType = printerType;
		this.colorSupport = colorSupport;
		this.connectionType = connectionType;

	}

	@Override
	public String serialize() {

		StringBuilder sb = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sb = sb.append(getId()).append("|").append(getVendor()).append("|").append(getModel()).append("|")
				.append(dateFormat.format(getWarrantyDate())).append("|").append(getNote()).append("|")
				.append(getType()).append("|").append(printerType).append("|").append(colorSupport).append("||")
				.append(convertSetToString(connectionType));
		return sb.toString();
	}

	private static String convertSetToString(Set<PrinterConnectionType> array) {
		StringBuilder result = new StringBuilder();
		for (PrinterConnectionType type : array) {
			result.append(type).append("|");
		}
		return result.toString();
	}

	@Override
	public Printer deserialize(String serialized) throws ParseException {
		// Сначала разделяем по двойной черте ||
		String[] mainParts = serialized.split("\\|\\|");
		if (mainParts.length != 2) {
			throw new IllegalArgumentException("Invalid serialized format - missing double pipe separator");
		}

		String[] assetParts = mainParts[0].split("\\|");
		if (assetParts.length < 8) {
			throw new IllegalArgumentException("Not enough elements in main part");
		}

		String[] connectionParts = mainParts[1].split("\\|");
		Set<PrinterConnectionType> connections = new HashSet<>();
		for (String conn : connectionParts) {
			if (!conn.isEmpty()) {
				connections.add(PrinterConnectionType.valueOf(conn));
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return new Printer(assetParts[0], // id
				assetParts[1], // vendor
				assetParts[2], // model
				dateFormat.parse(assetParts[3]), // warrantyDate
				assetParts[4], // note
				AssetType.valueOf(assetParts[5]), // assetType
				PrinterType.valueOf(assetParts[6]), // printerType
				connections, // connectionType
				PrinterColorSupport.valueOf(assetParts[7]) // colorSupport
		);
	}

	public PrinterType getPrinterType() {
		return printerType;
	}

	public void setType(PrinterType printerType) {
		this.printerType = printerType;
	}

	public Set<PrinterConnectionType> getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(Set<PrinterConnectionType> connectionType) {
		this.connectionType = connectionType;
	}

	public PrinterColorSupport getColorSupport() {
		return colorSupport;
	}

	public void setColorSupport(PrinterColorSupport colorSupport) {
		this.colorSupport = colorSupport;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(colorSupport, connectionType, printerType);
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
		Printer other = (Printer) obj;
		return colorSupport == other.colorSupport && connectionType == other.connectionType
				&& printerType == other.printerType;
	}

	@Override
	public String toString() {
		return super.toString() + "[printerType=" + printerType + ", connectionType=" + connectionType
				+ ", colorSupport=" + colorSupport + "]";
	}

}
