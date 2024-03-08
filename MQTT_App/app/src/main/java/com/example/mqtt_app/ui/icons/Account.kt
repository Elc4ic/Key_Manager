package com.example.mqtt_app.ui.icons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun rememberAccountBox(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "account_box",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.White),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.875f, 30.5f)
                quadToRelative(2.417f, -2.25f, 5.479f, -3.625f)
                quadTo(16.417f, 25.5f, 20f, 25.5f)
                quadToRelative(3.583f, 0f, 6.646f, 1.375f)
                quadToRelative(3.062f, 1.375f, 5.479f, 3.625f)
                verticalLineTo(7.875f)
                horizontalLineTo(7.875f)
                close()
                moveToRelative(12.208f, -8.167f)
                quadToRelative(2.375f, 0f, 4f, -1.645f)
                quadToRelative(1.625f, -1.646f, 1.625f, -4.021f)
                reflectiveQuadToRelative(-1.646f, -4.021f)
                quadTo(22.417f, 11f, 20.042f, 11f)
                quadToRelative(-2.375f, 0f, -4.021f, 1.646f)
                reflectiveQuadToRelative(-1.646f, 4.021f)
                quadToRelative(0f, 2.375f, 1.667f, 4.021f)
                quadToRelative(1.666f, 1.645f, 4.041f, 1.645f)
                close()
                moveTo(7.875f, 34.75f)
                quadToRelative(-1.042f, 0f, -1.833f, -0.792f)
                quadToRelative(-0.792f, -0.791f, -0.792f, -1.833f)
                verticalLineTo(7.875f)
                quadToRelative(0f, -1.042f, 0.792f, -1.833f)
                quadToRelative(0.791f, -0.792f, 1.833f, -0.792f)
                horizontalLineToRelative(24.25f)
                quadToRelative(1.042f, 0f, 1.833f, 0.792f)
                quadToRelative(0.792f, 0.791f, 0.792f, 1.833f)
                verticalLineToRelative(24.25f)
                quadToRelative(0f, 1.042f, -0.792f, 1.833f)
                quadToRelative(-0.791f, 0.792f, -1.833f, 0.792f)
                close()
                moveToRelative(2.5f, -2.625f)
                horizontalLineToRelative(19.25f)
                verticalLineToRelative(-0.417f)
                quadToRelative(-2.083f, -1.75f, -4.542f, -2.666f)
                quadToRelative(-2.458f, -0.917f, -5.083f, -0.917f)
                reflectiveQuadToRelative(-5.062f, 0.917f)
                quadToRelative(-2.438f, 0.916f, -4.563f, 2.666f)
                verticalLineToRelative(0.417f)
                close()
                moveToRelative(9.667f, -12.417f)
                quadToRelative(-1.25f, 0f, -2.125f, -0.896f)
                quadToRelative(-0.875f, -0.895f, -0.875f, -2.145f)
                reflectiveQuadToRelative(0.875f, -2.146f)
                quadToRelative(0.875f, -0.896f, 2.125f, -0.896f)
                quadToRelative(1.291f, 0f, 2.166f, 0.896f)
                reflectiveQuadToRelative(0.875f, 2.146f)
                quadToRelative(0f, 1.25f, -0.875f, 2.145f)
                quadToRelative(-0.875f, 0.896f, -2.166f, 0.896f)
                close()
                moveTo(20f, 19.167f)
                close()
            }
        }.build()
    }
}