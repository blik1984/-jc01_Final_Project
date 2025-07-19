package com.edu.office_equipment_storage.main;

import com.edu.office_equipment_storage.controller.AssetStorageController;
import com.edu.office_equipment_storage.ui.AssetStorageConsoleView;

public class Main {

	public static void main(String[] args) {
		AssetStorageConsoleView view = new AssetStorageConsoleView(new AssetStorageController());
		
		view.displayMenu();

	}

}
