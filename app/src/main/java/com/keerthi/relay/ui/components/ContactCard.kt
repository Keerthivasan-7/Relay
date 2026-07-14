package com.keerthi.relay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keerthi.relay.models.Contact

@Composable
fun ContactCard(
    contact: Contact,
    onClick: () -> Unit = {},
    onCallClick: (() -> Unit)? = null,
    onMessageClick: (() -> Unit)? = null,
    showDivider: Boolean = false
) {
    val initials = remember(contact.name) {
        val parts = contact.name.split(" ").filter { it.isNotEmpty() }
        if (parts.isEmpty()) "?"
        else parts.take(2).map { it[0].uppercase() }.joinToString("")
    }

    val avatarColor = remember(contact.name) {
        val colors = listOf(
            Color(0xFFEF9A9A), Color(0xFFF48FB1), Color(0xFFCE93D8),
            Color(0xFFB39DDB), Color(0xFF9FA8DA), Color(0xFF90CAF9),
            Color(0xFF81D4FA), Color(0xFF80DEEA), Color(0xFF80CBC4),
            Color(0xFFA5D6A7), Color(0xFFC5E1A5), Color(0xFFE6EE9C)
        )
        val hash = kotlin.math.abs(contact.name.hashCode())
        colors[hash % colors.size]
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(avatarColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = Color.Black
                )
                if (contact.number.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = contact.number,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            if (onMessageClick != null || onCallClick != null) {
                Row {
                    onMessageClick?.let {
                        IconButton(onClick = it) {
                            Icon(
                                imageVector = Icons.Default.Message,
                                contentDescription = "Message",
                                tint = Color(0xFF8E67D8)
                            )
                        }
                    }
                    onCallClick?.let {
                        IconButton(onClick = it) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Call",
                                tint = Color(0xFF4CAF50)
                            )
                        }
                    }
                }
            }
        }
        
        if (showDivider) {
            Divider(
                modifier = Modifier.padding(start = 80.dp, end = 16.dp),
                color = Color(0xFFF0F0F0)
            )
        }
    }
}
