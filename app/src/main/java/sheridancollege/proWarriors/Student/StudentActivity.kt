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
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.HomeAndLogin.LoginActivity
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Tutor.TutorActivity

class StudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home_page)

        val navController = this.findNavController(R.id.studentNavHost)
        NavigationUI.setupActionBarWithNavController(this, navController)

        /* database = Firebase.database.reference
        val delimiter ="@"
        val email = intent.getStringExtra("studentName")
        val username = email?.split(delimiter)?.get(0)
        heading = findViewById(R.id.headingView)

        if(intent.getStringExtra("tutName") == null){
        *//*    val studentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child("Students") != null) {
                        val data = dataSnapshot.child("Students")
                        val firstName = data.child(username.toString()).child("firstName").value.toString()
                        val lastName = data.child(username.toString()).child("lastName").value.toString()
                        val email = data.child(username.toString()).child("email").value.toString()
                        val phoneNo = data.child(username.toString()).child("phoneNo").value.toString()
                        val address = data.child(username.toString()).child("address").value.toString()
                        val tutor =  data.child(username.toString()).child("isTutor").value.toString().toBoolean()

                        StudentEntity.student = Student(
                            username.toString(),
                            firstName,
                            lastName,
                            email,
                            address,
                            phoneNo,
                            tutor
                        )
                        heading.text = "Welcome ${StudentEntity.student!!.firstName}"
                        tut = StudentEntity.student.isTutor!!
                    }
                    else{
                        Toast.makeText(
                            this@StudentActivity, "No DATA.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            }
            database.addValueEventListener(studentListener)
        }
        if (intent.getStringExtra("tutName") != null){
            heading.text = "Welcome " + intent.getStringExtra("tutName")
        }
        if(intent.getStringExtra("name" )!= null){
            heading.text = "Welcome " + intent.getStringExtra("name")
        }

        val navController = this.findNavController(R.id.studentNavHost)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.student_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.title.toString()){
            "Profile"->{
                var intent = Intent(this, StudentDetailsActivity::class.java)
                startActivity(intent)
            }
            "View As Tutor" -> {
                if(tut == true){
                    var intent = Intent(this, TutorActivity::class.java)
                    intent.putExtra("sName", StudentEntity.student.firstName)
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
    }*/

    }
}
