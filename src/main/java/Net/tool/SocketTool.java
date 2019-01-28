/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Net.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 *
 * @author TOKGO
 */
public class SocketTool {
    private SocketTool(){}
    public static final int MAXBUF =65500;
    public static boolean IsDebug = false;
    public static final Random random = new Random();
    
    public static int GetRandom(int minI,int maxI){
        if (maxI<minI) 
            return 0;
       int num = random.nextInt(maxI-minI);
        num = num + minI;
       return num;
    }
    
   
    /**
     *流转换成字符串
     * @param is 流对象
     * @return  转化后的字符串 null代表为空
     */
     public static String StreamToString(InputStream is){
        try {
            //在读取的过程中，将读取的内容存储到缓存中，然后一次性转化为字符串返回
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[MAXBUF];
            //临时变量 用于记录读取长度
            int temp = -1;
            while ((temp = is.read(buffer))!=-1)
                bos.write(buffer, 0, temp);
            return bos.toString(); 
        } catch (IOException ex) {}
         return null;
     }
     
    
}
