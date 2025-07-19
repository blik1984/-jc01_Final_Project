package com.edu.office_equipment_storage.controller;

import java.util.HashMap;
import java.util.Map;

import com.edu.office_equipment_storage.controller.impl.DeleteStorageCommand;
import com.edu.office_equipment_storage.controller.impl.GetAllStoragesCommand;
import com.edu.office_equipment_storage.controller.impl.GetCatalogStoragesCommand;
import com.edu.office_equipment_storage.controller.impl.GetStorageCommand;
import com.edu.office_equipment_storage.controller.impl.NoSuchCommand;
import com.edu.office_equipment_storage.controller.impl.SetStorageCommand;

public class CommandProvider {
	
	private final Map<CommandName, Command> repository = new HashMap<>();

	CommandProvider(){
		repository.put(CommandName.SETSTORAGE, new SetStorageCommand());
		repository.put(CommandName.DELETESTORAGE, new DeleteStorageCommand());
		repository.put(CommandName.GETALLSTORAGES, new GetAllStoragesCommand());
		repository.put(CommandName.GETSTORAGE, new GetStorageCommand());
		repository.put(CommandName.GETCATALOGSTORAGES, new GetCatalogStoragesCommand());
		repository.put(CommandName.WRONG_REQUEST, new NoSuchCommand());
		}

	Command getCommand(String name) {
		CommandName commandName = null;
		Command command = null;
		try {
			commandName = CommandName.valueOf(name.toUpperCase());
			command = repository.get(commandName);
		} catch (IllegalArgumentException | NullPointerException e) {
			command = repository.get(CommandName.WRONG_REQUEST);
		}
		return command;
	}
	
	
}
