package com.keerthi.relay.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingBottomSheet(
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        RatingSheet(onDismiss = onDismiss)
    }
}

@Composable
fun RatingSheet(onDismiss: () -> Unit) {
    val context = LocalContext.current
    var rating by remember { mutableIntStateOf(0) }
    var comments by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How are we doing?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8E67D8)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            repeat(5) { index ->
                val starIndex = index + 1
                val isSelected = starIndex <= rating
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star $starIndex",
                    tint = if (isSelected) Color(0xFFB6F146) else Color.LightGray,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { rating = starIndex }
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = comments,
            onValueChange = { comments = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            placeholder = { Text("Share your thoughts...") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (rating == 0) {
                    Toast.makeText(context, "Please select a rating", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
                    onDismiss()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8E67D8)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Rate Now")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}