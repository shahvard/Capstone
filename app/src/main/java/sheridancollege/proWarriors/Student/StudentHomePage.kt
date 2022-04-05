package sheridancollege.proWarriors.Student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import org.w3c.dom.Text
import sheridancollege.proWarriors.R

class StudentHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home_page)

        val heading = findViewById<TextView>(R.id.headingView)
        heading.text = "Welcome " + intent.getStringExtra("studentName")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.student_menu,menu)

        return true
    }
}