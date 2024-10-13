package com.chariot.nexus.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chariot.nexus.R
import com.chariot.nexus.Utils.FOLLOW
import com.chariot.nexus.databinding.SearchRvBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class SearchAdapter(var context: Context,var userList: ArrayList<com.chariot.nexus.Models.User>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    inner class ViewHolder(var binding: SearchRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        var binding = SearchRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount() : Int {
        return userList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        var isfollow = false
        Glide.with(context).load(userList.get(position).image).placeholder(R.drawable.profile).into(holder.binding.profileImage)
        holder.binding.name.text=userList.get(position).name

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList.get(position).email).get().addOnSuccessListener {
            if(it.documents.size==0){
                isfollow = false
            }else{
                holder.binding.follow.text="unfollow"
                isfollow = true
            }
        }
        holder.binding.follow.setOnClickListener{
            if(isfollow){
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList.get(position).email).get().addOnSuccessListener {
                   Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document(it.documents.get(0).id).delete()
                    holder.binding.follow.text="follow"
                    isfollow = false
                }
            }else{
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document()
                    .set(userList.get(position))
                holder.binding.follow.text="unfollow"
                isfollow = true
                loadData()
            }
        }
    }
    fun loadData(){
        var followList=ArrayList<com.chariot.nexus.Models.User>()
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).get()
            .addOnSuccessListener {
                var tempList=ArrayList<com.chariot.nexus.Models.User>()
                followList.clear()
                for(i in it.documents){
                    var user: com.chariot.nexus.Models.User =i.toObject<com.chariot.nexus.Models.User>()!!
                    tempList.add(user)
                }
                followList.addAll(tempList)
            }
    }


}