package com.apptorneo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.apptorneo.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.Principal

class MainActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth : FirebaseAuth

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth



        binding.btRegister.setOnClickListener{hacerRegistro()}


        binding.btLogin.setOnClickListener{hacerLogin()}

       val gso = GoogleSignInOptions.Builder(
           GoogleSignInOptions.DEFAULT_SIGN_IN)
           .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)
        binding.btGoogle.setOnClickListener{googleSignIn()}

    }

   private fun googleSignIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,5000)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==5000){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val cuenta = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(cuenta.idToken!!)
            }catch(e: ApiException){

            }
        }
    }


   private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    val user= auth.currentUser
                    refresca(user)
                }else{
                    refresca(null)
                }

            }
    }

    private fun hacerRegistro() {
        val email = binding.etCorreo.text.toString()
        val contra = binding.etClave.text.toString()


        auth.createUserWithEmailAndPassword(email,contra)
            .addOnCompleteListener(this) { task->
                if (task.isSuccessful){
                    val user = auth.currentUser
                    refresca(user)
                }else {
                    Toast.makeText(baseContext,"ERROR",Toast.LENGTH_LONG).show()
                    refresca(null)
                }
            }
    }

    private fun refresca(user: FirebaseUser?) {
        if(user != null) {
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)  }
    }

    private fun hacerLogin() {
        val email = binding.etCorreo.text.toString()
        val contra = binding.etClave.text.toString()
        Log.d("Autenticandonos","Haciendo llamada de autenticacion")

        auth.signInWithEmailAndPassword(email,contra)
            .addOnCompleteListener(this) { task->
                if (task.isSuccessful){
                    Log.d("Autenticado","se autentico")
                    val user = auth.currentUser
                    refresca(user)
                }else {
                    Toast.makeText(baseContext,"ERROR",Toast.LENGTH_LONG).show()
                    refresca(null)
                }

            }
    }



    public override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        refresca(usuario)
    }


}
