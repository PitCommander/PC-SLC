package org.pitcommander.slc.config

import com.google.gson.Gson
import java.io.File

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

object ConfigParser {
    private val gson = Gson()

    fun fromFile(path: String = "config.json"): ConfigRoot {
        val file = File(path)
        try {
            return gson.fromJson(file.readText(), ConfigRoot::class.java)
        } catch (e: Exception) {
            System.err.println("Error reading config file [$path].  (${e.javaClass.name})")
            e.printStackTrace()
            System.exit(1)
        }
        return null!!
    }
}