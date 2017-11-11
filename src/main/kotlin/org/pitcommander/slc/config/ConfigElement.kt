package org.pitcommander.slc.config

import org.pitcommander.slc.LightStates
import org.pitcommander.slc.hardware.HardwareController
import org.pitcommander.slc.hardware.Light

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

data class ConfigElement(val pin: Int,
                         val desc: String,
                         val requests: ArrayList<CommandActionPair>,
                         val handlers: ArrayList<CommandActionPair>) {

    @Transient lateinit var light: Light

    fun init() {
        requests.forEach { it.init() }
        handlers.forEach { it.init() }
        light = HardwareController.registerLight(pin)
    }
}