package sheridancollege.proWarriors.Student

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class stu{
   companion object{
   var student: Student= Student()

   }
}

object StudentEntity {

   fun getStudentDetails(username:String) {

      val database: DatabaseReference
      database = Firebase.database.reference

      val studentListener = object : ValueEventListener {
         override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.child("Students") != null) {
               val data = dataSnapshot.child("Students")
               val firstName = data.child(username.toString()).child("firstName").value.toString()
               val lastName = data.child(username.toString()).child("lastName").value.toString()
               val email = data.child(username.toString()).child("email").value.toString()
               val phoneNo = data.child(username.toString()).child("phoneNo").value.toString()
               val address = data.child(username.toString()).child("address").value.toString()
               val tutor = data.child(username.toString()).child("tutor").value.toString().toBoolean()

               stu.student = Student(
                  username.toString(),
                  firstName,
                  lastName,
                  email,
                  address,
                  phoneNo,
                  tutor
               )
            }
            else {

            }
         }

         override fun onCancelled(databaseError: DatabaseError) {
         }
      }
      database.addValueEventListener(studentListener)
   }
}