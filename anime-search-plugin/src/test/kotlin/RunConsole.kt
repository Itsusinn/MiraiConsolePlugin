import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.pure.MiraiConsolePureLoader
import org.meowcat.mirai.consoleplugin.AnimeSearchPlugin

suspend fun main() {
    MiraiConsolePureLoader.startAsDaemon()

    // 如下启动方案预计在 1.0-RC 支持

    AnimeSearchPlugin.load() // 主动加载插件, Console 会调用 MyPluginMain.onLoad
    AnimeSearchPlugin.enable() // 动启用插件, Console 会调用 MyPluginMain.onEnable

    val bot = MiraiConsole.addBot(123456, "").alsoLogin() // 登录一个测试环境的 Bot

    MiraiConsole.job.join()
}