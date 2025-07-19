package com.edu.office_equipment_storage.repostory.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.edu.office_equipment_storage.model.AssetStorage;
import com.edu.office_equipment_storage.repostory.StorageRepository;
import com.edu.office_equipment_storage.repostory.StorageRepositoryException;
import com.edu.office_equipment_storage.utils.Parser;
import com.edu.office_equipment_storage.utils.TextDataFormatter;

public class FileStorageRepository implements StorageRepository {

	private List<String> storageCatalog;

	public FileStorageRepository() {
		try {
			this.storageCatalog = getCatalogFromFile();
		} catch (IOException e) {
			this.storageCatalog = new ArrayList<>();
		}
	}

	@Override
	public void deleteStorage(String name) throws StorageRepositoryException {

		try {
			URL location = FileStorageRepository.class.getProtectionDomain().getCodeSource().getLocation();
			String filePath = new File(location.toURI()).getPath() + File.separator + "data" + File.separator
					+ "Storages" + File.separator + name + ".txt";

			Path path = Paths.get(filePath);
			Files.delete(path);
			storageCatalog.remove(name);
			writeCatalogToFile(storageCatalog);
		} catch (IOException | URISyntaxException e) {
			throw new StorageRepositoryException("Error deleting file");
		}
	}

	@Override
	public void saveStorage(AssetStorage storage) throws StorageRepositoryException {
		if (!storageCatalog.contains(storage.getName())) {
			String name = storage.getName();
			storageCatalog.add(name);
		}
		try {
			writeStorageToFile(storage);
			writeCatalogToFile(storageCatalog);
		} catch (IOException e) {
			throw new StorageRepositoryException("Error saving file");
		}
	}

	@Override
	public AssetStorage getStorage(String name) throws StorageRepositoryException {

		String data = null;
		AssetStorage storage;
		try {
			data = readStorageFromFile(name);
			storage = Parser.parseStorage(data);
		} catch (IOException | ParseException e) {
			throw new StorageRepositoryException("error reading file", e);
		}
		return storage;
	}

	@Override
	public List<AssetStorage> getAllStorages() throws StorageRepositoryException {
		List<AssetStorage> storages = new ArrayList<>();
		for (String name : storageCatalog) {
			storages.add(getStorage(name));
		}
		return storages;
	}

	@Override
	public List<String> getCatalog() {
		return storageCatalog;
	}

	private List<String> getCatalogFromFile() throws IOException {
		String data = readCatalogFromFile();
		List<String> catalog = Parser.parseCatalog(data);
		return catalog;
	}

	private String readCatalogFromFile() throws IOException {
		URL location = FileStorageRepository.class.getProtectionDomain().getCodeSource().getLocation();
		String filePath = location.getPath() + "data/" + "catalog.txt";
		StringBuilder content = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		}
		return content.toString();
	}

	private String readStorageFromFile(String name) throws IOException {
		URL location = FileStorageRepository.class.getProtectionDomain().getCodeSource().getLocation();
		String filePath = location.getPath() + "data/Storages/" + name + ".txt";
		StringBuilder content = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		}
		return content.toString();
	}

	private void writeStorageToFile(AssetStorage storage) throws IOException {

		URL location = FileStorageRepository.class.getProtectionDomain().getCodeSource().getLocation();

		String filePath = location.getPath() + "data/Storages/" + storage.getName() + ".txt";
		System.out.println(storage.getName());

		try (FileWriter writer = new FileWriter(filePath); PrintWriter printWriter = new PrintWriter(writer)) {

			String s = TextDataFormatter.convertStorageToString(storage);
			printWriter.println(s);
		}
	}

	private void writeCatalogToFile(List<String> storageCatalog) throws IOException {

		URL location = FileStorageRepository.class.getProtectionDomain().getCodeSource().getLocation();
		String filePath = location.getPath() + "data/catalog.txt";

		try (FileWriter writer = new FileWriter(filePath); PrintWriter printWriter = new PrintWriter(writer)) {
			String toWrite = TextDataFormatter.convertCatalogToString(storageCatalog);
			printWriter.println(toWrite);
		}
	}
}
