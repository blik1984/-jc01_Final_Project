package com.edu.office_equipment_storage.controller.impl;

import java.util.List;

import com.edu.office_equipment_storage.controller.Command;
import com.edu.office_equipment_storage.model.AssetStorage;
import com.edu.office_equipment_storage.service.AssetStorageService;
import com.edu.office_equipment_storage.service.AssetStorageServiceException;
import com.edu.office_equipment_storage.service.AssetStorageServiceProvider;
import com.edu.office_equipment_storage.utils.TextDataFormatter;

public class GetAllStoragesCommand implements Command {

	private final AssetStorageServiceProvider assetStorageServiceProvider = AssetStorageServiceProvider.getInstance();
	private final AssetStorageService assetStorageService = assetStorageServiceProvider.getAssetStorageService();

	@Override
	public String execute(String request) {

		String response = null;

		try {
			List<AssetStorage> newAllStorages = assetStorageService.getAllStorages();
			response = TextDataFormatter.convertStoragesToString(newAllStorages);

		} catch (AssetStorageServiceException e) {

			response = "I couldn't do it, I have paws";
		}
		return response;
	}

}
