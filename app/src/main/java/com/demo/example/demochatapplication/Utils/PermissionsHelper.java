package com.demo.example.demochatapplication.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;

import com.demo.example.demochatapplication.R;

import java.util.ArrayList;

/**
 * Created by poonampatel on 23/02/18.
 */

public class PermissionsHelper
{
    Context _context;

    private String[] PERMISSIONS_ARRAY = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION//,
//            Manifest.permission.READ_SMS
    };

    public static int APP_PERMISSIONS_CODE = 100;

    public PermissionsHelper(Context context)
    {
        _context = context;
    }

    /**
     * to check whether user has granted permission or not
     *
     * @param permissionName
     * @return
     */
    public boolean isPermissionGranted(String permissionName)
    {
        return ContextCompat.checkSelfPermission(_context, permissionName) == PackageManager.PERMISSION_GRANTED;
    }

    private String[] checkRemainingPermissions()
    {
        ArrayList<String> remainingPermissionsList = new ArrayList<>();
        for (String permission : PERMISSIONS_ARRAY)
        {
            if (!isPermissionGranted(permission))
            {
                remainingPermissionsList.add(permission);
            }
        }
        String[] remainingPermissionsArray = new String[remainingPermissionsList.size()];
        return remainingPermissionsList.toArray(remainingPermissionsArray);
    }

    public void requestRemaniningPermissionsfromUser(Activity requestActivity)
    {
        String[] permissions = checkRemainingPermissions();
        if (permissions.length > 0)
        {
            ActivityCompat.requestPermissions(requestActivity, permissions, PermissionsHelper.APP_PERMISSIONS_CODE);
        }
    }

    /**
     * checks if permission granted or denied
     *
     * @param grantResults
     * @return
     */
    public boolean checkPermissionRequestResults(@NonNull int[] grantResults)
    {
        for (int grantResult : grantResults)
        {
            if (grantResult == PackageManager.PERMISSION_DENIED)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * alert for showing permission dialog
     *
     * @param missingPermissionName
     * @param requestActivity
     * @param shouldExitScreen
     */
    public void showMissingPermissionDialog(String missingPermissionName, final Activity requestActivity, final boolean shouldExitScreen)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requestActivity);
        dialogBuilder.setTitle(_context.getResources().getString(R.string.permission_alert));
        dialogBuilder.setMessage(missingPermissionName + " " + _context.getResources().getString(R.string.permission_message));
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (shouldExitScreen)
                {
                    requestActivity.finish();
                }
            }
        });
        dialogBuilder.setPositiveButton("Go To Settings", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                startAppSettings();
                dialog.dismiss();
                if (shouldExitScreen)
                {
                    requestActivity.finish();
                }
            }
        });
        AppCompatDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    /**
     * As per the permission the app sets setting
     */
    private void startAppSettings()
    {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + _context.getPackageName()));
        _context.startActivity(intent);
    }
}
