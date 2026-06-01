package dev.rooster.core

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

object RoosterCore {
    const val moduleName = "RoosterCore"
    var logger: Logger = Logger.getLogger(moduleName)
    private lateinit var pluginInst: JavaPlugin
    val plugin: JavaPlugin
        get() {
            if (!::pluginInst.isInitialized) {
                throw UninitializedPropertyAccessException("You need to call ${moduleName}.init() before using $moduleName")
            }
            return pluginInst
        }

    fun init(plugin: JavaPlugin) {
        this.pluginInst = plugin
    }
}
