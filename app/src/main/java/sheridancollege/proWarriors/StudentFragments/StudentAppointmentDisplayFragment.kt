package sheridancollege.proWarriors.StudentFragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.facebook.react.bridge.UiThreadUtil
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.*
import sheridancollege.proWarriors.Tutor.Tutor
import sheridancollege.proWarriors.Tutor.TutorEntity
import sheridancollege.proWarriors.Tutor.tut
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.fixedRateTimer


class StudentAppointmentDisplayFragment : Fragment() {


    private lateinit var viewPager:ViewPager
    private lateinit var tabLayout: TabLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_appointment_display, container, false)


        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        //val fragmentAdapter = AppointmentFragmentAdapter(requireActivity().supportFragmentManager)
        /*fragmentAdapter.addFragment(StudentPastAppointment(), "Past")
        fragmentAdapter.addFragment(StudentFutureAppointment(), "Upcoming")*/
        setupViewPager(viewPager)

        // If we dont use setupWithViewPager() method then
        // tabs are not used or shown when activity opened
        tabLayout.setupWithViewPager(viewPager)

       /* viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)*/
        return view

    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab
        adapter.addFragment(StudentPastAppointment(), "Past")
        adapter.addFragment(StudentFutureAppointment(), "Upcoming")

        // setting adapter to view pager.
        viewpager.setAdapter(adapter)
    }


    class ViewPagerAdapter : FragmentPagerAdapter {

        // objects of arraylist. One is of Fragment type and
        // another one is of String type.*/
        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
        private final var fragmentTitleList1: ArrayList<String> = ArrayList()

        // this is a secondary constructor of ViewPagerAdapter class.
        public constructor(supportFragmentManager: FragmentManager)
                : super(supportFragmentManager)

        // returns which item is selected from arraylist of fragments.
        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        // returns which item is selected from arraylist of titles.
        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1.get(position)
        }

        // returns the number of items present in arraylist.
        override fun getCount(): Int {
            return fragmentList1.size
        }

        // this function adds the fragment and title in 2 separate  arraylist.
        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }
    }


}