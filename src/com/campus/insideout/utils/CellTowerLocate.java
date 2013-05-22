package com.campus.insideout.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;


public class CellTowerLocate {
	private TelephonyManager manager = null;
	
	public CellTowerLocate(Context context){
		manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}

	 /** 基站信息结构�?*/
    public class SCell{
    	public int MCC;
    	public int MNC;
    	public int LAC;
    	public int CID;
    }
    
    /** 经纬度信息结构体 */
    public class SItude{
    	public String latitude;
    	public String longitude;
    }
    
	/** 按钮点击回调函数 */
	public String onBtnClick() {		

		try {
			/** 获取基站数据 */
			SCell cell = getCellInfo();
			/** 根据基站数据获取经纬�?*/
			SItude itude = getItude(cell);
			/** 获取地理位置 */
			return "";
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 获取基站信息
	 * 
	 * @throws Exception
	 */
	private SCell getCellInfo() throws Exception {
		SCell cell = new SCell();

		/** 调用API获取基站信息 */
		GsmCellLocation location = (GsmCellLocation) manager.getCellLocation();
		if (location == null){	
			throw new Exception("获取基站信息失败");
		}

		String operator = manager.getNetworkOperator();
		int mcc = Integer.parseInt(operator.substring(0, 3));
		int mnc = Integer.parseInt(operator.substring(3));
		int cid = location.getCid();
		int lac = location.getLac();

		/** 将获得的数据放到结构体中 */
		cell.MCC = mcc;
		cell.MNC = mnc;
		cell.LAC = lac;
		cell.CID = cid;

		return cell;
	}
    
	/**
	 * 获取经纬�?
	 * 
	 * @throws Exception
	 */
	private SItude getItude(SCell cell) throws Exception {
		SItude itude = new SItude();

		/** 采用Android默认的HttpClient */
		HttpClient client = new DefaultHttpClient();
		/** 采用POST方法 */
		HttpPost post = new HttpPost("http://www.google.com/loc/json");
		try {
			/** 构�?POST的JSON数据 */
			JSONObject holder = new JSONObject();
			holder.put("version", "1.1.0");
			holder.put("host", "maps.google.com");
			holder.put("address_language", "zh_CN");
			holder.put("request_address", true);
			holder.put("radio_type", "gsm");
			holder.put("carrier", "HTC");

			JSONObject tower = new JSONObject();
			tower.put("mobile_country_code", cell.MCC);
			tower.put("mobile_network_code", cell.MNC);
			tower.put("cell_id", cell.CID);
			tower.put("location_area_code", cell.LAC);

			JSONArray towerarray = new JSONArray();
			towerarray.put(tower);
			holder.put("cell_towers", towerarray);

			StringEntity query = new StringEntity(holder.toString());
			post.setEntity(query);

			/** 发出POST数据并获取返回数�?*/
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer strBuff = new StringBuffer();
			String result = null;
			while ((result = buffReader.readLine()) != null) {
				strBuff.append(result);
			}

			/** 解析返回的JSON数据获得经纬�?*/
			JSONObject json = new JSONObject(strBuff.toString());
			JSONObject subjosn = new JSONObject(json.getString("location"));

			itude.latitude = subjosn.getString("latitude");
			itude.longitude = subjosn.getString("longitude");
			
			
		} catch (Exception e) {
			throw new Exception("获取经纬度出现错�?"+e.getMessage());
		} finally{
			post.abort();
			client = null;
		}
		
    	return itude;
    }	
}
