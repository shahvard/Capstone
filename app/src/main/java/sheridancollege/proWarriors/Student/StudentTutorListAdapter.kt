package sheridancollege.proWarriors.Student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.StudentFragments.User

class StudentTutorListAdapter(private val cList:List<User>) : RecyclerView.Adapter<StudentTutorListAdapter.MyViewHolder>(){

    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val tutorNameView: TextView = itemview.findViewById(R.id.courseName)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentTutorListAdapter.MyViewHolder {
        val itemview=
            LayoutInflater.from(parent.context).inflate(R.layout.student_tutor_display,parent,false)
        return StudentTutorListAdapter.MyViewHolder(itemview)
    }


    override fun onBindViewHolder(holder: StudentTutorListAdapter.MyViewHolder, position: Int) {
        val item=cList[position]
        holder.tutorNameView.text=item.name

        holder.tutorNameView.setOnClickListener(){
            val bundle = Bundle()
            bundle.putString("TutorName",item.name)
            bundle.putString("TutorUserName",item.username)
            Navigation.findNavController(holder.tutorNameView)
                .navigate(R.id.action_studentCourseInfoFragment_to_studentTutorDescriptionFragment,bundle)

        }
    }

    override fun getItemCount()=cList.size

}