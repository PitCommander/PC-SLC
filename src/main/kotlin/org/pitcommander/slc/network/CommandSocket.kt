package org.pitcommander.slc.network

import com.google.gson.Gson
import org.zeromq.ZMQ
import java.util.*
import java.util.concurrent.CountDownLatch

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

object CommandSocket: Runnable {
    private const val COMMAND_PORT = 5801
    var server = ""
    private val gson = Gson()
    private lateinit var context: ZMQ.Context
    private lateinit var socket: ZMQ.Socket

    private val queue = Vector<SocketTask>()

    private data class SocketTask(val command: Command) {
        val latch = CountDownLatch(1)
        lateinit var reply: Reply
    }

    override fun run() {
        context = ZMQ.context(1)
        socket = context.socket(ZMQ.REQ)
        println("Connecting to Server $server")
        socket.connect("tcp://$server:$COMMAND_PORT")

        var job: SocketTask? = null

        while (!Thread.interrupted()) {
            while (queue.size > 0) {
                job = queue.removeAt(0)
                if (job != null) {
                    println("SENDING COMMAND ${job.command.id}")
                    socket.send(gson.toJson(job.command))
                    println("COMMAND ${job.command.id} SENT")
                    job.reply = gson.fromJson(socket.recvStr(), Reply::class.java)
                    println("RESPONSE ${job.reply.id} RECEIVED")
                    job.latch.countDown()
                }
            }
            try {
                Thread.sleep(20L)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    fun request(command: Command): Reply {
        val job = SocketTask(command)
        queue.addElement(job)
        try {
            job.latch.await()
        } catch (e: InterruptedException) {}
        return job.reply
    }
}