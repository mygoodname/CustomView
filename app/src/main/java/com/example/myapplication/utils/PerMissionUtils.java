package com.example.myapplication.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public  class PerMissionUtils {

    public static boolean check(Activity context){
       /* // 检查权限是否获取（android6.0及以上系统可能默认关闭权限，且没提示）
        PackageManager pm = context.getPackageManager();
        boolean permission_readStorage = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.READ_EXTERNAL_STORAGE", "packageName"));
        boolean permission_writeStorage = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", "packageName"));

        if (!(permission_readStorage && permission_writeStorage)) {
            ActivityCompat.requestPermissions(context, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
            }, 0x01);
        }*/
        List<String> permissions = new ArrayList<>();
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
       /* if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)) {
            permissions.add(android.Manifest.permission.CAMERA);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO)) {
            permissions.add(android.Manifest.permission.RECORD_AUDIO);
        }*/
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissions.add( Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissions.size() != 0) {
            ActivityCompat.requestPermissions(context,(String[]) permissions.toArray(new String[0]),0x01);
            return false;
        }
        return true;
    }
}
