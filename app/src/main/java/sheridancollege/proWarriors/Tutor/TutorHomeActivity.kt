package sheridancollege.proWarriors.Tutor

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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.Login.LoginActivity
import sheridancollege.proWarriors.Profile.StudentDetailsActivity
import sheridancollege.proWarriors.Profile.TutorDetailsActivity
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.Student
import sheridancollege.proWarriors.Student.StudentEntity
import sheridancollege.proWarriors.Student.StudentHomePage

class TutorHomeActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var heading: TextView
    private  var stud: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_home)

        database = Firebase.database.reference
        val delimiter ="@"
        val email = intent.getStringExtra("tutorName")
        val username = email?.split(delimiter)?.get(0)
        heading = findViewById<TextView>(R.id.headingView)

        val tutorListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child("Tutors") != null) {
                    val data = dataSnapshot.child("Tutors")
                    val firstName = data.child(username.toString()).child("firstName").value.toString()
                    val lastName = data.child(username.toString()).child("lastName").value.toString()
                    val email = data.child(username.toString()).child("email").value.toString()
                    val phoneNo = data.child(username.toString()).child("phoneNo").value.toString()
                    val address = data.child(username.toString()).child("address").value.toString()
                    val s =  data.child(username.toString()).child("isStudent").value.toString().toBoolean()

                    TutorEntity.tutor = Tutor(
                        username.toString(),
                        firstName,
                        lastName,
                        email,
                        address,
                        phoneNo,
                        s
                    )
                    heading.text = "Welcome ${TutorEntity.tutor!!.firstName}"
                    stud = TutorEntity.tutor.isStudent!!
                }
                else{
                    Toast.makeText(
                        this@TutorHomeActivity, "No DATA.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(tutorListener)
        if(intent.getStringExtra("sName") == null){

        }
        if (intent.getStringExtra("sName") != null){
            heading.text = "Welcome " + intent.getStringExtra("sName")
        }

        if(intent.getStringExtra("name") != null){
            heading.text = "Welcome " + intent.getStringExtra("name")
        }
    }

   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.tutor_menu,menu)
        return true
   }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{

        when(item.title.toString()){
            "Person"->{
                var intent = Intent(this, TutorDetailsActivity::class.java)
                startActivity(intent)
            }
            "View As Student" -> {
                if(stud == true){
                    var intent = Intent(this, StudentHomePage::class.java)
                    intent.putExtra("tutName", TutorEntity.tutor.firstName)
                    startActivity(intent)
                }
                else{
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setMessage("You are not eligible to access Student Home.")
                        .setCancelable(false)
                        .setNegativeButton("Okay", DialogInterface.OnClickListener {
                                dialog, id -> dialog.cancel()
                        })
                    val alert = dialogBuilder.create()
                    alert.setTitle("Student Access denied.")
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