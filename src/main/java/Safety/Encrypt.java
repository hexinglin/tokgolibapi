/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Safety;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 11315
 */
public class Encrypt {
    
    /**
     *给指定字符串按照MD5算法加密
     * @param psd 需要加密的密码
     * @return MD5密文
     */
    private static String MD5encoder(String psd) throws NoSuchAlgorithmException{
        //指定加密算法类型
        MessageDigest digest = MessageDigest.getInstance("MD5");
        //将需要加密的字符串中转换成byte类型的数组，然后进行随机哈希过程
        byte[] bs = digest.digest(psd.getBytes());
        //循环遍历bs,然后让其生成32位字符串，固定写法
        StringBuilder stringBuffer = new StringBuilder();
        for(byte b :bs){
            int i = b & 0xff;
            String hexString = Integer.toHexString(i);
            if (hexString.length() <2){
                hexString = "0"+hexString;
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString();
    }
       
    
}
