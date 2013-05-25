package com.campus.insideout.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
/**
 * 文件操作
 * @author Administrator
 *
 */
public class FileOpreation {
	public FileOpreation() {
	}

	public static boolean newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			if (myDelFile != null && myDelFile.exists()) {
				if (myDelFile.delete()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	public static boolean copyFile(String file_from, String file_to) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(file_from);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(file_from);
				FileOutputStream fs = new FileOutputStream(file_to);
				byte[] buffer = new byte[CommonData.DF_MaxGifSize];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean copyFolder(String folder_from, String folder_to) {

		try {
			(new File(folder_to)).mkdirs();
			File a = new File(folder_from);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (folder_from.endsWith(File.separator)) {
					temp = new File(folder_from + file[i]);
				} else {
					temp = new File(folder_from + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(folder_to
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[CommonData.DF_MaxGifSize];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copyFolder(folder_from + "/" + file[i], folder_to + "/"
							+ file[i]);
				}
			}
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}
	public static byte[] getBytesFromFile(File f)
	  {
	    if (f == null)
	    {
	      return null;
	    }
	    
	    try
	    {
	      FileInputStream stream = new FileInputStream(f);
	      ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
	      
	      byte[] b = new byte[1000];
	      int n;
	      while ((n = stream.read(b)) != -1)
	      {
	        out.write(b, 0, n);
	      }
	      stream.close();
	      out.close();
	      
	      return out.toByteArray();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    
	    return null;
	  }

	public static File getFileFromBytes(byte[] b, String outputFile)
	  {
	    BufferedOutputStream stream = null;
	    File file = null;
	    try
	    {
	      file = new File(outputFile);
	      FileOutputStream fstream = new FileOutputStream(file);
	      stream = new BufferedOutputStream(fstream);
	      stream.write(b);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    finally
	    {
	      if (stream != null)
	      {
	        try
	        {
	          stream.close();
	        }
	        catch (IOException e1)
	        {
	          e1.printStackTrace();
	        }
	      }
	    }
	    return file;
	  }
}
