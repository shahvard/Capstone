package sheridancollege.proWarriors.StudentFragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.AppConfig
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentActivity
import sheridancollege.proWarriors.Student.StudentEntity
import sheridancollege.proWarriors.Student.stu
import javax.security.auth.callback.CallbackHandler

class  StudentLoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userName:String
    private lateinit var password:String
    private val delimiter="."
  
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //
        val view = inflater.inflate(R.layout.fragment_student_login, container, false)
        
        auth = Firebase.auth

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
        
        view.findViewById<TextView>(R.id.newUserText).setOnClickListener(){
            view.findNavController()
                .navigate(R.id.action_studentLoginFragment_to_signUpFragment)
        }

        view.findViewById<Button>(R.id.loginButton).setOnClickListener(){

            userName = view.findViewById<TextView>(R.id.userNameText).text.toString()
            password= view.findViewById<TextView>(R.id.passwordText).text.toString()
            if(userName != null && password != null){
                auth.signInWithEmailAndPassword(userName, password)
                    .addOnCompleteListener(this.requireActivity()) { task ->
                        if (task.isSuccessful) {
                            if(auth.currentUser!!.isEmailVerified) {
                                Log.d(TAG, "signInWithEmail:success")

                                //cometLogin
                                val cometUser: com.cometchat.pro.models.User =User()
                                cometUser.uid=userName?.split("@")?.get(0)!!
                                login(cometUser)

                                //Authentication message
                                Toast.makeText(
                                    this.context, "Authentication Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val user = auth.currentUser

                                GlobalScope.launch {
                                    StudentEntity.getStudentDetails(userName?.split(delimiter)?.get(0)!!)
                                    delay(500L)

                                    if (stu.student.firstName != null) {
                                        var intent =
                                            Intent(
                                                activity?.applicationContext,
                                                StudentActivity::class.java
                                            )
                                        startActivity(intent)
                                    }

                                    else{
                                        Log.d("data","You are not a student, please login as a tutor.")
                                        //Toast.makeText(this.context,"You are not a student",Toast.LENGTH_SHORT).show()
                                    }
                                }

                            }

                            //else if email not verified
                            else{
                                Toast.makeText(
                                    this.context, "Please verify your email to Login",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            //else if credentials are not correct
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                this.context, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            else{
                Toast.makeText(
                    this.context, "Please enter login credentials.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return view


    }

    //logging in to cometchat account
    fun login(user: com.cometchat.pro.models.User){
        CometChat.login(user.uid, AppConfig.AppDetails.AUTH_KEY, object : CometChat.CallbackListener<com.cometchat.pro.models.User?>() {
            override fun onSuccess(user: com.cometchat.pro.models.User?) {
                Log.d("Success","CometChat login successful")

                //progressBar.visibility = View.GONE
                //startActivity(Intent(this@RegistrationActivity, ConversationsActivity::class.java))
            }
            override fun onError(e: CometChatException) {
                //progressBar.visibility = View.GONE
                Log.d("error",e.toString())
                // Toast.makeText(this@RegistrationActivity, e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

}