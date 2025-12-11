package com.test.flutterhybirddemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.test.flutterhybirddemo.databinding.ActivityMainBinding
import io.flutter.embedding.android.FlutterActivity;

class MainActivity : AppCompatActivity() {
    val vb by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(vb.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vb.btnFlutter.setOnClickListener {
            val flutterIntent = FlutterActivity
                .withCachedEngine(MyApp.FLUTTER_ENGINE_ID) // 指定缓存的引擎ID
                .build(this)
            startActivity(flutterIntent)
        }
    }
}