package org.pitcommander.slc

import org.pitcommander.slc.config.ConfigParser
import org.pitcommander.slc.hardware.HardwareController
import org.pitcommander.slc.network.AnnounceSocket
import org.pitcommander.slc.network.Announcement
import org.pitcommander.slc.network.Command
import org.pitcommander.slc.network.CommandSocket

/*
 * StacklightController - Created on 11/9/17
 * Author: Cameron Earle
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at project root
 */

/**
 * @author Cameron Earle
 * @version 11/9/17
 */

fun main(args: Array<String>) {
    val root = ConfigParser.fromFile()
    root.init()

    AnnounceSocket.server = root.server
    CommandSocket.server = root.server

    HardwareController.init()

    val announceThread = Thread(AnnounceSocket).apply { start() }
    val commandThread = Thread(CommandSocket).apply { start() }
    var ready = false
    Thread {
        println("SENDING PING")
        CommandSocket.request(Command("PING", hashMapOf()))
        println("PONG RECEIVED")
        Handlers.handleRequests(root)
        println("REQUESTS HANDLED")
        root.lights.forEach {
            it.light.state = LightStates.OFF
        }
        println("LIGHTS OFF")
        ready = true
    }.start()

    var announcement: Announcement? = null
    while (true) {
        HardwareController.tick()
        if (ready) {
            announcement = AnnounceSocket.pop()
            if (announcement != null) {
                println("GOT ANNOUNCEMENT ${announcement.id}")
            }
            Handlers.handleAnnouncement(root, announcement)
        }
        Thread.sleep(20)
    }
}