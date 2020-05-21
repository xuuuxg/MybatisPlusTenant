package com.tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Xuxinguang on 2019/2/28.
 */
public class Tools {
    RequestConfig requestConfig;

    public static String doGet(String url,String param) {
        try{
            URL restURL = new URL(url);
        /*
         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
         */
            HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
            //请求方式
            conn.setRequestMethod("GET");
            //设置是否从httpUrlConnection读入，默认情况下是true; httpUrlConnection.setDoInput(true);
            conn.setDoOutput(true);
            //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
            conn.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(param);
            System.out.println(ps);
            ps.close();

            BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line,resultStr="";

            while(null != (line=bReader.readLine()))
            {
                resultStr +=line;
            }
            bReader.close();
            return resultStr;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    public static String doPost(String url,String param) {
        try{
            URL restURL = new URL(url);
        /*
         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
         */
            HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
            //请求方式
            conn.setRequestMethod("POST");
            //设置是否从httpUrlConnection读入，默认情况下是true; httpUrlConnection.setDoInput(true);
            conn.setDoOutput(true);
            //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
            conn.setAllowUserInteraction(false);

            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(param);

            ps.close();

            BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line,resultStr="";

            while(null != (line=bReader.readLine()))
            {
                resultStr +=line;
            }
            bReader.close();
            return resultStr;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public JSONObject sendGet(String url, Map<String, String> params) {
        String getUrl = url;
        if(params != null) {
            Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
            StringBuffer urlParamsBuffer = new StringBuffer();
            while(iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                urlParamsBuffer.append(entry.getKey()+"="+entry.getValue()+"&");
            }

            if(urlParamsBuffer.length() > 0) {
                urlParamsBuffer.deleteCharAt(urlParamsBuffer.length() - 1);
                getUrl += '?'+ urlParamsBuffer.toString();
            }
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet;
        httpGet = new HttpGet(getUrl);
        httpGet.setConfig(requestConfig);
        String responseContent = null;
        JSONObject jsonObject = new JSONObject();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity);
            try {
                jsonObject = JSONObject.fromObject(responseContent);
            } catch (Exception e) {
                jsonObject = JSONArray.fromObject(responseContent).getJSONObject(0);
            }
        } catch (IOException io) {

        }
        finally {

        }
        return jsonObject;
    }


}
