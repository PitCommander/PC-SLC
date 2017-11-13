package org.pitcommander.slc

import org.pitcommander.slc.config.ConfigParser
import org.pitcommander.slc.hardware.HardwareController
import org.pitcommander.slc.network.AnnounceSocket
import org.pitcommander.slc.network.Announcement
import org.pitcommander.slc.network.Command
import org.pitcommander.slc.network.CommandSocket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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
    // READ CONFIG
    val root = ConfigParser.fromFile("/home/cameronearle/Desktop/config.json")
    root.init()

    // INIT SOCKETS
    AnnounceSocket.init()
    CommandSocket.init()

    // INIT HW CONTROLLER
    HardwareController.init()
    val hardwareExecutor = Executors.newSingleThreadScheduledExecutor()
    hardwareExecutor.scheduleAtFixedRate(HardwareController, 0L, 20L, TimeUnit.MILLISECONDS)

    // CONNECT SOCKETS
    AnnounceSocket.connect(root.server, 5800)
    CommandSocket.connect(root.server, 5801)

    // PING
    CommandSocket.request(Command("PING", hashMapOf()))
    root.lights.forEach {
        it.light.state = LightStates.OFF
    }

    // HANDLE REQUESTS
    Handlers.handleRequests(root)

    // HANDLE ANNOUNCEMENTS
    var announcement: Announcement?
    while (true) {
        announcement = AnnounceSocket.read()
        Handlers.handleAnnouncement(root, announcement)
    }
}