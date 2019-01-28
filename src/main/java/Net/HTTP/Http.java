/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Net.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import Net.tool.SocketTool;

/**
 *
 * @author 11315
 */
public class Http {
    
    public static String Getfile(URL url) throws IOException{
        //开启一个链接
        HttpURLConnection connection =   (HttpURLConnection) url.openConnection();
        //设置请求超时
        connection.setConnectTimeout(2000);
        //设置读取超时
        connection.setReadTimeout(2000);
        //默认就是get请求方式
//        connection.setRequestMethod("POST");
        //获取请求响应码
        if(connection.getResponseCode() ==200){
           return SocketTool.StreamToString(connection.getInputStream());
        }
        throw new IOException("http get data error");
    }
    
    public static void Getfile(URL url,byte[] buffer) throws IOException{
        //开启一个链接
        HttpURLConnection connection =   (HttpURLConnection) url.openConnection();
        //设置请求超时
        connection.setConnectTimeout(2000);
        //设置读取超时
        connection.setReadTimeout(2000);
        //默认就是get请求方式
//        connection.setRequestMethod("POST");
        //获取请求响应码
        if(connection.getResponseCode() ==200){
            InputStream is = connection.getErrorStream();
           //在读取的过程中，将读取的内容存储到缓存中
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //临时变量 用于记录读取长度
            int temp = -1,len =0;
            while ((temp = is.read(buffer))!=-1){
                bos.write(buffer, len, temp);
                len += temp;
            }
        }
    }
    
}
