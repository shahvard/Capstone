package sheridancollege.proWarriors.Course

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sheridancollege.proWarriors.R

class MyCourseView(private val cList:List<ClassItem>): RecyclerView.Adapter<MyCourseView.MyViewHolder>() {

    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val nameView: TextView = itemview.findViewById(R.id.txtName)
        val checked:CheckBox = itemview.findViewById(R.id.checkBox)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview =LayoutInflater.from(parent.context).inflate(R.layout.row_item_courseselection,parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=cList[position]
        holder.nameView.text=item.courseName
        holder.checked.isChecked = item.isChecked

        holder.checked.setOnClickListener {
            Log.d("Inside second if SHUBH","Succcess")

            item.isChecked = true

            }

        }

       /* holder.checked.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->


            checkBoxStateArray.put(bindingAdapterPosition, isChecked)
        })*/



    override fun getItemCount()=cList.size


}