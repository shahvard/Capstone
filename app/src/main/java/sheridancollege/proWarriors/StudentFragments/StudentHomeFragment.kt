package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentEntity
import sheridancollege.proWarriors.Student.stu.Companion.student


class StudentHomeFragment : Fragment() {
private lateinit var username:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_home, container, false)

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }
        //var studententity = StudentEntity
        StudentEntity.getStudentDetails(username)

        val heading= view.findViewById<TextView>(R.id.headingText)

        GlobalScope.launch {
            delay(600L)
            if (student != null) {
                heading.text = "Welcome "+ student.firstName.toString()
            }
        }
        setHasOptionsMenu(true)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.student_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title.toString()) {
            "Profile" -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_studentHomeFragment_to_studentDetailsFragment)
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
                    .navigate(R.id.action_studentHomeFragment_to_studentLoginFragment2)
                Toast.makeText(
                    activity, "Successfully logged out.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return true
    }
}