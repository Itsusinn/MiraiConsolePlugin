
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.pure.MiraiConsolePureLoader
import org.meowcat.mirai.consoleplugin.AnimeSearchPlugin

fun main() {
    MiraiConsolePureLoader.main(arrayOf())
    AnimeSearchPlugin.enable() // 主动启用插件
}