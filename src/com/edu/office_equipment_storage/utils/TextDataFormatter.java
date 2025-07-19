package com.edu.office_equipment_storage.utils;

import java.util.List;
import java.util.Map;

import com.edu.office_equipment_storage.model.Asset;
import com.edu.office_equipment_storage.model.AssetStorage;

public class TextDataFormatter {

	public static String convertStoragesToString(List<AssetStorage> storages) {

		StringBuilder result = new StringBuilder();
		for (AssetStorage storage : storages) {
			result.append(convertStorageToString(storage)).append("||||");
		}
		return result.toString();
	}

	public static String convertStorageToString(AssetStorage storage) {

		String name = storage.getName() + "|||";
		Map<String, Asset> assets = storage.getAssetStorage();
		StringBuilder assetsString = new StringBuilder(name);
		for (Asset asset : assets.values()) {
			assetsString = assetsString.append(asset.serialize()).append("\n");
		}
		return assetsString.toString();
	}

	public static String convertCatalogToString(List<String> storageCatalog) {
		StringBuffer toWrite = new StringBuffer();
		for (String s : storageCatalog) {
			toWrite = toWrite.append(s + "\n");
		}
		return toWrite.toString();
	}
}
