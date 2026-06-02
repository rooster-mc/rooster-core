package dev.rooster.core

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

object RoosterCore {
    const val MODULE_NAME = "RoosterCore"
    var logger: Logger = Logger.getLogger(MODULE_NAME)
    private lateinit var pluginInst: JavaPlugin
    val plugin: JavaPlugin
        get() {
            if (!::pluginInst.isInitialized) {
                throw UninitializedPropertyAccessException("You need to call ${MODULE_NAME}.init() before using $MODULE_NAME")
            }
            return pluginInst
        }

    fun init(plugin: JavaPlugin) {
        this.pluginInst = plugin
    }
}
