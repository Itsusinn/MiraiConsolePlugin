package org.meowcat.mirai.consoleplugin

import kotlinx.coroutines.delay
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.MemberCommandSender
import net.mamoe.mirai.console.data.*
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.queryUrl
import org.meowcat.mirai.consoleplugin.AnimeSearchPlugin.animeSearchClient


object AnimeSearchPlugin : KotlinPlugin(
    JvmPluginDescription(
    "animeSearchPlugin",
    "0.1.0"
)
) {
    val animeSearchClient = TraceMoeService.create()



    override fun onEnable() {
        subscribeAlways<GroupMessageEvent>{

        }
        MySetting.reload()
        MyPluginData.reload()
        MyCompositeCommand.register()

    }

    override fun onDisable() {
        MyCompositeCommand.unregister()
    }

}

object MySetting : AutoSavePluginConfig() {
    val isEnabled by value(mutableListOf(1L,2L))
}
object MyPluginData : AutoSavePluginData() {

//    var list: MutableList<String> by value(mutableListOf("a", "b")) // mutableListOf("a", "b") 是初始值, 可以省略
//    var long: Long by value(0L) // 允许 var
//    var int by value(0) // 可以使用类型推断, 但更推荐使用 `var long: Long by value(0)` 这种定义方式.

}

// 复合指令
object MyCompositeCommand : CompositeCommand(
    AnimeSearchPlugin, "as",
    description = "动漫搜索"
    // prefixOptional = true // 还有更多参数可填, 此处忽略
) {



    // /as search <一张图片>
    // 支持 Image 类型, 需在聊天中执行此指令
    @SubCommand
    suspend fun CommandSender.search(image: Image){

        val url = image.queryUrl()
        delay(200)
        sendMessage("查询中")
        val aniResults = animeSearchClient.search(url).execute().body()?.docs
        sendMessage("查询完毕")
        val aniResult = aniResults!![0]
        val replyText = StringBuilder()

        replyText.append("""
                        番剧名称：${aniResult.anime}
                        上映时间：${aniResult.season}
                        出现集数：${aniResult.episode}
                        出现时间：${Tool.timeConvert(aniResult.at)}
                        相似度：${aniResult.similarity}
                    """.trimIndent())
        sendMessage(replyText.toString())
        delay(1500)
    }

    //开启
    @SubCommand
    suspend fun CommandSender.enable(){
        if (this is MemberCommandSender){
            MySetting.isEnabled.add(this.group.id)
            sendMessage("动画搜索 enabled")
        }
    }
    //关闭
    @SubCommand
    suspend fun CommandSender.disable(){
        if (this is MemberCommandSender){
            MySetting.isEnabled.remove(this.group.id)
            sendMessage("动画搜索 disabled")
        }
    }

}
