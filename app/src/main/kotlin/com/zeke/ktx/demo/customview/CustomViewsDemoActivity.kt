package com.zeke.ktx.demo.customview

import com.zeke.ktx.demo.BaseCardDemoActivity
import com.zeke.ktx.demo.customview.views.ChartMusicView
import com.zeke.ktx.demo.customview.views.ChartTextView
import com.zeke.ktx.demo.modle.CardItemModel
import com.zeke.ktx.demo.modle.DemoContentModel

/**
 * author: King.Z <br>
 * date:  2020/4/19 11:32 <br>
 * description: 自定义view Demo展示的页面 <br>
 */
class CustomViewsDemoActivity : BaseCardDemoActivity() {
    override fun initPageModels() {
        super.initPageModels()
        // 创建卡片数据
        cardList.add(CardItemModel("ChartMusicView", ChartMusicView(this)))
        cardList.add(CardItemModel("ChartMusicView2", ChartTextView(this)))

        // 初始化Page数据
        pageModels.add(DemoContentModel("自定义views", cardList))
    }
}