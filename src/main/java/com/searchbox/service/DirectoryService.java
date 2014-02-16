package com.searchbox.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DirectoryService {
	
	@Autowired
	ApplicationContext context;

	public File createTempFile(String prefix, String suffix) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
