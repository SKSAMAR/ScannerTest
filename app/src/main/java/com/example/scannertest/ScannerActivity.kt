package com.example.scannertest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.example.scannertest.ui.theme.ScannerTestTheme

class ScannerActivity: ComponentActivity() {

    private var tag: String = ScannerActivity::class.java.simpleName
    private val SCANNER_INIT = "unitech.scanservice.init"
    private val SCAN2KEY_SETTING = "unitech.scanservice.scan2key_setting"
    private val START_SCANSERVICE = "unitech.scanservice.start"
    private val CLOSE_SCANSERVICE = "unitech.scanservice.close"
    private val SOFTWARE_SCANKEY = "unitech.scanservice.software_scankey"
    private val ACTION_RECEIVE_DATA = "unitech.scanservice.data"
    private val ACTION_RECEIVE_DATABYTES = "unitech.scanservice.databyte"
    private val ACTION_RECEIVE_DATALENGTH = "unitech.scanservice.datalength"
    private val ACTION_RECEIVE_DATATYPE = "unitech.scanservice.datatype"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerScannerReceiver()
        callScanner()
        setContent {
            ScannerTestTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    Text(text = "Hello, World!")
                }
            }
        }

        val handler = Handler()
        handler.postDelayed({
            callScanner()
        }, 1000)
    }

    private fun registerScannerReceiver() {
        //register receiver
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_RECEIVE_DATA)
        intentFilter.addAction(ACTION_RECEIVE_DATABYTES)
        intentFilter.addAction(ACTION_RECEIVE_DATALENGTH)
        intentFilter.addAction(ACTION_RECEIVE_DATATYPE)
        registerReceiver(mScanReceiver, intentFilter)
    }


    private fun callScanner() {
        startScanService()
        setScan2Key()
        //set init intent
        setInit()

        //start scanning
        val bundle = Bundle()
        bundle.putBoolean("scan", true)
        val mIntent = Intent().setAction(SOFTWARE_SCANKEY).putExtras(bundle)
        sendBroadcast(mIntent)





    }

    private fun setScan2Key() {
        //which supports keyboard emulation features
        val bundle = Bundle()
        bundle.putBoolean("scan2key", false)
        val mIntent = Intent().setAction(SCAN2KEY_SETTING).putExtras(bundle)
        sendBroadcast(mIntent)
    }

    private fun setInit() {
        //init the scanner
        val bundle1 = Bundle()
        bundle1.putBoolean("enable", true)
        val mIntent1 = Intent().setAction(SCANNER_INIT).putExtras(bundle1)
        sendBroadcast(mIntent1)
    }

    private fun startScanService() {
        //to start scan service
        val bundle = Bundle()
        bundle.putBoolean("close", true)
        val mIntent = Intent().setAction(START_SCANSERVICE).putExtras(bundle)
        sendBroadcast(mIntent)
    }

    private fun closeScanService() {
        //to close scan service
        val bundle = Bundle()
        bundle.putBoolean("close", true)
        val mIntent = Intent().setAction(CLOSE_SCANSERVICE).putExtras(bundle)
        sendBroadcast(mIntent)
    }

    private val mScanReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.v(tag, "onReceive()")
            val action = intent.action
            val bundle = intent.extras ?: return
            when (action) {
                ACTION_RECEIVE_DATA -> {
                    Log.v(tag, "ACTION_RECEIVE_DATA")
                    val barcodeStr = bundle.getString("text")
                    Log.v(tag, "barcode data: $barcodeStr")

                    val handler = Handler()
                    handler.postDelayed({
                        closeScanService()
                    }, 1000) // Adjust the delay as needed
                    val visitIntent = Intent(this@ScannerActivity, MainActivity::class.java)
                    visitIntent.putExtra("barcodeData", barcodeStr)
                    startActivity(visitIntent)
                }
            }
        }
    }
}