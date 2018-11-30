package com.example.abdul.mobilesignal;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        CellSignalStrengthGsm gsm = this.getSystemService(Context.TELEPHONY_SERVICE);
//        SignalStrength signalStrength = mTelephonyManager.getSignal;
//        CellSignalStrengthGsm gsm = new CellSignalStrengthGsm();

//        mTelephonyManager.listen(, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

        GsmCellLocation gsmCellLocation = new GsmCellLocation();
        Log.d(TAG, "onCreate: " + "gsmCellLocation.getCid() -> " + gsmCellLocation.getCid());
        Log.d(TAG, "onCreate: " + "gsmCellLocation.getLac() -> " + gsmCellLocation.getLac());
        Log.d(TAG, "onCreate: " + "gsmCellLocation.getPsc() -> " + gsmCellLocation.getPsc());

        CellInfoGsm cellinfogsm;
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
            CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
            Log.d(TAG, "onCreate: cellSignalStrengthGsm.getDbm() -> " + cellSignalStrengthGsm.getDbm());
            Log.d(TAG, "onCreate: cellSignalStrengthGsm.getAsuLevel() -> " + cellSignalStrengthGsm.getAsuLevel());
            Log.d(TAG, "onCreate: cellSignalStrengthGsm.getLevel() -> " + cellSignalStrengthGsm.getLevel());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
        }

    }

//    public void setupSignalStrength() {
//        final TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        PhoneStateListener phoneListener = new PhoneStateListener() {
//            @Override
//            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
//                if (manager.getNetworkOperator().equals("")) {
//                    signalIcon.setVisibility(View.GONE);
//                } else {
//                    signalIcon.setVisibility(View.VISIBLE);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                         See https://github.com/AlstonLin/TheLearningLock/issues/54
//                        Integer imageRes = signalStrengthToIcon.get(signalStrength.getLevel());
//                        if (imageRes != null) signalIcon.setImageResource(imageRes);
//                        else signalIcon.setImageResource(signalStrengthToIcon.get(4));
//                    } else {
//                         Just show the full icon
//                        signalIcon.setImageResource(signalStrengthToIcon.get(4));
//                    }
//                }
//            }
//        };
//        manager.listen(phoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
//    }

}
