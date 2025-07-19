package com.edu.office_equipment_storage.controller;

public class AssetStorageController {
	
	private final char delimiter = '\n';
	private final CommandProvider commandProvider = new CommandProvider();

	public AssetStorageController() {}

	public String doAction(String request)  {

		String commandName;
		Command executionCommand;
		commandName = request.substring(0, request.indexOf(delimiter));
		executionCommand = commandProvider.getCommand(commandName.toUpperCase());
		String response;
		response = executionCommand.execute(request);
		return response;
	}

}
