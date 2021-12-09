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

class PostListAdapter(
    postList: List<Post>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    var  postList = postList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val holder = PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        )
        return holder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.title.text = postList[position].title
        holder.body.text = postList[position].body
        holder.delete.setOnClickListener { onDelete(position) }
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