package com.testapp;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
/**
 * Created by xuyazhong on 2018/1/16.
 */

public class AipModule extends ReactContextBaseJavaModule {
    private static final int REQUEST_CODE_CAMERA = 111;

    private Callback mCallback; // 保存回调

    private final ReactApplicationContext reactContext;

    @Override
    public String getName() {
        return "AipOCR";
    }

    public AipModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @ReactMethod
    public void actionIDCardOCRFront(Callback callback) {
        this.mCallback = callback;
        initAccessToken();

        Activity currentActivity = getCurrentActivity();

        Intent intent = new Intent(currentActivity.getApplicationContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(currentActivity.getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        currentActivity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /*初始化AccessToken*/
    private void initAccessToken() {
        Activity currentActivity = getCurrentActivity();

        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                Log.v("has token:", "success");
            }

            @Override
            public void onError(OCRError error) {
                Log.v("has not token:", "failure" + error.getMessage());
                error.printStackTrace();
                invokeError("licence方式获取token失败" + error.getMessage());
            }
        }, currentActivity.getApplicationContext());
    }

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
                RecognizeService.recBankCard(FileUtil.getSaveFile(activity.getApplicationContext()).getAbsolutePath(),
                        new RecognizeService.ServiceListener() {
                            @Override
                            public void onResult(String result) {
                                invokeSuccessWithResult(result);
                            }
                        });
            }
            if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                    String filePath = FileUtil.getSaveFile(activity.getApplicationContext()).getAbsolutePath();
                    if (!TextUtils.isEmpty(contentType)) {
                        if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                        }
                    }
                }
            }
        }
    };

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    if (result.getIdCardSide().equals("front")) {
                        JSONObject idCard = new JSONObject();
                        try {
                            idCard.put("address", ""+result.getAddress());
                            idCard.put("idNumber", ""+result.getIdNumber());
                            idCard.put("gender", ""+result.getGender());
                            idCard.put("name", ""+result.getName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        invokeSuccessWithResult(idCard.toString());
                    } else {
                        invokeError("null");
                    }
                } else {
                    invokeError("nil");
                }
            }

            @Override
            public void onError(OCRError error) {
                invokeError(error.getMessage());
            }
        });
    }


    /**
     * 识别成功时触发
     *
     */
    private void invokeSuccessWithResult(String result) {
        if (this.mCallback != null) {
            Log.v("-------------success:", result);
            this.mCallback.invoke(null, result);
            this.mCallback = null;
        }
    }

    /**
     * 失败时触发
     */
    private void invokeError(String result) {
        if (this.mCallback != null) {
            Log.v("++++++++++++++++error:", result);
            this.mCallback.invoke(result, null);
            this.mCallback = null;
        }
    }

}
