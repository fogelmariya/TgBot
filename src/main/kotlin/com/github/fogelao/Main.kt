package com.github.fogelao

import com.github.fogelao.ui.BotCore
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import toothpick.Toothpick
import toothpick.configuration.Configuration

/**
 * @author Fogel Artem on 24.04.2018.
 */

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ApiContextInitializer.init()
            val api = TelegramBotsApi()
            api.registerBot(BotCore())
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
//            val scope = Toothpick.openScope(Scopes.APP_SCOPE)
//            scope.installModules(AppModule())
//
//            val serverReceiver = scope.getInstance(ServerReceiver::class.java)
//            serverReceiver.start()
        }
    }
}