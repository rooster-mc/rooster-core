package dev.rooster.core

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import dev.rooster.core.util.uniqueKey
import org.bukkit.command.CommandSender
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class RoosterCache<K, V : Any>(cacheBuilder: CacheBuilder<Any, Any>, corePoolSize: Int = 1) {
    private var cache: Cache<String, V> = cacheBuilder.build()

    private val generalKey = "general"

    fun getIfPresent(key: K, sender: CommandSender? = null): V? {
        return cache.getIfPresent(combineKey(key, sender))
    }

    fun invalidate(key: K, sender: CommandSender? = null) {
        cache.invalidate(combineKey(key, sender))
    }

    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize)
    fun invalidateWithTimeout(key: K, sender: CommandSender? = null, clearTime: Long, unit: TimeUnit) {
        scheduler.schedule({
            cache.invalidate(combineKey(key, sender))
        }, clearTime, unit)
    }

    fun put(
        key: K,
        sender: CommandSender? = null,
        value: V,
        clearTime: Long? = null,
        unit: TimeUnit = TimeUnit.SECONDS
    ) {
        cache.put(combineKey(key, sender), value)

        if (clearTime != null) invalidateWithTimeout(key, sender, clearTime, unit)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : V> get(
        key: K,
        sender: CommandSender? = null,
        provider: () -> T,
        clearTime: Long? = null,
        unit: TimeUnit = TimeUnit.SECONDS
    ): T {
        if (clearTime != null) invalidateWithTimeout(key, sender, clearTime, unit)
        return cache.get(combineKey(key, sender), provider) as T
    }

    fun size() = cache.size()
    fun asMap(): Map<String, V> = cache.asMap()
    fun addAll(
        map: Map<K, V>,
        sender: CommandSender? = null,
        clearTime: Long? = null,
        unit: TimeUnit = TimeUnit.SECONDS
    ) {
        map.forEach { (key, value) -> put(key, sender, value, clearTime, unit) }
    }

    fun cleanUp() = cache.cleanUp()
    fun getAllPresent(keys: Iterable<K>, sender: CommandSender?) {
        cache.getAllPresent(keys.map {
            (sender?.uniqueKey() ?: generalKey) to it
        })
    }

    private fun combineKey(key: K, sender: CommandSender?): String {
        val typeKey = sender?.uniqueKey() ?: generalKey
        return "$typeKey;$key"
    }
}
