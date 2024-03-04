package com.example.scannertest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.scannertest.ScannerUtility.registerForScanData
import com.example.scannertest.ScannerUtility.unregisterForScanData
import com.example.scannertest.ui.theme.ScannerTestTheme

class MainActivity : ComponentActivity() {

    var barcodeData by mutableStateOf("No Data")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForScanData()
        setContent {
            ScannerTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = barcodeData,
                modifier = modifier
            )
            Button(
                onClick = {


                    //startActivity( Intent(this@MainActivity,ScannerActivity::class.java))
                }
            ) {
                Text(text = "Scan")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ScannerTestTheme {
            Greeting("Android")
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            barcodeData = it.getStringExtra("barcodeData") ?: "No Data"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForScanData()
    }

}