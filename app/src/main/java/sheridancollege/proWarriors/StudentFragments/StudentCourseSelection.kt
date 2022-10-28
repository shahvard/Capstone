package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.SearchView.OnQueryTextListener
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import sheridancollege.proWarriors.Student.StudentCourseItem
import sheridancollege.proWarriors.Student.StudentCourseViewAdapter
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentCourseSelectionAdapter
import sheridancollege.proWarriors.databinding.CourseSelectionRowBinding


class StudentCourseSelection : Fragment() {

    private var courseList:ArrayList<StudentCourseItem>?=null
    private lateinit var database: FirebaseDatabase
    private lateinit var db: DatabaseReference
    private lateinit var rView: RecyclerView
    private lateinit var username:String
    private lateinit var name:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.student_course_selection, container, false)
        database = FirebaseDatabase.getInstance()
        db = Firebase.database.reference
        courseList=ArrayList()
        rView = view.findViewById<View>(R.id.courseList) as RecyclerView
        rView.layoutManager=LinearLayoutManager(this.context)
        rView.setHasFixedSize(true)
        name = view.findViewById(R.id.studentNameHeading)
        val searchView: SearchView = view.findViewById(R.id.search)


        name.text = "Hello " + arguments?.getString("name").toString()

        //display courses
        database.getReference("Courses").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists())
                {
                    for (child in snapshot.children) {
                        val a = child.key
                        courseList!!.add(StudentCourseItem(a!!,false))
                    }
                }
                rView.adapter= StudentCourseSelectionAdapter(courseList!! as List<StudentCourseItem>)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        searchView.setOnQueryTextListener(object : OnQueryTextListener{
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
                if (course.isChecked) {
                    db.child("StudentCourse").child(username).push().setValue(course.courseName)
                }
            }

            val builder = android.app.AlertDialog.Builder(requireContext())
            builder.setMessage("Confirming selection will redirect to home page.")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->

                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_courseSelectionFragment_to_studentLoginFragment)

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
        val filteredlist: ArrayList<StudentCourseItem> = ArrayList()

        for (item in courseList!!) {
            if (item.courseName.lowercase().contains(text.lowercase())) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            rView.adapter = StudentCourseSelectionAdapter(filteredlist)

        }
    }
}


