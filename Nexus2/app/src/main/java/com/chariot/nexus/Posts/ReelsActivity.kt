package com.chariot.nexus.Posts

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chariot.nexus.HomeActivity
import com.chariot.nexus.Models.Post
import com.chariot.nexus.Models.Reel
import com.chariot.nexus.Models.User
import com.chariot.nexus.R
import com.chariot.nexus.Utils.POST
import com.chariot.nexus.Utils.POST_FOLDER
import com.chariot.nexus.Utils.REEL
import com.chariot.nexus.Utils.REEL_FOLDER
import com.chariot.nexus.Utils.USER_NODE
import com.chariot.nexus.Utils.uploadImage
import com.chariot.nexus.Utils.uploadVideo
import com.chariot.nexus.databinding.ActivityReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityReelsBinding.inflate(layoutInflater);
    }
    private lateinit var videoUrl:String
    lateinit var progressDialog:ProgressDialog
    private var launcher= registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?.let{
            uploadVideo(uri, REEL_FOLDER, progressDialog){
                    url ->
                if(url!=null){

                    videoUrl=url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        binding.selectVideo.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.cancelButton.setOnClickListener{
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.reelButton.setOnClickListener {

            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user:User = it.toObject<User>()!!
                val reel: Reel = Reel(videoUrl!!, binding.caption.editableText?.toString().toString(),user.image!!)
                Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel).addOnSuccessListener {
                        startActivity(Intent(this@ReelsActivity,HomeActivity::class.java))
                        finish()
                    }
                }


            }


        }

    }
}