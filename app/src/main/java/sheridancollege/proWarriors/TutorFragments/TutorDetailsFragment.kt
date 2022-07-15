package sheridancollege.proWarriors.TutorFragments

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
import sheridancollege.proWarriors.Tutor.Tutor
import sheridancollege.proWarriors.Tutor.TutorEntity
import sheridancollege.proWarriors.Tutor.tut

class TutorDetailsFragment : Fragment() {

    private lateinit var username:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutor_details, container, false)
        val user = Firebase.auth.currentUser

        var first = view.findViewById<TextView>(R.id.fName)
        var last = view.findViewById<TextView>(R.id.lName)
        var phone = view.findViewById<TextView>(R.id.cNumber)
        var add = view.findViewById<TextView>(R.id.address)

        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }

        TutorEntity.getTutorDetails(username)
        var tutor: Tutor = tut.tutor
        if (tutor != null) {
            first.text = tutor.firstName
            last.text = tutor.lastName
            phone.text = tutor.phoneNo
            add.text = tutor.address
        }
        else{
            Toast.makeText(activity,"Tutor info not found. Please try again later.", Toast.LENGTH_SHORT).show()
        }
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.tutor_details_menu, menu)
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