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
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*


class StudentAppointmentBookingFragment : Fragment() {

    private var notAvailable: String = ""
    private lateinit var tutorName: String
    private lateinit var tutorUserName: String
    lateinit var calendarView: CalendarView
    private lateinit var database: DatabaseReference
    lateinit var start: String
    lateinit var end: String
    lateinit var courseName:String

    //private lateinit var slotSpinner: Spinner
    private lateinit var halfHourCheck: RadioButton
    private lateinit var oneHourCheck: RadioButton
    private lateinit var slotTimeSelected: String
    private lateinit var day: String
    private lateinit var slotsArray: List<TimeSlot>
    private lateinit var slotsRV: RecyclerView
    private lateinit var studentUserName: String
    private lateinit var group: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_appointment_booking, container, false)

        calendarView = view.findViewById(R.id.calendarView)
        database = Firebase.database.reference
        tutorName = arguments?.getString("TutorName").toString()
        tutorUserName = arguments?.getString("TutorUserName").toString()
        Log.d("TUTOR NAME", tutorUserName)
        slotsRV = view.findViewById(R.id.slotRecView)
        slotsRV.layoutManager = LinearLayoutManager(this.context)
        slotsRV.setHasFixedSize(true)
        group = view.findViewById(R.id.radioGroup)
        halfHourCheck = view.findViewById(R.id.halfHourRadio)
        oneHourCheck = view.findViewById(R.id.oneHourRadio)
        slotsArray = arrayListOf()
        courseName=arguments?.getString("courseName").toString()
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            studentUserName = email?.split("@")?.get(0).toString()
        }

        group.setOnCheckedChangeListener { group, i ->
            when (i) {
                R.id.oneHourRadio -> {
                    slotTimeSelected = "1 hour"
                }
                R.id.halfHourRadio -> {
                    slotTimeSelected = "1/2 hour"
                }

        }
        
            calendarView.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->

                // Create calender object with which will have system date time.
                val calender: Calendar = Calendar.getInstance()
                // Set attributes in calender object as per selected date.
                calender.set(year, month, dayOfMonth)
                // Now set calenderView with this calender object to highlight selected date on UI.
                calView.setDate(calender.timeInMillis, true, true)

                var date = "$dayOfMonth/${month + 1}/$year"
                var dayOfWeekInt =
                    getDayOfWeekValue(LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0, 0))
                var monthFinal = month + 1
                when (dayOfWeekInt) {
                    1 -> day = "Sunday"
                    2 -> day = "Monday"
                    3 -> day = "Tuesday"
                    4 -> day = "Wednesday"
                    5 -> day = "Thursday"
                    6 -> day = "Friday"
                    7 -> day = "Saturday"
                }
                GlobalScope.launch {
                    database.child("Availability").child(tutorUserName).child(day)
                        .child("notAvailable").get()
                        .addOnSuccessListener {
                            notAvailable = it.value.toString()
                            Log.d("NOT AVAIL in FIRST", notAvailable)
                        }

                    delay(200)
                    Log.d("NOT AVAIL after FIRST", notAvailable)

                    if (notAvailable == "true") {
                        slotsArray = emptyList()
                        runOnUiThread{
                            slotsRV.adapter =
                                TimeSlotAdapter(slotsArray, tutorUserName, studentUserName, date,courseName)
                            Toast.makeText(
                                requireContext(),
                                "No slots Available on this day select another day",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }else if(notAvailable == "false"){
                        database.child("Availability").child(tutorUserName).child(day)
                            .child("startTime").get()
                            .addOnSuccessListener {
                                start = it.value.toString()
                            }.addOnFailureListener {
                                Log.e("firebase", "Error getting data", it)
                            }

                        database.child("Availability").child(tutorUserName).child(day)
                            .child("endTime").get()
                            .addOnSuccessListener {
                                end = it.value.toString()
                            }.addOnFailureListener {
                                Log.e("firebase", "Error getting data", it)
                            }

                        delay(500)
                       // runOnUiThread {



                            var startTime = LocalTime.parse(
                                start,
                                DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

                            var endTime =
                                LocalTime.parse(
                                    end,
                                    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                                )

                            var timeSlotObject = TimeSlot(startTime, endTime)

                            if (slotTimeSelected == "1 hour") {
                                slotsArray = timeSlotObject.divide(60)
                                runOnUiThread {
                                    Toast.makeText(
                                        requireContext(),
                                        "Select the slot time to continue",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    slotsRV.adapter =
                                        TimeSlotAdapter(
                                            slotsArray,
                                            tutorUserName,
                                            studentUserName,
                                            date,
                                            courseName
                                        )
                                }
                            } else if (slotTimeSelected == "1/2 hour") {
                                slotsArray = timeSlotObject.divide(30)
                                runOnUiThread {
                                    Toast.makeText(
                                        requireContext(),
                                        "Select the slot time to continue",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    slotsRV.adapter =
                                        TimeSlotAdapter(
                                            slotsArray,
                                            tutorUserName,
                                            studentUserName,
                                            date,
                                            courseName

                                        )
                                }
                            } else {
                                runOnUiThread {
                                    Toast.makeText(
                                        requireContext(),
                                        "Please select the time slot",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                    }
                }
            }
        }
        return view
    }

    private fun getDayOfWeekValue(input: LocalDateTime): Int {
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
            val nextEndTime = nextStartTime.plusMinutes(lengthHours)
            if (nextEndTime > endTime) {
                break
            }
            timeSlots.add(TimeSlot(nextStartTime, nextEndTime))
            nextStartTime = nextEndTime
        }
        return timeSlots
    }

}

data class TimeSlot(val startTime: LocalTime, val endTime: LocalTime) {

}
