package sheridancollege.proWarriors.TutorFragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.UiThreadUtil
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


class TutorHomeFragment : Fragment() {
    private lateinit var username: String
    private var count = 0
    private var starsCount = 0.0


    private lateinit var headingText: TextView
    private lateinit var studentName: TextView
    private lateinit var aptDate: TextView
    private lateinit var aptTime: TextView
    private lateinit var seeAppointments: TextView
    private lateinit var seeCourses: TextView
    private lateinit var courseIconRV: RecyclerView
    private lateinit var photo: ImageView
    private lateinit var courseNameTV:TextView

    //private lateinit var tutorPhoto: ImageView
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
    private var iconArrayList: ArrayList<StudentHome> = arrayListOf<StudentHome>()
    private lateinit var bookingCount:TextView
    private lateinit var ratingCount:TextView

    //private lateinit var username: String
    private lateinit var database: FirebaseDatabase
    private lateinit var allAppointmentsList: ArrayList<String>
    private lateinit var databaseref: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutor_home, container, false)

        bookingCount = view.findViewById(R.id.aptNum)
        ratingCount = view.findViewById(R.id.starsNum)
        headingText = view.findViewById(R.id.topName)
        studentName = view.findViewById(R.id.tutorName)
        aptDate = view.findViewById(R.id.aptDate)
        aptTime = view.findViewById(R.id.aptTime)
        seeAppointments = view.findViewById(R.id.seeAllTutorAppointments)
        //seeCourses = view.findViewById(R.id.seeAllCourses)
//        courseIconRV = view.findViewById(R.id.courseRecView)
        photo = view.findViewById(R.id.imgHome)
        //tutorPhoto = view.findViewById(R.id.tutorImage)
        database = FirebaseDatabase.getInstance()
        databaseref = Firebase.database.reference
        storageRef = FirebaseStorage.getInstance().reference
        allAppointmentsList = arrayListOf()
       /* courseIconRV.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        courseIconRV.setHasFixedSize(true)*/
        courseNameTV=view.findViewById(R.id.courseName)

        for (i in images) {
            when (i) {
                R.drawable.computer -> iconArrayList.add(
                    StudentHome(
                        "Computer",
                        R.drawable.computer
                    )
                )
                R.drawable.chemistry -> iconArrayList.add(
                    StudentHome(
                        "Chemistry",
                        R.drawable.chemistry
                    )
                )
                R.drawable.civil -> iconArrayList.add(StudentHome("Civil", R.drawable.civil))
                R.drawable.gaming -> iconArrayList.add(StudentHome("Gaming", R.drawable.gaming))
                R.drawable.law -> iconArrayList.add(StudentHome("Law", R.drawable.law))
                R.drawable.mechanical -> iconArrayList.add(
                    StudentHome(
                        "Mechanical",
                        R.drawable.mechanical
                    )
                )
                R.drawable.childcare -> iconArrayList.add(
                    StudentHome(
                        "Childcare",
                        R.drawable.childcare
                    )
                )
                R.drawable.sports -> iconArrayList.add(StudentHome("Sports", R.drawable.sports))
                R.drawable.all -> iconArrayList.add(StudentHome("All", R.drawable.all))
            }
        }

        val localFile = File.createTempFile("studentImage", "jpg")


        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }


        photo.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_tutorHomeFragment_to_tutorDetailsFragment)
        }

        storageRef.child("profile_images/$username.jpg").getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            if (bitmap != null) {
                Log.d("Inside Bitmap", "Yes")
                photo.setImageBitmap(bitmap)
            } else {
                storageRef.child("profile_images/$username.jpeg").getFile(localFile)
                    .addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        if (bitmap != null) {
                            Log.d("Inside Bitmap", "Yes")
                            photo.setImageBitmap(bitmap)
                        } else {
                            photo.setImageResource(R.drawable.profile)
                        }
                    }
            }
        }

        GlobalScope.launch {
            var name = " "
            TutorEntity.getTutorDetails(username)
            delay(700)
            var tutor: Tutor = tut.tutor
            if (tutor != null) {
                name = tutor.firstName.toString() + " " + tutor.lastName.toString()
            }
            UiThreadUtil.runOnUiThread {
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

            delay(600)
            var time: String = ""


            for (appointment in allAppointmentsList) {
                val appointmentListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.child("Appointments/$appointment") != null) {
                            val data = dataSnapshot.child("Appointments/$appointment")
                            if (data.child("tutorUserName").value!! == username) {
                                val simpleDate = SimpleDateFormat("dd/MM/yyyy")
                                var startTime = data.child("startTime").value.toString()
                                var endTime = data.child("endTime").value.toString()
                                var date = data.child("date").value.toString()
                                var courseName= data.child("courseName").value.toString()
                                val cmpDate = Date().compareTo(simpleDate.parse(date))
                                count++
                                when {
                                    cmpDate <= 0 -> {
                                        var studentUserName =
                                            data.child("studentUserName").value.toString()
                                        time = "$startTime to $endTime"
                                        StudentEntity.getStudentDetails(studentUserName)
                                        GlobalScope.launch {
                                            delay(800)
                                            var student: Student = stu.student
                                            UiThreadUtil.runOnUiThread {
                                                if (student != null) {
                                                    studentName.text =
                                                        student.firstName + " " + student.lastName
                                                    courseNameTV.text=courseName
                                                }
                                                bookingCount.text = count.toString()
                                            }
                                        }
                                        aptTime.text = time
                                        aptDate.text = date
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

            var len = 0
            var total :Double =0.0

            database.getReference("TutorReviews/$username/Stars")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot!!.exists()) {
                            for (child in snapshot.children) {
                                if(child.value!!::class.simpleName=="Double"){
                                    val star :Double =  child.value as Double
                                    total += star
                                    len++
                                }
                                else{
                                    val star :Long=   child.value as Long
                                    total += star
                                    len++
                                }
                            }
                            starsCount = String.format("%.1f",(total/len)).toDouble()
                        }else{
                            starsCount =0.0
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            delay(500)
            UiThreadUtil.runOnUiThread {
                ratingCount.text = starsCount.toString()
            }
        }
        return view
    }
}