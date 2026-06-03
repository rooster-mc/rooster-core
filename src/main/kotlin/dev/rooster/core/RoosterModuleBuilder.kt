package dev.rooster.core

import com.google.common.cache.CacheBuilder
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

fun initRooster(
    plugin: JavaPlugin,
    services: RoosterServices = RoosterServices(),
    cache: RoosterCache<String, Any> = RoosterCache(CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES)),
    block: RoosterModuleBuilder.() -> Unit = { }
) {
    RoosterCore.init(plugin)
    val builder = RoosterModuleBuilder(plugin, services, cache)
    block(builder)
    builder.afterHooks.forEach { it() }
}

class RoosterModuleBuilder(
    val plugin: JavaPlugin,
    val services: RoosterServices,
    val cache: RoosterCache<String, Any>
) {
    val afterHooks: MutableList<() -> Unit> = mutableListOf()
}
