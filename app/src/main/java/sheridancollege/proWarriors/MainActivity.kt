package sheridancollege.proWarriors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import sheridancollege.proWarriors.Landing.LandingActivity
import sheridancollege.proWarriors.Login.LoginActivity
import sheridancollege.proWarriors.Student.StudentHomePage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)



        val navController = this.findNavController(R.id.NavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }
}