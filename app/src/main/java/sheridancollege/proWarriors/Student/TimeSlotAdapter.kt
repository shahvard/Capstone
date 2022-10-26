package sheridancollege.proWarriors.Student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.StudentFragments.TimeSlot

class TimeSlotAdapter (private val cList:List<TimeSlot>) : RecyclerView.Adapter<TimeSlotAdapter.MyViewHolder>(){
    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val nameView: TextView = itemview.findViewById(R.id.courseName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview=
            LayoutInflater.from(parent.context).inflate(R.layout.student_course_display,parent,false)
        return TimeSlotAdapter.MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=cList[position]
        holder.nameView.text=item.startTime.toString()+"-"+item.endTime.toString()
        holder.nameView.setOnClickListener(){

           }
    }

    override fun getItemCount()=cList.size
    }
