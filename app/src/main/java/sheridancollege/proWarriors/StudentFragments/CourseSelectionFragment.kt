package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import sheridancollege.proWarriors.Course.CustomAdapter
import sheridancollege.proWarriors.Course.DataModel

import sheridancollege.proWarriors.R


class CourseSelectionFragment : Fragment() {

    private var dataModel: ArrayList<DataModel>? = null
    private var courseSelected:ArrayList<String>? = null
    private lateinit var database: DatabaseReference
    private lateinit var listView: ListView
    private lateinit var adapter: CustomAdapter
    private lateinit var username:String
    private lateinit var dbref:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_course_selection, container, false)
        listView = view.findViewById<View>(R.id.listView) as ListView
        dataModel = ArrayList<DataModel>()
        dataModel!!.add(DataModel("Java",false))
        dataModel!!.add(DataModel("C#",false))
        dataModel!!.add(DataModel("Networking",false))
        dataModel!!.add(DataModel("Waste water",false))
        adapter = CustomAdapter(dataModel!!, requireContext())
        listView.adapter=adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val dataModel: DataModel = dataModel!![position]
            dataModel.checked = !dataModel.checked
            adapter.notifyDataSetChanged()
        }

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }
        view.findViewById<Button>(R.id.Done).setOnClickListener(){

            for (d in dataModel!!){
                Log.d("Inside for each","Succcess")
                if(d.checked){
                    Log.d("Inside second if","Succcess")

                    dbref= FirebaseDatabase.getInstance().getReference(username)
                    dbref.push().setValue(d.name)
                        .addOnCompleteListener(){
                            Log.d("Success",d.name)
                        }
                    //database.child("StudentCourse").child(username).setValue(studentCourse)
                   // courseSelected?.set(i, d.name)

                }

            }
        }
            return view
        }


}
/*database = Firebase.database.reference
        database.child("Courses").child(*).child("name").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
*/