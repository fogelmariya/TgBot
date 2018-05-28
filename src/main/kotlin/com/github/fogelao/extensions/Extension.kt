package com.github.fogelao.extensions

import org.telegram.abilitybots.api.objects.MessageContext
import org.telegram.abilitybots.api.sender.SilentSender

/**
 * @author Fogel Artem on 28.05.2018.
 */

fun SilentSender.send(message: String, ctx: MessageContext) {
    send(message, ctx.chatId())
}

fun SilentSender.send(stringBuilder: StringBuilder, ctx: MessageContext) {
    send(stringBuilder.toString(), ctx.chatId())
}