package org.pitcommander.slc.config

import groovy.lang.GroovyShell
import groovy.lang.Script
import org.pitcommander.slc.LightStates
import org.pitcommander.slc.network.Reply

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

data class CommandActionPair(val cmd: String,
                             val action: String) {
    @Transient lateinit var script: Script
    @Transient lateinit var payload: HashMap<String, Any>

    fun update(payload: HashMap<String, Any>) {
        this.payload.clear()
        this.payload.putAll(payload)
    }

    fun init() {
        payload = hashMapOf()
        val binding = LightStates.toBinding()
        binding.setVariable("p", payload)

        script = GroovyShell(binding).parse(action)
    }
}