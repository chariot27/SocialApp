package com.chariot.nexus.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chariot.nexus.Models.Post
import com.chariot.nexus.Models.User
import com.chariot.nexus.R
import com.chariot.nexus.Utils.USER_NODE
import com.chariot.nexus.databinding.PostRvBinding
import com.chariot.nexus.databinding.ReelDgBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import java.sql.Time

class PostAdapter(var context:Context,var postList: ArrayList<Post>):RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(var binding: PostRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MyHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount() : Int {
        return postList.size
    }

    override fun onBindViewHolder(holder : MyHolder, position : Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).Uid).get().addOnSuccessListener {

                var user=it.toObject<User>();
                Glide.with(context).load(user!!.image).placeholder(R.drawable.profile).into(holder.binding.profileImage)
                holder.binding.profileName.setText(user.name)

            }
        }catch (e:Exception){

        }
        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.profile).into(holder.binding.imageView2)
        holder.binding.time.setText(postList.get(position).time)
        holder.binding.caption.setText(postList.get(position).caption)
        holder.binding.send.setOnClickListener {
            var i=Intent(Intent.ACTION_SEND)
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
            context.startActivity(i)

        }
        holder.binding.like.setOnClickListener {
            holder.binding.like.setImageResource(R.drawable.heart_full)

        }

    }

}