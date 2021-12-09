package com.example.lab7_api

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7_api.databinding.PostItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostListAdapter(private val context: Context, private val coroutineScope: CoroutineScope, private val postList: List<Post>, private val userId: Int): RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val holder = PostViewHolder(
                LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        )
        return holder
    }

    private fun deletePost(position: Int) {
        coroutineScope.async {
            val result = APIApp.instance.service.deletePost("${position+1}")
            Toast.makeText(APIApp.instance, "delete post with ${result.code()}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.title.text = postList[position].title
        holder.body.text = postList[position].body
        holder.delete.setOnClickListener{deletePost(position)}
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding: PostItemBinding = PostItemBinding.bind(itemView)
        val title: TextView = binding.postTitle
        val body: TextView = binding.postBody
        val delete: ImageView = binding.delete
    }

}