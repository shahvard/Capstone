package sheridancollege.proWarriors.Student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sheridancollege.proWarriors.R

class StudentCourseSelectionAdapter(private val cList:List<StudentCourseItem>): RecyclerView.Adapter<StudentCourseSelectionAdapter.MyViewHolder>() {


    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val nameView: TextView = itemview.findViewById(R.id.courseName)
        val checked:CheckBox = itemview.findViewById(R.id.checkBox)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview =LayoutInflater.from(parent.context).inflate(R.layout.course_selection_row,parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: StudentCourseItem = cList[position]
        holder.nameView.text=item.courseName
        holder.checked.isChecked = item.isChecked

        holder.checked.setOnClickListener {
            item.isChecked = true
            }
        }

    override fun getItemCount()=cList.size


}