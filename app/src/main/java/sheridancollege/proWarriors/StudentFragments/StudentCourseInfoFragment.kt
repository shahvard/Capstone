package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import sheridancollege.proWarriors.R


class StudentCourseInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_course_info, container, false)


            view.findViewById<TextView>(R.id.textView).text = arguments?.getString("CourseName").toString()





        return view
    }


}