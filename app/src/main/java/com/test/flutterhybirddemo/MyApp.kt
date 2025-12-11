package com.test.flutterhybirddemo

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MyApp : Application() {
    // 定义引擎唯一标识
    companion object {
        const val FLUTTER_ENGINE_ID = "my_custom_flutter_engine"
    }

    lateinit var flutterEngine: FlutterEngine

    override fun onCreate() {
        super.onCreate()

        // 1. 创建FlutterEngine实例
        flutterEngine = FlutterEngine(this)

        // 2. 配置引擎（可选，如初始化路由、传递参数）
        val initialRoute = "/home" // 指定Flutter的初始路由
        flutterEngine.navigationChannel.setInitialRoute(initialRoute)

        // 3. 预加载Flutter Dart代码（关键：提前执行runApp）
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // 4. 将引擎缓存到FlutterEngineCache，供后续Activity绑定
        FlutterEngineCache
            .getInstance()
            .put(FLUTTER_ENGINE_ID, flutterEngine)
    }

    override fun onTerminate() {
        super.onTerminate()
        // 释放引擎资源（避免内存泄漏）
        flutterEngine.destroy()
    }
}