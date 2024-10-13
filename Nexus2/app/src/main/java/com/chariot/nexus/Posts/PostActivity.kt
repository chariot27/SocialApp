package com.chariot.nexus.Posts

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.chariot.nexus.HomeActivity
import com.chariot.nexus.Models.Post
import com.chariot.nexus.Models.User
import com.chariot.nexus.Utils.POST
import com.chariot.nexus.Utils.POST_FOLDER
import com.chariot.nexus.Utils.USER_NODE
import com.chariot.nexus.Utils.uploadImage
import com.chariot.nexus.databinding.ActivityPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl:String?=null
    private var launcher= registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?.let{
            uploadImage(uri, POST_FOLDER){
                url ->
                if(url!=null){
                    binding.selectImage.setImageURI(uri)
                    imageUrl=url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener{
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user=it.toObject<User>()!!
                val post:Post=Post(
                    imageUrl!!,
                    binding.caption.editableText?.toString().toString(),
                    Firebase.auth.currentUser!!.uid,
                    System.currentTimeMillis().toString())

                Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                        startActivity(Intent(this@PostActivity,HomeActivity::class.java))
                        finish()
                    }
                }
            }

        }
    }
}