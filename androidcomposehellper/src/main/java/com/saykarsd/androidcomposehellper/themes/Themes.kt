package com.saykarsd.androidcomposehellper.themes
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.saykarsd.androidcomposehellper.colors.Colors

private val DarkColors=darkColorScheme(
primary=Colors.Gold,
onPrimary=Colors.Black,
primaryContainer=Colors.GoldenRod,
onPrimaryContainer=Colors.Black,
secondary=Colors.Peru,
onSecondary=Colors.Black,
secondaryContainer=Colors.SaddleBrown,
onSecondaryContainer=Colors.White,
tertiary=Colors.DarkGoldenRod,
onTertiary=Colors.Black,
background=Colors.Black,
onBackground=Colors.White,
surface=Colors.DarkSlateGray,
onSurface=Colors.White,
surfaceVariant=Colors.DimGray,
onSurfaceVariant=Colors.LightGray,
outline=Colors.Gray,
outlineVariant=Colors.DarkGray,
inverseSurface=Colors.White,
inverseOnSurface=Colors.Black,
inversePrimary=Colors.Gold,
error=Colors.Red,
onError=Colors.White,
errorContainer=Colors.DarkRed,
onErrorContainer=Colors.White,
scrim=Colors.Black.copy(alpha=0.8f)
)

private val LightColors=lightColorScheme(
primary=Colors.Gold,
onPrimary=Colors.Black,
primaryContainer=Colors.DarkGoldenRod,
onPrimaryContainer=Colors.Black,
secondary=Colors.SaddleBrown,
onSecondary=Colors.White,
secondaryContainer=Colors.Peru,
onSecondaryContainer=Colors.Black,
tertiary=Colors.GoldenRod,
onTertiary=Colors.Black,
background=Colors.White,
onBackground=Colors.DarkSlateGray,
surface=Colors.Ivory,
onSurface=Colors.DarkSlateGray,
surfaceVariant=Colors.OldLace,
onSurfaceVariant=Colors.DimGray,
outline=Colors.Gray,
outlineVariant=Colors.LightGray,
inverseSurface=Colors.Black,
inverseOnSurface=Colors.White,
inversePrimary=Colors.Gold,
error=Colors.Red,
onError=Colors.White,
errorContainer=Colors.DarkRed,
onErrorContainer=Colors.White,
scrim=Colors.Black.copy(alpha=0.6f)
)

@Composable
fun ACHLightTheme(content: @Composable () -> Unit) {
MaterialTheme(colorScheme=LightColors,typography=MaterialTheme.typography,shapes=MaterialTheme.shapes,content=content)
}

@Composable
fun ACHDarkTheme(content: @Composable () -> Unit) {
MaterialTheme(colorScheme=DarkColors,typography=MaterialTheme.typography,shapes=MaterialTheme.shapes,content=content)
}

@Composable
fun ACHTheme(
useDynamicColor:Boolean=true,
content: @Composable () -> Unit
){
val context=LocalContext.current
val isDark=isSystemInDarkTheme()

val colorScheme=when{
useDynamicColor && Build.VERSION.SDK_INT>=Build.VERSION_CODES.S->{
if(isDark) dynamicDarkColorScheme(context)
else dynamicLightColorScheme(context)
}
isDark->DarkColors
else->LightColors
}

MaterialTheme(
colorScheme=colorScheme,
typography=MaterialTheme.typography,
shapes=MaterialTheme.shapes,
content=content
)
}

