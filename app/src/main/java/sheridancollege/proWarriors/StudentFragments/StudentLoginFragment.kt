package sheridancollege.proWarriors.StudentFragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Credentials
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
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentActivity
import sheridancollege.proWarriors.Student.StudentEntity
import sheridancollege.proWarriors.Student.stu
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler

class  StudentLoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userName:String
    private lateinit var password:String
    private lateinit var account: Auth0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // Firebase.auth.signOut()

        val view = inflater.inflate(R.layout.fragment_student_login, container, false)

        // Set up the account object with the Auth0 application details
        account = Auth0(
            "MZBxOMznLoWJp5GIawlJAMRDUCAchuj8",
            "dev-gcybgobx.us.auth0.com"
        )


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

            WebAuthProvider.login(account)
                .withScheme("demo")
                .withScope("openid profile email")
                // Launch the authentication passing the callback where the results will be received
                .start(this.requireContext(), object : Callback<Credentials, AuthenticationException>,
                    com.auth0.android.callback.Callback<com.auth0.android.result.Credentials, AuthenticationException> {
                    // Called when there is an authentication failure
                    override fun onFailure(exception: AuthenticationException) {
                        // Something went wrong!
                    }

                    // Called when authentication completed successfully
                    fun onSuccess(credentials: Credentials) {
                        // Get the access token from the credentials object.
                        // This can be used to call APIs
                        //val accessToken = credentials.accessToken
                    }

                    override fun onSuccess(result: com.auth0.android.result.Credentials) {
                        TODO("Not yet implemented")
                    }
                })







        /*
        userName = view.findViewById<TextView>(R.id.userNameText).text.toString()
        password= view.findViewById<TextView>(R.id.passwordText).text.toString()
            auth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this.requireActivity()) { task ->
                    if (task.isSuccessful) {
                       //if(auth.currentUser!!.isEmailVerified) {
                           Log.d(TAG, "signInWithEmail:success")
                           Toast.makeText(
                               this.context, "Authentication Successful",
                               Toast.LENGTH_SHORT
                           ).show()
                           val user = auth.currentUser
                        val delimiter="."
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
                                   Log.d("data","You are not a student")
                                   //Toast.makeText(this.context,"You are not a student",Toast.LENGTH_SHORT).show()
                               }
                           }
                      *//* }
                        else{
                           Toast.makeText(
                               this.context, "Please verify your email to Login",
                               Toast.LENGTH_SHORT
                           ).show()
                       }*//*
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this.context, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }*/
        }
        return view
    }

}