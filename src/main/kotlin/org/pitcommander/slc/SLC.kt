package org.pitcommander.slc

import org.pitcommander.slc.config.ConfigParser
import org.pitcommander.slc.hardware.HardwareController
import org.pitcommander.slc.network.AnnounceSocket
import org.pitcommander.slc.network.Announcement
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
    val root = ConfigParser.fromFile("/home/cameronearle/Desktop/config.json")
    root.init()

    AnnounceSocket.server = root.server
    CommandSocket.server = root.server

    HardwareController.init()

    //val announceThread = Thread(AnnounceSocket).apply { start() }

    var announcement: Announcement? = null
    while (true) {
        HardwareController.tick()
        announcement = AnnounceSocket.pop()

        if (announcement != null) {
            root.lights.forEach {
                val light = it.light
                val desc = it.desc
                it.handlers.forEach { //Iterate each handler
                    if (it.cmd == announcement?.id) { //If the handler matches the current announcement
                        it.update(announcement?.payload!!) //Update the payload of the handler
                        var scriptResult: Any? = null
                        try {
                            scriptResult = it.script.run() //Execute the script
                        } catch (e: Exception) {
                            System.out.println("Error in handler [${it.cmd}], light [$desc] (${e.localizedMessage})")
                        }
                        if (scriptResult != null && scriptResult is LightStates) {
                            light.state = scriptResult //Set the light state
                        }
                    }
                }
            }
        }
        Thread.sleep(20)
    }
}