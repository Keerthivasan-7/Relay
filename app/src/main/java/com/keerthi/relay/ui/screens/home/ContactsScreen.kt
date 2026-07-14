package com.keerthi.relay.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keerthi.relay.models.Contact
import com.keerthi.relay.ui.components.ContactCard
import com.keerthi.relay.ui.components.RelaySearchBar
import com.keerthi.relay.utils.ContactFetcher
import com.keerthi.relay.utils.permissionhelper.ContactsPermission
import kotlinx.coroutines.launch

@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onContactClick: (String, String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var contacts by remember { mutableStateOf(emptyList<Contact>()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        RelaySearchBar(
            modifier = Modifier.fillMaxWidth(),
            searchText = searchText,
            onSearchTextChange = { searchText = it },
            onMenuClick = onMenuClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        ContactsPermission(modifier = Modifier) {
            // Permission is granted, load contacts
            contacts = remember(context) { ContactFetcher.fetchContacts(context) }

            val filteredContacts = contacts.filter {
                it.name.contains(searchText, ignoreCase = true) ||
                        it.number.contains(searchText)
            }

            if (filteredContacts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchText.isEmpty()) "No contacts found" else "No matches found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                val listState = androidx.compose.foundation.lazy.rememberLazyListState()
                val coroutineScope = androidx.compose.runtime.rememberCoroutineScope()
                
                Row(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        items(filteredContacts) { contact ->
                            ContactCard(
                                contact = contact, 
                                onClick = { onContactClick(contact.name, contact.number) },
                                showDivider = true
                            )
                        }
                    }
                    
                    val alphabet = ('A'..'Z').map { it.toString() } + "#"
                    AlphabetScroller(
                        letters = alphabet,
                        onLetterSelected = { letter ->
                            val index = filteredContacts.indexOfFirst {
                                if (letter == "#") {
                                    !it.name.first().isLetter()
                                } else {
                                    it.name.startsWith(letter, ignoreCase = true)
                                }
                            }
                            if (index != -1) {
                                coroutineScope.launch {
                                    listState.scrollToItem(index)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun AlphabetScroller(
    letters: List<String>,
    onLetterSelected: (String) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    var selectedLetter by remember { mutableStateOf("") }
    var isDragging by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
        // Divider showing the selected letter
        if (isDragging && selectedLetter.isNotEmpty()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = selectedLetter,
                    style = MaterialTheme.typography.headlineLarge.copy(
    
                        fontSize = 32.sp,
                        color = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Divider(
                    modifier = Modifier.width(24.dp),
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }

        Box(
            modifier = Modifier
                .width(32.dp)
                .fillMaxHeight()
                .padding(vertical = 16.dp)
        ) {
            // Heart icon at the top
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Favorite,
                contentDescription = "Favorites",
                tint = Color.Red,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.TopCenter)
            )
            
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 24.dp)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onDragStart = { isDragging = true },
                            onDragEnd = {
                                isDragging = false
                                selectedIndex = -1
                                selectedLetter = ""
                            },
                            onDragCancel = {
                                isDragging = false
                                selectedIndex = -1
                                selectedLetter = ""
                            }
                        ) { change, _ ->
                            val y = change.position.y
                            val height = this.size.height - 24.dp.toPx()
                            val itemHeight = height / letters.size
                            val index = (y / itemHeight).toInt().coerceIn(0, letters.size - 1)
                            if (index != selectedIndex) {
                                selectedIndex = index
                                selectedLetter = letters[index]
                                onLetterSelected(selectedLetter)
                            }
                        }
                    },
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                letters.forEachIndexed { index, letter ->
                    Text(
                        text = letter,
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
            }
        }
    }
}
