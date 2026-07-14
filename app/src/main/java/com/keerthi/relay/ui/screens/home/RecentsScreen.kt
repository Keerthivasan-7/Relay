@file:OptIn(ExperimentalMaterial3Api::class)

package com.keerthi.relay.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.keerthi.relay.models.CallLogEntry
import com.keerthi.relay.ui.components.RecentsCard
import com.keerthi.relay.ui.components.RelaySearchBar
import com.keerthi.relay.utils.ContactFetcher
import com.keerthi.relay.utils.permissionhelper.CallLogsPermission

@Composable
fun RecentsScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onContactClick: (String, String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var callLogs by remember { mutableStateOf(emptyList<CallLogEntry>()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        RelaySearchBar(
            modifier = Modifier.fillMaxWidth(),
            searchText = searchText,
            onSearchTextChange = { searchText = it },
            onMenuClick = onMenuClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        CallLogsPermission(modifier = Modifier) {
            // Permission is granted, load call logs
            callLogs = remember(context) { ContactFetcher.fetchCallLogs(context) }

            val filteredCallLogs = callLogs.filter {
                (it.name?.contains(searchText, ignoreCase = true) ?: false) ||
                        it.number.contains(searchText)
            }

            if (filteredCallLogs.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchText.isEmpty()) "No call logs found" else "No matches found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredCallLogs) { log ->
                        RecentsCard(
                            log = log,
                            onCallClick = {
                                onContactClick(log.name ?: log.number, log.number)
                            }
                        )
                    }
                }
            }
        }
    }
}



