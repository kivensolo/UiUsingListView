package com.zeke.demo.model

/**
 * author: King.Z <br>
 * date:  2020/3/13 23:43 <br>
 * description: Demo内容数据模型 <br>
 *     包括当前页标题和当前页content数据集合
 */
class DemoContentModel internal constructor(var title: String,
        var  demoData:MutableList<CardItemModel>?)