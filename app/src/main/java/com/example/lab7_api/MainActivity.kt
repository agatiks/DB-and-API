package com.example.lab7_api

import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab7_api.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val userId: Int = 1
    private var postList: List<Post>? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostListAdapter
    private lateinit var service: PostAPIService
    private lateinit var listState: Parcelable
    private val KEY_LIST = "list"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        service = APIApp.instance.service
        lifecycle.coroutineScope.async {
            postList = service.listPosts()
            Toast.makeText(APIApp.instance, "got response", Toast.LENGTH_SHORT).show()
            postAdapter = PostListAdapter(
                this@MainActivity,
                lifecycle.coroutineScope,
                postList!!,
                userId
            )
                val manager = LinearLayoutManager(this@MainActivity)
                binding.postList.apply {
                    layoutManager = manager
                    adapter = postAdapter
                }
            binding.progressCircular.visibility = View.GONE
        }
        binding.addButton.setOnClickListener{
            lifecycle.coroutineScope.async {
                val result = service.makePost(Post("Hello", "It's my post"))
                Toast.makeText(
                    APIApp.instance,
                    "put post with ${result.code()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Toast.makeText(APIApp.instance, "${savedInstanceState.getString(KEY_LIST)}", Toast.LENGTH_SHORT).show()
    }
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        val str = postList!![1].title
        outState.putString(KEY_LIST, str)
        super.onSaveInstanceState(outState, outPersistentState)
    }

}