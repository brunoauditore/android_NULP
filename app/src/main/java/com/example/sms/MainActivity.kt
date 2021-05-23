package com.example.sms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var key: EditText? = null
    private var button_save: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        key = findViewById(R.id.key)
        button_save = findViewById(R.id.Save)

        button_save?.setOnClickListener {
            if(key?.text?.toString()?.equals("")!!)
                Toast.makeText(this, "Поле Key не може бути пустим",Toast.LENGTH_LONG).show()
            else {
                val key_api = Intent(this,Activity2::class.java)
                val key_text = key?.text?.toString()
                key_api.putExtra(Activity2.TORAL_COUNT, key_text)
                startActivity(key_api)
            }
        }
    }
}