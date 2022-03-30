package sheridancollege.proWarriors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class TutorHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_home)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.main_menu,menu)

        return true
    }
}