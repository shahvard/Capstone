package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentCourseItem
import sheridancollege.proWarriors.Student.StudentTutorListAdapter


class StudentCourseInfoFragment : Fragment() {

    private lateinit var databaseref: DatabaseReference
    private lateinit var courseName: String
    private lateinit var courseDescription: String
    private lateinit var database: FirebaseDatabase
    private lateinit var tutorAllUserNamesList: ArrayList<String>
    private lateinit var tutorUserNamesList: ArrayList<String>
    private lateinit var tutorFullNamesList: ArrayList<User>
    private lateinit var rView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //initializing all variables
        val view = inflater.inflate(R.layout.fragment_course_info, container, false)

        databaseref = Firebase.database.reference
        database = FirebaseDatabase.getInstance()
        courseDescription = ""
        tutorAllUserNamesList = arrayListOf()
        tutorUserNamesList= arrayListOf()
        tutorFullNamesList= arrayListOf()
        rView = view.findViewById<View>(R.id.tutorNames) as RecyclerView

        rView.layoutManager= LinearLayoutManager(this.context)
        rView.setHasFixedSize(true)


        courseName = arguments?.getString("CourseName").toString()

        view.findViewById<TextView>(R.id.CourseName).text = courseName


        //getting course Description from Firebase
        val courseDescriptionListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child("Courses") != null) {
                    val data = dataSnapshot.child("Courses")
                    courseDescription = data.child(courseName).value.toString()
                    view.findViewById<TextView>(R.id.CourseDescription).text = courseDescription

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        databaseref.addValueEventListener(courseDescriptionListener)




        //Log.d("Tutor names from get method", tutorNamesOnlyList1[0])

       GlobalScope.launch {

           //getting AllTutorNames from the database
           database.getReference("Tutors").addValueEventListener(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot!!.exists()) {

                       for (child in snapshot.children) {
                           val a = child.key

                           tutorAllUserNamesList.add(a.toString())
                           //Log.d("Tutors", tutorNamesOnlyList[0])

                       }

                   }
               }

               override fun onCancelled(error: DatabaseError) {
                   TODO("Not yet implemented")
               }
           })
           delay(100)
           // Log.d("Tutor names from get method", tutorNamesOnlyList[0])


           //getting TutorNames from Firebase
           for (tutor in tutorAllUserNamesList!!) {


               database.getReference("TutorCourse/" + tutor)
                   .addValueEventListener(object : ValueEventListener {
                       override fun onDataChange(snapshot: DataSnapshot) {
                           if (snapshot!!.exists()) {
                               for (child in snapshot.children) {
                                   if (child.value!!.equals(courseName))
                                       tutorUserNamesList!!.add(tutor)


                               }
                           }

                       }


                       override fun onCancelled(error: DatabaseError) {

                       }
                   })


           }
           delay(100)


           for (tutors in tutorUserNamesList) {
               val tutorNamesListener = object : ValueEventListener {
                   override fun onDataChange(dataSnapshot: DataSnapshot) {
                       if (dataSnapshot.child("Tutors/" + tutors) != null) {
                           val data = dataSnapshot.child("Tutors")
                           val firstName = data.child(tutors).child("firstName").value.toString()
                           val lastName = data.child(tutors).child("lastName").value.toString()
                           val fullName = firstName + " "+lastName
                           tutorFullNamesList.add(User(fullName,tutors))
                       }


                       //Log.d("TutorsForCourse", tutorNamesList[0])
                   }

                   override fun onCancelled(databaseError: DatabaseError) {
                   }
               }
               databaseref.addValueEventListener(tutorNamesListener)


           }

           delay(200)
           runOnUiThread {
               rView.adapter = StudentTutorListAdapter(tutorFullNamesList as List<User>)
           }

           }





        return view
    }



}

data class User(var name:String,var username:String) {

}



