package sheridancollege.proWarriors.StudentFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cometchat.pro.uikit.ui_components.cometchat_ui.CometChatUI
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentCourseViewAdapter
import sheridancollege.proWarriors.Student.StudentEntity
import sheridancollege.proWarriors.Student.stu.Companion.student
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StudentHomeFragment : Fragment() {
    private lateinit var username: String
    private lateinit var rView: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var courseList: ArrayList<String>
    private lateinit var allAppointmentsList: ArrayList<String>
    private lateinit var appointmentTextView: TextView
    private lateinit var databaseref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_home, container, false)
        rView = view.findViewById<View>(R.id.courseView) as RecyclerView
        database = FirebaseDatabase.getInstance()
        courseList = ArrayList<String>()
        databaseref = Firebase.database.reference
        allAppointmentsList = arrayListOf()
        appointmentTextView = view.findViewById(R.id.appointmentsDisplay)
        rView.layoutManager = LinearLayoutManager(this.context)
        rView.setHasFixedSize(true)
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }

        StudentEntity.getStudentDetails(username)


        val heading = view.findViewById<TextView>(R.id.headingText)


        GlobalScope.launch {
            delay(600)
            if (student != null) {
                heading.text = "Welcome " + student.firstName.toString()
            }

            database.getReference("Appointments")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot!!.exists()) {
                            for (child in snapshot.children) {
                                val a = child.key
                                allAppointmentsList.add(a.toString())
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            delay(500)
            var string1: String = ""

            //getting TutorNames from Firebase
            for (appointment in allAppointmentsList) {
                val appointmentListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.child("Appointments/" + appointment) != null) {

                            val data = dataSnapshot.child("Appointments/" + appointment)
                            if (data.child("studentUserName").value!!.equals(username)) {
                                val simpleDate = SimpleDateFormat("dd/MM/yyyy")
                                val currentDate = simpleDate.format(Date())
                                var startTime = data.child("startTime").value.toString()
                                var endTime = data.child("endTime").value.toString()
                                var date = data.child("date").value.toString()

                                val cmpDate = currentDate.compareTo(date)
                                when {
                                    cmpDate <= 0 -> {
                                        Log.d("This should not be displayed", "Yes")
                                        val simpleTime = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                                        val currentTimeWithDate = simpleTime.format(Date())
                                        var currentTime =
                                            currentTimeWithDate.split(" ")?.get(1).toString()
                                        val cmpTime = currentTime.compareTo(startTime + ":00")
                                        when {
                                            cmpTime < 0 -> {
                                                var tutorUserName =
                                                    data.child("tutorUserName").value.toString()
                                                string1 += "Start Time :" + startTime + "\n" + "End Time :" +
                                                        endTime + "\n" + "date :" + date + "\n Tutor User Name :" + tutorUserName +
                                                        "\n"
                                                appointmentTextView.text = string1
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                }
                databaseref.addValueEventListener(appointmentListener)
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
        }
        //setHasOptionsMenu(true)
        return view
    }

}
/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.student_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title.toString()) {
            *//*"Profile" -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_studentHomeFragment_to_studentDetailsFragment)
            }*//*
            *//*"View As Tutor" -> {
            if(tut == true){
                var intent = Intent(this, TutorActivity::class.java)
                intent.putExtra("sName", StudentEntity.student.firstName)
                startActivity(intent)
            }
            else{
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("You do not have an access to tutor login.")
                    .setCancelable(false)
                    .setNegativeButton("Okay", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                    })
                val alert = dialogBuilder.create()
                alert.setTitle("Tutor Access denied.")
                alert.show()
            }
        }*//*
            "Logout" -> {
                AuthUI.getInstance().signOut(this.requireContext())
                // Firebase.auth.signOut()
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_studentHomeFragment_to_studentLoginFragment2)
                Toast.makeText(
                    activity, "Successfully logged out.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            "Chat" -> {
                startActivity(Intent(this.requireContext(), CometChatUI::class.java))
            }
        }
        return true
    }*/

