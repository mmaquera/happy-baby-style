package com.mmaquera.happybabystyle.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mmaquera.happybabystyle.R

/**
 * Custom icons for baby clothing categories
 * Following Single Responsibility Principle - only handles custom icon definitions
 */
object CustomIcons {
    
    /**
     * Bodysuit icon
     */
    val Bodysuit: ImageVector = ImageVector.Builder(
        name = "Bodysuit",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF8A6163)),
            fillAlpha = 1.0f,
            strokeAlpha = 1.0f,
            strokeLineWidth = 0.0f,
            strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Butt,
            strokeLineJoin = androidx.compose.ui.graphics.StrokeJoin.Miter,
            strokeLineMiter = 4.0f,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero,
        ) {
            moveTo(12f, 2f)
            curveTo(10.9f, 2f, 10f, 2.9f, 10f, 4f)
            lineTo(10f, 6f)
            lineTo(8f, 6f)
            lineTo(8f, 8f)
            lineTo(10f, 8f)
            lineTo(10f, 10f)
            lineTo(8f, 10f)
            lineTo(8f, 12f)
            lineTo(10f, 12f)
            lineTo(10f, 14f)
            lineTo(8f, 14f)
            lineTo(8f, 16f)
            lineTo(10f, 16f)
            lineTo(10f, 18f)
            lineTo(8f, 18f)
            lineTo(8f, 20f)
            lineTo(10f, 20f)
            lineTo(10f, 22f)
            lineTo(14f, 22f)
            lineTo(14f, 20f)
            lineTo(16f, 20f)
            lineTo(16f, 18f)
            lineTo(14f, 18f)
            lineTo(14f, 16f)
            lineTo(16f, 16f)
            lineTo(16f, 14f)
            lineTo(14f, 14f)
            lineTo(14f, 12f)
            lineTo(16f, 12f)
            lineTo(16f, 10f)
            lineTo(14f, 10f)
            lineTo(14f, 8f)
            lineTo(16f, 8f)
            lineTo(16f, 6f)
            lineTo(14f, 6f)
            lineTo(14f, 4f)
            curveTo(14f, 2.9f, 13.1f, 2f, 12f, 2f)
            close()
            moveTo(12f, 4f)
            curveTo(12.6f, 4f, 13f, 4.4f, 13f, 5f)
            lineTo(13f, 6f)
            lineTo(11f, 6f)
            lineTo(11f, 5f)
            curveTo(11f, 4.4f, 11.4f, 4f, 12f, 4f)
            close()
        }
    }.build()
    
    /**
     * Hat icon
     */
    val Hat: ImageVector = ImageVector.Builder(
        name = "Hat",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF8A6163)),
            fillAlpha = 1.0f,
            strokeAlpha = 1.0f,
            strokeLineWidth = 0.0f,
            strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Butt,
            strokeLineJoin = androidx.compose.ui.graphics.StrokeJoin.Miter,
            strokeLineMiter = 4.0f,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero,
        ) {
            moveTo(12f, 2f)
            curveTo(13.1f, 2f, 14f, 2.9f, 14f, 4f)
            curveTo(14f, 5.1f, 13.1f, 6f, 12f, 6f)
            curveTo(10.9f, 6f, 10f, 5.1f, 10f, 4f)
            curveTo(10f, 2.9f, 10.9f, 2f, 12f, 2f)
            close()
            moveTo(21f, 9f)
            lineTo(21f, 7f)
            lineTo(15f, 1f)
            lineTo(5f, 1f)
            curveTo(3.89f, 1f, 3f, 1.89f, 3f, 3f)
            lineTo(3f, 21f)
            curveTo(3f, 22.11f, 3.89f, 23f, 5f, 23f)
            lineTo(19f, 23f)
            curveTo(20.11f, 23f, 21f, 22.11f, 21f, 21f)
            lineTo(21f, 9f)
            close()
            moveTo(19f, 9f)
            lineTo(14f, 9f)
            lineTo(14f, 4f)
            lineTo(5f, 4f)
            lineTo(5f, 21f)
            lineTo(19f, 21f)
            lineTo(19f, 9f)
            close()
        }
    }.build()
    
    /**
     * Socks icon
     */
    val Socks: ImageVector = ImageVector.Builder(
        name = "Socks",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF8A6163)),
            fillAlpha = 1.0f,
            strokeAlpha = 1.0f,
            strokeLineWidth = 0.0f,
            strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Butt,
            strokeLineJoin = androidx.compose.ui.graphics.StrokeJoin.Miter,
            strokeLineMiter = 4.0f,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero,
        ) {
            moveTo(12f, 2f)
            curveTo(17.5f, 2f, 22f, 6.5f, 22f, 12f)
            curveTo(22f, 17.5f, 17.5f, 22f, 12f, 22f)
            curveTo(6.5f, 22f, 2f, 17.5f, 2f, 12f)
            curveTo(2f, 6.5f, 6.5f, 2f, 12f, 2f)
            close()
            moveTo(12f, 4f)
            curveTo(7.6f, 4f, 4f, 7.6f, 4f, 12f)
            curveTo(4f, 16.4f, 7.6f, 20f, 12f, 20f)
            curveTo(16.4f, 20f, 20f, 16.4f, 20f, 12f)
            curveTo(20f, 7.6f, 16.4f, 4f, 12f, 4f)
            close()
            moveTo(12f, 6f)
            curveTo(14.8f, 6f, 17f, 8.2f, 17f, 11f)
            curveTo(17f, 13.8f, 14.8f, 16f, 12f, 16f)
            curveTo(9.2f, 16f, 7f, 13.8f, 7f, 11f)
            curveTo(7f, 8.2f, 9.2f, 6f, 12f, 6f)
            close()
            moveTo(12f, 8f)
            curveTo(10.3f, 8f, 9f, 9.3f, 9f, 11f)
            curveTo(9f, 12.7f, 10.3f, 14f, 12f, 14f)
            curveTo(13.7f, 14f, 15f, 12.7f, 15f, 11f)
            curveTo(15f, 9.3f, 13.7f, 8f, 12f, 8f)
            close()
        }
    }.build()
    
    /**
     * Pajamas icon
     */
    val Pajamas: ImageVector = ImageVector.Builder(
        name = "Pajamas",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF8A6163)),
            fillAlpha = 1.0f,
            strokeAlpha = 1.0f,
            strokeLineWidth = 0.0f,
            strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Butt,
            strokeLineJoin = androidx.compose.ui.graphics.StrokeJoin.Miter,
            strokeLineMiter = 4.0f,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero,
        ) {
            moveTo(12f, 2f)
            curveTo(10.9f, 2f, 10f, 2.9f, 10f, 4f)
            lineTo(10f, 6f)
            lineTo(8f, 6f)
            lineTo(8f, 8f)
            lineTo(10f, 8f)
            lineTo(10f, 10f)
            lineTo(8f, 10f)
            lineTo(8f, 12f)
            lineTo(10f, 12f)
            lineTo(10f, 14f)
            lineTo(8f, 14f)
            lineTo(8f, 16f)
            lineTo(10f, 16f)
            lineTo(10f, 18f)
            lineTo(8f, 18f)
            lineTo(8f, 20f)
            lineTo(10f, 20f)
            lineTo(10f, 22f)
            lineTo(14f, 22f)
            lineTo(14f, 20f)
            lineTo(16f, 20f)
            lineTo(16f, 18f)
            lineTo(14f, 18f)
            lineTo(14f, 16f)
            lineTo(16f, 16f)
            lineTo(16f, 14f)
            lineTo(14f, 14f)
            lineTo(14f, 12f)
            lineTo(16f, 12f)
            lineTo(16f, 10f)
            lineTo(14f, 10f)
            lineTo(14f, 8f)
            lineTo(16f, 8f)
            lineTo(16f, 6f)
            lineTo(14f, 6f)
            lineTo(14f, 4f)
            curveTo(14f, 2.9f, 13.1f, 2f, 12f, 2f)
            close()
            moveTo(12f, 4f)
            curveTo(12.6f, 4f, 13f, 4.4f, 13f, 5f)
            lineTo(13f, 6f)
            lineTo(11f, 6f)
            lineTo(11f, 5f)
            curveTo(11f, 4.4f, 11.4f, 4f, 12f, 4f)
            close()
        }
    }.build()
} 