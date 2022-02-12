package com.liangguo.timingexecutorsamole

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.liangguo.timingexecutor.TimingExecutor
import com.liangguo.timingexecutor.core.TimingExecutorConfig
import com.liangguo.timingexecutor.core.TimingExecutorConfig.checkIntervalMills

class MainActivity : AppCompatActivity() {

    private val mTextView by lazy {
        findViewById<TextView>(R.id.tv)
    }

    init {
        TimingExecutor.config {
            //配置检查的时间是每隔50ms，默认是100
            it.checkIntervalMills = 50L
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView.setOnTouchListener { _, _ ->
            TimingExecutor.delayExecute(2000, mTextView) {
                Toast.makeText(this, "时间到！", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }
}