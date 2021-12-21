package com.example.lab8_db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8_db.databinding.PostItemBinding

class PostListAdapter(
    postList: List<Post>,
    private val onDelete: (Post) -> Unit
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
        holder.delete.setOnClickListener { onDelete(postList[position]) }
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