package com.edu.office_equipment_storage.service;

public class AssetStorageServiceException extends Exception{

	private static final long serialVersionUID = -3498205420521875880L;
	
	public AssetStorageServiceException() {
		super();
	}

	public AssetStorageServiceException(String message) {
		super(message);
	}

	public AssetStorageServiceException(Exception e) {
		super(e);
	}

	public AssetStorageServiceException(String message, Exception e) {
		super(message, e);
	}


}
