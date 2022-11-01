package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.UiThreadUtil
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentTutorListAdapter


class CourseDetailsFragment : Fragment() {

    private lateinit var databaseref: DatabaseReference
    private lateinit var courseName: String
    private lateinit var courseDescription: String
    private lateinit var database: FirebaseDatabase
    private lateinit var allTutorsList: ArrayList<String>
    private lateinit var availableTutorsList: ArrayList<String>
    private lateinit var tutorFullNamesList: ArrayList<User>
    private lateinit var rView: RecyclerView
    private lateinit var courseHead:TextView
    private lateinit var courseDesc:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_course_details, container, false)
        databaseref = Firebase.database.reference
        database = FirebaseDatabase.getInstance()
        courseDescription = ""
        allTutorsList = arrayListOf()
        availableTutorsList= arrayListOf()
        tutorFullNamesList= arrayListOf()
        rView = view.findViewById<View>(R.id.tutorNames) as RecyclerView
        rView.layoutManager= LinearLayoutManager(this.context)
        rView.setHasFixedSize(true)
        courseHead = view.findViewById(R.id.courseNameHeading)
        courseDesc = view.findViewById(R.id.courseDescView)
        courseName = arguments?.getString("CourseName").toString()
        courseHead.text = courseName

        val courseDescListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child("Courses") != null) {
                    val data = dataSnapshot.child("Courses")
                    courseDesc.text = data.child(courseName).value.toString()
                }
                else{
                    Toast.makeText(requireContext(), "Error fetching data. Please contact support", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        databaseref.addValueEventListener(courseDescListener)

        GlobalScope.launch {
            database.getReference("Tutors").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot!!.exists()) {
                        for (child in snapshot.children) {
                            val name = child.key
                            allTutorsList.add(name.toString())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            delay(100)

            for (tutor in allTutorsList!!) {
                database.getReference("TutorCourse/$tutor")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot!!.exists()) {
                                for (child in snapshot.children) {
                                    if (child.value!!.equals(courseName))
                                        availableTutorsList!!.add(tutor)
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                delay(100)
            }

            for (tutors in availableTutorsList) {
                val tutorNamesListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.child("Tutors/" + tutors) != null) {
                            val data = dataSnapshot.child("Tutors")
                            val firstName = data.child(tutors).child("firstName").value.toString()
                            val lastName = data.child(tutors).child("lastName").value.toString()
                            val fullName = "$firstName $lastName"
                            tutorFullNamesList.add(User(fullName,tutors))
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                }
                databaseref.addValueEventListener(tutorNamesListener)
            }
            delay(200)
            UiThreadUtil.runOnUiThread {
                rView.adapter = StudentTutorListAdapter(tutorFullNamesList as List<User>,courseName)
            }
        }
        // Inflate the layout for this fragment
        return view
    }

}
data class User(var name:String,var username:String) {

}
