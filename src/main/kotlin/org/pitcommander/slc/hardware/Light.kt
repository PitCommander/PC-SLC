package org.pitcommander.slc.hardware

import com.pi4j.io.gpio.GpioPinDigitalOutput
import org.pitcommander.slc.LightStates

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

class Light(val pin: Int, state: LightStates) {
    var state: LightStates = state
        @Synchronized get
        @Synchronized set

    lateinit var hardwarePin: GpioPinDigitalOutput
}