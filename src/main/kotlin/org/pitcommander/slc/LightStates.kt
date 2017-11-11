package org.pitcommander.slc

import groovy.lang.Binding


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

enum class LightStates(val speed: Int = -1) {
    OFF,
    ON,
    BLINK(500),
    STROBE(100);

    companion object {
        fun toBinding(): Binding {
            val binding = Binding()
            values().forEach {
                binding.setVariable(it.name.toLowerCase(), it)
            }
            return binding
        }
    }
}