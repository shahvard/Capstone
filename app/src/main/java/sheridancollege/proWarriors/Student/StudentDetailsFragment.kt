package sheridancollege.proWarriors.Student

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Tutor.TutorActivity


class StudentDetailsFragment : Fragment() {

    //private lateinit var student:Student
    private lateinit var username:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_details, container, false)
        val user = Firebase.auth.currentUser

        var first = view.findViewById<TextView>(R.id.fName)
        var last = view.findViewById<TextView>(R.id.lName)
        var phone = view.findViewById<TextView>(R.id.cNumber)
        var add = view.findViewById<TextView>(R.id.address)

        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }
        var studententity = StudentEntity
        studententity.getStudentDetails(username)
        var student:Student= stu.student
        if (student != null) {
            first.text = student.firstName
            last.text = student.lastName
            phone.text = student.phoneNo
            add.text = student.address
        }

        else{
            Toast.makeText(activity,"Student info not found. Please try again later.", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.details_menu, menu)
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