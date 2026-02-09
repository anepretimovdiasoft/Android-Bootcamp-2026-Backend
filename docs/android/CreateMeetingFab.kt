import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateMeetingFab(onCreateClick: () -> Unit) {
    var showLabel by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            visible = showLabel,
            enter = fadeIn() + slideInHorizontally { it },
            exit = fadeOut() + slideOutHorizontally { it }
        ) {
            Surface(
                color = Color(0xE6E9EDFF),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Создать встречу",
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .clickable {
                            showLabel = false
                            onCreateClick()
                        },
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        FloatingActionButton(
            onClick = {
                showLabel = true
                scope.launch {
                    delay(2000)
                    showLabel = false
                }
            },
            containerColor = Color(0xFF00C8F2),
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
        }
    }
}

@Composable
fun ExtendedCreateFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(Icons.Filled.Add, null) },
        text = { Text("Создать встречу") },
        containerColor = Color(0xFF1D2939),
        contentColor = Color.White,
        shape = CircleShape
    )
}
