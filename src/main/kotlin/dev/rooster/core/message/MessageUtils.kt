package dev.rooster.core.message

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun Player.send(message: Message) = sendMessage(message.resolve(this))

fun CommandSender.send(message: Message) {
    if (this is Player) send(message)
    else sendMessage(message.resolveDefault())
}
