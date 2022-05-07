package sheridancollege.proWarriors.HomeAndLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import sheridancollege.proWarriors.R
class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        var sButton = view.findViewById<Button>(R.id.stdButton)
        var tButton = view.findViewById<Button>(R.id.tutorButton)

        sButton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_homeFragment_to_studentLoginFragment)
            /*var intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("type", "student")
            startActivity(intent)*/
        }

        tButton.setOnClickListener {

            view.findNavController()
                .navigate(R.id.action_homeFragment_to_tutorLoginFragment)
            /*var intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("type", "tutor")
            startActivity(intent)*/
        }

        return view
    }


}