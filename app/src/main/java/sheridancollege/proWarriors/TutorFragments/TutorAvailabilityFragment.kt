package sheridancollege.proWarriors.TutorFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R



class TutorAvailabilityFragment : Fragment() {

    private lateinit var mondayStartSpinner: Spinner
    private lateinit var mondayEndSpinner: Spinner
    private lateinit var tuesdayStartSpinner: Spinner
    private lateinit var tuesdayEndSpinner: Spinner
    private lateinit var wednesdayStartSpinner: Spinner
    private lateinit var wednesdayEndSpinner: Spinner
    private lateinit var thursdayStartSpinner: Spinner
    private lateinit var thursdayEndSpinner: Spinner
    private lateinit var fridayStartSpinner: Spinner
    private lateinit var fridayEndSpinner: Spinner
    private lateinit var saturdayStartSpinner: Spinner
    private lateinit var saturdayEndSpinner: Spinner
    private lateinit var sundayStartSpinner: Spinner
    private lateinit var sundayEndSpinner: Spinner
    private lateinit var mondayNot:CheckBox
    private lateinit var tuesdayNot:CheckBox
    private lateinit var wednesdayNot:CheckBox
    private lateinit var thursdayNot:CheckBox
    private lateinit var fridayNot:CheckBox
    private lateinit var saturdayNot:CheckBox
    private lateinit var sundayNot:CheckBox
    private lateinit var username:String
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view=  inflater.inflate(R.layout.fragment_tutor_availability, container, false)
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }
        database = Firebase.database.reference
        mondayStartSpinner = view.findViewById<Spinner>(R.id.mondayStart)
        mondayEndSpinner=view.findViewById<Spinner>(R.id.mondayEnd)
        tuesdayStartSpinner=view.findViewById<Spinner>(R.id.tuesdayStart)
        tuesdayEndSpinner=view.findViewById<Spinner>(R.id.tuesdayEnd)
        wednesdayStartSpinner=view.findViewById<Spinner>(R.id.wednesdayStart)
        wednesdayEndSpinner=view.findViewById<Spinner>(R.id.wednesdayEnd)
        thursdayStartSpinner=view.findViewById<Spinner>(R.id.thursdayStart)
        thursdayEndSpinner=view.findViewById<Spinner>(R.id.thursdayEnd)
        fridayStartSpinner=view.findViewById<Spinner>(R.id.fridayStart)
        fridayEndSpinner=view.findViewById<Spinner>(R.id.fridayEnd)
        saturdayStartSpinner=view.findViewById<Spinner>(R.id.saturdayStart)
        saturdayEndSpinner=view.findViewById<Spinner>(R.id.saturdayEnd)
        sundayStartSpinner=view.findViewById<Spinner>(R.id.sundayStart)
        sundayEndSpinner=view.findViewById<Spinner>(R.id.sundayEnd)
        mondayNot=view.findViewById(R.id.mondayNot)
        tuesdayNot=view.findViewById(R.id.tuesdayNot)
        wednesdayNot=view.findViewById(R.id.wednesdayNot)
        thursdayNot=view.findViewById(R.id.thursdayNot)
        fridayNot=view.findViewById(R.id.fridayNot)
        saturdayNot=view.findViewById(R.id.saturdayNot)
        sundayNot=view.findViewById(R.id.sundayNot)





        var time = arrayOf<String>("8:00 AM","8:30 AM","9:00 AM","9:30 AM","10:00 AM","10:30 AM","11:00 AM"
        ,"11:30 AM","12:00 PM","12:30 PM","1:00 PM","1:30 PM","2:00 PM","2:30 PM","3:00 PM","3:30 PM","4:00 PM",
        "4:30 PM","5:00 PM","5:30 PM","6:00 PM","6:30 PM","7:00 PM","7:30 PM","8:00 PM","8:30 PM","9:00 PM",
        "9:30 PM","10:00 PM")
        val adapter = ArrayAdapter(this.requireContext(),
                android.R.layout.simple_spinner_item, time)

        var mondayStart=""
        var mondayEnd=""
        var tuesdayStart=""
        var tuesdayEnd=""
        var wednesdayStart=""
        var wednesdayEnd=""
        var thursdayStart=""
        var thursdayEnd=""
        var fridayStart=""
        var fridayEnd=""
        var saturdayStart=""
        var saturdayEnd=""
        var sundayStart=""
        var sundayEnd=""
        if (mondayStartSpinner != null) {
            mondayStartSpinner.adapter = adapter

            mondayStartSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        mondayStart = time[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }
        }
            if (mondayEndSpinner != null) {
                mondayEndSpinner.adapter = adapter

                mondayEndSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            mondayEnd = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }

            if (tuesdayStartSpinner != null) {
                tuesdayStartSpinner.adapter = adapter

                tuesdayStartSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            tuesdayStart = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }


            if (tuesdayEndSpinner != null) {
                tuesdayEndSpinner.adapter = adapter

                tuesdayEndSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            tuesdayEnd = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }



            if (wednesdayStartSpinner != null) {
                wednesdayStartSpinner.adapter = adapter

                wednesdayStartSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            wednesdayStart = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }

            if (wednesdayEndSpinner != null) {
                wednesdayEndSpinner.adapter = adapter

                wednesdayEndSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            wednesdayEnd = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }

            if (thursdayStartSpinner != null) {
                thursdayStartSpinner.adapter = adapter

                thursdayStartSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            thursdayStart = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }

            if (thursdayEndSpinner != null) {
                thursdayEndSpinner.adapter = adapter

                thursdayEndSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            thursdayEnd = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }

            if (fridayStartSpinner != null) {
                fridayStartSpinner.adapter = adapter

                fridayStartSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            fridayStart = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }


            if (fridayEndSpinner != null) {
                fridayEndSpinner.adapter = adapter

                fridayEndSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            fridayEnd = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }

            if (saturdayStartSpinner != null) {
                saturdayStartSpinner.adapter = adapter

                saturdayStartSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            saturdayStart = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }


            if (saturdayEndSpinner != null) {
                saturdayEndSpinner.adapter = adapter

                saturdayEndSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            saturdayEnd = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }

            if (sundayStartSpinner != null) {
                sundayStartSpinner.adapter = adapter

                sundayStartSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            sundayStart = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }

            if (sundayEndSpinner != null) {
                sundayEndSpinner.adapter = adapter

                sundayEndSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            sundayEnd = time[position]
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
            }



        view.findViewById<Button>(R.id.update).setOnClickListener(){

            if(mondayNot.isChecked){
                mondayStart="na"
                mondayEnd="na"
            }
            if(tuesdayNot.isChecked){
             tuesdayStart="na"
                tuesdayEnd="na"
            }

            if(wednesdayNot.isChecked){
                wednesdayStart="na"
                wednesdayEnd="na"
            }

            if(thursdayNot.isChecked){
                thursdayStart="na"
                thursdayEnd="na"
            }
            if(fridayNot.isChecked){
                fridayStart="na"
                fridayEnd="na"
            }
            if(saturdayNot.isChecked){
                saturdayStart="na"
                saturdayEnd="na"
            }

            if(sundayNot.isChecked){
               sundayStart="na"
                sundayEnd="na"
            }
            var availability=Availability(mondayStart,mondayEnd,tuesdayStart,
                tuesdayEnd,wednesdayStart,wednesdayEnd,thursdayStart,thursdayEnd,
                fridayStart,fridayEnd,saturdayStart,saturdayEnd,sundayStart,
                sundayEnd)

            database.child("Availability").child(username).setValue(availability)
            Log.d("username",username)





        }

        return view
    }


    class Availability(var mondayStart:String,var mondayEnd:String,
    var tuesdayStart:String,var tuesdayEnd:String, var wednesdayStart:String,
    var wednesdayEnd:String,var thursdayStart:String,var thursdayEnd:String,
    var fridayStart:String,var fridayEnd:String,var saturdayStart:String,
    var saturdayEnd:String,var sundayStart:String,var sundayEnd:String)



}