package com.example.sms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import java.net.URL

class Activity3 : AppCompatActivity() {

    private lateinit var kod_text: TextView
    private var number_text: TextView? = null
    private var api_key: String? = null
    var id: String = "_"
    var number: String = "_"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)
        kod_text = findViewById(R.id.kod)
        number_text = findViewById(R.id.number)

        var map = intent.getStringExtra(MAP)
        api_key = map?.substringBefore("response:")
        var resp = map?.substringAfter("response:")
        var response = resp?.substringAfterLast("ACCESS_NUMBER:")

        number = response?.substringAfterLast(":").toString()
        id = response?.substringBeforeLast(":").toString()

        val url = "https://sms-activate.ru/stubs/handler_api.php?api_key=$api_key&action=setStatus&status=1&id=$id"

        Log.d("Number","$number - #id $id")
        kod_text?.setText("чекаю ...")
        number_text?.setText("$number")

    }

    override fun onResume() {
        super.onResume()
        var kode = get_code(id)
        kod_text.text = kode
        kod_text.setOnClickListener {
            kod_text.text = kode
        }

    }


    fun get_code(id:String): String? {
        val url = "https://sms-activate.ru/stubs/handler_api.php?api_key=$api_key&action=getStatus&id=$id"
        var response: String? = null

        val job = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                val postOperation =
                    async(Dispatchers.IO) { // <- extension on launch scope, launched in IO dispatcher
                        // blocking I/O operation
                        URL(url).readText()
                    }
                response =
                    postOperation.await() // wait for result of I/O operation without blocking the main thread
                    Log.d("Kod:" , response.toString())
                if ("STATUS_OK:" in response!!) {
                    response = response!!.substringBefore("STATUS_OK:").toString()
                    kod_text.text = response
                    break
                   // return response?.substringAfter("STATUS_OK:")?.toInt()
                }
            }
        }

        return response
    }
    companion object {
        const val MAP = "map"
    }


}
