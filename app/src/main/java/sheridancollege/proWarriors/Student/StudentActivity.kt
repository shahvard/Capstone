package sheridancollege.proWarriors.Student

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.cometchat.pro.uikit.ui_components.cometchat_ui.CometChatUI
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

class StudentActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var storageRef: StorageReference
    private lateinit var username:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }

        storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$username.jpg")

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.student_nav_view)
        val bottomView = findViewById<BottomNavigationView>(R.id.student_bottom_navigation)
        val navController = this.findNavController(R.id.studentNavHost)
        val img = findViewById<ImageView>(R.id.hamburgerMenuIcon)
        val head = findViewById<TextView>(R.id.pageTitle)
        val localFile = File.createTempFile("tempImage", "jpg")

        GlobalScope.launch {
            StudentEntity.getStudentDetails(username)
            var student: Student = stu.student
            delay(900)
            if (stu.student != null) {
               findViewById<TextView>(R.id.drawerName).text = stu.student.firstName.toString()
                if (student.isTutor == false){
                    findViewById<TextView>(R.id.drawerAccount).text = "Student"
                }else{
                    findViewById<TextView>(R.id.drawerAccount).text = "Tutor"
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

        val listener = NavController.OnDestinationChangedListener {controller, destination, arguments ->
            head.text = destination.label
        }

        navController.addOnDestinationChangedListener(listener)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.studentHomeFragment, R.id.studentDetailsFragment, R.id.studentCourseInfoFragment), drawerLayout)
        navView.setupWithNavController(navController)
        bottomView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.studentNavHost)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun chatMenuClick(item: MenuItem) {
        startActivity(Intent(applicationContext, CometChatUI::class.java))
    }
}
