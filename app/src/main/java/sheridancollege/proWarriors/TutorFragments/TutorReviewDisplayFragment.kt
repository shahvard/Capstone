package sheridancollege.proWarriors.TutorFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.UiThreadUtil
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.Student
import sheridancollege.proWarriors.Student.StudentCourseSelectionAdapter
import sheridancollege.proWarriors.Student.StudentEntity
import sheridancollege.proWarriors.Student.stu
import java.text.SimpleDateFormat
import java.util.*


class TutorReviewDisplayFragment : Fragment() {

    private lateinit var username: String
    private lateinit var database: FirebaseDatabase
    private lateinit var commentsArray: ArrayList<String>
    private lateinit var starsArray: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutor_display_review, container, false)
        val user = Firebase.auth.currentUser
        database = FirebaseDatabase.getInstance()
        commentsArray = arrayListOf()
        starsArray = arrayListOf()
        var rView = view.findViewById<RecyclerView>(R.id.rvReview)
        rView.layoutManager = LinearLayoutManager(this.context)
        rView.setHasFixedSize(true)

        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }

        GlobalScope.launch {

            database.getReference("TutorReviews/$username/Reviews")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot!!.exists()) {
                            for (child in snapshot.children) {
                                val a = child.value
                                commentsArray.add(a.toString())
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            database.getReference("TutorReviews/$username/Stars")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot!!.exists()) {
                            for (child in snapshot.children) {
                                val a = child.value
                                starsArray.add(a.toString())
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            delay(500)
            Log.d("commentsArray", commentsArray.toString())

            runOnUiThread {
                rView.adapter = TutorReviewDisplayAdapter(commentsArray, starsArray)

            }

        }
        return view
    }


}

class TutorReviewDisplayAdapter(
    private val commentsArray: List<String>,
    private val starsArray: List<String>
) : RecyclerView.Adapter<TutorReviewDisplayAdapter.MyViewHolder>() {
    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val commentView: TextView = itemview.findViewById(R.id.commentsReview)
        val starView: TextView = itemview.findViewById(R.id.starsReview)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context)
            .inflate(R.layout.tutor_review_display_row, parent, false)
        return MyViewHolder(itemview)
    }


    override fun getItemCount() = commentsArray.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment: String = commentsArray[position]
        val star: String = starsArray[position]
        holder.commentView.text = comment
        holder.starView.text = star
    }

}
