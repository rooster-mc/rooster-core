package dev.rooster.core.message

import dev.rooster.core.util.minimessage
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

interface Message {
    fun resolve(player: Player): Component
    fun resolveDefault(): Component
    fun withReplacements(vararg replacements: Pair<String, String>): Message
}

data class PlainMessage(val text: String) : Message {
    private val component = minimessage(text)
    override fun resolve(player: Player): Component = component
    override fun resolveDefault(): Component = component
    override fun withReplacements(vararg replacements: Pair<String, String>): Message {
        var result = text
        for ((key, value) in replacements) result = result.replace("\${$key}", value)
        return PlainMessage(result)
    }
}
