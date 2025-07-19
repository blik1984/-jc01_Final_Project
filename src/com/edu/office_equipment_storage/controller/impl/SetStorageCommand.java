package com.edu.office_equipment_storage.controller.impl;

import java.text.ParseException;

import com.edu.office_equipment_storage.controller.Command;
import com.edu.office_equipment_storage.model.AssetStorage;
import com.edu.office_equipment_storage.service.AssetStorageService;
import com.edu.office_equipment_storage.service.AssetStorageServiceException;
import com.edu.office_equipment_storage.service.AssetStorageServiceProvider;
import com.edu.office_equipment_storage.utils.Parser;

public class SetStorageCommand implements Command {

	private final AssetStorageServiceProvider assetStorageServiceProvider = AssetStorageServiceProvider.getInstance();
	private final AssetStorageService assetStorageService = assetStorageServiceProvider.getAssetStorageService();

	@Override
	public String execute(String request)   {

		String response = null;
		String[] params;

		params = request.split("\n", 2);

		try {
			AssetStorage newStorage = Parser.parseStorage(params[1]);
			assetStorageService.setStorage(newStorage);
			response = "Storage saved";

		} catch (AssetStorageServiceException |ParseException e) {

			response = "I couldn't do it, I have paws";
		}
		return response;
	}

}
