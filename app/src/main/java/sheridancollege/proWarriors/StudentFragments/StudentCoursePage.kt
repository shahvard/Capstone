package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentCourseItem
import sheridancollege.proWarriors.Student.StudentCourseSelectionAdapter
import sheridancollege.proWarriors.Student.StudentCourseViewAdapter


class StudentCoursePage : Fragment() {

    private lateinit var username: String
    private lateinit var rView: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var courseList: ArrayList<String>
    private lateinit var databaseref: DatabaseReference
    private lateinit var search:SearchView
    private var searchStr: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_student_course_page, container, false)

        rView = view.findViewById<View>(R.id.coursesRecycler) as RecyclerView
        database = FirebaseDatabase.getInstance()
        courseList = ArrayList<String>()
        databaseref = Firebase.database.reference
        search = view.findViewById(R.id.searchCourseList)
        rView.layoutManager = LinearLayoutManager(this.context)
        rView.setHasFixedSize(true)

        if (arguments?.getBoolean("firstTime") == true){
            searchStr = arguments?.getString("searchItem").toString()
            filter(searchStr)
            arguments?.putBoolean("firstTime", false)
        }
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }

        database.getReference("StudentCourse/" + username)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot!!.exists()) {
                        for (child in snapshot.children) {
                            val a = child.value
                            courseList!!.add(a.toString())
                        }
                    }
                    rView.adapter = StudentCourseViewAdapter(courseList as List<String>)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                filter(p0!!)
                return false
            }

            override fun onQueryTextChange(msg: String?): Boolean {
                filter(msg!!)
                return false
            }
        })
        return view
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<String> = ArrayList()

        for (item in courseList!!) {
            if (item.lowercase().contains(text.lowercase())) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            rView.adapter = StudentCourseViewAdapter(filteredlist as List<String>)

        }
    }

}