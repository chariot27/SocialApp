package com.chariot.nexus.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chariot.nexus.Adapters.FollowAdapter
import com.chariot.nexus.Adapters.PostAdapter
import com.chariot.nexus.Adapters.SearchAdapter
import com.chariot.nexus.Models.Post
import com.chariot.nexus.Models.User
import com.chariot.nexus.R
import com.chariot.nexus.Utils.FOLLOW
import com.chariot.nexus.Utils.POST
import com.chariot.nexus.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private var postList=ArrayList<Post>()
    private lateinit var adapter : PostAdapter
    private var followList=ArrayList<User>()
    private lateinit var followAdapter : FollowAdapter
    private lateinit var searchAdapter : SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = PostAdapter(requireContext(),postList)

        binding.rv2.layoutManager=LinearLayoutManager(requireContext())
        binding.rv2.adapter=adapter

        followAdapter=FollowAdapter(requireContext(),followList)


        //setHasOptionsMenu(true)
        //(requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)

        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList=ArrayList<Post>()
            postList.clear()
            for(i in it.documents){

                var post:Post=i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}