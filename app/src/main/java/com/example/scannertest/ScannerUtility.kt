package com.example.scannertest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log

object ScannerUtility {

    private const val ACTION_SCAN_DATA = "unitech.scanservice.data"
    private const val KEY_SCAN_DATA_TEXT = "text"
    private const val ACTION_SCAN2KEY_SETTING = "unitech.scanservice.scan2key_setting"
    private const val ACTION_SCAN2KEY_OUTPUTMETHOD = "unitech.scanservice.scan2key_outputmethod"
    private const val ACTION_SOFTWARE_SCANKEY = "unitech.scanservice.software_scankey"
    private const val KEY_SCAN2KEY = "scan2key"
    private const val KEY_OUTPUTMETHOD = "outputmethod"
    private const val KEY_SCAN = "scan"

    fun Context.enableScan2Key() {
        val intent = Intent(ACTION_SCAN2KEY_SETTING)
        val bundle = Bundle()
        bundle.putBoolean(KEY_SCAN2KEY, true)
        intent.putExtras(bundle)
        this.sendBroadcast(intent)
    }

    fun Context.disableScan2Key() {
        val intent = Intent(ACTION_SCAN2KEY_SETTING)
        val bundle = Bundle()
        bundle.putBoolean(KEY_SCAN2KEY, false)
        intent.putExtras(bundle)
        this.sendBroadcast(intent)
    }

    fun Context.setScan2KeyEventOutput() {
        val intent = Intent(ACTION_SCAN2KEY_OUTPUTMETHOD)
        val bundle = Bundle()
        bundle.putInt(KEY_OUTPUTMETHOD, 0) // Key Event
        intent.putExtras(bundle)
        this.sendBroadcast(intent)
    }

    fun Context.setScan2KeyAutoOutput() {
        val intent = Intent(ACTION_SCAN2KEY_OUTPUTMETHOD)
        val bundle = Bundle()
        bundle.putInt(KEY_OUTPUTMETHOD, 2) // Auto
        intent.putExtras(bundle)
        this.sendBroadcast(intent)
    }

    fun Context.startScan() {
        val intent = Intent(ACTION_SOFTWARE_SCANKEY)
        intent.putExtra(KEY_SCAN, true)
        this.sendBroadcast(intent)
    }

    fun Context.stopScan() {
        val intent = Intent(ACTION_SOFTWARE_SCANKEY)
        intent.putExtra(KEY_SCAN, false)
        this.sendBroadcast(intent)
    }


    private val barcodeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ACTION_SCAN_DATA) {
                val scannedData = intent.getStringExtra(KEY_SCAN_DATA_TEXT)
                // Handle the scanned data (e.g., display it in a TextView)
                Log.d("ScannerUtility", "Scanned data: $scannedData")
            }
        }
    }

    fun Context.registerForScanData() {
        val intentFilter = IntentFilter(ACTION_SCAN_DATA)
        this.registerReceiver(barcodeReceiver, intentFilter)
    }

    fun Context.unregisterForScanData() {
        this.unregisterReceiver(barcodeReceiver)
    }

}
