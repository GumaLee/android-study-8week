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

        val roomDB = AppDataBase.getInstance(this)
        if(roomDB != null) {
            // 앱을 시작하면 roomDB에 값이 있는 경우 그 데이터를 가지고 온다.
            val memoList = roomDB.memoDao().selectAll()

            for(i in 0 until memoList.size)
                dataList.add(memoList[i]) // 가지고 온 데이터를 recycler view에 하나씩 넣어준다.

            viewBinding.rvData.adapter = dataRVAdapter
            viewBinding.rvData.layoutManager = LinearLayoutManager(this)
        }
        // registerForActivityResult
        // 메모장에 입력되는 data를 받아서 메모장의 결과를 저장하는 변수를 생성
        getResultText = registerForActivityResult (
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK){
                if(roomDB != null) {
                    val memoList = roomDB.memoDao().selectAll()
                    dataList.add(memoList[memoList.size-1])

                    viewBinding.rvData.adapter = dataRVAdapter
                    viewBinding.rvData.layoutManager = LinearLayoutManager(this)
                }
//                val content = result.data?.getStringExtra("text").toString()
//                val title = result.data?.getStringExtra("title").toString()
//                dataList.add(MemoData(content, title))
//                viewBinding.rvData.adapter = dataRVAdapter
//                viewBinding.rvData.layoutManager = LinearLayoutManager(this)
            }
        }

        viewBinding.fbtnAdd.setOnClickListener {
            // main 엑티비티에서 second 엑티비티로 정보 전달
            var intent = Intent(this, SecondActivity::class.java)
            getResultText.launch((intent))
        }

        dataRVAdapter.setItemClickListener(object: DataRVAdapter.onItemClickListener {
            override fun onClick(v: View, position: Int) {
                Toast.makeText(viewBinding.root.context, "삭제", Toast.LENGTH_SHORT).show()
                dataList.removeAt(position)
                dataRVAdapter.notifyDataSetChanged()

                val roomDB = AppDataBase.getInstance(this@MainActivity)
                roomDB?.memoDao()?.delete(dataList[position])
                dataRVAdapter.notifyDataSetChanged()
            }
        })
    }
}