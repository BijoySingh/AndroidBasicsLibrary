package com.github.bijoysingh.starter.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for Marshmallow permissions
 * Created by bijoy on 2/14/16.
 */
public class PermissionManager {
    public static final Integer REQUEST_CODE_ASK_PERMISSIONS = 1201;

    private Activity activity;
    private String[] permissions;

    /**
     * Constructor for the Permission Manager
     *
     * @param activity the activity which will request the permissions
     */
    public PermissionManager(Activity activity) {
        this.activity = activity;
        this.permissions = new String[]{};
    }

    /**
     * Constructor for the Permission Manager
     *
     * @param activity    the activity which will request the permissions
     * @param permissions the list of permissions requested
     */
    public PermissionManager(Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;
    }

    /**
     * Set the requested permissions
     *
     * @param permissions the permissions which are needed
     */
    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    /**
     * Checks if the application has the permission
     *
     * @param permission the permission
     * @return true if has permission
     */
    public Boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission)
            == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Returns a list of missing permissions
     *
     * @return the list permissions which the app does not have
     */
    public String[] getMissingPermissions() {
        List<String> missingPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                missingPermissions.add(permission);
            }
        }

        String[] missingPermissionsArray = new String[missingPermissions.size()];
        missingPermissions.toArray(missingPermissionsArray);
        return missingPermissionsArray;
    }

    /**
     * Returns if the application already has all desired permissions
     *
     * @return has all permissions
     */
    public Boolean hasAllPermissions() {
        String[] missingPermissions = getMissingPermissions();
        return missingPermissions.length == 0;
    }

    /**
     * Request for permissions if the phone sdk > 23 and any permission is missing
     *
     * @param requestCode the request code
     */
    public void requestPermissions(Integer requestCode) {
        if (Build.VERSION.SDK_INT >= 23 && !hasAllPermissions()) {
            activity.requestPermissions(getMissingPermissions(), requestCode);
        }
    }

    /**
     * Request for permissions if the phone sdk > 23 and any permission is
     * missing with default request code
     */
    public void requestPermissions() {
        requestPermissions(REQUEST_CODE_ASK_PERMISSIONS);
    }
}
