package sheridancollege.proWarriors.Student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import sheridancollege.proWarriors.R

class StudentHomeCourseAdapter(private val iconList:ArrayList<StudentHome>): RecyclerView.Adapter<StudentHomeCourseAdapter.MyViewHolder>(){

    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val iconView: ImageView = itemview.findViewById(R.id.iconImage)
        val iconTextView:TextView = itemview.findViewById(R.id.iconText)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentHomeCourseAdapter.MyViewHolder {
        val itemview=
            LayoutInflater.from(parent.context).inflate(R.layout.home_courses_rv,parent,false)
        return StudentHomeCourseAdapter.MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: StudentHomeCourseAdapter.MyViewHolder, position: Int) {
        val item = iconList[position]

        holder.iconView.setImageResource(item.image)
        holder.iconTextView.text = item.icon
        //val courseName = item.toString().split(".").get(0)
        holder.iconView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("searchItem",item.icon)
            bundle.putBoolean("firstTime", true)
            Navigation.findNavController(holder.iconView)
                .navigate(R.id.action_studentHomeFragment_to_studentCoursePage,bundle)
        }
    }

    override fun getItemCount(): Int = iconList.size

}

data class StudentHome(val icon:String, val image:Int)