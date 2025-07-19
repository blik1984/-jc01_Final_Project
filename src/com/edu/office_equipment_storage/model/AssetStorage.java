package com.edu.office_equipment_storage.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AssetStorage {

	private String name;
	private Map<String, Asset> assetStorage;
	private Map<AssetType, Set<String>> catalogs;

	public AssetStorage() {
	}

	public AssetStorage(String name) {
		this.name = name;
		this.assetStorage = new HashMap<>();
		this.catalogs = new HashMap<>();
	}

	public AssetStorage(String name, Map<String, Asset> assetStorage) {
		this.name = name;
		this.assetStorage = assetStorage;
		this.catalogs = new HashMap<AssetType, Set<String>>();
		for (AssetType type : AssetType.values()) {
			catalogs.put(type, new HashSet<>());
		}
		for (Map.Entry<String, Asset> entry : assetStorage.entrySet()) {
			Asset asset = entry.getValue();
			catalogs.get(asset.getType()).add(asset.getId());
		}
	}

	public void addAsset(Map<String, Asset> storage) {
		for (Map.Entry<String, Asset> entry : storage.entrySet()) {
			addAsset(entry.getValue());
		}
	}
	/*
	 * public void addAsset(Asset asset) { assetStorage.put(asset.getId(), asset);
	 * catalogs.set(asset.getType()).add(asset.getId()); }
	 */

	public void addAsset(Asset asset) {
		if (asset != null) {
			assetStorage.put(asset.getId(), asset);
			catalogs.computeIfAbsent(asset.getType(), k -> new HashSet<>()).add(asset.getId());
		}
	}

	public void deleteAsset(String id) {
		catalogs.get(assetStorage.get(id).getType()).remove(id);
		assetStorage.remove(id);
	}

	public Map<String, Asset> getAssetStorage() {
		return new HashMap<String, Asset>(assetStorage);
	}

	public Asset getAsset(String id) {
		return assetStorage.get(id);
	}

	public void setAssetStorage(Map<String, Asset> assetStorage) {
		this.assetStorage = assetStorage;
		this.catalogs = new HashMap<AssetType, Set<String>>();
		for (AssetType type : AssetType.values()) {
			catalogs.put(type, new HashSet<>());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<AssetType, Set<String>> getCatalogs() {
		return catalogs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(assetStorage, catalogs, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssetStorage other = (AssetStorage) obj;
		return Objects.equals(assetStorage, other.assetStorage) && Objects.equals(catalogs, other.catalogs)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "AssetStorage [name=" + name + ", assetStorage=" + assetStorage + ", catalogs=" + catalogs + "]";
	}

}
