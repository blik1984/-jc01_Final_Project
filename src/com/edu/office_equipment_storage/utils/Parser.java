package com.edu.office_equipment_storage.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.office_equipment_storage.model.Asset;
import com.edu.office_equipment_storage.model.AssetStorage;
import com.edu.office_equipment_storage.model.AssetType;
import com.edu.office_equipment_storage.model.Laptop;
import com.edu.office_equipment_storage.model.Monitor;
import com.edu.office_equipment_storage.model.Printer;
import com.edu.office_equipment_storage.model.Switch;
import com.edu.office_equipment_storage.model.Workstation;

public class Parser {

	public static AssetStorage parseStorage(String serializedData) throws ParseException {

		String[] parts = serializedData.split("\\|\\|\\|");

		String name = parts[0];
		if (parts.length >= 2) {
			String[] assetLines = parts[1].split("\n");
			Map<String, Asset> assets = new HashMap<>();
			for (String line : assetLines) {
				if (line.trim().isEmpty())
					continue;
				String[] assetFields = line.split("\\|");
				if (assetFields.length < 6) {
					throw new ParseException("Invalid asset format - not enough fields", 0);
				}
				AssetType type = AssetType.valueOf(assetFields[5]);
				Asset asset = createAssetByType(type, line);
				assets.put(asset.getId(), asset);
			}
			return new AssetStorage(name, assets);
		}
		return new AssetStorage(name);

	}

	private static Asset createAssetByType(AssetType type, String serializedData) throws ParseException {
		switch (type) {
		case LAPTOP:
			return new Laptop().deserialize(serializedData);
		case MONITOR:
			return new Monitor().deserialize(serializedData);
		case PRINTER:
			return new Printer().deserialize(serializedData);
		case SWITCH:
			return new Switch().deserialize(serializedData);
		case WORKSTATION:
			return new Workstation().deserialize(serializedData);
		default:
			throw new ParseException("Unknown asset type: " + type, 0);
		}
	}

	public static List<String> parseCatalog(String data) {
		if (data == null || data.isEmpty()) {
			return new ArrayList<>();
		}
		String[] items = data.split("\n");
		return new ArrayList<>(Arrays.asList(items));
	}
}
