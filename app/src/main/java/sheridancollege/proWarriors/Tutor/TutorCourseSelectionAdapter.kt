package sheridancollege.proWarriors.Tutor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.StudentCourseItem
import sheridancollege.proWarriors.Student.StudentCourseSelectionAdapter

class TutorCourseSelectionAdapter (private val cList:List<TutorCourseItem>): RecyclerView.Adapter<TutorCourseSelectionAdapter.MyViewHolder>(){

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val nameView: TextView = itemview.findViewById(R.id.courseName)
        val checked: CheckBox = itemview.findViewById(R.id.checkBox)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.course_selection_row,parent,false)
        return TutorCourseSelectionAdapter.MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: TutorCourseItem = cList[position]
        holder.nameView.text=item.courseName
        holder.checked.isChecked = item.isChecked

        holder.checked.setOnClickListener {
            item.isChecked = true
        }
    }

    override fun getItemCount()=cList.size
}