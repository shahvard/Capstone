package sheridancollege.proWarriors.StudentFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import sheridancollege.proWarriors.R
import kotlin.collections.ArrayList


class StudentAppointmentDisplayFragment : Fragment() {


    private lateinit var viewPager:ViewPager
    private lateinit var tabLayout: TabLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_appointment_display, container, false)


        viewPager = view.findViewById(R.id.viewPagerTutor)
        tabLayout = view.findViewById(R.id.tabLayoutTutor)
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        return view

    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)

        adapter.addFragment(StudentPastAppointment(), "Past")
        adapter.addFragment(StudentFutureAppointment(), "Upcoming")

        viewpager.setAdapter(adapter)
    }


    class ViewPagerAdapter : FragmentPagerAdapter {

        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
        private final var fragmentTitleList1: ArrayList<String> = ArrayList()

        public constructor(supportFragmentManager: FragmentManager)
                : super(supportFragmentManager)

        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1.get(position)
        }

        override fun getCount(): Int {
            return fragmentList1.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }
    }
}