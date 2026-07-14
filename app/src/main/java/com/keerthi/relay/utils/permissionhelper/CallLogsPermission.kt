package com.keerthi.relay.utils.permissionhelper

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.keerthi.relay.logic.openAppSettings
import com.keerthi.relay.state.CallLogsPermissionState
import com.keerthi.relay.state.checkCallLogsPermission
import com.keerthi.relay.ui.components.MyButton
import com.keerthi.relay.ui.components.PermissionSettingsSheet

@Composable
fun CallLogsPermission(
    modifier: Modifier = Modifier,
    onPermissionGranted: @Composable () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity

    val lifecycleOwner = LocalLifecycleOwner.current

    var permissionPopup by remember {
        mutableStateOf(false)
    }

    var permissionState by remember {
        mutableStateOf(
            checkCallLogsPermission(activity)
        )
    }


    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { granted ->

            permissionState =
                if (granted) {
                    CallLogsPermissionState.GRANTED
                } else {
                    checkCallLogsPermission(activity)
                }
        }


    // Recheck when app returns from Settings
    DisposableEffect(lifecycleOwner) {

        val observer =
            LifecycleEventObserver { _, event ->

                if (event == Lifecycle.Event.ON_RESUME) {

                    permissionState =
                        checkCallLogsPermission(activity)
                }
            }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    when (permissionState) {

        CallLogsPermissionState.GRANTED -> {
            permissionPopup = false
            onPermissionGranted()
        }


        CallLogsPermissionState.DENIED -> {

            MyButton(
                text = "Get CallLogs",
                onClick = {
                    permissionLauncher.launch(
                        Manifest.permission.READ_CALL_LOG
                    )
                }
            )
        }


        CallLogsPermissionState.PERMANENTLY_DENIED -> {

            MyButton(
                text = "Get Call Logs",
                onClick = {
                    permissionPopup = true
                }
            )
        }
    }

    if (permissionPopup){
        PermissionSettingsSheet(
            title = "Enable Call Logs Permission",
            description = "Relay needs access to your call logs to display your recent call history.",
            onOpenSettings = {
                openAppSettings(context)
            },
            onDismiss = {
                permissionPopup = false
            }
        )
    }
}