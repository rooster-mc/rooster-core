package dev.rooster.core.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

fun String.toComponent() = Component.text(this)

fun minimessage(text: String): Component = MiniMessage.miniMessage().deserialize(text)
fun mm(text: String) = minimessage(text)
