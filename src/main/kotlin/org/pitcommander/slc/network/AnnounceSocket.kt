package org.pitcommander.slc.network

import com.google.gson.Gson
import org.zeromq.ZMQ
import java.util.concurrent.ConcurrentLinkedDeque

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

object AnnounceSocket: Runnable {
    private val queue = ConcurrentLinkedDeque<Announcement>()
    private const val ANNOUNCE_PORT = 5800

    var server = ""
    private val gson = Gson()

    fun pop(): Announcement? {
        return queue.pollFirst()
    }

    override fun run() {
        val context = ZMQ.context(1)
        val socket = context.socket(ZMQ.SUB)
        socket.receiveTimeOut = 1000
        socket.subscribe("".toByteArray())
        socket.connect("tcp://$server:$ANNOUNCE_PORT")
        var currentData = ""

        while (!Thread.interrupted()) {
            currentData = socket.recvStr()
            if (currentData.isNotEmpty()) {
                try {
                    queue.add(gson.fromJson(currentData, Announcement::class.java))
                } catch (e: Exception) {}
            }
        }
    }
}