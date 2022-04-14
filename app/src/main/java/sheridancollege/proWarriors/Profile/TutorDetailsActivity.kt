package sheridancollege.proWarriors.Profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Tutor.TutorEntity

class TutorDetailsActivity : AppCompatActivity() {
    var tutor = TutorEntity.tutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_details)

        var first = findViewById<TextView>(R.id.fName)
        var last = findViewById<TextView>(R.id.lName)
        var phone = findViewById<TextView>(R.id.cNumber)
        var add = findViewById<TextView>(R.id.address)

        if (tutor != null) {
            first.text = tutor.firstName
            last.text = tutor.lastName
            phone.text = tutor.phoneNo
            add.text = tutor.address
        }
        if(tutor == null){
            Toast.makeText(this,"Tutor info not found. Please try again later.", Toast.LENGTH_SHORT).show()
        }
    }
}