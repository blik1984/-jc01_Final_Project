package com.edu.office_equipment_storage.controller.impl;

import com.edu.office_equipment_storage.controller.Command;

public class NoSuchCommand implements Command {

	@Override
	public String execute(String request) {

		return "Request error";
	}
}
