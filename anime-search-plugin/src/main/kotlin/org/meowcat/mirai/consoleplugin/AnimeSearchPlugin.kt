package org.meowcat.mirai.consoleplugin

import kotlinx.coroutines.delay
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.message.data.queryUrl


object AnimeSearchPlugin : KotlinPlugin() {

    private val nbnhhshClient = NbnhhshService.Factory.create()
    private val animeSearchClient = TraceMoeService.Factory.create()
    override fun onEnable() {

        //能不能好好说话
        subscribeGroupMessages {
            always {
                var str = this.message.content
                if (str.startsWith("/nbnhhsh ")
                        || str.startsWith("/能不能好好说话 ")
                        || str.startsWith("好好说话 ")) {
                    if (str.startsWith("/nbnhhsh ") || str.startsWith("/nbnhhsh ")) {
                        str = str.substring(9)
                    } else {
                        str = str.substring(5)
                    }
                    val guess = nbnhhshClient.guess(mapOf("text" to str))
                    val resultAnswers: StringBuilder = java.lang.StringBuilder()
                    val results = guess.execute().body()?.get(0)?.trans
                    results?.forEach {
                        resultAnswers.append("\n" + it)
                    }
                    reply("${str}的意思是$resultAnswers")
                }
            }

            //echo
            startsWith("echo "){
                val text = this.message.content.substring(5)
                delay(200)
                reply(text)
            }


            //动画搜图
            startsWith("as"){
                val image = this.message[Image]
                if (image != null) {
                    val url = image.queryUrl()
                    delay(200)
                    reply("查询中")
                    val aniResults = animeSearchClient.search(url).execute().body()?.docs
                    reply("查询完毕")
                    val aniResult = aniResults!![0]
                    val replyText = StringBuilder()
                    var time1 = aniResult.at / 60
                    var time2 = time1.toInt().toFloat()
                    PlainText
                    time1 -= time2
                    time1 *= 60
                    replyText.append("""
                        番剧名称：${aniResult.anime}
                        上映时间：${aniResult.season}
                        出现集数：${aniResult.episode}
                        出现时间：${time2}分${time1}秒
                        相似度：${aniResult.similarity}
                    """.trimIndent())
                    reply(replyText.toString())
                    delay(1500)
                }else{
                    reply("请在as后添加图片");
                    return@startsWith
                }

            }
        }



    }

    override fun onDisable() {

    }
}
