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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home_page)

        database = Firebase.database.reference
        val delimiter ="@"
        val email = intent.getStringExtra("studentName")
        val username = email?.split(delimiter)?.get(0)
        val heading = findViewById<TextView>(R.id.headingView)
        var user: User? =null



        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               if( dataSnapshot.child("Students") != null) {
                    val data = dataSnapshot.child("Students")
                    val name =  data.child("shahvard").child("firstName").value.toString()
                    heading.text = "Welcome $name"

                //Toast.makeText(this@,"Inside",Toast.LENGTH_SHORT).show()
                }
                // Get Post object and use the values to update the UI
                //val post = dataSnapshot.getValue<Post>()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }

        }
        database.addValueEventListener(postListener)
        /*if (username != null) {
            var a = database.child("Students").child(username)
            a.get().addOnSuccessListener { document->
               document.getValue<String>()
            }
        }*/



       // heading.text = "Welcome " + intent.getStringExtra("studentName")
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