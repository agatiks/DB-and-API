package com.example.lab8_db

import android.opengl.Visibility
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab8_db.databinding.ActivityMainBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostListAdapter
    private var cnt = 0
    private lateinit var db: Dao
    private val KEY_LIST = "list"
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        db = APIApp.instance.database
        lifecycleScope.launch {
            db.getAllPosts().collect{
                postAdapter.postList = it
            }
        }
        binding.refreshButton.setOnClickListener{
            lifecycleScope.async {
                upd()
            }
            cnt = postAdapter.postList.size
        }
        binding.addButton.setOnClickListener{
            lifecycleScope.async {
                val post = Post(cnt, "Hello", "It's my post")
                Log.i("post_id", "${post.id}")
                db.insertAll(listOf(post))
            }
            cnt++
        }
    }

    suspend fun upd() {
        binding.progressCircular.visibility = View.VISIBLE
        db.clear()
        val postList = APIApp.instance.service.listPosts()
        db.insertAll(postList)
        binding.progressCircular.visibility = View.GONE
    }

    private fun onDelete(post: Post) {
        lifecycleScope.async {
            db.deletePost(post)
        }
        cnt--
    }
}
