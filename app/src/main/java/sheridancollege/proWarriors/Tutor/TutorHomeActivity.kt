package sheridancollege.proWarriors.Tutor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.Login.LoginActivity
import sheridancollege.proWarriors.Profile.StudentDetailsActivity
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentHomePage

class TutorHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_home)
    }

   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.tutor_menu,menu)
        return true
   }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{

        when(item.title.toString()){
            "Person"->{
                var intent = Intent(this, StudentDetailsActivity::class.java)
                startActivity(intent)
            }
            "View As Student" -> {
                var intent = Intent(this, StudentHomePage::class.java)
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