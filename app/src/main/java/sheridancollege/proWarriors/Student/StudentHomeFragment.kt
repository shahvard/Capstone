package sheridancollege.proWarriors.Student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R


class StudentHomeFragment : Fragment() {


// ...

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val view = inflater.inflate(R.layout.fragment_student_home, container, false)
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            val delimiter ="@"

            val username = email?.split(delimiter)?.get(0)



            Toast.makeText(this.context,email,Toast.LENGTH_SHORT).show()

        }





        return view
    }


}