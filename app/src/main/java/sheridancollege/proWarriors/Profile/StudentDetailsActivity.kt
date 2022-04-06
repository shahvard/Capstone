package sheridancollege.proWarriors.Profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import sheridancollege.proWarriors.R

class StudentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        var first = findViewById<TextView>(R.id.fName)
        var last = findViewById<TextView>(R.id.lName)
        var phone = findViewById<TextView>(R.id.cNumber)
        var add = findViewById<TextView>(R.id.address)
        var sInfo: ArrayList<String> = intent.getStringArrayListExtra("studentObject") as ArrayList<String>
        //intent.get
        sInfo.get(0)

        first.text = sInfo.get(1)
        last.text = sInfo.get(2)
        phone.text = sInfo.get(3)
        add.text = sInfo.get(5)
        //first.text = sInfo.get(1)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.details_menu,menu)
        return true
    }
}