package com.chariot.nexus.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chariot.nexus.Adapters.ReelAdapter
import com.chariot.nexus.Models.Reel
import com.chariot.nexus.R
import com.chariot.nexus.Utils.REEL
import com.chariot.nexus.databinding.FragmentReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class ReelsFragment : Fragment() {

    private lateinit var binding:FragmentReelsBinding
    lateinit var adapter : ReelAdapter
    var reelList=ArrayList<Reel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelsBinding.inflate(inflater, container, false)
        adapter = ReelAdapter(requireContext(),reelList)
        binding.vp.adapter=adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList=ArrayList<Reel>()
            reelList.clear()
            for(i in it.documents){
                var reel=i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.reverse()
            adapter.notifyDataSetChanged()
            reelList.addAll(tempList)

        }


        return binding.root
    }

    companion object {

    }
}