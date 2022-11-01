package sheridancollege.proWarriors.StudentFragments

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat


class StudentTutorDescriptionFragment : Fragment() {

    private lateinit var reviewsList: ArrayList<String>
    private lateinit var tutorName:String
    private lateinit var displayName:TextView
    private lateinit var username:String
    private lateinit var book:TextView
    private lateinit var photo: ImageView
    private lateinit var reviews:TextView
    private lateinit var reviewPhoto:ImageView
    private lateinit var appointment:ImageView
    private lateinit var storageRef: StorageReference
    private lateinit var database:FirebaseDatabase
    private lateinit var courseName:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val view =inflater.inflate(R.layout.fragment_student_tutor_description, container, false)

        tutorName = arguments?.getString("TutorName").toString()
        username = arguments?.getString("TutorUserName").toString()
        book = view.findViewById(R.id.bookAppointment)
        photo = view.findViewById(R.id.img_profile)
        displayName = view.findViewById(R.id.name_profile)
        reviews = view.findViewById(R.id.reviewText)
        reviewPhoto = view.findViewById(R.id.reviewImage)
        appointment = view.findViewById(R.id.bookingImg)
        database = FirebaseDatabase.getInstance()
        reviewsList = arrayListOf()
        courseName=arguments?.getString("courseName").toString()

        var i = 0
        var size=0
        var total :Double =0.0
        var avgStars :Double=0.0

        displayName.text=tutorName
        storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$username.jpg")
        val localFile = File.createTempFile("tempImage", "jpg")

        GlobalScope.launch {
            storageRef.getFile(localFile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                if (bitmap != null){
                    photo.setImageBitmap(bitmap)
                }else{
                }
            }
                .addOnFailureListener(){
                    photo.setImageResource(R.drawable.profile)
                }

            database.getReference("TutorReviews/$username/Reviews")
                .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot!!.exists()) {
                        for (child in snapshot.children) {
                            val review = child.value
                            i++
                            reviewsList.add(review.toString())
                        }
                    }else{
                        i = 0
                        avgStars =0.0
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    i = 0
                    avgStars =0.0
                }
            })

            database.getReference("TutorReviews/$username/Stars")
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot!!.exists()) {
                            for (child in snapshot.children) {
                                if(child.value!!::class.simpleName=="Double"){
                                    val star :Double=   child.value as Double
                                    total += star
                                    size++

                                }
                                else{
                                    val star :Long=   child.value as Long
                                    total += star
                                    size++
                                }
                            }
                            avgStars = String.format("%.1f",(total/size)).toDouble()
                        }else{
                            i = 0
                            avgStars =0.0
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        i = 0
                        avgStars =0.0
                    }
                })

            delay(200)

            runOnUiThread{
                reviews.text = "${avgStars.toString()} ( $i Reviews )"

            }
        }

        book.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("TutorName",tutorName)
            bundle.putString("TutorUserName",username)
            bundle.putString("courseName",courseName)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_studentTutorDescriptionFragment_to_studentAppointmentBookingFragment,bundle)
        }

        reviewPhoto.setOnClickListener(){
            val bundle = Bundle()
            bundle.putString("TutorName",tutorName)
            bundle.putString("TutorUserName",username)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_studentTutorDescriptionFragment_to_tutorReviewFragment,bundle)
        }

        reviews.setOnClickListener(){

        }

        appointment.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("TutorName",tutorName)
            bundle.putString("TutorUserName",username)
            bundle.putString("courseName",courseName)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_studentTutorDescriptionFragment_to_studentAppointmentDisplayFragment,bundle)
        }
        return view
    }

}