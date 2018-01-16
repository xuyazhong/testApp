package com.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
/**
 * Created by xuyazhong on 2018/1/16.
 */

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("初始化", "init======");
    }

    //Callback 把结果传回去;
    public int ActionPlus() {
        int a = 1;
        int b = 2;
        int sum = a + b;
        return sum;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
