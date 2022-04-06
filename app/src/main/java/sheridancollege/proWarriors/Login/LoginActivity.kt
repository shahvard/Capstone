package sheridancollege.proWarriors.Login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.Landing.LandingActivity
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentHomePage
import sheridancollege.proWarriors.Tutor.TutorHomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        //val currentUser = auth.currentUser
        /*if(currentUser != null){
            reload();
        }*/
        val userName = findViewById<EditText>(R.id.userNameText)
        val userPass = findViewById<EditText>(R.id.passwordText)
        val loginButton = findViewById<Button>(R.id.loginButton)


        loginButton.setOnClickListener {

            auth.signInWithEmailAndPassword(userName.text.toString(), userPass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        if (intent.getStringExtra("type") == "student") {
                            var intent = Intent(this, StudentHomePage::class.java)
                            intent.putExtra("studentName", userName.text.toString())
                            startActivity(intent)
                        }
                        if (intent.getStringExtra("type") == "tutor") {
                            var intent = Intent(this, TutorHomeActivity::class.java)
                            intent.putExtra("tutorName", userName.text.toString())
                            startActivity(intent)
                        }

                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //updateUI(null)
                    }
                }
        }
    }
}