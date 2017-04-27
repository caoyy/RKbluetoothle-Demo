package com.roky.demo.mock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 本地存储虚拟类
 * Created by YuanZhiJian on 16/5/24.
 */
public class DAOServiceApiMock {


    public static void saveAuthCode(Context context, String connectedDeviceAddress,String authCode) {

        //此处建议使用数据库进行处理,这里为mock代码使用xml 文件存储方式保存
        SharedPreferences mySharedPreferences= context.getSharedPreferences("DAOServiceApiMock",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(connectedDeviceAddress, authCode);
        editor.commit();

    }

    public static String getAuthCode(Context context, String connectedDeviceAddress){
        SharedPreferences mySharedPreferences= context.getSharedPreferences("DAOServiceApiMock",
                Activity.MODE_PRIVATE);
        return mySharedPreferences.getString(connectedDeviceAddress,"");
    }
}
