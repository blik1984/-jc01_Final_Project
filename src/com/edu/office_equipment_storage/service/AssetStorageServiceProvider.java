package com.edu.office_equipment_storage.service;

import com.edu.office_equipment_storage.service.impl.AssetStorageServiceOne;

public class AssetStorageServiceProvider {
	
	private static final AssetStorageServiceProvider instance = new AssetStorageServiceProvider();
	private final AssetStorageService assetStorageService = new AssetStorageServiceOne();
	
	private AssetStorageServiceProvider() {}
	
	public static AssetStorageServiceProvider getInstance() {
		return instance;
	}

	public AssetStorageService getAssetStorageService() {
		return assetStorageService;
	}
	

}
