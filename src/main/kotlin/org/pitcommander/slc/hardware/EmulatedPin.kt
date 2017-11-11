package org.pitcommander.slc.hardware

import com.pi4j.io.gpio.*
import com.pi4j.io.gpio.event.GpioPinListener
import java.util.concurrent.Callable
import java.util.concurrent.Future

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

class EmulatedPin: GpioPinDigitalOutput {
    private var state = false

    override fun low() {
        state = false
    }
    override fun high() {
        state = true
    }

    override fun toggle() {
        state = !state
    }

    override fun isLow(): Boolean = !state
    override fun isHigh() = state

    override fun setState(state: Boolean) {
        this.state = state
    }

    override fun toString(): String {
        return if (state) "*" else "-"
    }

    ////////////////////////////////////

    override fun getPin(): Pin {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pulse(duration: Long): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pulse(duration: Long, callback: Callable<Void>?): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pulse(duration: Long, blocking: Boolean): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pulse(duration: Long, blocking: Boolean, callback: Callable<Void>?): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pulse(duration: Long, pulseState: PinState?): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pulse(duration: Long, pulseState: PinState?, callback: Callable<Void>?): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pulse(duration: Long, pulseState: PinState?, blocking: Boolean): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pulse(duration: Long, pulseState: PinState?, blocking: Boolean, callback: Callable<Void>?): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getListeners(): MutableCollection<GpioPinListener> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addListener(vararg listener: GpioPinListener?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addListener(listeners: MutableList<out GpioPinListener>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setState(state: PinState?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasListener(vararg listener: GpioPinListener?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProperties(): MutableMap<String, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProperty(key: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProperty(key: String?, defaultValue: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isPullResistance(resistance: PinPullResistance?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeListener(vararg listener: GpioPinListener?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeListener(listeners: MutableList<out GpioPinListener>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unexport() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMode(): PinMode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTag(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPullResistance(): PinPullResistance {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasProperty(key: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getState(): PinState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setShutdownOptions(options: GpioPinShutdown?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setShutdownOptions(unexport: Boolean?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setShutdownOptions(unexport: Boolean?, state: PinState?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setShutdownOptions(unexport: Boolean?, state: PinState?, resistance: PinPullResistance?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setShutdownOptions(unexport: Boolean?, state: PinState?, resistance: PinPullResistance?, mode: PinMode?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isState(state: PinState?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMode(mode: PinMode?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setProperty(key: String?, value: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearProperties() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPullResistance(resistance: PinPullResistance?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeProperty(key: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isMode(mode: PinMode?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getShutdownOptions(): GpioPinShutdown {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProvider(): GpioProvider {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setName(name: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun export(mode: PinMode?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun export(mode: PinMode?, defaultState: PinState?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTag(tag: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAllListeners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun blink(delay: Long): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun blink(delay: Long, blinkState: PinState?): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun blink(delay: Long, duration: Long): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun blink(delay: Long, duration: Long, blinkState: PinState?): Future<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}