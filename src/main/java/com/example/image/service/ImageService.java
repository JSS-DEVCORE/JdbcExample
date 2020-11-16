/**
 * @project		JdbcExample - Image Service
 * @author		User
 * @date		Nov 16, 2020
 */
package com.example.image.service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageService {

	private static final Logger logger = LogManager.getLogger(ImageService.class);

	/**
	 * Encode
	 * 
	 * @param imageFile
	 * @return
	 * @throws IOException
	 */
	public static String encodeAsString(String imageFile) throws IOException {
		logger.info("Encoding Image: {} to Base64...", imageFile);
		byte[] fileContentAsBytes = FileUtils.readFileToByteArray(new File(imageFile));
		String encString = Base64.getEncoder().encodeToString(fileContentAsBytes);

		return encString;
	}
	
	/**
	 * Encode
	 * 
	 * @param imageFile
	 * @return
	 * @throws IOException
	 */
	public static byte[] encodeAsBytes(String imageFile) throws IOException {
		logger.info("Encoding Image: {} to Bytes...", imageFile);
		byte[] fileContentAsBytes = FileUtils.readFileToByteArray(new File(imageFile));
		return fileContentAsBytes;
	}
}
