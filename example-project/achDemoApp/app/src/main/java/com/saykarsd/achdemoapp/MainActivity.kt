package com.saykarsd.achdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.saykarsd.androidcomposehellper.debugging.TestACH
import com.saykarsd.androidcomposehellper.themes.ACHTheme
import com.saykarsd.androidcomposehellper.ui.TextView

class MainActivity : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
enableEdgeToEdge()
setContent {
ACHTheme {
Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
TestingScreen(
modifier = Modifier.padding(innerPadding)
)
}
}
}
}
}

@Composable
fun TestingScreen(modifier: Modifier = Modifier) {
TextView(
text = TestACH(),
modifier = modifier
)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
ACHTheme {
TestingScreen()
}
}