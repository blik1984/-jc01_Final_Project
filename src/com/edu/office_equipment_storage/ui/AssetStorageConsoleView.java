package com.edu.office_equipment_storage.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.edu.office_equipment_storage.controller.AssetStorageController;
import com.edu.office_equipment_storage.model.Asset;
import com.edu.office_equipment_storage.model.AssetStorage;
import com.edu.office_equipment_storage.model.AssetType;
import com.edu.office_equipment_storage.model.Laptop;
import com.edu.office_equipment_storage.model.LaptopConnectionType;
import com.edu.office_equipment_storage.model.Monitor;
import com.edu.office_equipment_storage.model.MonitorConnectionType;
import com.edu.office_equipment_storage.model.Printer;
import com.edu.office_equipment_storage.model.PrinterColorSupport;
import com.edu.office_equipment_storage.model.PrinterConnectionType;
import com.edu.office_equipment_storage.model.PrinterType;
import com.edu.office_equipment_storage.model.Switch;
import com.edu.office_equipment_storage.model.WiFiRange;
import com.edu.office_equipment_storage.model.Workstation;
import com.edu.office_equipment_storage.model.WorkstationConnectionType;
import com.edu.office_equipment_storage.utils.Parser;
import com.edu.office_equipment_storage.utils.TextDataFormatter;

public class AssetStorageConsoleView {

	private final Scanner scanner = new Scanner(System.in);
	private final AssetStorageController controller;
	private AssetStorage currentStorage;
	private Asset currentAsset;

	public AssetStorageConsoleView(AssetStorageController controller) {
		this.controller = controller;
	}

	public void displayMenu() {
		while (true) {
			System.out.println("\n==== Course Management Menu ====");
			System.out.println("1. Choose Storage");
			System.out.println("2. Create Storage");
			System.out.println("3. Delete Storage");
			System.out.println("4. Save Storage");
			System.out.println("5. Check warranty of all assets");
			System.out.println("6. Choose Asset");
			System.out.println("7. Add Asset");
			System.out.println("8. Delete Asset");
			System.out.println("9. Show Asset Info");
			System.out.println("10. Edit Asset");
			System.out.println("11. Exit");
			System.out.print("Choose option: ");

			int choice = Integer.parseInt(scanner.nextLine());

			switch (choice) {
			case 1 -> choosStorage();
			case 2 -> createStorage();
			case 3 -> deleteStorage();
			case 4 -> saveStorage();
			case 5 -> checkWarranty();
			case 6 -> chooseAsset();
			case 7 -> addAsset();
			case 8 -> deleteAsset();
			case 9 -> showAssetInfo();
			case 10 -> editAsset();
			case 11 -> {
				saveStorage();
				System.out.println("Exiting.");
				return;
			}
			default -> System.out.println("Invalid option.");
			}
		}
	}

	public void choosStorage() {

		List<String> catalogStorages = getCatalog();

		if (catalogStorages.size() == 0) {
			System.out.println("No storages available. Please create a storage first.");
			return;
		}
		System.out.println("Storages available: ");

		for (int i = 0; i < catalogStorages.size(); i++) {
			System.out.println(i + 1 + " -- " + catalogStorages.get(i));
		}
		System.out.print("Enter storage number: ");

		while (!scanner.hasNextInt()) {
			System.out.print("Enter storage number: ");
			scanner.nextLine();
		}
		int storageNumber = scanner.nextInt();
		scanner.nextLine();
		currentStorage = getStorage(catalogStorages.get(storageNumber - 1));
	}

	private void createStorage() {
		System.out.print("Storage name: ");
		String storageName = scanner.nextLine();
		AssetStorage newStorage = new AssetStorage(storageName);

		String response = controller
				.doAction("SETSTORAGE" + "\n" + TextDataFormatter.convertStorageToString(newStorage));

		if (!response.equalsIgnoreCase("I couldn't do it, I have paws")) {
			currentStorage = newStorage;
			System.out.println(response);
			return;
		}
		System.out.println(response);
	}

	private void deleteStorage() {

		choosStorage();

		String response = controller.doAction("DELETESTORAGE" + "\n" + currentStorage.getName());

		if (!response.equalsIgnoreCase("I couldn't do it, I have paws")) {
			currentStorage = null;
			System.out.println(response);
			return;
		}
		System.out.println(response);
	}

	public void saveStorage() {

		String response = controller
				.doAction("SETSTORAGE" + "\n" + TextDataFormatter.convertStorageToString(currentStorage));
		System.out.println(response);
	}

	public void chooseAsset() {
		System.out.println("\n=== Asset selection ===");
		if (currentStorage == null) {
			System.out.println("There are no assets in the repository!");
			return;
		}
		Map<AssetType, Set<String>> catalogs = currentStorage.getCatalogs();
		if (catalogs.isEmpty()) {
			System.out.println("There are no assets in the repository!");
			return;
		}

		System.out.println("Available asset types:");
		List<AssetType> types = new ArrayList<>(catalogs.keySet());
		for (int i = 0; i < types.size(); i++) {
			System.out.printf("%d. %s\n", i + 1, types.get(i));
		}

		AssetType selectedType = null;
		while (selectedType == null) {
			System.out.print("Select type (0 - cancel): ");
			try {
				int choice = scanner.nextInt();
				scanner.nextLine();

				if (choice == 0) {
					System.out.println("Cancel selection");
					return;
				}

				if (choice > 0 && choice <= types.size()) {
					selectedType = types.get(choice - 1);
				} else {
					System.out.println("Invalid type number!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: enter a number!");
				scanner.nextLine();
			}
		}
		System.out.println("авария выбора типа   " + selectedType);

		Set<String> assetIds = catalogs.get(selectedType);
		System.out.println("авария получения списка ассетов   " + assetIds.toString());

		if (assetIds == null || assetIds.isEmpty()) {
			System.out.println("There are no assets of this type\r\n" + "\r\n" + "!");
			return;
		}

		System.out.printf("\nAvailable assets type %s:\n", selectedType);
		List<Asset> assets = new ArrayList<>();
		for (String id : assetIds) {
			Asset asset = currentStorage.getAssetStorage().get(id);
			if (asset != null) {
				assets.add(asset);
			}
		}

		for (int i = 0; i < assets.size(); i++) {
			Asset asset = assets.get(i);
			System.out.printf("%d. ID: %s, %s %s\n", i + 1, asset.getId(), asset.getVendor(), asset.getModel());
		}

		Asset selectedAsset = null;
		while (selectedAsset == null) {
			System.out.print("Select asset (0 - cancel): ");
			try {
				int choice = scanner.nextInt();
				scanner.nextLine();

				if (choice == 0) {
					System.out.println("Cancel selection");
					return;
				}

				if (choice > 0 && choice <= assets.size()) {
					selectedAsset = assets.get(choice - 1);
					currentAsset = selectedAsset;
					System.out.printf("Asset selected: %s %s\n", selectedAsset.getVendor(), selectedAsset.getModel());
				} else {
					System.out.println("Invalid asset number!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: enter a number!");
				scanner.nextLine();
			}
		}
	}

	public void deleteAsset() {
		chooseAsset();
		this.currentStorage.deleteAsset(currentAsset.getId());
		saveStorage();
	}

	public void showAssetInfo() {
		if (currentAsset == null) {
			System.out.println("No asset selected!");
			return;
		}

		System.out.println("\n=== Complete Asset Information ===");
		System.out.println("Type: " + currentAsset.getType());
		System.out.println("ID: " + currentAsset.getId());
		System.out.println("Vendor: " + currentAsset.getVendor());
		System.out.println("Model: " + currentAsset.getModel());
		System.out
				.println("Warranty Date: " + new SimpleDateFormat("yyyy-MM-dd").format(currentAsset.getWarrantyDate()));
		System.out.println("Note: " + currentAsset.getNote());

		if (currentAsset instanceof Laptop) {
			Laptop laptop = (Laptop) currentAsset;
			System.out.println("Screen Diagonal: " + laptop.getDiagonal() + "\"");
			System.out.println("HDD Size: " + laptop.getHardDiskSize() + " GB");
			System.out.println("RAM Size: " + laptop.getRAMSize() + " GB");
			System.out.println("Connection Types: " + setToString(laptop.getLaptopConnectionType()));
		} else if (currentAsset instanceof Monitor) {
			Monitor monitor = (Monitor) currentAsset;
			System.out.println("Screen Diagonal: " + monitor.getMonitorDiagonal() + "\"");
			System.out.println("Connection Type: " + monitor.getConnectionType());
		} else if (currentAsset instanceof Printer) {
			Printer printer = (Printer) currentAsset;
			System.out.println("Printer Type: " + printer.getPrinterType());
			System.out.println("Color Support: " + printer.getColorSupport());
			System.out.println("Connection Types: " + setToString(printer.getConnectionType()));
		} else if (currentAsset instanceof Switch) {
			Switch networkSwitch = (Switch) currentAsset;
			System.out.println("LAN Ports Count: " + networkSwitch.getNumberLanPorts());
			System.out.println("WiFi Ranges: " + setToString(networkSwitch.getWiFiRanges()));
		} else if (currentAsset instanceof Workstation) {
			Workstation workstation = (Workstation) currentAsset;
			System.out.println("HDD Size: " + workstation.getHardDiskSize() + " GB");
			System.out.println("RAM Size: " + workstation.getRAMSize() + " GB");
			System.out.println("Connection Types: " + setToString(workstation.getWorkstationConnectionType()));
		}
	}

	private String setToString(Set<? extends Enum<?>> enumSet) {
		return enumSet.stream().map(Enum::name).collect(Collectors.joining(", "));
	}

	public void addAsset() {
		try {
			Asset asset = createAssetInteractive();
			currentStorage.addAsset(asset);
			System.out.println("Created object: " + asset);
		} catch (Exception e) {
			System.out.println("Error creating object");
		}
	}

	public Asset createAssetInteractive() throws ParseException {
		System.out.println("Select asset type:");
		Arrays.stream(AssetType.values()).forEach(type -> System.out.println((type.ordinal() + 1) + ". " + type));

		AssetType type = readEnumInput("Enter type number: ", AssetType.class);

		System.out.println("\nEnter common asset data:");
		String id = readStringInput("ID: ");
		String vendor = readStringInput("Vendor: ");
		String model = readStringInput("Model: ");
		Date warrantyDate = readDateInput("Warranty Date (yyyy-MM-dd): ");
		String note = readStringInput("Note: ");

		switch (type) {
		case LAPTOP:
			return createLaptop(id, vendor, model, warrantyDate, note);
		case MONITOR:
			return createMonitor(id, vendor, model, warrantyDate, note);
		case PRINTER:
			return createPrinter(id, vendor, model, warrantyDate, note);
		case SWITCH:
			return createSwitch(id, vendor, model, warrantyDate, note);
		case WORKSTATION:
			return createWorkstation(id, vendor, model, warrantyDate, note);
		default:
			throw new IllegalArgumentException("Unknown asset type");
		}
	}

	private Laptop createLaptop(String id, String vendor, String model, Date warrantyDate, String note) {
		double diagonal = readDoubleInput("Screen Diagonal: ");
		double hardDiskSize = readDoubleInput("Hard Disk Size (GB): ");
		double ramSize = readDoubleInput("RAM Size (GB): ");
		Set<LaptopConnectionType> connections = readEnumSetInput("Connection Types (comma separated): ",
				LaptopConnectionType.class);
		return new Laptop(id, vendor, model, warrantyDate, note, AssetType.LAPTOP, diagonal, hardDiskSize, ramSize,
				connections);
	}

	private Monitor createMonitor(String id, String vendor, String model, Date warrantyDate, String note) {
		double diagonal = readDoubleInput("Screen Diagonal (inches): ");
		MonitorConnectionType connection = readEnumInput("Connection Type: ", MonitorConnectionType.class);
		return new Monitor(id, vendor, model, warrantyDate, note, AssetType.MONITOR, diagonal, connection);
	}

	private Printer createPrinter(String id, String vendor, String model, Date warrantyDate, String note) {
		PrinterType printerType = readEnumInput("Printer Type: ", PrinterType.class);
		Set<PrinterConnectionType> connections = readEnumSetInput("Connection Types (comma separated): ",
				PrinterConnectionType.class);
		PrinterColorSupport colorSupport = readEnumInput("Color Support: ", PrinterColorSupport.class);
		return new Printer(id, vendor, model, warrantyDate, note, AssetType.PRINTER, printerType, connections,
				colorSupport);
	}

	private Switch createSwitch(String id, String vendor, String model, Date warrantyDate, String note) {
		int ports = readIntInput("LAN Ports Count: ");
		Set<WiFiRange> wifiRanges = readEnumSetInput("WiFi Ranges (comma separated): ", WiFiRange.class);
		return new Switch(id, vendor, model, warrantyDate, note, AssetType.SWITCH, ports, wifiRanges);
	}

	private Workstation createWorkstation(String id, String vendor, String model, Date warrantyDate, String note) {
		double hardDiskSize = readDoubleInput("Hard Disk Size (GB): ");
		double ramSize = readDoubleInput("RAM Size (GB): ");
		Set<WorkstationConnectionType> connections = readEnumSetInput("Connection Types (comma separated): ",
				WorkstationConnectionType.class);
		return new Workstation(id, vendor, model, warrantyDate, note, AssetType.WORKSTATION, hardDiskSize, ramSize,
				connections);
	}

	private int readIntInput(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Error! Enter an integer.");
			}
		}
	}

	private String readStringInput(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	private double readDoubleInput(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return Double.parseDouble(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Error! Enter a number.");
			}
		}
	}

	private Date readDateInput(String prompt) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		while (true) {
			try {
				System.out.print(prompt);
				return dateFormat.parse(scanner.nextLine());
			} catch (ParseException e) {
				System.out.println("Error! Use yyyy-MM-dd format.");
			}
		}
	}

	private <E extends Enum<E>> E readEnumInput(String prompt, Class<E> enumClass) {
		E[] values = enumClass.getEnumConstants();
		while (true) {
			try {
				System.out.print(prompt);
				int choice = Integer.parseInt(scanner.nextLine());
				if (choice >= 1 && choice <= values.length) {
					return values[choice - 1];
				}
				System.out.println("Enter a number between 1 and " + values.length);
			} catch (NumberFormatException e) {
				System.out.println("Error! Enter a number.");
			}
		}
	}

	private <E extends Enum<E>> Set<E> readEnumSetInput(String prompt, Class<E> enumClass) {
		E[] values = enumClass.getEnumConstants();
		System.out.println("Available options:");
		for (int i = 0; i < values.length; i++) {
			System.out.println((i + 1) + ". " + values[i]);
		}
		System.out.print(prompt);

		String input = scanner.nextLine();
		return Arrays.stream(input.split(",")).map(String::trim).filter(s -> !s.isEmpty())
				.map(s -> Enum.valueOf(enumClass, s.toUpperCase())).collect(Collectors.toSet());
	}

	private List<String> getCatalog() {

		String allStorages = controller.doAction("GETCATALOGSTORAGES" + "\n");

		if (!allStorages.equalsIgnoreCase("I couldn't do it, I have paws")) {
			List<String> storages = Parser.parseCatalog(allStorages);
			return storages;
		}
		return new ArrayList<String>();
	}

	private AssetStorage getStorage(String name) {

		String response = controller.doAction("GETSTORAGE" + "\n" + name);

		if (!response.equalsIgnoreCase("I couldn't do it, I have paws")) {
			AssetStorage storage;
			try {
				storage = Parser.parseStorage(response);
				return storage;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return currentStorage;
	}

	public void checkWarranty() {
		if (currentStorage == null) {
			System.out.println("No storage selected!");
			return;
		}

		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		boolean hasExpired = false;

		System.out.println("\n=== Warranty Check ===");
		System.out.println("Current date: " + dateFormat.format(today));
		System.out.println("Checking assets in storage: " + currentStorage.getName());

		for (Asset asset : currentStorage.getAssetStorage().values()) {
			if (asset.getWarrantyDate().before(today)) {
				System.out.printf("[EXPIRED] %s (ID: %s) - Warranty until: %s\n", asset.getClass().getSimpleName(),
						asset.getId(), dateFormat.format(asset.getWarrantyDate()));
				hasExpired = true;
			}
		}
		if (!hasExpired) {
			System.out.println("All assets are under warranty.");
		}
	}

	public void editAsset() {
		if (currentAsset == null) {
			System.out.println("No asset selected! Please choose an asset first.");
			return;
		}

		System.out.println("\n=== Editing Asset ===");
		showAssetInfo();

		try {
			System.out.println("\nEdit common fields (leave blank to skip):");
			String newVendor = readOptionalStringInput("Vendor [" + currentAsset.getVendor() + "]: ");
			String newModel = readOptionalStringInput("Model [" + currentAsset.getModel() + "]: ");
			Date newWarrantyDate = readOptionalDateInput("Warranty Date ["
					+ new SimpleDateFormat("yyyy-MM-dd").format(currentAsset.getWarrantyDate()) + "]: ");
			String newNote = readOptionalStringInput("Note [" + currentAsset.getNote() + "]: ");

			if (!newVendor.isEmpty())
				currentAsset.setVendor(newVendor);
			if (!newModel.isEmpty())
				currentAsset.setModel(newModel);
			if (newWarrantyDate != null)
				currentAsset.setWarrantyDate(newWarrantyDate);
			if (!newNote.isEmpty())
				currentAsset.setNote(newNote);

			// Edit type-specific fields
			if (currentAsset instanceof Laptop) {
				editLaptopFields((Laptop) currentAsset);
			} else if (currentAsset instanceof Monitor) {
				editMonitorFields((Monitor) currentAsset);
			} else if (currentAsset instanceof Printer) {
				editPrinterFields((Printer) currentAsset);
			} else if (currentAsset instanceof Switch) {
				editSwitchFields((Switch) currentAsset);
			} else if (currentAsset instanceof Workstation) {
				editWorkstationFields((Workstation) currentAsset);
			}

			System.out.println("Asset updated successfully!");
			saveStorage();
		} catch (Exception e) {
			System.out.println("Error editing asset: " + e.getMessage());
		}
	}

	// Helper methods for input
	private String readOptionalStringInput(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine().trim();
	}

	private Date readOptionalDateInput(String prompt) throws ParseException {
		System.out.print(prompt);
		String input = scanner.nextLine().trim();
		if (input.isEmpty())
			return null;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.parse(input);
	}

	private boolean confirm(String prompt) {
		System.out.print(prompt);
		String input = scanner.nextLine().trim().toLowerCase();
		return input.equals("y") || input.equals("yes");
	}

	private void editLaptopFields(Laptop laptop) {
		System.out.println("\nEdit laptop fields (leave blank to skip):");
		String newDiagonal = readOptionalStringInput("Diagonal [" + laptop.getDiagonal() + "\"]: ");
		String newHddSize = readOptionalStringInput("HDD Size [" + laptop.getHardDiskSize() + "GB]: ");
		String newRamSize = readOptionalStringInput("RAM Size [" + laptop.getRAMSize() + "GB]: ");

		if (!newDiagonal.isEmpty())
			laptop.setDiagonal(Double.parseDouble(newDiagonal));
		if (!newHddSize.isEmpty())
			laptop.setHardDiskSize(Double.parseDouble(newHddSize));
		if (!newRamSize.isEmpty())
			laptop.setRAMSize(Double.parseDouble(newRamSize));

		System.out.println("Current connections: " + setToString(laptop.getLaptopConnectionType()));
		if (confirm("Do you want to edit connections? (y/n): ")) {
			Set<LaptopConnectionType> connections = readEnumSetInput("New connections (comma separated): ",
					LaptopConnectionType.class);
			laptop.setLaptopConnectionType(connections);
		}
	}

	private void editMonitorFields(Monitor monitor) {
		System.out.println("\nEdit monitor fields (leave blank to skip):");
		String newDiagonal = readOptionalStringInput("Diagonal [" + monitor.getMonitorDiagonal() + "\"]: ");

		if (!newDiagonal.isEmpty())
			monitor.setMonitorDiagonal(Double.parseDouble(newDiagonal));

		System.out.println("Current connection: " + monitor.getConnectionType());
		if (confirm("Do you want to change connection type? (y/n): ")) {
			MonitorConnectionType connection = readEnumInput("New connection type: ", MonitorConnectionType.class);
			monitor.setConnectionType(connection);
		}
	}

	private void editPrinterFields(Printer printer) {
		System.out.println("\nEdit printer fields (leave blank to skip):");
		System.out.println("Current printer type: " + printer.getPrinterType());
		if (confirm("Do you want to change printer type? (y/n): ")) {
			PrinterType printerType = readEnumInput("New printer type: ", PrinterType.class);
			printer.setType(printerType);
		}

		System.out.println("Current color support: " + printer.getColorSupport());
		if (confirm("Do you want to change color support? (y/n): ")) {
			PrinterColorSupport colorSupport = readEnumInput("New color support: ", PrinterColorSupport.class);
			printer.setColorSupport(colorSupport);
		}

		System.out.println("Current connections: " + setToString(printer.getConnectionType()));
		if (confirm("Do you want to edit connections? (y/n): ")) {
			Set<PrinterConnectionType> connections = readEnumSetInput("New connections (comma separated): ",
					PrinterConnectionType.class);
			printer.setConnectionType(connections);
		}
	}

	private void editSwitchFields(Switch networkSwitch) {
		System.out.println("\nEdit switch fields (leave blank to skip):");
		String newPorts = readOptionalStringInput("LAN Ports [" + networkSwitch.getNumberLanPorts() + "]: ");

		if (!newPorts.isEmpty())
			networkSwitch.setNumberLanPorts(Integer.parseInt(newPorts));

		System.out.println("Current WiFi ranges: " + setToString(networkSwitch.getWiFiRanges()));
		if (confirm("Do you want to edit WiFi ranges? (y/n): ")) {
			Set<WiFiRange> wifiRanges = readEnumSetInput("New WiFi ranges (comma separated): ", WiFiRange.class);
			networkSwitch.setWiFiRanges(wifiRanges);
		}
	}

	private void editWorkstationFields(Workstation workstation) {
		System.out.println("\nEdit workstation fields (leave blank to skip):");
		String newHddSize = readOptionalStringInput("HDD Size [" + workstation.getHardDiskSize() + "GB]: ");
		String newRamSize = readOptionalStringInput("RAM Size [" + workstation.getRAMSize() + "GB]: ");

		if (!newHddSize.isEmpty())
			workstation.setHardDiskSize(Double.parseDouble(newHddSize));
		if (!newRamSize.isEmpty())
			workstation.setRAMSize(Double.parseDouble(newRamSize));

		System.out.println("Current connections: " + setToString(workstation.getWorkstationConnectionType()));
		if (confirm("Do you want to edit connections? (y/n): ")) {
			Set<WorkstationConnectionType> connections = readEnumSetInput("New connections (comma separated): ",
					WorkstationConnectionType.class);
			workstation.setWorkstationConnectionType(connections);
		}
	}
}
