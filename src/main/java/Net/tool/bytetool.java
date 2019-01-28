/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Net.tool;

import java.io.IOException;

/**
 *
 * @author TOKGO
 */
public class bytetool {
    private bytetool(){}
    
    /**
     *
     * @param ch
     * @param data
     * @param length
     * @return
     */
    public static int findchar(char ch ,byte[] data,int length){
        for(int i=0;i<length;i++)
            if(data[i]==ch)
                return i;
        return -1;
    }
    
    public static boolean Equal(byte[] data1,byte[] data2,int len){
        if(data1.length<len)
            return false;
        if(data2.length<len)
            return false;
        for(int i=0;i<len;i++)
            if(data1[i]!=data2[i])
                return false;
        return true;
    }
    
    public static byte[] SubByte(byte[] data,int off,int len) throws IOException{
        if(data.length<(off+len))
            throw new IOException("len to long");
        if(off<0||len<1)
            throw new IOException("Parameter is not valid");
        byte[] newbyte = new byte[len];
        System.arraycopy(data, off, newbyte, 0, len);
        return newbyte;
    }
    public static void CopyByte(byte [] orgBses,byte[] data,int len){
        for (int i = 0; i < len; i++) 
            data[i]= orgBses[i];
    }
    
    public static void SubbyteArray(byte[] data,int off,int len){
        int i=off,j=0;
        for(;j<len;j++,i++)
            data[j]= data[i];
    }
}
