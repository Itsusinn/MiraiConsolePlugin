package org.meowcat.mirai.consoleplugin

import net.mamoe.mirai.message.data.PlainText

object Tool {
    fun timeConvert(time:Float):String{
        var time1 = time / 60
        var time2 = time1.toInt().toFloat()
        PlainText
        time1 -= time2
        time1 *= 60
        return "${time2}分${time1}秒"
    }
}