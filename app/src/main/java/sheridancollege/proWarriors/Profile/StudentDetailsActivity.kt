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
import sheridancollege.proWarriors.Student.StudentEntity
import sheridancollege.proWarriors.Student.StudentHomePage
import sheridancollege.proWarriors.Tutor.TutorEntity
import sheridancollege.proWarriors.Tutor.TutorHomeActivity

class StudentDetailsActivity : AppCompatActivity() {

    var student = StudentEntity.student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        var first = findViewById<TextView>(R.id.fName)
        var last = findViewById<TextView>(R.id.lName)
        var phone = findViewById<TextView>(R.id.cNumber)
        var add = findViewById<TextView>(R.id.address)



        if (student != null) {
            first.text = student.firstName
            last.text = student.lastName
            phone.text = student.phoneNo
            add.text = student.address
        }
        else{
            Toast.makeText(this,"Student info not found. Please try again later.", Toast.LENGTH_SHORT).show()
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
                if (student.isTutor == false){
                    var intent = Intent(this, StudentHomePage::class.java)
                    intent.putExtra("info", student)
                    startActivity(intent)
                }
                else if(student.isTutor == true){
                    var intent = Intent(this, StudentHomePage::class.java)
                    intent.putExtra("info", student)
                    startActivity(intent)
                }

            }
            "View As Tutor" -> {
                if(student.isTutor == true){
                    var intent = Intent(this, TutorHomeActivity::class.java)
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