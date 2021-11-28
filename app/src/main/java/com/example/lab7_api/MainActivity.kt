package com.example.lab7_api

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab7_api.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private val userId: Int = 1
    private var postList: List<Post>? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostListAdapter
    private lateinit var service: PostAPIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        service = APIApp.instance.service
        val callPosts = service.listPosts(userId)

        callPosts.enqueue(object: Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("e", "Failed with", t)
            }
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Toast.makeText(APIApp.instance, "got response", Toast.LENGTH_SHORT).show()
                postList = response.body()
                makePostList(postList!!) // обработать если пустой
                /* Log.i("list", "${postList?.get(0)?.title}") */
            }
        })
        binding.addButton.setOnClickListener{addPost()}
    }

    private fun addPost() {
        val newPostCall = service.makePost(userId, "Hello", "It's my post")
        newPostCall.enqueue(object: Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("e", "Failed with", t)
            }
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                Toast.makeText(APIApp.instance, "put post with ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun makePostList(list: List<Post>) {
        postAdapter = PostListAdapter(this, list, userId)
        val manager = LinearLayoutManager(this)
        binding.postList.apply {
            layoutManager = manager
            adapter = postAdapter
        }
    }
}