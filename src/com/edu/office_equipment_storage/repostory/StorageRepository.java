package com.edu.office_equipment_storage.repostory;

import java.util.List;

import com.edu.office_equipment_storage.model.AssetStorage;

public interface StorageRepository {
	
	void deleteStorage(String name) throws StorageRepositoryException;

	void saveStorage(AssetStorage storage) throws StorageRepositoryException;

	AssetStorage getStorage(String name) throws StorageRepositoryException;

	List<AssetStorage> getAllStorages() throws StorageRepositoryException;

	List<String> getCatalog() throws StorageRepositoryException;

}
