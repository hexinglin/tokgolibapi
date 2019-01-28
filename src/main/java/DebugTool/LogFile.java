/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DebugTool;

import FunctionTool.TimeTool;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author TOKGO
 */
public class LogFile {
    
    /**
     * 文件日志打印
     * @param filename 日志文件名
     * @param flag 日志标记
     * @param data 日志内容
     * @return 写入结果 成功或失败
     */
    public static boolean WriteLog(String filename,String flag, String data){
         FileWriter writer = null;  
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(filename, true);
        } catch (IOException ex) {
            if(!createDir(filename))
                return false;
             try {
                 writer = new FileWriter(filename, true);
             } catch (IOException ex1) {
                 return false;
             }
        }
        try {
            //重组日志数据
            String logstr =TimeTool.GetSystem()+": "+flag+"  -----------  "+data+"\r\n";
            //追加日志到文件中
            writer.write(logstr);       
        } catch (IOException e) {
            return false;
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
               return false;   
            }     
        } 
        return true; 
     }
    
    /**
     * 文件日志打印 日志存储位置默认：data/log.log
     * @param flag 日志标记
     * @param data 日志内容
     * @return 写入结果 成功或失败
     */
    public static boolean WriteLog(String flag, String data){
         return WriteLog("data/log.log",flag,data);
    }
    
    /**
     *创建目录
     * @param destDirName 文件名
     * @return 创建结果
     */
    private static boolean createDir(String destDirName) {
        String dirstr = destDirName.substring(0, destDirName.lastIndexOf('/')+1);
        File dir = new File(dirstr);
        // 判断目录是否存在
        if (dir.exists()) 
            return true;
        // 创建目标目录
        return dir.mkdirs();
    }
}
