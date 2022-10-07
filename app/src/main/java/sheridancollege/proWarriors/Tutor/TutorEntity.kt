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

    fun getTutorDetails(username:String){
        val database: DatabaseReference
        database = Firebase.database.reference

        val tutorListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child("Tutors") != null) {
                    val data = dataSnapshot.child("Tutors")
                    val firstName = data.child(username).child("firstName").value.toString()
                    val lastName = data.child(username).child("lastName").value.toString()
                    val email = data.child(username).child("email").value.toString()
                    val phoneNo = data.child(username).child("phoneNo").value.toString()
                    val address = data.child(username).child("address").value.toString()
                    val student = data.child(username).child("isStudent").value.toString().toBoolean()

                    tut.tutor = Tutor(
                        username,
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