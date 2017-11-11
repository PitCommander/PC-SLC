package org.pitcommander.slc

import org.pitcommander.slc.config.ConfigRoot
import org.pitcommander.slc.network.Announcement
import org.pitcommander.slc.network.CommandSocket
import org.pitcommander.slc.network.Request

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

object Handlers {
    fun handleRequests(root: ConfigRoot) {
        root.lights.forEach {
            it.requests.forEach {
                val reply = CommandSocket.request(Request(it.cmd, hashMapOf()))
            }
        }
    }

    fun handleAnnouncement(root: ConfigRoot, announcement: Announcement?) {
        if (announcement != null) {
            root.lights.forEach {
                val light = it.light
                val desc = it.desc
                it.handlers.forEach {
                    if (it.cmd == announcement.id) {
                        it.update(announcement.payload)
                        var scriptResult: Any? = null
                        try {
                            scriptResult = it.script.run()
                        } catch (e: Exception) {
                            System.err.println("Error running script for light [$desc] on action [${it.cmd}] (${e.localizedMessage})")
                        }
                        if (scriptResult != null && scriptResult is LightStates) {
                            light.state = scriptResult
                        }
                    }
                }
            }
        }
    }
}