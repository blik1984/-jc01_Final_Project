package com.edu.office_equipment_storage.controller.impl;

import com.edu.office_equipment_storage.controller.Command;
import com.edu.office_equipment_storage.service.AssetStorageService;
import com.edu.office_equipment_storage.service.AssetStorageServiceException;
import com.edu.office_equipment_storage.service.AssetStorageServiceProvider;

public class DeleteStorageCommand implements Command {

	private final AssetStorageServiceProvider assetStorageServiceProvider = AssetStorageServiceProvider.getInstance();
	private final AssetStorageService assetStorageService = assetStorageServiceProvider.getAssetStorageService();

	@Override
	public String execute(String request) {

		String response = null;
		String[] params;

		params = request.split("\n", 2);

		try {
			assetStorageService.deleteStorage(params[1]);
			response = "Storage deleted";

		} catch (AssetStorageServiceException e) {
			response = "I couldn't do it, I have paws";
		}
		return response;
	}

}
