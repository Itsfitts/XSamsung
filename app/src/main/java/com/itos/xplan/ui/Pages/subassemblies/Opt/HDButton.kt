package com.itos.xplan.ui.Pages.subassemblies.Opt

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itos.xplan.XPlan.Companion.app
import com.itos.xplan.utils.OLog

@Composable
fun HDButton() {
    Row(
        modifier = Modifier
            .padding(vertical = 45.dp)
    ) {
        FilledTonalButton(
            modifier = Modifier
                .size(width = 130.dp, height = 60.dp),
            shape = RoundedCornerShape(30),
            onClick = { HideHD() }
        ) {
            Text("隐藏HD")
        }
        Spacer(modifier = Modifier.width(25.dp))
        FilledTonalButton(
            modifier = Modifier
                .size(width = 130.dp, height = 60.dp),
            shape = RoundedCornerShape(30),
            onClick = { UnHideHD() }
        ) {
            Text("还原HD")
        }
    }
}

fun HideHD() {
    var data = app.ShizukuExec("settings get secure icon_blacklist".toByteArray())
    OLog.i("隐藏HD", "当前黑名单列表: $data")
    data = data!!.trimEnd()
    data = data.replace(Regex(",+"), ",")
    OLog.i("隐藏HD", "处理后黑名单列表: $data")
    app.ShizukuExec("settings put secure icon_blacklist $data,rotate,hd".toByteArray())
}

fun UnHideHD() {
    var data = app.ShizukuExec("settings get secure icon_blacklist".toByteArray())
    data = data!!.trimEnd()

    OLog.i("还原HD", "待处理数据: $data")
    val targets = listOf("rotate", "hd")
    // 构建正则表达式，使用 joinToString 将多个子串连接起来
    val regex = targets.joinToString(separator = "|").toRegex()
    // 使用正则表达式替换为""
    var resultString = regex.replace(data, "")
    resultString = resultString.replace(Regex(",+"), ",")
    OLog.i("还原HD", "处理结果: $resultString")

    app.ShizukuExec("settings put secure icon_blacklist $resultString".toByteArray())
}

