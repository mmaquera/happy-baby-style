import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmaquera.happybabystyle.ui.theme.BorderLight
import com.mmaquera.happybabystyle.ui.theme.PrimaryText
import com.mmaquera.happybabystyle.ui.theme.Secondary

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean = false,
    enabled: Boolean = true,
    @StringRes textResId: Int
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFFfabac2) else Color(0xFFF5F0F0),
            contentColor = Color(0xFF171212),
            disabledContainerColor = Color(0xFFE5DBDB),
            disabledContentColor = Color(0xFF8A6163)
        ),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp)
    ) {
        Text(
            text = stringResource(textResId),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean = false,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFFfabac2) else Secondary,
            contentColor = PrimaryText, // #171212 from Figma (ver Color.kt)
            disabledContainerColor = BorderLight, // #e5dbdb (ver Color.kt)
            disabledContentColor = Secondary // #8a6163 (ver Color.kt)
        ),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp)
    ) {
        content()
    }
}