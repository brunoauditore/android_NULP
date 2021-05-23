package com.example.sms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL


class Activity2 : AppCompatActivity() {
    companion object {
        const val TORAL_COUNT = "toral count"
    }

    private lateinit var balans: TextView
    private var api_key:String? = null

    private var button_tg: Button? = null
    private var button_vi: Button? = null
    private var button_fb: Button? = null
    private var button_go: Button? = null
    private var button_tw: Button? = null
    private var button_ig: Button? = null

    private var check_country_0: CheckBox? = null
    private var check_country_1: CheckBox? = null
    private var check_country_2: CheckBox? = null
    private var check_country_3: CheckBox? = null
    private var check_country_4: CheckBox? = null
    private var check_country_5: CheckBox? = null

    private var country_arr = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        showeKey()
        Log.d("d", api_key.toString())
        balans = findViewById(R.id.Balans)

        button_tg = findViewById(R.id.tg)
        button_go = findViewById(R.id.go)
        button_fb = findViewById(R.id.fb)
        button_vi = findViewById(R.id.vi)
        button_ig = findViewById(R.id.ig)
        button_tw = findViewById(R.id.tw)

        check_country_0 = findViewById(R.id.country_0)
        check_country_1 = findViewById(R.id.country_1)
        check_country_2 = findViewById(R.id.country_2)
        check_country_3 = findViewById(R.id.country_3)
        check_country_4 = findViewById(R.id.country_4)
        check_country_5 = findViewById(R.id.country_5)


        button_tg?.setOnClickListener {
            get_number("tg")
        }
        button_go?.setOnClickListener {
            get_number("go")
        }
        button_fb?.setOnClickListener {
            get_number("fb")
        }
        button_vi?.setOnClickListener {
            get_number("vi")
        }
        button_ig?.setOnClickListener {
            get_number("ig")
        }
        button_tw?.setOnClickListener {
            get_number("tw")
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("info","work")
        get_balans()
    }
    fun showeKey() {
        api_key = intent.getStringExtra(TORAL_COUNT).toString()
    }

    fun get_balans() {
        val url_balans: String = "https://sms-activate.ru/stubs/handler_api.php?api_key=$api_key&action=getBalance"
        doAsync {
            val api_response_balans:String = URL(url_balans).readText().substringAfterLast(":")
            balans.text = api_response_balans
            Log.d("Info",api_response_balans)

        }
    }
    fun get_number(service:String) {
        var ctr: Int = 0

        if(check_country_0!!.isChecked()){
            ctr = 0
        }
        if(check_country_1!!.isChecked()){
            ctr = 1
        }
        if(check_country_2!!.isChecked()){
            ctr = 2
        }
        if(check_country_3!!.isChecked()){
            ctr = 15
        }
        if(check_country_4!!.isChecked()){
            ctr = 16
        }
        if(check_country_5!!.isChecked()){
            ctr = 12
        }

        val url: String = "https://sms-activate.ru/stubs/handler_api.php?api_key=$api_key&action=getNumber&service=$service&country=$ctr"
        val activity_3 = Intent(this, Activity3::class.java)

        doAsync {
            var api_response = URL(url).readText().toString()
            Log.d("Number", api_response.toString())
            activity_3.putExtra(Activity3.MAP, "$api_key response:$api_response")
            startActivity(activity_3)
        }
    }


}