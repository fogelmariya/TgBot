package com.github.fogelao.ui

import com.github.fogelao.extensions.send
import com.github.fogelao.presentation.Presenter
import com.github.fogelao.presentation.view.MainView
import org.telegram.abilitybots.api.bot.AbilityBot
import org.telegram.abilitybots.api.objects.Ability
import org.telegram.abilitybots.api.objects.Locality
import org.telegram.abilitybots.api.objects.Privacy
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.logging.BotLogger

/**
 * @author Fogel Artem on 27.05.2018.
 */
class BotCore : AbilityBot(BOT_TOKEN, BOT_USERNAME), MainView {

    companion object {
        const val BOT_TOKEN = "BOT_TOKEN"
        const val BOT_USERNAME = "Post Track Bot"
        const val GOD_ID = 325376472
        const val TOKEN = "9f15fde0150fdeedb549d34007ad8786"
        const val DB_NAME_CODES = "CODES"
    }

    private val presenter = Presenter(this)

    init {
        BotLogger.info("INFO", "Started..")
    }

    override fun creatorId() = GOD_ID

    override fun onUpdateReceived(update: Update) {
//        println(update.message.from.userName)
        super.onUpdateReceived(update)
    }

    fun showCommands() = Ability
            .builder()
            .name(DEFAULT)
            .privacy(Privacy.PUBLIC)
            .locality(Locality.ALL)
            .input(0)
            .action { ctx -> silent.send("Hello there!", ctx.chatId()) }
            .build()

    fun showUsers() = Ability
            .builder()
            .name("users")
            .input(0)
            .locality(Locality.USER)
            .privacy(Privacy.ADMIN)
            .action { ctx ->
                run {
                    val users = users().values.toList()
                    val stringBuilder = StringBuilder()
                    users.forEach { stringBuilder.appendln(it.username()) }
                    silent.send(stringBuilder.toString(), ctx.chatId())
                }
            }
            .build()

    fun sayHelloWorld() = Ability
            .builder()
            .name("hello")
            .info("says hello world!")
            .input(0)
            .locality(Locality.ALL)
            .privacy(Privacy.ADMIN)
            .action { silent }
            .build()

    fun track() = Ability
            .builder()
            .name("track")
            .info("Add code to track list")
            .input(1)
            .locality(Locality.USER)
            .privacy(Privacy.PUBLIC)
            .action { ctx ->
                run {
                    val userId = ctx.user().id().toString()
                    val code = ctx.arguments().first()

                    val codesMap = db.getMap<String, MutableSet<String>>(DB_NAME_CODES)
                    val codes = codesMap[userId] ?: HashSet()
                    println(codes)
                    codes.add(code)
                    codesMap[userId] = codes

                    presenter.track(code, ctx.chatId())
                    silent.send("Getting info..", ctx.chatId())
                }
            }
            .build()

    fun getCodes() = Ability
            .builder()
            .name("codes")
            .info("Show your track codes list")
            .input(0)
            .locality(Locality.USER)
            .privacy(Privacy.PUBLIC)
            .action { ctx ->
                run {
                    val userId = ctx.user().id().toString()

                    val codesMap = db.getMap<String, MutableSet<String>>(DB_NAME_CODES)
                    val codes = codesMap[userId] ?: HashSet()

                    if (codes.isNotEmpty()) {
                        val stringBuilder = StringBuilder("Your codes:\n")
                        codes.forEach {
                            stringBuilder.append("$it\n")
                        }
                        silent.send(stringBuilder.toString(), ctx)
                    } else {
                        silent.send("You have no track codes", ctx)
                    }
                }
            }
            .build()

    fun clear() = Ability
            .builder()
            .name("clear")
            .info("Clear tour track code list")
            .input(0)
            .locality(Locality.USER)
            .privacy(Privacy.PUBLIC)
            .action { ctx ->
                run {
                    val userId = ctx.user().id().toString()

                    val codesMap = getCodesMap()
                    val codes = codesMap[userId] ?: HashSet()
                    codes.clear()
                    codesMap[userId] = codes
                    db.commit()
                    silent.send("Your track codes are clear", ctx)
                }
            }
            .build()

    override fun showCodeInfo(message: String, chatId: Long) {
        if (message.isEmpty())
            return
        silent.send("Track statement:\n$message", chatId)
    }

    private fun getCodesMap() = db.getMap<String, MutableSet<String>>(DB_NAME_CODES)
}