package sheridancollege.proWarriors.StudentFragments

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
import sheridancollege.proWarriors.Course.ClassItem
import sheridancollege.proWarriors.Course.MyCourseView

import sheridancollege.proWarriors.R


class CourseSelectionFragment : Fragment() {

   // private var dataModel: List<ClassItem>? = null
    private var courseList:ArrayList<ClassItem>?=null
    private var courseSelected:ArrayList<String>? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var db: DatabaseReference
    private lateinit var rView: RecyclerView
    private lateinit var username:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_course_selection, container, false)
        database = FirebaseDatabase.getInstance()
        db = Firebase.database.reference
        rView = view.findViewById<View>(R.id.courseList) as RecyclerView
        courseList=ArrayList<ClassItem>()



        rView.layoutManager=LinearLayoutManager(this.context)
        rView.setHasFixedSize(true)

        database.getReference("Courses").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists())
                {
                    for (child in snapshot.children) {
                        val a = child.key
                        courseList!!.add(ClassItem(a!!,false))
                        Log.d("courseList",courseList!![0].courseName)
                        Log.d("list",a)

                    }
                }
                rView.adapter=MyCourseView(courseList!! as List<ClassItem>)


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }





           // val list=a.split("=").get(0)
          //  Log.d("First value",list.toString())

       /* @Override
        public void onDataChange(DataSnapshot snapshot) {
            ArrayList<String> ids = new ArrayList<String>();
            for (DataSnapshot childSnapshot: snapshot.getChildren()) {
            ids.add(childSnapshot.getValue().toString());
        }
        }*/

        val check = view.findViewById<CheckBox>(R.id.checkBox)

        view.findViewById<Button>(R.id.Done).setOnClickListener() {


            for (course in courseList!!) {
                Log.d("Inside for each", "Succcess")

                if (course.isChecked == true) {
                    Log.d("Inside second if","Succcess")
                    db.child("StudentCourse").child(username).push().setValue(course.courseName)


                }
            }

            Toast.makeText(this.requireContext(),"Sign up completed. Log in to continue",Toast.LENGTH_SHORT)

            Navigation.findNavController(requireView())
                .navigate(R.id.action_courseSelectionFragment_to_homeFragment)

        }
            return view
        }




}


