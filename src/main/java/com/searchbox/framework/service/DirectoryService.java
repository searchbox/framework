/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.framework.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DirectoryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryService.class);

	@Autowired
	ApplicationContext context;

	File tempDir;
	
	DirectoryService(){
		//this.tempDir = new File
	}
	
	public String getApplicationRelativePath(String fname){
		Resource tempDir = context.getResource("WEB-INF/temp/");
		File file;
		try {
			file = new File(tempDir.getFile().getAbsolutePath()+"/"+fname);
			LOGGER.debug("Application absolutePath: " + context.getResource("").getFile().getAbsolutePath() );
			String relative = context.getResource("").getFile().toURI().relativize(file.toURI()).getPath();
			return relative;
		} catch (IOException e) {
			LOGGER.error("Could not create temp file in directoryService",e);
		}
		return null;
	}
	
	public String getApplicationRelativePath(File file){
		try {
			LOGGER.debug("Application absolutePath: " + context.getResource("").getFile().getAbsolutePath() );
			String relative = context.getResource("").getFile().toURI().relativize(file.toURI()).getPath();
			return relative;
		} catch (IOException e) {
			LOGGER.error("Could not get application-relative path in directoryService",e);
		}
		return null;
	}
	
	public Boolean fileExists(String fname){
		Resource tempDir = context.getResource("WEB-INF/temp/");
		File newFile;
		try {
			newFile = new File(tempDir.getFile().getAbsolutePath()+"/"+fname);
			return newFile.exists();
		} catch (IOException e) {
			LOGGER.error("Could not create temp file in directoryService",e);
		}
		return false;
	}
	
	public File createFile(String fname, String content){
		File file = this.createFile(fname);
		try {
			FileUtils.writeStringToFile(file, content);
		} catch (IOException e) {
			LOGGER.error("Could not write to temp file in directoryService",e);
		}
		return file;
	}
	
	public File createFile(String fname) {
		Resource tempDir = context.getResource("WEB-INF/temp/");
		if(!tempDir.exists()){
			try {
				tempDir.getFile().mkdirs();
			} catch (IOException e) {
				LOGGER.error("Could not create temp file in directoryService",e);
			}
		}
		try {
			File newFile =  new File(tempDir.getFile().getAbsolutePath()+"/"+fname);
			newFile.deleteOnExit();
			return newFile;
		} catch (IOException e) {
			LOGGER.error("Could not create temp file in directoryService",e);

		}
		return null;
	}
	
}
