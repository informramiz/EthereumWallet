package com.ramiz.ethereumwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramiz.ethereumwallet.ui.theme.EthereumWalletTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EthereumWalletTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ScreenUi()
                    }
                }
            }
        }
    }
}

@Composable
private fun ScreenUi() {
    Column {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Go")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EthereumWalletTheme {
        ScreenUi()
    }
}
