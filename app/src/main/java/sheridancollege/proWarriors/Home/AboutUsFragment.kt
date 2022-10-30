package sheridancollege.proWarriors.Home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import sheridancollege.proWarriors.R

class AboutUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_about_us, container, false)

        view.findViewById<ImageView>(R.id.vardhmanLinkedin).setOnClickListener(){
            openLinkedin("https://www.linkedin.com/in/vardhman-shah99/")
        }

        view.findViewById<ImageView>(R.id.shubhLinkedin).setOnClickListener(){
            openLinkedin("https://www.linkedin.com/in/patelshubh228/")

        }

        view.findViewById<ImageView>(R.id.chandanLinkedin).setOnClickListener(){
            openLinkedin("https://www.linkedin.com/in/chandan-chhaparia/")

        }


        view.findViewById<ImageView>(R.id.purvLinkedin).setOnClickListener(){
            openLinkedin("https://www.linkedin.com/in/purvpatel/")

        }

        return view
    }

    fun openLinkedin(url:String) {
        val openIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        if (openIntent.resolveActivity(requireActivity().packageManager)!=null){
            startActivity(openIntent)
        }

    }

}