package sheridancollege.proWarriors.Student

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
public class stu{
   companion object{
   var student: Student= Student()
   }
}
public object StudentEntity {



   public fun getStudentDetails(username:String) {

      val database: DatabaseReference
      database = Firebase.database.reference

      val studentListener = object : ValueEventListener {
         override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.child("Students") != null) {
               val data = dataSnapshot.child("Students")
               val firstName = data.child(username.toString()).child("firstName").value.toString()
              // Toast.makeText(this.,username,Toast.LENGTH_SHORT).show()
               val lastName = data.child(username.toString()).child("lastName").value.toString()
               val email = data.child(username.toString()).child("email").value.toString()
               val phoneNo = data.child(username.toString()).child("phoneNo").value.toString()
               val address = data.child(username.toString()).child("address").value.toString()
               val tutor =
                  data.child(username.toString()).child("isTutor").value.toString().toBoolean()

               stu.student = Student(
                  username.toString(),
                  firstName,
                  lastName,
                  email,
                  address,
                  phoneNo,
                  tutor
               )
               Log.d("data",stu.student.firstName.toString())


            } else {

            }

         }

         override fun onCancelled(databaseError: DatabaseError) {
         }
      }
      database.addValueEventListener(studentListener)

     // return stu.student
   }





}