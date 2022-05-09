package sheridancollege.proWarriors.HomeAndLogin

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.Student


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        auth = Firebase.auth
        database = Firebase.database.reference
        view.findViewById<Button>(R.id.signUpButton).setOnClickListener() {
            val email = view.findViewById<TextView>(R.id.userNameText)
            val password = view.findViewById<TextView>(R.id.passwordText)

            val delimiter = "@"

            val username = email?.text.toString().split(delimiter)?.get(0)
            val check = email?.text.toString().split(delimiter)?.get(1)
            val firstName = view.findViewById<TextView>(R.id.firstNameText)
            val lastName = view.findViewById<TextView>(R.id.lastNameText)
            val address = view.findViewById<TextView>(R.id.addressText)
            val phoneNo = view.findViewById<TextView>(R.id.phoneNumberText)

            if (check == "sheridancollege.ca") {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this.requireActivity()) { task ->
                        if (task.isSuccessful) {
                            auth.currentUser!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            view.context,
                                            "Registered Successfully and Verification " +
                                                    "Email sent",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val student = Student(
                                            username,
                                            firstName.text.toString(),
                                            lastName.text.toString(),
                                            email.text.toString(),
                                            address.text.toString(),
                                            phoneNo.text.toString(),
                                            false
                                        )
                                        database.child("Students").child(username!!)
                                            .setValue(student)
                                        firstName.text = ""
                                        lastName.text = ""
                                        address.text = ""
                                        phoneNo.text = ""
                                        email.text = ""
                                        password.text = ""
                                    } else {
                                        Toast.makeText(
                                            view.context, task.exception!!.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                view.context, "Authentication failed.",
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
        return view
    }
}