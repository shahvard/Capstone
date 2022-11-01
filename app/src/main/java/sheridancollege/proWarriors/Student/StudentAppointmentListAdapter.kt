package sheridancollege.proWarriors.Student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sheridancollege.proWarriors.R

class StudentAppointmentListAdapter(private val cList:List<Appointment>,val tutorName:List<String>): RecyclerView.Adapter<StudentAppointmentListAdapter.MyViewHolder>()  {

    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        val courseName:TextView=itemView.findViewById(R.id.courseNameDisplay)
        val date:TextView=itemView.findViewById(R.id.dateDisplay)
        val tutorName:TextView=itemview.findViewById(R.id.tutorNameDisplay)
        val time:TextView=itemview.findViewById(R.id.timeDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.appointment_view_row,parent,false)
        return StudentAppointmentListAdapter.MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Appointment = cList[position]
        holder.courseName.text=item.courseName
        holder.date.text = item.date
        holder.tutorName.text=tutorName[position]
        holder.time.text=item.startTime +" to "+item.endTime

    }

    override fun getItemCount()=cList.size
}
