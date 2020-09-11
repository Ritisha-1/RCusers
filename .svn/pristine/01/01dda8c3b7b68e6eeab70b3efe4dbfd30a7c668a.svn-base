package com.mexel.frmk.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileHelper {

//	public static String readFileContent(Context ctx, int resId)
//			throws PasException {
//		StringBuilder sb = new StringBuilder();
//
//		InputStream inputStream = ctx.getResources().openRawResource(resId);
//		BufferedReader reader = new BufferedReader(new InputStreamReader(
//				inputStream));
//
//		String line;
//		try {
//			while ((line = reader.readLine()) != null) {
//				sb.append(line).append("\n");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			Log.e(ctx.getPackageName(), "Fail to Read File ", e);
//			throw new PasException(ctx.getPackageName(), e);
//		}
//		return sb.toString();
//	}

//	public static String readHelpFileContent(Context ctx, String fileName) {
//		Class<R.raw> rawClass = R.raw.class;
//		try {
//
//			Field f = rawClass.getField(fileName);
//			int resId = f.getInt(null);
//			return readFileContent(ctx, resId);
//		} catch (Exception e) {
//			return "";
//		}
//	}

//	public static List<String> readAsList(Context ctx, int resId)
//			throws PasException {
//		List<String> lines = new ArrayList<String>();
//		InputStream inputStream = ctx.getResources().openRawResource(resId);
//		BufferedReader reader = new BufferedReader(new InputStreamReader(
//				inputStream));
//		BufferedReader buffreader = new BufferedReader(reader);
//		String line;
//		try {
//			while ((line = buffreader.readLine()) != null) {
//				lines.add(line);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			Log.e(ctx.getPackageName(), "Fail to Read File ", e);
//			throw new PasException(ctx.getPackageName(), e);
//		}
//		return lines;
//	}

	public static void closeFile(Closeable out) {
		if (out == null) {
			return;
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


//	public static String readFileAsString(Context ctx, int resId) {
//		StringBuffer fileData = new StringBuffer(1000);
//		try {
//			InputStream inputStream = ctx.getResources().openRawResource(resId);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					inputStream));
//
//			char[] buf = new char[1024];
//			int numRead = 0;
//			while ((numRead = reader.read(buf)) != -1) {
//				String readData = String.valueOf(buf, 0, numRead);
//				fileData.append(readData);
//				buf = new char[1024];
//			}
//			reader.close();
//			return fileData.toString();
//		} catch (Exception e) {
//			Log.d("FileHelper", "Fail to Read File", e);
//		}
//		return fileData.toString();
//	}

	public static void copyFile(File src, File dst) throws IOException {
		FileInputStream in = new FileInputStream(src);
		FileOutputStream out = new FileOutputStream(dst);
		FileChannel fromChannel = null, toChannel = null;
		try {
			fromChannel = in.getChannel();
			toChannel = out.getChannel();
			fromChannel.transferTo(0, fromChannel.size(), toChannel);
		} finally {
			if (fromChannel != null)
				fromChannel.close();
			if (toChannel != null)
				toChannel.close();
			closeFile(out);
			closeFile(in);
		}
	}
	
    public static void copyDirectory(File src, File dest)
        	throws IOException{
     
        	if(src.isDirectory()){
     
        		//if directory not exists, create it
        		if(!dest.exists()){
        		   dest.mkdir();
        		}
     
        		//list all the directory contents
        		String files[] = src.list();
     
        		for (String file : files) {
        		   //construct the src and dest file structure
        		   File srcFile = new File(src, file);
        		   File destFile = new File(dest, file);
        		   //recursive copy
        		   copyDirectory(srcFile,destFile);
        		}
     
        	}else{
        		//copy file
        		copy(src, dest);
        	}
        }

	
	public static void delteFile(File file){
		if(file.isDirectory()){
			for(File f:file.listFiles()){
				delteFile(f);
			}
		}
		file.delete();
		
	}


	public static void zip(File directory, File zipfile) throws IOException {
		URI base = directory.toURI();
		Deque<File> queue = new LinkedList<File>();
		queue.push(directory);
		OutputStream out = new FileOutputStream(zipfile);
		Closeable res = out;
		try {
			ZipOutputStream zout = new ZipOutputStream(out);
			res = zout;
			while (!queue.isEmpty()) {
				directory = queue.pop();
				for (File kid : directory.listFiles()) {
					String name = base.relativize(kid.toURI()).getPath();
					if (kid.isDirectory()) {
						queue.push(kid);
						name = name.endsWith("/") ? name : name + "/";
						zout.putNextEntry(new ZipEntry(name));
					} else {
						zout.putNextEntry(new ZipEntry(name));
						copy(kid, zout);
						zout.closeEntry();
					}
				}
			}
		} finally {
			res.close();
		}
	}

	public static void unzip(File zipfile, File directory) throws IOException {
		ZipFile zfile = new ZipFile(zipfile);
		Enumeration<? extends ZipEntry> entries = zfile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			File file = new File(directory, entry.getName());
			if (entry.isDirectory()) {
				file.mkdirs();
			} else {
				file.getParentFile().mkdirs();
				InputStream in = zfile.getInputStream(entry);
				try {
					copy(in, file);
				} finally {
					in.close();
				}
			}
		}
	}
	private static void copy(File file, File out) throws IOException {
		FileOutputStream ioOut = new FileOutputStream(out);
		try{
			copy(file, ioOut);
		}
		finally{
			ioOut.close();
		}
	}

	private static void copy(File file, OutputStream out) throws IOException {
		InputStream in = new FileInputStream(file);
		try {
			copy(in, out);
		} finally {
			in.close();
		}
	}

	private static void copy(InputStream in, File file) throws IOException {
		OutputStream out = new FileOutputStream(file);
		try {
			copy(in, out);
		} finally {
			out.close();
		}
	}

	private static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		while (true) {
			int readCount = in.read(buffer);
			if (readCount < 0) {
				break;
			}
			out.write(buffer, 0, readCount);
		}
	}

}
