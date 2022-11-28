package com.example.memodb

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.memodb.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnSave.setOnClickListener {
            // second 엑티비티에서 main 엑티비티로 정보 전달
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("text", viewBinding.edtContent.text.toString())
            intent.putExtra("title", viewBinding.edtTitle.text.toString())
            setResult(RESULT_OK, intent)
            if (!isFinishing) finish()
        }
    }
}