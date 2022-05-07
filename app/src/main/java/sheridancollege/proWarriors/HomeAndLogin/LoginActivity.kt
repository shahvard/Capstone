package sheridancollege.proWarriors.HomeAndLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentActivity
import sheridancollege.proWarriors.Tutor.TutorActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        val userName = findViewById<EditText>(R.id.userNameText)
        val userPass = findViewById<EditText>(R.id.passwordText)
        val loginButton = findViewById<Button>(R.id.signUpButton)

        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(userName.text.toString(), userPass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        if (intent.getStringExtra("type") == "student") {
                            var intent = Intent(this, StudentActivity::class.java)
                            intent.putExtra("studentName", userName.text.toString())
                            //intent
                            startActivity(intent)
                        }
                        if (intent.getStringExtra("type") == "tutor") {
                            var intent = Intent(this, TutorActivity::class.java)
                            //intent
                            intent.putExtra("tutorName", userName.text.toString())
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}