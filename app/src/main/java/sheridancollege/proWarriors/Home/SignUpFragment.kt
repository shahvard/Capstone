package sheridancollege.proWarriors.Home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.navigation.findNavController

import androidx.navigation.Navigation
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database

import com.cometchat.pro.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.AppConfig
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.Student
import java.lang.StringBuilder


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var username:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        auth = Firebase.auth
        database = Firebase.database.reference

        val db = Firebase.firestore

        view.findViewById<Button>(R.id.signUpButton).setOnClickListener() {
            val email = view.findViewById<TextView>(R.id.userNameText)
            val password = view.findViewById<TextView>(R.id.passwordText)

            val delimiter = "@"

            username = email?.text?.toString()?.split(delimiter)?.get(0)!!
            val check = email?.text.toString().split(delimiter)?.get(1)
            val name = view.findViewById<TextView>(R.id.nameText)
            val firstName: String = name?.text.toString().split(" ")?.get(0)
            val lastName: String = name?.text.toString().split(" ")?.get(1)
            val address = view.findViewById<TextView>(R.id.address)
            val phoneNo = view.findViewById<TextView>(R.id.phoneNumberText)

            if (check == "sheridancollege.ca") {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this.requireActivity()) { task ->
                        if (task.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        auth.currentUser!!.email?.let { it1 -> Log.d(TAG, it1) }
                                        Toast.makeText(
                                            view.context,
                                            "Registered Successfully and Verification " +
                                                    "Email sent",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        
                                        val student = Student(
                                            username,
                                            firstName,
                                            lastName,
                                            email.text.toString(),
                                            address.text.toString(),
                                            phoneNo.text.toString(),
                                            false
                                        )
                                        database.child("Students").child(username!!)
                                            .setValue(student)
                                      
                                        val fullName = name.text
                                        if (username != null) {
                                            signUpTapped( fullName)
                                        }
                                        
                                        name.text = ""
                                        address.text = ""
                                        phoneNo.text = ""
                                        email.text = ""
                                        password.text = ""


                                        //redirecting to course selection page
                                        /* Navigation.findNavController(requireView())
                                             .navigate(R.id.action_signUpFragment_to_courseSelectionFragment)*/


                                    }
                                }
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                view.context, "Sign up failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    view.context, "Please enter Sheridan Email Address",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        view.findViewById<TextView>(R.id.forgotPasswordText).setOnClickListener() {

            if (view.findViewById<TextView>(R.id.userNameText).text.toString()!! == "") {
                Toast.makeText(this.context,"Please enter your Email Address",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                auth.sendPasswordResetEmail(view.findViewById<TextView>(R.id.userNameText).text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent.")
                        }
                    }
            }
        }


        view.findViewById<TextView>(R.id.signInText).setOnClickListener(){
            view.findNavController()
                .navigate(R.id.action_signUpFragment_to_homeFragment)
        }
        return view
    }

    fun signUpTapped(name:String){
        val user: User = User()
        user.uid  = username
        user.name =name

        registerUser(user)
    }

    fun registerUser(user:User){
        CometChat.createUser(user, AppConfig.AppDetails.AUTH_KEY, object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User) {
                Log.d("Comet Account","success")
                // progressBar.visibility = View.GONE
                //login(user)
            }
            override fun onError(e: CometChatException) {
                Log.d("debug",e.toString())
                Log.d("Comet Account","failure")
                //sprogressBar.visibility = View.GONE
                //  createUserBtn.isClickable = true
                // Toast.makeText(this@RegistrationActivity, e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }
}