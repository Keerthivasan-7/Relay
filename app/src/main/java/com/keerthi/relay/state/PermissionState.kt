package com.keerthi.relay.state

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

enum class ContactPermissionState {
    GRANTED,
    DENIED,
    PERMANENTLY_DENIED
}

enum class CallLogsPermissionState {
    GRANTED,
    DENIED,
    PERMANENTLY_DENIED
}

fun checkContactsPermission(
    activity: Activity
): ContactPermissionState {

    val isGranted =
        ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

    if (isGranted) {
        return ContactPermissionState.GRANTED
    }

    val shouldShowRationale =
        ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            android.Manifest.permission.READ_CONTACTS
        )

    return if (shouldShowRationale) {
        ContactPermissionState.DENIED
    } else {
        ContactPermissionState.PERMANENTLY_DENIED
    }
}

fun checkCallLogsPermission(
    activity: Activity
): CallLogsPermissionState {

    val isGranted =
        ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.READ_CALL_LOG
        ) == PackageManager.PERMISSION_GRANTED

    if (isGranted) {
        return CallLogsPermissionState.GRANTED
    }

    val shouldShowRationale =
        ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            android.Manifest.permission.READ_CALL_LOG
        )

    return if (shouldShowRationale) {
        CallLogsPermissionState.DENIED
    } else {
        CallLogsPermissionState.PERMANENTLY_DENIED
    }
}