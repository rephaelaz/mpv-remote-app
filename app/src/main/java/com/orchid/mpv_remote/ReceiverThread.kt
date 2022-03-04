package com.orchid.mpv_remote

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.SocketException

class ReceiverThread(private val context: MainActivity) : Thread() {
    override fun run() {
        val reader = BufferedReader(InputStreamReader(context.socket.getInputStream()))
        while (true) {
            try {
                val data = reader.readLine() ?: return
                val message = JSONObject(data)
                context.onReceiverReceive(message)
            } catch (e: SocketException) {
                if (e.message.equals("Socket closed")) {
                    return
                }
                throw e
            }
        }
    }
}