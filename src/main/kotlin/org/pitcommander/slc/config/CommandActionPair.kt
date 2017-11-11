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
    @Transient lateinit var reply: Reply

    fun update(payload: HashMap<String, Any>, reply: Reply? = null) {
        this.payload.clear()
        this.payload.putAll(payload)
        if (reply != null) {
            this.reply = reply
        }
    }

    fun init() {
        payload = hashMapOf()
        val binding = LightStates.toBinding()
        binding.setVariable("p", payload)
        binding.setVariable("r", reply)

        script = GroovyShell(binding).parse(action)
    }
}