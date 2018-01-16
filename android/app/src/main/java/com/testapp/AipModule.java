package com.testapp;

import com.facebook.react.bridge.ReactContextBaseJavaModule;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
/**
 * Created by xuyazhong on 2018/1/16.
 */

public class AipModule extends ReactContextBaseJavaModule {

    private Callback mCallback; // 保存回调

    private final ReactApplicationContext reactContext;

    @Override
    public String getName() {
        return "Aip";
    }

    public AipModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void actionPlus(int a, int b, Callback callback) {

        this.mCallback = callback;
        openActivity();

    }

    @ReactMethod
    public void actionMult(int a, int b, Callback callback) {

        this.mCallback = callback;
        openActivity();

    }

    @ReactMethod
    public void actionSub(int a, int b, Callback callback) {

        this.mCallback = callback;
        openActivity();

    }

    private void openActivity() {

        try {
            Log.v("打开NewActivity:", "begin");
            Activity currentActivity = getCurrentActivity();
            Intent intent = new Intent(currentActivity, NewActivity.class);
            currentActivity.startActivity(intent);
        } catch (Exception e){
            Log.v("无法打开NewActivity页面:", e.getMessage());
        }

        //我想调用ActionPlus把结果放到result，然后关闭NewActivity

        this.mCallback.invoke(null, "result");

    }

}
