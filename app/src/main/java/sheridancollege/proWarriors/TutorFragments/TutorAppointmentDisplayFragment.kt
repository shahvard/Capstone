package sheridancollege.proWarriors.TutorFragments

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
import sheridancollege.proWarriors.StudentFragments.StudentAppointmentDisplayFragment
import sheridancollege.proWarriors.StudentFragments.StudentFutureAppointment
import sheridancollege.proWarriors.StudentFragments.StudentPastAppointment


class TutorAppointmentDisplayFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_tutor_appointment_display, container, false)
        viewPager = view.findViewById(R.id.viewPagerTutor)
        tabLayout = view.findViewById(R.id.tabLayoutTutor)

        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)



        return  view

    }


    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter =
           ViewPagerAdapter(requireActivity().supportFragmentManager)

        adapter.addFragment(TutorPastAppointment(), "Past")
        adapter.addFragment(TutorFutureAppointment(), "Upcoming")

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