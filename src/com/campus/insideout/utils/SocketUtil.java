package com.campus.insideout.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

public class SocketUtil {

	private static BufferedReader in = null;
	private static PrintWriter out = null;
	public boolean mClientFlag = false;
	private static Socket socket;

	public static String request(String HOST, int PORT, String REQUEST) {
		try {
			long startTime = System.currentTimeMillis();
			socket = new Socket(HOST, PORT);
			socket.setSoTimeout(10000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			out.println(REQUEST);
			// �?��要，添加结束标记
			socket.shutdownOutput();
			StringBuffer buffer = new StringBuffer();
			String line = null;

			while (true) {
				line = in.readLine();
				if (line == null) {
					break;
				}
				buffer.append(line);
				// if (System.currentTimeMillis() - startTime > 10000) {
				// break;
				// }
			}
			line = buffer.toString();
			// 关闭�?
			out.close();
			in.close();
			// 关闭连接
			socket.close();
			return line;

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

}