package sheridancollege.proWarriors.TutorFragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Tutor.TutorActivity


class TutorLoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userName:String
    private lateinit var password:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tutor_login, container, false)
        auth = Firebase.auth

        view.findViewById<Button>(R.id.loginButton).setOnClickListener(){
            userName = view.findViewById<TextView>(R.id.userNameText).text.toString()
            password= view.findViewById<TextView>(R.id.passwordText).text.toString()
            auth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        if(auth.currentUser!!.isEmailVerified) {
                            Log.d(ContentValues.TAG, "signInWithEmail:success")
                            Toast.makeText(
                                this.context, "Authentication Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            val user = auth.currentUser
                            /*view.findNavController()
                                .navigate(R.id.action_studentLoginFragment_to_studentHomeFragment2)*/
                            var intent = Intent(activity?.applicationContext, TutorActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(
                                this.context, "Please verify your email to Login",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this.context, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        return view
    }

}