package com.chariot.nexus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chariot.nexus.Models.User
import com.chariot.nexus.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.editProfile.setOnClickListener {
            if (binding.email.editableText?.toString().equals("") or
                binding.senha.editableText?.toString().equals(""))
            {
                Toast.makeText(this@LoginActivity,"por favor preencha todos os campos",Toast.LENGTH_SHORT).show()
            }else{
                val user= User(binding.email.editableText?.toString(),
                    binding.senha.editableText?.toString()
                )
                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.senha!!)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(/* packageContext = */ this@LoginActivity,/* cls = */
                                HomeActivity::class.java))
                        }else{
                            Toast.makeText(this@LoginActivity,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.create.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
            finish()
        }
    }
}