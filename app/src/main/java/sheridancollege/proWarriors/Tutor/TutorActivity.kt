package sheridancollege.proWarriors.Tutor

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import java.io.File

class TutorActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var storageRef: StorageReference
    private lateinit var username:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }

        storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$username.jpg")

        val drawerLayout = findViewById<DrawerLayout>(R.id.tutor_drawer_layout)
        val navView = findViewById<NavigationView>(R.id.tutor_nav_view)
        val bottomView = findViewById<BottomNavigationView>(R.id.tutor_bottom_navigation)
        val navController = this.findNavController(R.id.tutorNavHost)
        val img = findViewById<ImageView>(R.id.hamburgerMenuIcon)
        val head = findViewById<TextView>(R.id.pageTitle)
        val localFile = File.createTempFile("tempImage", "jpg")

        Log.d("USERNAME", username)
        GlobalScope.launch {
            TutorEntity.getTutorDetails(username)
            var tutor: Tutor = tut.tutor
            delay(1000)
            if (tut.tutor != null) {
                Log.d("TUTOR", tut.tutor.firstName.toString())
                findViewById<TextView>(R.id.drawerName).text = tut.tutor.firstName.toString()
                if (tutor.isStudent != null){
                    findViewById<TextView>(R.id.drawerAccount).text = "Tutor"
                }else{
                    findViewById<TextView>(R.id.drawerAccount).text = "Student"
                }
            }
            storageRef.getFile(localFile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                if (bitmap != null){
                    findViewById<ImageView>(R.id.headerImage).setImageBitmap(bitmap)
                }else{
                    findViewById<ImageView>(R.id.headerImage).setImageResource(R.drawable.profile)
                }
            }
        }

        img.setOnClickListener{
            if (drawerLayout.isDrawerVisible(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.END)
            }else{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            head.text = destination.label
        }
        navController.addOnDestinationChangedListener(listener)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.tutorHomeFragment, R.id.tutorDetailsFragment), drawerLayout)
        navView.setupWithNavController(navController)
        bottomView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.tutorNavHost)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
