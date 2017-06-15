package com.hidi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 
 * @author Administrator
 * 
 */
public class FileUtil {

	/**
	 * read file contents by the assigned filepath
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath){
		BufferedReader br = null;
		String result = "";
		StringBuffer total = new StringBuffer();
		try {
			//create bufferreader to read datastream
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			//read lines, and return a string which splited with ":" to connect lines
			String line = null;
			while((line = br.readLine()) != null){
				total.append(":");
				total.append(line);
			}
			if(total.length() > 0) result = total.toString().substring(1);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally{
			//close the open datastream
			try {
				if(br !=null) br.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}	
		}
		return result;
	}
	
	/**
	 * write string to assigned file
	 * @param filePath 
	 * @param content 
	 */
	public static void writeFile(String filePath, String content){
		OutputStreamWriter  writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally{
			//close the open datastream
			try {
				if(writer != null) writer.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}	
		}
	}
}
