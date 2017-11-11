package org.pitcommander.slc.network

import com.google.gson.Gson
import org.zeromq.ZMQ

/*
 * StacklightController - Created on 11/10/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 11/10/17
 */

object CommandSocket {
    private const val COMMAND_PORT = 5801
    var server = ""
    private val gson = Gson()
    private lateinit var context: ZMQ.Context
    private lateinit var socket: ZMQ.Socket

    fun init() {
        context = ZMQ.context(1)
        socket = context.socket(ZMQ.REQ)
        socket.connect("tcp://$server:$COMMAND_PORT")
    }

    @Synchronized fun request(request: Request): Reply {
        val requestString = gson.toJson(request)
        socket.send(requestString)
        val responseString = socket.recvStr()
        return gson.fromJson(responseString, Reply::class.java)
    }
}