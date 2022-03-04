package com.orchid.mpv_remote

import android.content.Context
import android.net.wifi.WifiManager
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException

class ListenerThread(private val context: MainActivity) : Thread() {
    lateinit var socket: DatagramSocket

    override fun run() {
        val buffer = ByteArray(4096)
        socket = DatagramSocket(Global.PORT, InetAddress.getByName("0.0.0.0"))
        socket.broadcast = true

        val packet = DatagramPacket(buffer, buffer.size)
        while (true) {
            try {
                socket.receive(packet)
            } catch (e: SocketException) {
                if (e.message.equals("Socket closed")) {
                    return
                }
                throw e
            }
            val magic = packet.data.decodeToString(0, 8)
            if (magic == Global.MAGIC) {
                break
            }
        }
        context.onListenerReceive(packet)
        socket.close()
    }
}