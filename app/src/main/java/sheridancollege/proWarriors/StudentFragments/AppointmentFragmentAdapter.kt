/*
package sheridancollege.proWarriors.StudentFragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.appbar.AppBarLayout
import sheridancollege.proWarriors.R

class AppointmentFragmentAdapter(var context: Context, private var fragmentList: ArrayList<Fragment>): PagerAdapter(){

    //var fragmentList: ArrayList<Fragment> = ArrayList()
    var fragmentTitle: ArrayList<String> = ArrayList()
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {

        val frag:Fragment
        val inflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.appointment_view_row, container, false)
        frag=itemView.
        return super.instantiateItem(container, position)
    }
}*/
