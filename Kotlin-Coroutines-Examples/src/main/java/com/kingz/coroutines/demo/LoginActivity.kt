package com.kingz.coroutines.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.kingz.coroutines.data.api.RetrofitBuilder
import com.kingz.coroutines.data.api.WAndroidApiImpl
import com.kingz.coroutines.demo.base.BaseVMActivity
import com.kingz.coroutines.demo.vm.LoginViewModel
import com.kingz.coroutines.utils.ViewModelFactory
import com.zeke.example.coroutines.R
import com.zeke.kangaroo.utils.ZLog
import com.zeke.network.OkHttpClientManager
import com.zeke.network.OkHttpClientManager.getAsyn
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Cache

/**
 * @author zeke.wang
 * @date 2020/5/27
 * @maintainer zeke.wang
 * @copyright 2020 www.xgimi.com Inc. All rights reserved.
 * @desc: 模拟登录操作的页面
 */
class LoginActivity : BaseVMActivity<LoginViewModel>() {

    // Use the 'by viewModels()' Kotlin property delegate
    override val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.build {
            LoginViewModel(WAndroidApiImpl(RetrofitBuilder.wAndroidApi))
        }
    }

    override val layoutRes: Int = R.layout.activity_login
    override val binding: ViewDataBinding?
        get() = DataBindingUtil.setContentView(this, layoutRes)

    override fun onCreate(savedInstanceState: Bundle?) {
//      binding.viewModel = viewModel
        binding?.lifecycleOwner = this
        super.onCreate(savedInstanceState)

        viewModel.viewModelScope.apply {
        }

        // 不使用DataBinding时需要这样手动设置
        viewModel.apply {
            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            loginData.observe(this@LoginActivity, Observer {
                it?.apply {
                    ZLog.d("MVVM", "View onChanged --->$this")
                    tips_view.text = "登录成功,可登录"
                    startActivity(Intent(this@LoginActivity, ChaptersActivity::class.java))
                }
            })
        }

        bt_login.setOnClickListener {
            ZLog.d("MVVM", "View ---> fetchMockData")
//            viewModel.fetchMockData()
//            tips_view.text = "登陆中...."\
            GlobalScope.launch(Dispatchers.IO) {
                val absoluteFile = externalCacheDir.absoluteFile
                Log.d("kingz", "absoluteFile=$absoluteFile")
                OkHttpClientManager.getInstance().setCache(Cache(absoluteFile, 10 * 1024 * 1024))
                val result = getAsyn("https://publicobject.com/helloworld.txt")
                withContext(Dispatchers.Main){
                    tips_view.text = result.body()?.string() ?: ""
                }
            }

//            runBlocking {
//                val absoluteFile = externalCacheDir.absoluteFile
//                Log.d("kingz", "absoluteFile=$absoluteFile")
//                OkHttpClientManager.getInstance().setCache(Cache(absoluteFile, 10 * 1024 * 1024))
//                getAsyn("https://publicobject.com/helloworld.txt")
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ZLog.d("MVVM", "View ---> onDestroy")
    }
}