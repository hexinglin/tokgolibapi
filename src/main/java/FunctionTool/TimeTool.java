/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FunctionTool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author TOKGO
 */
public class TimeTool {
    
    public static String GetSystem(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return (df.format(new Date()));// new Date()为获取当前系统时间
    }
}
