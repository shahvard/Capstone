package sheridancollege.proWarriors.Student

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import sheridancollege.proWarriors.Login.LoginActivity
import sheridancollege.proWarriors.Profile.StudentDetailsActivity
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Tutor.TutorHomeActivity

class StudentHomePage : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    var isTutor: Boolean = false
    var studentInfo: ArrayList<String> = TODO()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home_page)

        database = Firebase.database.reference
        val delimiter ="@"
        val email = intent.getStringExtra("studentName")
        val username = email?.split(delimiter)?.get(0)
        val heading = findViewById<TextView>(R.id.headingView)
        //var user: User? =null



        val studentListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               if( dataSnapshot.child("Students") != null) {
                   val data = dataSnapshot.child("Students")
                   studentInfo.add(username.toString())
                   studentInfo.add(data.child(username.toString()).child("firstName").value.toString())
                   studentInfo.add(data.child(username.toString()).child("lastName").value.toString())
                   studentInfo.add(data.child(username.toString()).child("phoneNo").value.toString())
                   studentInfo.add(data.child(username.toString()).child("email").value.toString())
                   studentInfo.add(data.child(username.toString()).child("address").value.toString())
                   studentInfo.add(data.child(username.toString()).child("isTutor").value.toString())
                   val name = studentInfo.get(1)
                   /*if (data.child(username.toString()).child("isTutor").value.toString() == "true")
                       isTutor = true
                   else
                       isTutor = false*/

                   if(studentInfo.get(6) == "true"){
                       isTutor = true
                   }
                   else{
                       isTutor = false
                   }

                   heading.text = "Welcome $name"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }

        }
        database.addValueEventListener(studentListener)
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
                intent.putExtra("studentObject", studentInfo)
                startActivity(intent)
            }
            "View As Tutor" -> {
                if(isTutor == true){
                    var intent = Intent(this, TutorHomeActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setMessage("You do not have an access to tutor login.")
                        .setCancelable(false)
                        .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                                dialog, id -> finish()
                        })
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener {
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