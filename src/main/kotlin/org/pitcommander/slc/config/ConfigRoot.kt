package org.pitcommander.slc.config

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

data class ConfigRoot(val server: String,
                      val inverted: Boolean = false,
                      val lights: ArrayList<ConfigElement>) {
    fun init() {
        lights.forEach { it.init() }
    }
}