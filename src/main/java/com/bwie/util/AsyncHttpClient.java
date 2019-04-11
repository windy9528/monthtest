package com.bwie.util;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncHttpClient {

    private AsyncHttpClient instance = new AsyncHttpClient();

    private AsyncHttpClient(){};

    public AsyncHttpClient getInstance(){
        return instance;
    }

    public static String getDataFromServer(String url){

        HttpURLConnection connection = null;
        try {
            URL url1 = new URL(url);
            connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                return CharStreams.toString(new InputStreamReader(connection.getInputStream()));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public static void getDataFromServer(String url, final AsyncCallBack callBack){
        new AsyncTask<String, Void, String>(){

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //如果获取的数据不为空，则成功
                if(!TextUtils.isEmpty(s)){
                    callBack.onSuccess(s);
                }else {
                    callBack.onFaild();
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                return getDataFromServer(strings[0]);
            }
        }.execute(url);

    }

    //定义接口
    public interface AsyncCallBack{
        void onSuccess(String result);
        void onFaild();
    }
}
