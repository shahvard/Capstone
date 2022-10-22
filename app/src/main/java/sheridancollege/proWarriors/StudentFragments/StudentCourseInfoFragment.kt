package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Tutor.TutorCourseItem
import sheridancollege.proWarriors.Tutor.TutorCourseSelectionAdapter
import sheridancollege.proWarriors.Tutor.tut


class StudentCourseInfoFragment : Fragment() {

    private lateinit var databaseref: DatabaseReference
    private lateinit var courseName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseref = Firebase.database.reference
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_course_info, container, false)
            courseName = arguments?.getString("CourseName").toString()

            view.findViewById<TextView>(R.id.CourseName).text = courseName
        view.findViewById<TextView>(R.id.CourseDescription).text =getCourseDescription(courseName)
        Log.d("Desc",getCourseDescription(courseName))
        view.findViewById<TextView>(R.id.tutorNames).text=getTutorNames(courseName)
        Log.d("Tutor",getTutorNames(courseName))



        return view
    }

    fun getCourseDescription(courseName:String):String{
        databaseref = Firebase.database.reference

        var courseDescription:String=""
        databaseref.child("Courses").child(courseName).get().addOnSuccessListener {
          courseDescription= it.value.toString()
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return courseDescription

    }

    fun getTutorNames(courseName: String):String{
        lateinit var database: FirebaseDatabase
        database = FirebaseDatabase.getInstance()
        var tutorName:String=""
        database.getReference("TutorCourse").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists())
                {
                    for (child in snapshot.children) {
                        if(child.value!!.equals(courseName))
                        tutorName=child.key.toString()

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return tutorName
    }


}