package com.keerthi.relay.utils.permissionhelper

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
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
import com.keerthi.relay.state.ContactPermissionState
import com.keerthi.relay.state.checkContactsPermission
import com.keerthi.relay.ui.components.MyButton
import com.keerthi.relay.ui.components.PermissionSettingsSheet

@Composable
fun ContactsPermission(
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
            checkContactsPermission(activity)
        )
    }


    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { granted ->

            permissionState =
                if (granted) {
                    ContactPermissionState.GRANTED
                } else {
                    checkContactsPermission(activity)
                }
        }


    // Recheck when app returns from Settings
    DisposableEffect(lifecycleOwner) {

        val observer =
            LifecycleEventObserver { _, event ->

                if (event == Lifecycle.Event.ON_RESUME) {

                    permissionState =
                        checkContactsPermission(activity)
                }
            }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    when (permissionState) {

        ContactPermissionState.GRANTED -> {
            permissionPopup = false
            onPermissionGranted()
        }


        ContactPermissionState.DENIED -> {

            MyButton(
                text = "Grant Contacts Permission",
                onClick = {
                    permissionLauncher.launch(
                        Manifest.permission.READ_CONTACTS
                    )
                }
            )
        }


        ContactPermissionState.PERMANENTLY_DENIED -> {

            MyButton(
                text = "Open Settings",
                onClick = {
                    permissionPopup = true
                }
            )
        }
    }

    if (permissionPopup){
        PermissionSettingsSheet(
            title = "Enable Contacts Permission",
            description = "Relay needs access to your contacts to help you search, organize and connect with people faster.",
            onOpenSettings = {
                openAppSettings(context)
            },
            onDismiss = {
                permissionPopup = false
            }
        )
    }
}