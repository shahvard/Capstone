package sheridancollege.proWarriors.TutorFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.Home.MainActivity
import sheridancollege.proWarriors.R


class TutorAvailabilityFragment : Fragment() {

    private lateinit var sunday: ImageView
    private lateinit var monday: ImageView
    private lateinit var tuesday: ImageView
    private lateinit var wednesday: ImageView
    private lateinit var thursday: ImageView
    private lateinit var friday: ImageView
    private lateinit var saturday: ImageView
    private lateinit var heading:TextView
    private lateinit var notAvail: CheckBox
    private lateinit var confirmButton: Button
    private lateinit var addButton:Button
    private lateinit var sTime:EditText
    private lateinit var eTime:EditText
    private lateinit var username: String
    private lateinit var availList: ArrayList<Availability>
    private lateinit var database: DatabaseReference
    private lateinit var days: Array<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutor_availability, container, false)

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }
        database = Firebase.database.reference
        availList = arrayListOf()
        heading = view.findViewById(R.id.timeHeading)
        sunday = view.findViewById(R.id.sunButton)
        sunday.tag = "sunday"
        saturday = view.findViewById(R.id.satButton)
        saturday.tag = "saturday"
        monday = view.findViewById(R.id.monButton)
        monday.tag = "monday"
        tuesday = view.findViewById(R.id.tueButton)
        tuesday.tag = "tuesday"
        wednesday = view.findViewById(R.id.wedButton)
        wednesday.tag = "wednesday"
        thursday = view.findViewById(R.id.thuButton)
        thursday.tag = "thursday"
        friday = view.findViewById(R.id.friButton)
        friday.tag = "friday"
        sTime = view.findViewById(R.id.startTime)
        eTime = view.findViewById(R.id.endTime)
        confirmButton = view.findViewById(R.id.confirm)
        addButton = view.findViewById(R.id.addButton)
        notAvail = view.findViewById(R.id.notAvailable)
        days = arrayOf(sunday, monday, tuesday, wednesday, thursday, friday, saturday)

        var fromStudent = arguments?.getBoolean("fromStudent")
        if (fromStudent == true){
            view.findViewById<TextView>(R.id.nameHeading).visibility = View.GONE
        }

        var edit = arguments?.getBoolean("edit")

        if (edit == true){
            view.findViewById<TextView>(R.id.nameHeading).visibility = View.GONE
            view.findViewById<TextView>(R.id.tutorDesc).visibility = View.GONE
        }
        var provided:Int = 0

        sunday.setOnClickListener {
            heading.text = "Select timings for Sunday"
            addButton.setOnClickListener {
                showAllImages()
                getTimings("Sunday")
                clearTextFields()
                provided++
            }
            invisbleOthers("sunday")
        }
        monday.setOnClickListener {
            heading.text = "Select timings for Monday"
            addButton.setOnClickListener {
                showAllImages()
                getTimings("Monday")
                clearTextFields()
                provided++
            }
            invisbleOthers("monday")
        }
        tuesday.setOnClickListener {
            heading.text = "Select timings for Tuesday"
            addButton.setOnClickListener {
                showAllImages()
                getTimings("Tuesday")
                clearTextFields()
                provided++
            }
            invisbleOthers("tuesday")
        }
        wednesday.setOnClickListener {
            heading.text = "Select timings for Wednesday"
            addButton.setOnClickListener {
                showAllImages()
                getTimings("Wednesday")
                clearTextFields()
                provided++
            }
            invisbleOthers("wednesday")
        }
        thursday.setOnClickListener {
            heading.text = "Select timings for Thursday"
            addButton.setOnClickListener {
                showAllImages()
                getTimings("Thursday")
                clearTextFields()
                provided++
            }
            invisbleOthers("thursday")
        }
        friday.setOnClickListener {
            heading.text = "Select timings for Friday"
            addButton.setOnClickListener {
                showAllImages()
                getTimings("Friday")
                clearTextFields()
                provided++
            }
            invisbleOthers("friday")
        }
        saturday.setOnClickListener {
            heading.text = "Select timings for Saturday"
            addButton.setOnClickListener {
                showAllImages()
                getTimings("Saturday")
                clearTextFields()
                provided++
            }
            invisbleOthers("saturday")
        }

        confirmButton.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(requireContext())
            if (provided < 7){
                Toast.makeText(requireContext(), "Please provide availability for each day to continue.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Congratulations tutor! You have completed whole boarding process.", Toast.LENGTH_SHORT).show()


                builder.setMessage("Redirecting to login, please login using sheridan email and password.")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        if(fromStudent==true){
                            val bundle = Bundle()
                            bundle.putBoolean("fromStudent", true)
                            bundle.putString("tutorUserName", username)
                            var intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_tutorAvailabilityFragment2_to_tutorLoginFragment)
                        }

                    }
                    .setNegativeButton("No"){ dialog, id ->
                        dialog.cancel()
                    }
                    .setTitle("Confirm the availability has been provided.")
                val alert = builder.create()
                alert.show()
            }
        }
        return view
    }


    private fun invisbleOthers(day: String) {

        for (d in days){
            var name = d.tag
            if (name==day){
                d.visibility = View.VISIBLE
            }
            else{
                d.visibility = View.INVISIBLE
            }
        }
    }

    private fun showAllImages(){
        for (d in days){
            d.visibility = View.VISIBLE
        }
    }

    private fun clearTextFields() {
        sTime.text.clear()
        eTime.text.clear()
        notAvail.isChecked = false
    }

    private fun getTimings(day: String) {
        var nAvail = false
        var start = ""
        var end = ""
        if(!notAvail.isChecked){
            nAvail = false
            start = sTime.text.toString()
            var startWithoutMinutes = start.split(":").get(0)
            if(startWithoutMinutes.toInt()<12){
                start=start+" AM"
            }
            else if(startWithoutMinutes.toInt()>12){
                startWithoutMinutes=(startWithoutMinutes.toInt()-12).toString()
                startWithoutMinutes=startWithoutMinutes+":"+start.split(":").get(1)+" PM"
                start=startWithoutMinutes
            }
            end = eTime.text.toString()
            var endWithoutMinutes = end.split(":").get(0)
            if(endWithoutMinutes.toInt()<12){
                end=end+" AM"
            }
            else if(endWithoutMinutes.toInt()>12){
                endWithoutMinutes=(endWithoutMinutes.toInt()-12).toString()
                endWithoutMinutes=endWithoutMinutes+":"+end.split(":").get(1)+" PM"
                end=endWithoutMinutes
            }
            var item = Availability(startTime = start, endTime = end, notAvailable = nAvail)
            database.child("Availability").child(username).child(day).setValue(item)

            //availList.add(item)
        }else{
            nAvail = true
            start = "NA"
            end = "NA"
            var item = Availability(startTime = start, endTime = end, notAvailable = nAvail)
            database.child("Availability").child(username).child(day).setValue(item)

            // availList.add(item)
        }
    }

    class Availability(
        var startTime: String, var endTime: String, var notAvailable: Boolean
    )


}