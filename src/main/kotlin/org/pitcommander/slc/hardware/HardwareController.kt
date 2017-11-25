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

object HardwareController: Runnable {
    private val gpio = GpioFactory.getInstance()
    private val lights = arrayListOf<Light>()
    private var inverted = false

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

    fun init(inverted: Boolean) {
        lights.forEach {
            it.hardwarePin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(it.pin))
        }

        this.inverted = inverted

        StateManager.init()
    }

    fun tick() {
        StateManager.tick()

        lights.forEach {
            when (it.state) {
                LightStates.OFF -> if (!inverted) it.hardwarePin.low() else it.hardwarePin.high()
                LightStates.ON -> if (!inverted) it.hardwarePin.high() else it.hardwarePin.low()
                LightStates.BLINK -> it.hardwarePin.setState(if (!inverted) StateManager.blinkState else !StateManager.blinkState)
                LightStates.STROBE -> it.hardwarePin.setState(if (!inverted) StateManager.strobeState else !StateManager.strobeState)
            }
        }

    }

    override fun run() = tick()
}