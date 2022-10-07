package sheridancollege.proWarriors.Student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import sheridancollege.proWarriors.R

class StudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        val navController = this.findNavController(R.id.studentNavHost)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }
}
