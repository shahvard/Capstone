package sheridancollege.proWarriors.Landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import sheridancollege.proWarriors.Login.LoginActivity
import sheridancollege.proWarriors.R

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        var sButton = findViewById<Button>(R.id.stdButton)
        var tButton = findViewById<Button>(R.id.tutorButton)

        sButton.setOnClickListener {

            var intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("type", "student")
            startActivity(intent)
        }

        tButton.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("type", "tutor")
            startActivity(intent)
        }
    }
}