package sheridancollege.proWarriors.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import sheridancollege.proWarriors.Landing.LandingActivity
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentHomePage
import sheridancollege.proWarriors.Tutor.TutorHomeActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userName = findViewById<EditText>(R.id.userNameText)
        val userPass = findViewById<EditText>(R.id.passwordText)
        val loginButton = findViewById<Button>(R.id.loginButton)


        loginButton.setOnClickListener {
            if(intent.getStringExtra("type") == "student"){
                var i = Intent(this, StudentHomePage::class.java)
                startActivity(i)
            }
            if(intent.getStringExtra("type") == "tutor"){
                var i = Intent(this, TutorHomeActivity::class.java)
                startActivity(i)
            }
        }
    }
}