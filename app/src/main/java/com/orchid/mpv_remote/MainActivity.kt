package com.orchid.mpv_remote


import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.DatagramPacket
import java.net.Socket


class MainActivity : AppCompatActivity() {
    private var listener: ListenerThread? = null
    private var connected: Boolean = false

    lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            Toast.makeText(this, "Please enable Wi-Fi, then restart app", Toast.LENGTH_LONG).show()
            return
        }
        listener = ListenerThread(this)
        listener!!.start()
    }

    override fun onStop() {
        super.onStop()
        if (listener?.isAlive == true) {
            listener!!.socket.close()
            listener!!.join()
        }
        if (connected) {
            socket.close()
            connected = false
        }
        findViewById<TextView>(R.id.text_status).text = getString(R.string.disconnected)
    }

    fun onListenerReceive(packet: DatagramPacket) {
        val ip = packet.address.hostAddress!!
        val hostname = packet.data.decodeToString(8, packet.data.size).trim(Char(0))
        socket = Socket(ip, Global.PORT)
        connected = true
        runOnUiThread {
            findViewById<TextView>(R.id.text_status).text = hostname
        }
    }

    fun onPlay(view: View) {
        val thread = SenderThread(this, "play")
        thread.start()
    }

    fun onFfw(view: View) {
        val thread = SenderThread(this, "ffw")
        thread.start()
    }

    fun onRwd(view: View) {
        val thread = SenderThread(this, "rwd")
        thread.start()
    }

    fun onNext(view: View) {
        val thread = SenderThread(this, "next")
        thread.start()
    }

    fun onPrev(view: View) {
        val thread = SenderThread(this, "prev")
        thread.start()
    }
}