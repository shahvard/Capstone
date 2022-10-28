package sheridancollege.proWarriors.TutorFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentCourseItem
import sheridancollege.proWarriors.Student.StudentCourseSelectionAdapter
import sheridancollege.proWarriors.Tutor.TutorCourseItem
import sheridancollege.proWarriors.Tutor.TutorCourseSelectionAdapter

class TutorCourseSelection : Fragment() {

    private var courseList:ArrayList<TutorCourseItem>?=null
    private lateinit var database: FirebaseDatabase
    private lateinit var db: DatabaseReference
    private lateinit var rView: RecyclerView
    private lateinit var username:String
    private lateinit var name: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutor_course_selection, container, false)

        database = FirebaseDatabase.getInstance()
        db = Firebase.database.reference
        rView = view.findViewById<View>(R.id.courseList) as RecyclerView
        courseList=ArrayList()
        rView.layoutManager= LinearLayoutManager(this.context)
        rView.setHasFixedSize(true)
        name = view.findViewById(R.id.nameHeading)
        val searchView: SearchView = view.findViewById(R.id.tutorSearch)

        name.text = "Hello " + arguments?.getString("name").toString()

        database.getReference("Courses").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists())
                {
                    for (child in snapshot.children) {
                        val a = child.key
                        courseList!!.add(TutorCourseItem(a!!,false))
                    }
                }
                rView.adapter= TutorCourseSelectionAdapter(courseList!! as List<TutorCourseItem>)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                filter(p0!!)
                return false
            }

            override fun onQueryTextChange(msg: String?): Boolean {
                filter(msg!!)
                return false
            }
        })

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }


        view.findViewById<Button>(R.id.Done).setOnClickListener() {
            for (course in courseList!!) {

                if (course.isChecked == true) {
                    db.child("TutorCourse").child(username).push().setValue(course.courseName)
                }
            }

            val builder = android.app.AlertDialog.Builder(requireContext())
            builder.setMessage("Confirming selection will redirect to next page.")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->

                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_tutorCourseSelectionFragment_to_tutorApplicationFragment)

                }
                .setNegativeButton("No"){ dialog, id ->
                    dialog.cancel()
                }
                .setTitle("Confirm selection for the courses?")
            val alert = builder.create()
            alert.show()
        }
        return view
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<TutorCourseItem> = ArrayList()

        for (item in courseList!!) {
            if (item.courseName.lowercase().contains(text.lowercase())) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            rView.adapter = TutorCourseSelectionAdapter(filteredlist)

        }
    }
}