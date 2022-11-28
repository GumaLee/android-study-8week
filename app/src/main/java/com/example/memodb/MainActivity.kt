package com.example.memodb

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memodb.databinding.ActivityMainBinding
import com.example.memodb.databinding.ActivitySecondBinding
import com.example.memodb.databinding.ItemMemoBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var getResultText: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val dataList: ArrayList<MemoData> = arrayListOf()

        val dataRVAdapter = DataRVAdapter(dataList)

        // registerForActivityResult
        // 메모장에 입력되는 data를 받아서 메모장의 결과를 저장하는 변수를 생성
        getResultText = registerForActivityResult (
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK){
                val content = result.data?.getStringExtra("text").toString()
                val title = result.data?.getStringExtra("title").toString()
                dataList.add(MemoData(content, title))
                viewBinding.rvData.adapter = dataRVAdapter
                viewBinding.rvData.layoutManager = LinearLayoutManager(this)
            }
        }

        viewBinding.fbtnAdd.setOnClickListener {
            // main 엑티비티에서 second 엑티비티로 정보 전달
            var intent = Intent(this, SecondActivity::class.java)
            getResultText.launch((intent))
        }

        dataRVAdapter.setItemClickListener(object: DataRVAdapter.onItemClickListener {
            override fun onClick(v: View, position: Int) {
                var intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
                // Toast.makeText(viewBinding.root.context, "삭제", Toast.LENGTH_SHORT).show()
                // dataList.removeAt(position)
                // dataRVAdapter.notifyDataSetChanged()
            }
        })
    }
}