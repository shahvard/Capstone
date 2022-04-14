package sheridancollege.proWarriors.Profile

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.Login.LoginActivity
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentHomePage
import sheridancollege.proWarriors.Tutor.TutorEntity
import sheridancollege.proWarriors.Tutor.TutorHomeActivity

class TutorDetailsActivity : AppCompatActivity() {
    var tutor = TutorEntity.tutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_details)

        var first = findViewById<TextView>(R.id.fName)
        var last = findViewById<TextView>(R.id.lName)
        var phone = findViewById<TextView>(R.id.cNumber)
        var add = findViewById<TextView>(R.id.address)

        if (tutor != null) {
            first.text = tutor.firstName
            last.text = tutor.lastName
            phone.text = tutor.phoneNo
            add.text = tutor.address
        }
        if(tutor == null){
            Toast.makeText(this,"Tutor info not found. Please try again later.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.details_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{

        when(item.title.toString()){
            "Home"->{
                if (tutor.isStudent == false){
                    var intent = Intent(this, TutorHomeActivity::class.java)
                    intent.putExtra("name", tutor.firstName)
                    startActivity(intent)
                }
                else if(tutor.isStudent == true){
                    var intent = Intent(this, TutorHomeActivity::class.java)
                    intent.putExtra("name", tutor.firstName)
                    startActivity(intent)
                }

            }
            "View As Tutor" -> {
                if(tutor.isStudent == true){
                    var intent = Intent(this, TutorHomeActivity::class.java)
                    intent.putExtra("TutorName", tutor.firstName)
                    startActivity(intent)
                }
                else{
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setMessage("You do not have an access to tutor login.")
                        .setCancelable(false)
                        .setNegativeButton("Okay", DialogInterface.OnClickListener {
                                dialog, id -> dialog.cancel()
                        })
                    val alert = dialogBuilder.create()
                    alert.setTitle("Tutor Access denied.")
                    alert.show()
                }
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