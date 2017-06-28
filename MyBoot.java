package com.xuexue.vince.prevent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by dennis on 2017/6/20.
 */

public class MyBootReceiver extends BroadcastReceiver{
    SharedPreferences sp;
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("----------->防盗软件监听到了开机广播");

        sp=context.getSharedPreferences("usersetting",Context.MODE_PRIVATE);

        if (sp.getBoolean("safe",false)){
            String safeNum=sp.getString("safeNumber",null);

            if (!TextUtils.isEmpty(safeNum)){
                SmsManager smsManager = SmsManager.getDefault();
               ArrayList<String> msgs=smsManager.divideMessage("你的手机可能已经被盗，此处是被盗者的信息");

                for (String msg : msgs){
                    smsManager.sendTextMessage(safeNum,null,msg,null,null);
                }
            }
        }
    }
}
