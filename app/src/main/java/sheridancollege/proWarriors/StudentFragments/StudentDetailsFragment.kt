package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.Student
import sheridancollege.proWarriors.Student.StudentEntity
import sheridancollege.proWarriors.Student.stu


class StudentDetailsFragment : Fragment() {

    private lateinit var username:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        val user = Firebase.auth.currentUser

        var fullName = view.findViewById<TextView>(R.id.fName)
        var first = view.findViewById<TextView>(R.id.firstName)
        var phone = view.findViewById<TextView>(R.id.cNumber)
        var add = view.findViewById<TextView>(R.id.address)
        var id = view.findViewById<TextView>(R.id.email)
        var idInfo = view.findViewById<TextView>(R.id.emailID)
        var back = view.findViewById<TextView>(R.id.backToHome)

        back.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_studentDetailsFragment_to_studentHomeFragment)

        }
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }

        StudentEntity.getStudentDetails(username)
        var student: Student = stu.student
        if (student != null) {
            first.text = student.firstName
            fullName.text = student.firstName + " " + student.lastName
            phone.text = student.phoneNo
            add.text = student.address
            id.text = student.email
            idInfo.text = student.email
        }
        else{
            Toast.makeText(activity,"Student info not found. Please try again later.", Toast.LENGTH_SHORT).show()
        }
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.student_details_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title.toString()){
            "Home"->{
                return NavigationUI.onNavDestinationSelected(item,
                    requireView().findNavController()) || super.onOptionsItemSelected(item)
            }
          /* *//* "View As Tutor" -> {
                if(student.isTutor == true){
                    var intent = Intent(activity?.applicationContext, TutorActivity::class.java)
                    intent.putExtra("TutorName", student.firstName)
                    startActivity(intent)
                }*//*
                else{
                    val dialogBuilder = AlertDialog.Builder(activity?.applicationContext!!)
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
            "Logout"->{
                Firebase.auth.signOut()
                Navigation.findNavController(requireView()).navigate(R.id.action_studentDetailsFragment_to_studentLoginFragment2)
                Toast.makeText(
                    activity, "Successfully logged out.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return true
    }
}