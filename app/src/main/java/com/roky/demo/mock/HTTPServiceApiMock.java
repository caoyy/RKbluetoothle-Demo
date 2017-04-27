package com.roky.demo.mock;

/**
 * 网络接口虚拟类
 * Created by apple on 16/5/24.
 */
public class HTTPServiceApiMock {

//    Q1NsmKbbaf+mfktSpyNJ5w==
//    B00G10B6F3
//    C0:27:15:09:A7:E6

//    uEFmx5HRQ23oH1vy5yKIxw==
//    B00GFT30J4

//    icFqEzLDMAxWBGj/+2QB9w==
//    T0011B00E0

//    Q1NsmKbbaf+mfktSpyNJ5w==
//    C0:27:15:09:A7:E6

//    Shu35+z+7k+mpotqlddWfA==
//    C0:27:15:09:A7:E9

    public interface AuthCodeCallback{
        public void success(String authCode);
        public void error(Exception e);
    }

    public static void getAuthCode(String sn,AuthCodeCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (sn.equalsIgnoreCase("B00G7P0DD5")){
                    callback.success("zYTEKLfC/1vnnJHDRMTRcg==");
                } else {
                    callback.error(new Exception("不存在设备序列号"));
                }
            }
        }).start();
    }

}
