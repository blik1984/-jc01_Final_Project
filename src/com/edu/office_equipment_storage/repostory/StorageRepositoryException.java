package com.edu.office_equipment_storage.repostory;

public class StorageRepositoryException extends Exception{
	
	private static final long serialVersionUID = 6308894527602885068L;
	
	public StorageRepositoryException() {
		super();
	}

	public StorageRepositoryException(String message) {
		super(message);
	}

	public StorageRepositoryException(Exception e) {
		super(e);
	}

	public StorageRepositoryException(String message, Exception e) {
		super(message, e);
	}
	

}
