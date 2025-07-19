package com.edu.office_equipment_storage.service.impl;

import java.util.List;

import com.edu.office_equipment_storage.model.AssetStorage;
import com.edu.office_equipment_storage.repostory.StorageRepository;
import com.edu.office_equipment_storage.repostory.StorageRepositoryException;
import com.edu.office_equipment_storage.repostory.StorageRepositoryProvider;
import com.edu.office_equipment_storage.service.AssetStorageService;
import com.edu.office_equipment_storage.service.AssetStorageServiceException;

public class AssetStorageServiceOne implements AssetStorageService {

	private final StorageRepository storageRepository;

	{
		StorageRepositoryProvider provider = StorageRepositoryProvider.getInstance();
		storageRepository = provider.getStorageRepository();
	}

	public AssetStorageServiceOne() {
	}

	@Override
	public void setStorage(AssetStorage storage) throws AssetStorageServiceException {

		try {
			storageRepository.saveStorage(storage);
		} catch (StorageRepositoryException e) {
			throw new AssetStorageServiceException(e.getMessage());
		}
	}

	@Override
	public void deleteStorage(String name) throws AssetStorageServiceException {
		try {
			storageRepository.deleteStorage(name);
		} catch (StorageRepositoryException e) {
			throw new AssetStorageServiceException(e.getMessage());
		}
	}

	@Override
	public List<AssetStorage> getAllStorages() throws AssetStorageServiceException {
		List<AssetStorage> allStorages;
		try {
			allStorages = storageRepository.getAllStorages();
		} catch (StorageRepositoryException e) {
			throw new AssetStorageServiceException(e.getMessage());
		}
		return allStorages;
	}

	@Override
	public AssetStorage getStorage(String nameStorage) throws AssetStorageServiceException {

		try {
			return(storageRepository.getStorage(nameStorage));
		} catch (StorageRepositoryException e) {
			throw new AssetStorageServiceException(e.getMessage());
		}
	}

	@Override
	public List<String> getCatalogStorages() throws AssetStorageServiceException {
		List<String> catalog;
		try {
			catalog = storageRepository.getCatalog();
		} catch (StorageRepositoryException e) {
			throw new AssetStorageServiceException(e.getMessage());
		}
		return catalog;
	}
}
