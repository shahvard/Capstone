package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import sheridancollege.proWarriors.R

class BookingConfirmationFragment : Fragment() {

    private lateinit var goHome:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking_confirmation, container, false)

        goHome = view.findViewById(R.id.goToHome)


        goHome.setOnClickListener(){
            Navigation.findNavController(requireView())
                .navigate(R.id.action_bookingConfirmationFragment_to_studentHomeFragment)
        }
        // Inflate the layout for this fragment
        return view
    }

}