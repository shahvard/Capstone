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
        // Inflate the layout for this fragment
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }
        TutorEntity.getTutorDetails(username)
        val heading= view.findViewById<TextView>(R.id.headingText)

        GlobalScope.launch {
            if (tutor != null) {
                delay(500L)
                heading.text = "Welcome "+ tutor.firstName.toString()
            }
            else{
                delay(900L)

                heading.text = "No name"
            }
        }




        view.findViewById<Button>(R.id.setAvailability).setOnClickListener(){
            Navigation.findNavController(requireView())
                .navigate(R.id.action_tutorHomeFragment_to_tutorAvailabilityFragment)

        }


        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.tutor_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title.toString()) {
            "Profile" -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_tutorHomeFragment_to_studentDetailsFragment2)
            }
            /* "View As Tutor" -> {
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
            }*/
            "Logout" -> {
                Firebase.auth.signOut()
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_tutorHomeFragment_to_tutorLoginFragment2)
                Toast.makeText(
                    activity, "Successfully logged out.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            "Chat"->{
                startActivity(Intent(this.requireContext(), CometChatUI::class.java))

            }
        }
        return true
    }

}