package com.edu.office_equipment_storage.repostory;

import com.edu.office_equipment_storage.repostory.impl.FileStorageRepository;

public class StorageRepositoryProvider {

	private static final StorageRepositoryProvider instance = new StorageRepositoryProvider();
	private final StorageRepository storageRepository = new FileStorageRepository();

	private StorageRepositoryProvider() {
	}

	public static StorageRepositoryProvider getInstance() {
		return instance;
	}

	public StorageRepository getStorageRepository() {
		return storageRepository;
	}

}
