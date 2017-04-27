package com.roky.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.rokyinfo.ble.toolbox.protocol.Rk4103ApiService;
import com.rokyinfo.ble.toolbox.protocol.model.BatteryParameter;
import com.rokyinfo.ble.toolbox.protocol.model.ConfigResult;
import com.rokyinfo.ble.toolbox.protocol.model.ElectricMachineParameter;
import com.rokyinfo.ble.toolbox.protocol.model.FirmwareVersion;
import com.rokyinfo.ble.toolbox.protocol.model.GearParameter;
import com.rokyinfo.ble.toolbox.protocol.model.MotorControllerParameter;
import com.rokyinfo.ble.toolbox.protocol.model.RK4102ECUParameter;
import com.rokyinfo.ble.toolbox.protocol.model.RK4103Fault;
import com.rokyinfo.ble.toolbox.protocol.model.RemainderRangeStatus;
import com.rokyinfo.ble.toolbox.protocol.model.RemoteControlResult;
import com.rokyinfo.ble.toolbox.protocol.model.RemoteController;
import com.rokyinfo.ble.toolbox.protocol.model.SpeedParameter;
import com.rokyinfo.ble.toolbox.protocol.model.VehicleStatus;
import com.roky.demo.mock.RkCCUDevice;
import rx.Observable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RkCCUDevice mRkCCUDevice = new RkCCUDevice();
        mRkCCUDevice.setSn("B00G7P0DD5");
        mRkCCUDevice.setMacAddress("C0:27:15:09:D1:5C");
        App.setCurrentRkCCUDevice(this, mRkCCUDevice);
    }

    public void lock(View view) {

        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();
        Observable<RemoteControlResult> mObservable = mRk4103ApiService.lock(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(mRemoteControlResult -> {
            Log.e("cyy", "isSuccess:" + mRemoteControlResult.isSuccess());
            if (mRemoteControlResult.isSuccess()) {
                Toast.makeText(this, "设防成功", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });

    }

    public void unlock(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        Observable<RemoteControlResult> mObservable = mRk4103ApiService.unlock(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(mRemoteControlResult -> {
            Log.e("cyy", "isSuccess:" + mRemoteControlResult.isSuccess());
            if (mRemoteControlResult.isSuccess()) {
                Toast.makeText(this, "撤防成功", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });

    }

    public void find(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        Observable<RemoteControlResult> mObservable = mRk4103ApiService.find(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(mRemoteControlResult -> {
            Log.e("cyy", "isSuccess:" + mRemoteControlResult.isSuccess());
            if (mRemoteControlResult.isSuccess()) {
                Toast.makeText(this, "寻车成功", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });

    }

    public void openBox(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();

        Observable<RemoteControlResult> mObservable = mRk4103ApiService.openBox(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(mRemoteControlResult -> {
            Log.e("cyy", "isSuccess:" + mRemoteControlResult.isSuccess());
            if (mRemoteControlResult.isSuccess()) {
                Toast.makeText(this, "打开坐桶盖成功", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });

    }

    public void getCarStatus(View view) {

        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();
        Observable<VehicleStatus> mObservable = mRk4103ApiService.getVehicleStatus(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(vehicleStatus -> {
            Toast.makeText(this, vehicleStatus.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + vehicleStatus.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getFault(View view) {

        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();
        Observable<RK4103Fault> mObservable = mRk4103ApiService.getFault(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(fault -> {
            Toast.makeText(this, fault.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + fault.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void setCenterControlParams(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        RK4102ECUParameter ecuParameter = new RK4102ECUParameter();
        ecuParameter.setVibrationLevel(1);
        ecuParameter.setLcdScreenCode(3);
        ecuParameter.setTraRemoteControlSwitch(1);

        Observable<ConfigResult> mObservable = mRk4103ApiService.setECUParameter(App.getCurrentRkCCUDevice(this).getMacAddress(), ecuParameter);

        mObservable.subscribe(configResult -> {
            Toast.makeText(this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + configResult.isSuccess());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getCenterControlParams(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        Observable<RK4102ECUParameter> mObservable = mRk4103ApiService.getECUParameter(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(ecuParameter -> {
            Toast.makeText(this, ecuParameter.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + ecuParameter.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getFirmwareVersion(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        Observable<FirmwareVersion> mObservable = mRk4103ApiService.getFirmwareVersion(App.getCurrentRkCCUDevice(this).getMacAddress(), 0);

        mObservable.subscribe(firmwareVersion -> {
            Toast.makeText(this, firmwareVersion.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + firmwareVersion.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getRemoteControllers(View view) {

        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();
        for (int i = 1; i <= 19; i++) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Observable<RemoteController> mObservable = mRk4103ApiService.getRemoteControllers(App.getCurrentRkCCUDevice(this).getMacAddress(), i);

            mObservable.subscribe(remoteController -> {
                Toast.makeText(this, remoteController.toString(), Toast.LENGTH_SHORT).show();
                Log.e("cyy", "" + remoteController.toString());
            }, throwable -> {
                Log.e("cyy", "" + throwable);
            });
        }

    }

    public void syncRemoteControllers(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        RemoteController remoteController = new RemoteController();
        remoteController.setIndex(4);
        remoteController.setMacAddress("302341113278");

        Observable<ConfigResult> mObservable = mRk4103ApiService.syncRemoteController(App.getCurrentRkCCUDevice(this).getMacAddress(), remoteController);

        mObservable.subscribe(configResult -> {
            Toast.makeText(this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + String.valueOf(configResult.isSuccess()));
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void setRemainderRange(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        Observable<ConfigResult> mObservable = mRk4103ApiService.setRemainderRange(App.getCurrentRkCCUDevice(this).getMacAddress(), true);

        mObservable.subscribe(configResult -> {
            Toast.makeText(this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + String.valueOf(configResult.isSuccess()));
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getRemainderRangeStatus(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        Observable<RemainderRangeStatus> mObservable = mRk4103ApiService.getRemainderRangeStatus(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(remainderRangeStatus -> {
            Toast.makeText(this, remainderRangeStatus.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + remainderRangeStatus.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void setBatteryParameter(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        BatteryParameter batteryParameter = new BatteryParameter();
        batteryParameter.setRatedVoltage(12);
        batteryParameter.setBatteryNumber(3);
        batteryParameter.setAhAmount(16);
        batteryParameter.setDischargeCoefficient(220);
        batteryParameter.setBatteryAttenuationCoefficient(99);
        batteryParameter.setTemperatureCoefficient(78);
        batteryParameter.setDefaultMileage(110);
        batteryParameter.setEndurMileageCompParams(108);
        batteryParameter.setUpperLimitVoltage(6321);
        batteryParameter.setLowerLimitVoltage(5765);
        batteryParameter.setAlarmVoltage(6275);

        Observable<ConfigResult> mObservable = mRk4103ApiService.setBatteryParameter(App.getCurrentRkCCUDevice(this).getMacAddress(), batteryParameter);

        mObservable.subscribe(configResult -> {
            Toast.makeText(this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + String.valueOf(configResult.isSuccess()));
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getBatteryParameter(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        Observable<BatteryParameter> mObservable = mRk4103ApiService.getBatteryParameter(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(batteryParameter -> {
            Toast.makeText(this, batteryParameter.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + batteryParameter.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void setElectricMachineParameter(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        ElectricMachineParameter electricMachineParameter = new ElectricMachineParameter();
        electricMachineParameter.setMotorHolzerAngle(2);
        electricMachineParameter.setMotorPhase(3);
        electricMachineParameter.setMaximumCurrentLimit(23);
        electricMachineParameter.setMotorPolePairs(18);
        electricMachineParameter.setMotorWheelDiameter(19);
        electricMachineParameter.setMotorForwardParameter(22222);

        Observable<ConfigResult> mObservable = mRk4103ApiService.setElectricMachineParameter(App.getCurrentRkCCUDevice(this).getMacAddress(), electricMachineParameter);

        mObservable.subscribe(configResult -> {
            Toast.makeText(this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + String.valueOf(configResult.isSuccess()));
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getElectricMachineParameter(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        Observable<ElectricMachineParameter> mObservable = mRk4103ApiService.getElectricMachineParameter(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(electricMachineParameter -> {
            Toast.makeText(this, electricMachineParameter.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + electricMachineParameter.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void setMotorControllerParameter(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        MotorControllerParameter motorControllerParameter = new MotorControllerParameter();
        motorControllerParameter.setReusableParams(45);
        motorControllerParameter.setPwmDeadTime(86);

        Observable<ConfigResult> mObservable = mRk4103ApiService.setMotorControllerParameter(App.getCurrentRkCCUDevice(this).getMacAddress(), motorControllerParameter);

        mObservable.subscribe(configResult -> {
            Toast.makeText(this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + String.valueOf(configResult.isSuccess()));
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getMotorControllerParameter(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        Observable<MotorControllerParameter> mObservable = mRk4103ApiService.getMotorControllerParameter(App.getCurrentRkCCUDevice(this).getMacAddress());

        mObservable.subscribe(motorControllerParameter -> {
            Toast.makeText(this, motorControllerParameter.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + motorControllerParameter.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void setSpeedParameter(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();


        SpeedParameter speedParameter = new SpeedParameter();
        speedParameter.setMaxSpeed(70);
        speedParameter.setPushSpeed(11);
        speedParameter.setBackSpeed(12);
        speedParameter.setRepairSpeed(2);
        Log.d("cyy", "start set:" + speedParameter.toString());

        Observable<ConfigResult> mObservable = mRk4103ApiService.setSpeedParameter(App.getCurrentRkCCUDevice(this).getMacAddress(), speedParameter);

        mObservable.subscribe(configResult -> {
            Toast.makeText(this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + String.valueOf(configResult.isSuccess()));
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getSpeedParameter(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();
        Observable<SpeedParameter> mObservable = mRk4103ApiService.getSpeedParameter(App.getCurrentRkCCUDevice(this).getMacAddress());
        mObservable.subscribe(speedParameter -> {
            Toast.makeText(this, speedParameter.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + speedParameter.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void setGearParameter(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();

        GearParameter gearParameter = new GearParameter();
        gearParameter.setGearCount(3);
        gearParameter.setMaxLimitSpeedPercentage1(1);
        gearParameter.setMaxLimitCurrentPercentage1(2);
        gearParameter.setSpeedUpFactor1(3);
        gearParameter.setSpeedDownFactor1(4);

        gearParameter.setMaxLimitSpeedPercentage2(11);
        gearParameter.setMaxLimitCurrentPercentage2(12);
        gearParameter.setSpeedUpFactor2(13);
        gearParameter.setSpeedDownFactor2(14);

        gearParameter.setMaxLimitSpeedPercentage3(21);
        gearParameter.setMaxLimitCurrentPercentage3(22);
        gearParameter.setSpeedUpFactor3(23);
        gearParameter.setSpeedDownFactor3(24);
        Log.d("cyy", "start set:" + gearParameter.toString());

        Observable<ConfigResult> mObservable = mRk4103ApiService.setGearParameter(App.getCurrentRkCCUDevice(this).getMacAddress(), gearParameter);
        mObservable.subscribe(configResult -> {
            Toast.makeText(this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + String.valueOf(configResult.isSuccess()));
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void getGearParameter(View view) {

        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();
        Observable<GearParameter> mObservable = mRk4103ApiService.getGearParameter(App.getCurrentRkCCUDevice(this).getMacAddress());
        mObservable.subscribe(gearParameter -> {
            Toast.makeText(this, gearParameter.toString(), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + gearParameter.toString());
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

    public void setTurboParameter(View view) {
//        new MaterialDialog.Builder(this)
//                .title("云动力")
//                .content("示例：12:23:54（高:中:低）")
//                .inputType(InputType.TYPE_CLASS_TEXT)
//                .input("请以冒号分割输入高中低3个字节", turboparas.getText(), new MaterialDialog.InputCallback() {
//                    @Override
//                    public void onInput(MaterialDialog dialog, CharSequence input) {
//                        if(!TextUtils.isEmpty(input)){
//                            String[] strArray = input.toString().split(":");
//                            if(strArray != null && strArray.length ==3){
//                                int high = Integer.parseInt(strArray[0].trim());
//                                int mid = Integer.parseInt(strArray[1].trim());
//                                int low = Integer.parseInt(strArray[2].trim());
//                                if(high > 255 || mid > 255 || low > 255){
//                                    Toast.makeText(MainActivity.this, "每个字节的大小不能超过255", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(MainActivity.this).getRk4103ApiService();
//                                TurboParameter turboParameter = new TurboParameter();
//                                turboParameter.setHighGear(high);
//                                turboParameter.setMidGear(mid);
//                                turboParameter.setLowGear(low);
//                                Log.d("cyy", "start set:" + turboParameter.toString());
//                                Observable<ConfigResult> mObservable = mRk4103ApiService.setTurboParameter(App.getCurrentRkCCUDevice(MainActivity.this).getMacAddress(), turboParameter);
//                                mObservable.subscribe(configResult -> {
//                                    Toast.makeText(MainActivity.this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
//                                    turboparas.setText(input);
//                                    Log.e("cyy", "" + String.valueOf(configResult.isSuccess()));
//                                }, throwable -> {
//                                    Log.e("cyy", "" + throwable);
//                                });
//                            }
//                        }
//                    }
//                }).show();
    }

    public void setGatewayStatus(View view) {
        Rk4103ApiService mRk4103ApiService = App.getRkBluetoothClient(this).getRk4103ApiService();
        Observable<ConfigResult> mObservable = mRk4103ApiService.setGatewayStatus(App.getCurrentRkCCUDevice(this).getMacAddress(),"", true);
        mObservable.subscribe(configResult -> {
            Toast.makeText(this, String.valueOf(configResult.isSuccess()), Toast.LENGTH_SHORT).show();
            Log.e("cyy", "" + String.valueOf(configResult.isSuccess()));
        }, throwable -> {
            Log.e("cyy", "" + throwable);
        });
    }

}
