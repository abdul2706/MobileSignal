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
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static MainActivity context;

    private TextView textViewCDMADBM, textViewCDMAECIO, textViewEVDODBM, textViewEVDOECIO, textViewEVDOSNR, textViewGSMBITERRORRATE, textViewGSMSIGNALSTRENGTH, textViewGETLEVEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        textViewCDMADBM = this.findViewById(R.id.textViewCDMADBM);
        textViewCDMAECIO = this.findViewById(R.id.textViewCDMAECIO);
        textViewEVDODBM = this.findViewById(R.id.textViewEVDODBM);
        textViewEVDOECIO = this.findViewById(R.id.textViewEVDOECIO);
        textViewEVDOSNR = this.findViewById(R.id.textViewEVDOSNR);
        textViewGSMBITERRORRATE = this.findViewById(R.id.textViewGSMBITERRORRATE);
        textViewGSMSIGNALSTRENGTH = this.findViewById(R.id.textViewGSMSIGNALSTRENGTH);
        textViewGETLEVEL = this.findViewById(R.id.textViewGETLEVEL);

        CellInfoGsm cellinfogsm;
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(telephonyManager != null) {
                cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
                Log.d(TAG, "onCreate: cellSignalStrengthGsm.getDbm() -> " + cellSignalStrengthGsm.getDbm());
                Log.d(TAG, "onCreate: cellSignalStrengthGsm.getAsuLevel() -> " + cellSignalStrengthGsm.getAsuLevel());
                Log.d(TAG, "onCreate: cellSignalStrengthGsm.getLevel() -> " + cellSignalStrengthGsm.getLevel());
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
        }

        setupSignalStrength();

    }

    public static MainActivity getContext() {
        return context;
    }

    public void setupSignalStrength() {
        final TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener phoneListener = new PhoneStateListener() {
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                textViewCDMADBM.setText(String.format(Locale.getDefault(), "%d", signalStrength.getCdmaDbm()));
                textViewCDMAECIO.setText(String.format(Locale.getDefault(), "%d", signalStrength.getCdmaEcio()));
                textViewEVDODBM.setText(String.format(Locale.getDefault(), "%d", signalStrength.getEvdoDbm()));
                textViewEVDOECIO.setText(String.format(Locale.getDefault(), "%d", signalStrength.getEvdoEcio()));
                textViewEVDOSNR.setText(String.format(Locale.getDefault(), "%d", signalStrength.getEvdoSnr()));
                textViewGSMBITERRORRATE.setText(String.format(Locale.getDefault(), "%d", signalStrength.getGsmBitErrorRate()));
                textViewGSMSIGNALSTRENGTH.setText(String.format(Locale.getDefault(), "%d", signalStrength.getGsmSignalStrength()));
                textViewGETLEVEL.setText(String.format(Locale.getDefault(), "%d", signalStrength.getLevel()));
            }
        };
        if(manager != null) {
            manager.listen(phoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        } else {
            Toast.makeText(this, "TelephonyManager not Fount", Toast.LENGTH_LONG).show();
        }
    }

}
