package com.example.sms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import java.net.URL

class Activity3 : AppCompatActivity() {

    private lateinit var kod_text: TextView
    private lateinit var button: Button
    private var number_text: TextView? = null
    private var api_key: String? = null
    var id: String = "_"
    var number: String = "_"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)
        kod_text = findViewById(R.id.kod)
        number_text = findViewById(R.id.number)
        button = findViewById(R.id.button)

        var map = intent.getStringExtra(MAP)
        api_key = map?.substringBefore("response:")
        var resp = map?.substringAfter("response:")
        var response = resp?.substringAfter("ACCESS_NUMBER:")

        number = response?.substringAfter(":").toString()
        id = response?.substringBefore(":").toString()



    }

    override fun onResume() {
        super.onResume()

        // Зміна статуса на 1 - тобто готовий прийняти смс
        val url_edit_status = "https://sms-activate.ru/stubs/handler_api.php?api_key=$api_key&action=setStatus&status=1&id=$id"
        doAsync {
            URL(url_edit_status)
        }

        val url = "https://sms-activate.ru/stubs/handler_api.php?api_key=$api_key&action=setStatus&status=1&id=$id"
            //Log.d("Number","$number - #id $id")

        number_text?.text = "$number"

        val url_get_code = "https://sms-activate.ru/stubs/handler_api.php?api_key=$api_key&action=getStatus&id=$id"
        var code: String = "чекаю код.."
        var response = ""
        button.setOnClickListener() {

            doAsync {
                URL(url_edit_status)
                response = URL(url_get_code).readText()
                if ("STATUS_OK:" in response) {
                    code = response.substringAfter("STATUS_OK:").toString()
                    kod_text.text = code
                }
            }

        }

    }

    companion object {
        const val MAP = "map"
    }


}
