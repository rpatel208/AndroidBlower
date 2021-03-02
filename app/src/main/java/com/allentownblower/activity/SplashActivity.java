package com.allentownblower.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.allentownblower.R;
import com.allentownblower.application.AllentownBlowerApplication;
import com.allentownblower.common.PrefManager;
import com.allentownblower.common.ResponseHandler;
import com.allentownblower.common.Utility;
import com.allentownblower.database.SqliteHelper;
import com.allentownblower.module.RackModel;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private AllentownBlowerApplication allentownBlowerApplication;
    private SqliteHelper dpHelper;
    private Activity act;
    private RackModel rackModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        act = this;
        Utility.setSoftInputAlwaysHide(act);
//        allentownBlowerApplication = (AllentownBlowerApplication) act.getApplication();
//        allentownBlowerApplication.getObserver().addObserver(this);
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        dpHelper = new SqliteHelper(act);
//        isSetUpCompleted = dpHelper.isSetupCompletedCheckFromDataBase();
        ArrayList<RackModel> arrRackList = new ArrayList<>();
        arrRackList = dpHelper.getDataFromRackSetUpTable();

        if (arrRackList.size() > 0) {
            rackModel = arrRackList.get(0);
        }

        ArrayList<RackModel> finalArrRackList = arrRackList;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              if (finalArrRackList.size() > 0) {
                  Intent intent = new Intent(act, HomeActivity.class);
                  startActivity(intent);
//                  finish();
              } else {
                  Intent intent = new Intent(act, RackSetUpNewActivity.class);
                  startActivity(intent);
//                  finish();
              }

            }
        }, 300);
    }
}