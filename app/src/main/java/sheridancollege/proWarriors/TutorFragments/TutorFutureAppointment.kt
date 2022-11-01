package sheridancollege.proWarriors.TutorFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.UiThreadUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.*
import sheridancollege.proWarriors.Tutor.TutorEntity
import sheridancollege.proWarriors.Tutor.tut
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TutorFutureAppointment : Fragment() {

    private lateinit var allAppointmentsList: ArrayList<String>
    private lateinit var appointmentsListPast: ArrayList<Appointment>
    private lateinit var appointmentsListFuture: ArrayList<Appointment>
    private lateinit var appointmentListStudentNames: ArrayList<String>
    private lateinit var username: String
    private lateinit var futureRV: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_tutor_future_appointment, container, false)

        database = Firebase.database
        allAppointmentsList = arrayListOf()
        appointmentsListPast = arrayListOf()
        appointmentListStudentNames= arrayListOf()
        appointmentsListFuture = arrayListOf()
        futureRV = view.findViewById(R.id.futureRVTutor)
        futureRV.layoutManager = LinearLayoutManager(this.context)
        futureRV.setHasFixedSize(true)


        databaseref = Firebase.database.reference
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }


        GlobalScope.launch {
            var name = " "
            StudentEntity.getStudentDetails(username)
            delay(100)
            var student: Student = stu.student
            if (student != null) {
                name = student.firstName.toString() + " " + student.lastName.toString()
            }
            delay(600)

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
            delay(300)
            //getting appointments for the user from Firebase
            for (appointment in allAppointmentsList) {
                val appointmentListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.child("Appointments/$appointment") != null) {
                            val data = dataSnapshot.child("Appointments/$appointment")
                            if (data.child("tutorUserName").value!!.equals(username)) {
                                val simpleDate = SimpleDateFormat("dd/MM/yyyy")
                                var startTime = data.child("startTime").value.toString()
                                var endTime = data.child("endTime").value.toString()
                                var date = data.child("date").value.toString()
                                var courseName = data.child("courseName").value.toString()
                                var studentUserName =
                                    data.child("studentUserName").value.toString()
                                StudentEntity.getStudentDetails(studentUserName)
                                //delay(100)
                                val cmpDate = Date().compareTo(  simpleDate.parse(date))
                                when {
                                    cmpDate < 0 -> {
                                        Log.d("date greater than",date)
                                        appointmentsListFuture.add(
                                            Appointment(
                                                studentUserName,
                                                username,
                                                startTime,
                                                endTime,
                                                date,
                                                courseName
                                            )
                                        )
                                        appointmentListStudentNames.add(stu.student.firstName +" "+stu.student.lastName)

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
            delay(500)


            UiThreadUtil.runOnUiThread {
                futureRV.adapter=
                    StudentAppointmentListAdapter(appointmentsListFuture, appointmentListStudentNames)


            }
        }



        return  view }


}