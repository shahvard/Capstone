package sheridancollege.proWarriors.Profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentEntity

class StudentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

         var student=StudentEntity.student
        var first = findViewById<TextView>(R.id.fName)
        var last = findViewById<TextView>(R.id.lName)
        var phone = findViewById<TextView>(R.id.cNumber)
        var add = findViewById<TextView>(R.id.address)
        var sInfo: ArrayList<String> = intent.getStringArrayListExtra("studentObject") as ArrayList<String>
        //intent.get
        //sInfo.get(0)

        if (student != null) {
            first.text = student.firstName
            last.text = student.lastName//sInfo.get(2)
            phone.text = student.phoneNo//sInfo.get(3)
            add.text = student.address//sInfo.get(5)
        }
        if(student == null){
            Toast.makeText(this,"Student null",Toast.LENGTH_SHORT).show()
        }

        //first.text = sInfo.get(1)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.details_menu,menu)
        return true
    }
}