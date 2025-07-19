package com.edu.office_equipment_storage.service;

import java.util.List;

import com.edu.office_equipment_storage.model.AssetStorage;

public interface AssetStorageService {

	public void setStorage(AssetStorage storage) throws AssetStorageServiceException;

	public void deleteStorage(String name) throws AssetStorageServiceException;

	public List<AssetStorage> getAllStorages() throws AssetStorageServiceException;

	public AssetStorage getStorage(String nameStorage) throws AssetStorageServiceException;
	
	public List<String> getCatalogStorages() throws AssetStorageServiceException;

}
