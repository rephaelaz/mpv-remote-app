package com.orchid.mpv_remote

class SenderThread(private val context: MainActivity, private val data: String) : Thread() {
    override fun run() {
        synchronized(context.socket) {
            context.socket.getOutputStream().write(data.toByteArray())
        }
    }
}