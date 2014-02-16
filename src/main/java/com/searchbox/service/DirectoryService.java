package com.searchbox.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.searchbox.core.search.result.TemplatedHitList;

@Service
public class DirectoryService {
	
	private static Logger logger = LoggerFactory.getLogger(DirectoryService.class);

	@Autowired
	ApplicationContext context;

	File tempDir;
	
	DirectoryService(){
		//this.tempDir = new File
	}
	
	public String getApplicationRelativePath(File file){
		try {
			logger.debug("Application absolutePath: " + context.getResource("").getFile().getAbsolutePath() );
			String relative = context.getResource("").getFile().toURI().relativize(file.toURI()).getPath();
			return relative;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public File createTempFile(String prefix, String suffix) {
		
		Resource tempDir = context.getResource("WEB-INF/temp/");
		if(!tempDir.exists()){
			try {
				tempDir.getFile().mkdirs();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			return File.createTempFile(prefix, suffix, tempDir.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
