package com.example.abdul.mobilesignal;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView signalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signalTextView = this.findViewById(R.id.signalTextView);

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
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

        setupSignalStrength();

    }

    public void setupSignalStrength() {
        final TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener phoneListener = new PhoneStateListener() {
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                if (manager != null) {
                    signalTextView.setText(String.format("%d", signalStrength.getLevel()));
//                    Toast.makeText(MainActivity.this, "level -> " + signalStrength.getLevel(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        manager.listen(phoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

}
