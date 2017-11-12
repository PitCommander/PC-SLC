package org.pitcommander.slc.hardware

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.RaspiPin
import org.pitcommander.slc.LightStates
import org.pitcommander.slc.config.ConfigRoot

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

object HardwareController {
    private lateinit var gpio: GpioController
    private val lights = arrayListOf<Light>()
    private const val DEBUG = false

    init {
        if (!DEBUG) {
            gpio = GpioFactory.getInstance()
        }
    }

    private object StateManager {
        var lastBlink = 0L
        var lastStrobe = 0L
        var time = 0L

        var blinkState = false
        var strobeState = false

        val BLINK_TIME = LightStates.BLINK.speed
        val STROBE_TIME = LightStates.STROBE.speed

        fun init() {
            time = System.currentTimeMillis()
            lastBlink = time
            lastStrobe = time
        }

        fun tick() {
            time = System.currentTimeMillis()

            if (time - lastBlink > BLINK_TIME) {
                blinkState = !blinkState
                lastBlink = time
            }

            if (time - lastStrobe > STROBE_TIME) {
                strobeState = !strobeState
                lastStrobe = time
            }
        }
    }

    fun registerLight(pin: Int): Light {
        val light = Light(pin, LightStates.BLINK)
        lights.add(light)
        return light
    }

    fun init() {
        lights.forEach {
            if (!DEBUG) {
                it.hardwarePin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(it.pin))
            } else {
                it.hardwarePin = EmulatedPin()
            }
        }

        StateManager.init()
    }

    fun tick() {
        StateManager.tick()

        lights.forEach {
            when (it.state) {
                LightStates.OFF -> it.hardwarePin.low()
                LightStates.ON -> it.hardwarePin.high()
                LightStates.BLINK -> it.hardwarePin.setState(StateManager.blinkState)
                LightStates.STROBE -> it.hardwarePin.setState(StateManager.strobeState)
            }

            if (DEBUG) {
                println(" ${it.hardwarePin} ")
            }
        }

    }
}