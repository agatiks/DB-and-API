package com.example.lab7_api

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab7_api.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val userId: Int = 1
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostListAdapter
    private lateinit var service: PostAPIService
    private lateinit var listState: Parcelable
    private val KEY_LIST = "list"
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        service = APIApp.instance.service
        val manager = LinearLayoutManager(this@MainActivity)
        postAdapter = PostListAdapter(
            emptyList()
        ) {
            onDelete(it)
        }
        binding.postList.apply {
            layoutManager = manager
            adapter = postAdapter
        }
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
            lifecycle.coroutineScope.async {
                val postList = service.listPosts()
                Log.d(TAG, "onCreate: $postList")
                Toast.makeText(APIApp.instance, "got response", Toast.LENGTH_SHORT).show()
                postAdapter.postList = postList
            }
        } else {
            val postList: ArrayList<Post> =
                savedInstanceState.getParcelableArrayList(KEY_LIST) ?: arrayListOf()
            Log.d(TAG, "onCreate:123  ${postList}")
            postAdapter.postList = postList.toArray().toList() as List<Post>
        }
        binding.progressCircular.visibility = View.GONE

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
    private fun onDelete(position: Int) {
        lifecycle.coroutineScope.async {
            val result = APIApp.instance.service.deletePost("${position + 1}")
            Toast.makeText(APIApp.instance, "delete post with ${result.code()}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(
            TAG,
            "onSaveInstanceState() called with: outState = $outState"
        )
        outState.putParcelableArrayList(KEY_LIST, ArrayList(postAdapter.postList))
        super.onSaveInstanceState(outState)
    }

}