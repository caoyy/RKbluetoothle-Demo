package com.roky.demo;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.rokyinfo.ble.AuthCodeCreator;
import com.rokyinfo.ble.BleError;
import com.rokyinfo.ble.BleLog;
import com.rokyinfo.ble.toolbox.AuthCodeDeliverer;
import com.rokyinfo.ble.toolbox.RkBluetoothClient;
import com.rokyinfo.ble.toolbox.protocol.model.AuthResult;
import com.roky.demo.mock.DAOServiceApiMock;
import com.roky.demo.mock.HTTPServiceApiMock;
import com.roky.demo.mock.RkCCUDevice;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by apple on 16/5/24.
 */
public class App extends Application {

    private RkBluetoothClient rkBluetoothClient;

    private RkCCUDevice currentRkCCUDevice;

    /**
     * In practise you will use some kind of dependency injection pattern.
     */
    public static RkBluetoothClient getRkBluetoothClient(Context context) {
        App application = (App) context.getApplicationContext();
        return application.rkBluetoothClient;
    }

    public static RkCCUDevice getCurrentRkCCUDevice(Context context) {
        App application = (App) context.getApplicationContext();
        return application.currentRkCCUDevice;
    }

    public static void setCurrentRkCCUDevice(Context context, RkCCUDevice currentRkCCUDevice) {
        App application = (App) context.getApplicationContext();
        application.currentRkCCUDevice = currentRkCCUDevice;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //开启SDK log
        BleLog.setDEBUG(true);

        try {
            rkBluetoothClient = RkBluetoothClient.create(this);
            rkBluetoothClient.setQueueSize(1);
            rkBluetoothClient.getRk4103ApiService().setAuthCodeCreator(new AuthCodeCreator() {
                @Override
                public void getAuthCode(AuthCodeDeliverer callBack) {
                    obtainAuthCode(callBack);
                }
            });

            rkBluetoothClient.observeAuthResult().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<AuthResult>() {
                @Override
                public void call(AuthResult authResult) {

                    if (authResult.isSuccess()) {
                        //鉴权成功后，缓存到本地。
                        DAOServiceApiMock.saveAuthCode(getApplicationContext(), authResult.getMacAddress(), authResult.getAuthCodeStr());
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void obtainAuthCode(AuthCodeDeliverer callBack){
        //查询本地存储的当前手机，如果已经连接过设备并鉴权成功则直接获取该鉴权码，本地获取不到则从平台接口获取鉴权码
        String authCode = DAOServiceApiMock.getAuthCode(getApplicationContext(), currentRkCCUDevice.getMacAddress());
        if (!TextUtils.isEmpty(authCode)) {
            callBack.postAuthCode(authCode, 0, null);
        } else {
            //1.联网掉接口获取鉴权码
            HTTPServiceApiMock.getAuthCode(currentRkCCUDevice.getSn(), new HTTPServiceApiMock.AuthCodeCallback() {
                @Override
                public void success(String authCode) {
                    //2.提交鉴权码
                    callBack.postAuthCode(authCode, 0, null);
                }

                @Override
                public void error(Exception e) {
                    callBack.postAuthCode(null, 0, new BleError(e));
                }
            });
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        rkBluetoothClient.disconnect();
    }

}
