package sheridancollege.proWarriors.Student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import sheridancollege.proWarriors.Login.LoginActivity
import sheridancollege.proWarriors.Profile.StudentDetailsActivity
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Tutor.TutorHomeActivity

class StudentHomePage : AppCompatActivity() {
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home_page)

        val heading = findViewById<TextView>(R.id.headingView)
        heading.text = "Welcome " + intent.getStringExtra("studentName")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.student_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{

        when(item.title.toString()){
            "Person"->{
                var intent = Intent(this, StudentDetailsActivity::class.java)
                startActivity(intent)
            }
            "View As Tutor" -> {
                var intent = Intent(this, TutorHomeActivity::class.java)
                startActivity(intent)
            }
            "Logout"->{
                Firebase.auth.signOut()
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(
                    this, "Successfully logged out.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return true
    }
}