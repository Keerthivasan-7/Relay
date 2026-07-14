package com.keerthi.relay.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NavItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun FloatingNavBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {

    val items = listOf(
        NavItem(" Recents", Icons.Default.Phone),
        NavItem(" Contacts", Icons.Default.Contacts),
        NavItem(" Favorites", Icons.Default.Favorite),
        NavItem(" Dial", Icons.Default.Dialpad)
    )

    Row(
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(Color(0xFFF3EEF9))
            .padding(8.dp),

        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items.forEachIndexed { index, item ->

            NavBarItem(
                item = item,
                selected = index == selectedIndex,
                onClick = {
                    onTabSelected(index)
                }
            )
        }
    }
}

@Composable
fun RowScope.NavBarItem(
    item: NavItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val weight by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (selected) 2.5f else 1f,
        label = "weight"
    )

    Box(
        modifier = Modifier
            .weight(weight)
            .height(56.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(
                if (selected)
                    Color(0xFF8E67D8)
                else
                    Color(0xFFE4D8F5)
            )
            .clickable {
                onClick()
            },

        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = Color.White
            )

            AnimatedVisibility(
                visible = selected
            ) {

                Row {
                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = item.title,
                        color = Color.White,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FloatingNavBar(
        modifier = Modifier,
        selectedIndex = 0,
        onTabSelected = {}
    )
}
