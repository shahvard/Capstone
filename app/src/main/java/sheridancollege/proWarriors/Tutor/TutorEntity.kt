package sheridancollege.proWarriors.Tutor

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class tut{
    companion object{
        var tutor: Tutor = Tutor()
    }
}

object TutorEntity {

    fun getDetails(username:String){
        val database: DatabaseReference
        database = Firebase.database.reference

        val tutorListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child("Tutors") != null) {
                    val data = dataSnapshot.child("Tutors")
                    val firstName = data.child(username.toString()).child("firstName").value.toString()
                    val lastName = data.child(username.toString()).child("lastName").value.toString()
                    val email = data.child(username.toString()).child("email").value.toString()
                    val phoneNo = data.child(username.toString()).child("phoneNo").value.toString()
                    val address = data.child(username.toString()).child("address").value.toString()
                    val student = data.child(username.toString()).child("isStudent").value.toString().toBoolean()

                    tut.tutor = Tutor(
                        username.toString(),
                        firstName,
                        lastName,
                        email,
                        address,
                        phoneNo,
                        student
                    )
                }
                else {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(tutorListener)
    }
}