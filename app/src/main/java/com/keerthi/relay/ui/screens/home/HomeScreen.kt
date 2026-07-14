package com.keerthi.relay.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keerthi.relay.ui.components.FloatingNavBar
import com.keerthi.relay.ui.components.RatingBottomSheet
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedIndex by remember { mutableIntStateOf(0) }
    var showRatingSheet by remember { mutableStateOf(false) }

    // Simple state-based navigation for contact details
    var selectedContactName by remember { mutableStateOf<String?>(null) }
    var selectedContactNumber by remember { mutableStateOf<String?>(null) }

    val openDrawer: () -> Unit = {
        scope.launch { drawerState.open() }
    }

    if (selectedContactName != null && selectedContactNumber != null) {
        ContactDetailsScreen(
            contactName = selectedContactName!!,
            contactNumber = selectedContactNumber!!,
            onBack = {
                selectedContactName = null
                selectedContactNumber = null
            }
        )
        return
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Relay",
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8E67D8)
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Contacts,
                            contentDescription = null
                        )
                    },
                    label = { Text("Contacts") },
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null
                        )
                    },
                    label = { Text("Settings") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    ) {
        Scaffold(
            containerColor = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    when (selectedIndex) {
                        0 -> RecentsScreen(
                            onMenuClick = openDrawer,
                            onContactClick = { name, number ->
                                selectedContactName = name
                                selectedContactNumber = number
                            }
                        )
                        1 -> ContactsScreen(
                            onMenuClick = openDrawer,
                            onContactClick = { name, number ->
                                selectedContactName = name
                                selectedContactNumber = number
                            }
                        )
                        2 -> FavoritesScreen()
                        3 -> DialScreen()
                    }
                }

                FloatingNavBar(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 4.dp),
                    selectedIndex = selectedIndex,
                    onTabSelected = { selectedIndex = it }
                )
            }
        }
    }

    if (showRatingSheet) {
        RatingBottomSheet(
            onDismiss = { showRatingSheet = false }
        )
    }
}