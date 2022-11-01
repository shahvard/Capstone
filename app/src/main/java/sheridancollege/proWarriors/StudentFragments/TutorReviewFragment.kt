package sheridancollege.proWarriors.StudentFragments

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import java.io.File


class TutorReviewFragment : Fragment() {


    private lateinit var ratingBar: RatingBar
    private lateinit var commentTxt: TextView
    private lateinit var tutorName: TextView
    private lateinit var photo: ImageView
    private lateinit var storageRef: StorageReference
    private lateinit var username: String
    private lateinit var submitBtn: Button
    private lateinit var database :DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutor_review, container, false)
        photo = view.findViewById(R.id.img_profile)
        ratingBar = view.findViewById(R.id.rBar)
        commentTxt = view.findViewById(R.id.comments)
        tutorName = view.findViewById(R.id.tutorName)
        submitBtn=view.findViewById(R.id.submit)
        database=Firebase.database.reference

        username = arguments?.getString("TutorUserName").toString()
        var tutName = arguments?.getString("TutorName").toString()

        tutorName.text = tutName
        storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$username.jpg")
        val localFile = File.createTempFile("tempImage", "jpg")

        GlobalScope.launch {
            storageRef.getFile(localFile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                if (bitmap != null) {
                    photo.setImageBitmap(bitmap)
                } else {
                }
            }
                .addOnFailureListener(){
                    photo.setImageResource(R.drawable.profile)
                }
        }

        commentTxt.setOnClickListener {
            commentTxt.text = " "
        }

        submitBtn.setOnClickListener(){
            database.child("TutorReviews").child(username).child("Reviews").push().setValue(commentTxt.text.toString())
            val rating = ratingBar.rating
            database.child("TutorReviews").child(username).child("Stars").push().setValue(rating.toFloat())
            val bundle = Bundle()
            bundle.putString("TutorName",tutName)
            bundle.putString("TutorUserName",username)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_tutorReviewFragment_to_studentTutorDescriptionFragment, bundle)
        }
        return view
    }
}
