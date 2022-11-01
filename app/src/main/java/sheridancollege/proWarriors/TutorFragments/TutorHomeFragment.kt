package sheridancollege.proWarriors.TutorFragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.cometchat.pro.uikit.ui_components.cometchat_ui.CometChatUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.stu
import sheridancollege.proWarriors.Tutor.Tutor
import sheridancollege.proWarriors.Tutor.TutorEntity
import sheridancollege.proWarriors.Tutor.tut
import sheridancollege.proWarriors.Tutor.tut.Companion.tutor


class TutorHomeFragment : Fragment() {
    private lateinit var username:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutor_home, container, false)

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }
        TutorEntity.getTutorDetails(username)
        val heading= view.findViewById<TextView>(R.id.headingText)

        GlobalScope.launch {
            delay(600L)
            if (tutor != null) {
                heading.text = "Welcome "+ tutor.firstName.toString()
            }
            else{
                heading.text = "No name"
            }
        }
        view.findViewById<Button>(R.id.setAvailability).setOnClickListener(){
            val bundle = Bundle()
            bundle.putBoolean("edit", true)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_tutorHomeFragment_to_tutorAvailabilityFragment, bundle)

        }

        view.findViewById<TextView>(R.id.seeAllTutorAppointments).setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_tutorHomeFragment_to_tutorAppointmentDisplayFragment)

        }
        return view
    }
}