package sheridancollege.proWarriors.StudentFragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.*
import sheridancollege.proWarriors.Tutor.Tutor
import sheridancollege.proWarriors.Tutor.TutorEntity
import sheridancollege.proWarriors.Tutor.tut
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StudentHomeFragment : Fragment() {

    private lateinit var headingText: TextView
    private lateinit var tutorName: TextView
    private lateinit var tutorLabel:TextView
    private lateinit var aptDate: TextView
    private lateinit var aptTime: TextView
    private lateinit var seeAppointments: TextView
    private lateinit var noAptText:TextView
    private lateinit var seeCourses: TextView
    private lateinit var courseIconRV: RecyclerView
    private lateinit var studentPhoto: ImageView
    private lateinit var tutorPhoto: ImageView
    private var images: List<Int> = listOf(
        R.drawable.computer,
        R.drawable.chemistry,
        R.drawable.civil,
        R.drawable.gaming,
        R.drawable.law,
        R.drawable.mechanical,
        R.drawable.childcare,
        R.drawable.sports,
        R.drawable.all
    )
    private lateinit var storageRef: StorageReference
    private var iconArrayList: ArrayList<StudentHome> = arrayListOf()
    private lateinit var username: String
    private lateinit var database: FirebaseDatabase
    private lateinit var allAppointmentsList: ArrayList<String>
    private lateinit var databaseref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_home, container, false)

        tutorLabel = view.findViewById(R.id.tutorLabel)
        headingText = view.findViewById(R.id.topName)
        tutorName = view.findViewById(R.id.tutorName)
        aptDate = view.findViewById(R.id.aptDate)
        aptTime = view.findViewById(R.id.aptTime)
        seeAppointments = view.findViewById(R.id.seeAllAppointments)
        noAptText = view.findViewById(R.id.noApt)
        noAptText.visibility = View.GONE
        seeCourses = view.findViewById(R.id.seeAllCourses)
        courseIconRV = view.findViewById(R.id.courseRecView)
        studentPhoto = view.findViewById(R.id.imgHome)
        tutorPhoto = view.findViewById(R.id.tutorImage)
        database = FirebaseDatabase.getInstance()
        databaseref = Firebase.database.reference
        storageRef = FirebaseStorage.getInstance().reference
        allAppointmentsList = arrayListOf()
        courseIconRV.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        courseIconRV.setHasFixedSize(true)

        for (i in images) {
            when (i) {
                R.drawable.computer -> iconArrayList.add(StudentHome("Computer", R.drawable.computer))
                R.drawable.chemistry -> iconArrayList.add(StudentHome("Chemistry", R.drawable.chemistry))
                R.drawable.civil -> iconArrayList.add(StudentHome("Civil", R.drawable.civil))
                R.drawable.gaming -> iconArrayList.add(StudentHome("Gaming", R.drawable.gaming))
                R.drawable.law -> iconArrayList.add(StudentHome("Law", R.drawable.law))
                R.drawable.mechanical -> iconArrayList.add(StudentHome("Mechanical", R.drawable.mechanical))
                R.drawable.childcare -> iconArrayList.add(StudentHome("Childcare", R.drawable.childcare))
                R.drawable.sports -> iconArrayList.add(StudentHome("Sports", R.drawable.sports))
                R.drawable.all -> iconArrayList.add(StudentHome("All", R.drawable.all))
            }
        }

        courseIconRV.adapter = StudentHomeCourseAdapter(iconArrayList)

        val localFile = File.createTempFile("studentImage", "jpg")

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }


        studentPhoto.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_studentHomeFragment_to_studentDetailsFragment)
        }

        seeAppointments.setOnClickListener() {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_studentHomeFragment_to_studentAppointmentDisplayFragment)
        }

        seeCourses.setOnClickListener() {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_studentHomeFragment_to_studentCoursePage)

        }

        storageRef.child("profile_images/$username.jpg").getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            if (bitmap != null) {
                studentPhoto.setImageBitmap(bitmap)
            } else {
                studentPhoto.setImageResource(R.drawable.profile)
            }
        }

        GlobalScope.launch {
            var name = " "
            StudentEntity.getStudentDetails(username)
            delay(300)
            var student: Student = stu.student
            if (student != null) {
                name = student.firstName.toString() + " " + student.lastName.toString()
            }
            //delay(600)
            runOnUiThread {
                headingText.text = name
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
            var time: String = ""

            //getting TutorNames from Firebase
            for (appointment in allAppointmentsList) {

                val appointmentListener = object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        if (dataSnapshot.child("Appointments/$appointment") != null) {
                            Log.d("inside", "first if loop")

                            val data = dataSnapshot.child("Appointments/$appointment")
                            if (data.child("studentUserName").value!!.equals(username)) {
                                noAptText.visibility = View.GONE
                                tutorPhoto.visibility = View.VISIBLE
                                tutorLabel.text = "Tutor"

                                Log.d("inside", "if loop")
                                val simpleDate = SimpleDateFormat("dd/MM/yyyy")

                                var startTime = data.child("startTime").value.toString()
                                var endTime = data.child("endTime").value.toString()
                                var date = data.child("date").value.toString()

                                val cmpDate = Date().compareTo(simpleDate.parse(date))

                                when {
                                    cmpDate <= 0 -> {
                                        val simpleTime = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                                        val currentTimeWithDate = simpleTime.format(Date())
                                        var currentTime =
                                            currentTimeWithDate.split(" ")?.get(1).toString()
                                        val cmpTime = currentTime.compareTo("$startTime:00")
                                        var tutorUserName =
                                            data.child("tutorUserName").value.toString()
                                        time = "$startTime to $endTime"
                                        TutorEntity.getTutorDetails(tutorUserName)
                                        GlobalScope.launch {
                                            delay(800)

                                            var tutor: Tutor = tut.tutor
                                            runOnUiThread {

                                                if (tutor != null) {
                                                    tutorName.text =
                                                        tutor.firstName + " " + tutor.lastName
                                                }
                                                aptTime.text = time
                                                aptDate.text = date
                                            }
                                        }
                                        val localFile2 = File.createTempFile("tutorImage", "jpg")
                                        storageRef.child("profile_images/$tutorUserName.jpg")
                                            .getFile(localFile2).addOnSuccessListener {
                                            val bitmap =
                                                BitmapFactory.decodeFile(localFile2.absolutePath)
                                            if (bitmap != null) {
                                                tutorPhoto.setImageBitmap(bitmap)
                                            } else {
                                                tutorPhoto.setImageResource(R.drawable.profile)
                                            }
                                        }
                                    }
                                }
                            }
                            else{
                                noAptText.visibility = View.VISIBLE
                                tutorName.text = ""
                                tutorPhoto.visibility = View.GONE
                                tutorLabel.text = ""
                                aptDate.text = ""
                                aptTime.text = ""
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        noAptText.visibility = View.VISIBLE
                        tutorName.text = ""
                        tutorPhoto.visibility = View.GONE
                        tutorLabel.text = " "
                        aptDate.text = ""
                        aptTime.text = ""
                    }
                }
                databaseref.addValueEventListener(appointmentListener)
            }
        }
        return view
    }
}


