package sheridancollege.proWarriors.Profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sheridancollege.proWarriors.R

class StudentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        var sInfo: ArrayList<String> = intent.getStringArrayListExtra("studentObject") as ArrayList<String>
        //intent.get
        sInfo.get(0)

    }
}