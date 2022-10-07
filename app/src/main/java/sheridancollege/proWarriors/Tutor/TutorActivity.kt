package sheridancollege.proWarriors.Tutor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.database.DatabaseReference
import sheridancollege.proWarriors.R

class TutorActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var heading: TextView
    private  var stud: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)

        val navController = this.findNavController(R.id.tutorNavHost)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.tutor_menu,menu)
        return true
   }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{

        when(item.title.toString()){
            "Person"->{
               /* var intent = Intent(this, TutorDetailsActivity::class.java)
                startActivity(intent)*/
            }
            /*"View As Student" -> {
                if(stud == true){
                    var intent = Intent(this, StudentActivity::class.java)
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
            }*/
            /*"Logout"->{
                Firebase.auth.signOut()
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(
                    this, "Successfully logged out.",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
        }
        return true
    }
}