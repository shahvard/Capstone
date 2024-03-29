package sheridancollege.proWarriors.Student

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.StudentFragments.TimeSlot


class TimeSlotAdapter(
    private val cList: List<TimeSlot>,
    private val tutorUserName: String,
    private val studentUserName: String,
    private val date: String,
    private val courseName:String
) : RecyclerView.Adapter<TimeSlotAdapter.MyViewHolder>() {


    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val nameView: TextView = itemview.findViewById(R.id.courseName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.student_course_display, parent, false)
        return TimeSlotAdapter.MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = cList[position]
        holder.nameView.text = item.startTime.toString() + " to " + item.endTime.toString()
        holder.nameView.setOnClickListener() {
            val builder = AlertDialog.Builder(holder.nameView.context)
            builder.setMessage("Are you sure you want to Confirm the Booking?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    var database = Firebase.database.reference
                    var appointment = Appointment(
                        studentUserName,
                        tutorUserName,
                        item.startTime.toString(),
                        item.endTime.toString(),
                        date,
                        courseName
                    )
                    database.child("Appointments").push().setValue(appointment)
                    Navigation.findNavController(holder.nameView)
                        .navigate(R.id.action_studentAppointmentBookingFragment_to_bookingConfirmationFragment)
                    Toast.makeText(
                        holder.nameView.context,
                        "Appointment has been booked successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    override fun getItemCount() = cList.size
}

data class Appointment(
    var studentUserName: String,
    var tutorUserName: String,
    var startTime: String,
    var endTime: String,
    var date: String,
    var courseName:String
)

