package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.TimeSlotAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*


class StudentAppointmentBookingFragment : Fragment() {

    private lateinit var tutorName: String
    private lateinit var tutorUserName: String
    lateinit var calendarView: CalendarView
    private lateinit var database: DatabaseReference
    lateinit var start: String
    lateinit var end: String
    private lateinit var slotSpinner: Spinner
    private lateinit var slotTimeSelected: String
    private lateinit var day: String
    private lateinit var slotsArray: List<TimeSlot>
    private lateinit var slotsRV: RecyclerView
    private lateinit var studentUserName:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_appointment_booking, container, false)
        database = Firebase.database.reference
        tutorName = arguments?.getString("TutorName").toString()
        tutorUserName = arguments?.getString("TutorUserName").toString()
        view.findViewById<TextView>(R.id.name).text = "Tutor Name :" + tutorName
        calendarView = view.findViewById(R.id.calendarView)
        slotSpinner = view.findViewById(R.id.slotSpinner)
        slotsRV = view.findViewById(R.id.slotsRV)
        slotsRV.layoutManager = LinearLayoutManager(this.context)
        slotsRV.setHasFixedSize(true)
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            studentUserName = email?.split("@")?.get(0).toString()
        }

        var slotTime = arrayOf<String>("Select Slot time", "1/2 hour", "1 hour")
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item, slotTime
        )


        if (slotSpinner != null) {
            slotSpinner.adapter = adapter

            slotSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        slotTimeSelected = slotTime[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }
        }
        calendarView.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->

            // Create calender object with which will have system date time.
            val calender: Calendar = Calendar.getInstance()
            calView
            // Set attributes in calender object as per selected date.
            calender.set(year, month, dayOfMonth)

            // Now set calenderView with this calender object to highlight selected date on UI.
            calView.setDate(calender.timeInMillis, true, true)

           var date="$dayOfMonth/${month + 1}/$year"
            var dayOfWeekInt =
                getDayOfWeekValue(LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0, 0))
            Log.d("Day=", dayOfWeekInt.toString())
            var monthFinal=month+1
            //var dateString=monthFinal.toString()+"."+dayOfMonth.toString()+"."+year.toString()
            //val formatter = SimpleDateFormat("MM.dd.yyyy")
           /* var date = formatter.parse(dateString)*/
            Log.d("Day selected",dayOfWeekInt.toString())
            when (dayOfWeekInt) {
                1 -> day = "sunday"
                2 -> day = "monday"
                3 -> day = "tuesday"
                4 -> day = "wednesday"
                5 -> day = "thursday"
                6 -> day = "friday"
                7 -> day = "saturday"
            }

            GlobalScope.launch {
                database.child("Availability").child(tutorUserName).child(day + "Start").get()
                    .addOnSuccessListener {

                        start = it.value.toString()


                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }


                database.child("Availability").child(tutorUserName).child(day + "End").get()
                    .addOnSuccessListener {
                        end = it.value.toString()

                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }

                delay(500)
                runOnUiThread {
                    Log.d("Tuesday start",start)
                    if (start == "na") {
                        Toast.makeText(
                            requireContext(),
                            "No slots Available on this day select another day",
                            Toast.LENGTH_SHORT
                        ).show()
                        slotsArray = emptyList()
                        slotsRV.adapter = TimeSlotAdapter(slotsArray,tutorUserName,studentUserName,date)


                    } else {
                        var startTime = LocalTime.parse(
                            start, DateTimeFormatter.ofLocalizedTime(
                                FormatStyle.SHORT
                            )
                        )
                        var endTime =
                            LocalTime.parse(
                                end,
                                DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                            )

                        Log.d("Start Time converted", startTime.toString())
                        Log.d("Start Time ", start.toString())
                        Log.d("Start Time converted", endTime.toString())
                        Log.d("Start Time ", end.toString())


                        var timeSlotObject = TimeSlot(startTime, endTime)


                        if (slotTimeSelected == "1 hour") {
                            slotsArray = timeSlotObject.divide(60)

                        }
                        if (slotTimeSelected == "1/2 hour") {
                            slotsArray = timeSlotObject.divide(30)
                        }
                        if (slotTimeSelected == "Select Slot time") {
                            Toast.makeText(
                                requireContext(),
                                "Select the slot time to continue",
                                Toast.LENGTH_SHORT
                            ).show()
                            slotsArray = emptyList()

                        }


                        slotsRV.adapter = TimeSlotAdapter(slotsArray,tutorUserName,studentUserName,date)
                        Log.d("date",date.toString())

                    }
                }
            }

        }


        return view
    }

    fun getDayOfWeekValue(input: LocalDateTime): Int {
        return Math.toIntExact(
            ChronoUnit.DAYS.between(
                input.with(
                    TemporalAdjusters.previousOrSame(
                        WeekFields.of(Locale.US)
                            .getFirstDayOfWeek()
                    )
                ),
                input.plusDays(1)
            )
        )

    }

    fun TimeSlot.divide(lengthHours: Long): List<TimeSlot> {
        require(lengthHours > 0) { "lengthHours was $lengthHours. Must specify positive amount of hours." }
        val timeSlots = mutableListOf<TimeSlot>()
        var nextStartTime = startTime
        while (true) {
            //nextStartTime.minute.plus(lengthHours)
            val nextEndTime = nextStartTime.plusMinutes(lengthHours)
            //nextEndTime.minute.plus(lengthHours)
            Log.d("NextEndTime",nextEndTime.toString())
            Log.d("EndTime",endTime.toString())


            if (nextEndTime > endTime) {

                break
            }
           // Log.d("start time - End time",nextStartTime.toString()+" - "+nextEndTime.toString())
            timeSlots.add(TimeSlot(nextStartTime, nextEndTime))
            nextStartTime = nextEndTime
        }
        return timeSlots
    }

}

data class TimeSlot(val startTime: LocalTime, val endTime: LocalTime) {

}