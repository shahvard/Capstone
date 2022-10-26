package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import sheridancollege.proWarriors.R


class StudentTutorDescriptionFragment : Fragment() {

    private lateinit var tutorName:String
    private lateinit var tutorUserName:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val view =inflater.inflate(R.layout.fragment_student_tutor_description, container, false)
        tutorName=arguments?.getString("TutorName").toString()
        tutorUserName=arguments?.getString("TutorUserName").toString()
        view.findViewById<TextView>(R.id.tutorName).text = tutorName
        view.findViewById<Button>(R.id.bookAppointment).setOnClickListener(){
            val bundle = Bundle()
            bundle.putString("TutorName",tutorName)
            bundle.putString("TutorUserName",tutorUserName)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_studentTutorDescriptionFragment_to_studentAppointmentBookingFragment,bundle)

        }
       // Log.d("TutorName",tutorUserName)







        return view
    }

}