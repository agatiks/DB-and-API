package com.example.lab7_api

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7_api.databinding.PostItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostListAdapter(private val context: Context, private val postList: List<Post>, private val userId: Int): RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val holder = PostViewHolder(
                LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        )
        return holder
    }

    private fun deletePost(position: Int) {
        val delPostCall = APIApp.instance.service.deletePost(userId, position+1)
        delPostCall.enqueue(object: Callback<Post>{
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("e", "Failed with", t)
            }
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                Toast.makeText(APIApp.instance, "delete post with ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        })
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