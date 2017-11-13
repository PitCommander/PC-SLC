package org.pitcommander.slc.network

import com.google.gson.Gson
import org.zeromq.ZMQ

/*
 * PC-SLC - Created on 11/12/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 11/12/17
 */

object AnnounceSocket {
    private var server = ""
    private var port = 0

    private lateinit var context: ZMQ.Context
    private lateinit var socket: ZMQ.Socket

    private val gson = Gson()

    fun init() {
        context = ZMQ.context(1)
        socket = context.socket(ZMQ.SUB)
    }

    fun connect(server: String, port: Int) {
        socket.connect("tcp://$server:$port")
        socket.subscribe("".toByteArray())
    }

    fun read(): Announcement {
        val input = socket.recvStr()
        return gson.fromJson(input, Announcement::class.java)
    }
}