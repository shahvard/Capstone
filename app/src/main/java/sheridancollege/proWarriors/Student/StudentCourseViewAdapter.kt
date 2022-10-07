package sheridancollege.proWarriors.Student

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import sheridancollege.proWarriors.R

class StudentCourseViewAdapter (private val cList:List<String>) : RecyclerView.Adapter<StudentCourseViewAdapter.MyViewHolder>(){

    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val nameView: TextView = itemview.findViewById(R.id.courseName)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview=LayoutInflater.from(parent.context).inflate(R.layout.student_course_display,parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=cList[position]
        holder.nameView.text=item
    }


    override fun getItemCount()=cList.size



}
