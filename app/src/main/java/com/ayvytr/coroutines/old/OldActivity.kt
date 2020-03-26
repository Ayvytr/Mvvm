package com.ayvytr.coroutines.old

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ayvytr.coroutines.R
import kotlinx.android.synthetic.main.activity_old.*
import kotlinx.coroutines.*

class OldActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    lateinit var job:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old)

        btn.setOnClickListener {
            job.cancel()
        }
        run()
    }

    private fun run() {
        job = launch {
            val a = async {
                var i = 0
                while (isActive) {
                    delay(1000)
                    i++

                    Log.e("tag", i.toString())
                    btn.setText(i.toString())
                }
                1
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
