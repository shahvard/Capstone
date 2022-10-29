package sheridancollege.proWarriors.Support

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import sheridancollege.proWarriors.R



class SupportFragment : Fragment() {

    lateinit var gitHubImageButton: ImageButton
    lateinit var gmailImageButton:ImageButton
    lateinit var contactMeImageButton:ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_support, container, false)


        gitHubImageButton = view.findViewById(R.id.gitHub)
        gitHubImageButton.setOnClickListener {
            openGitHub()
        }

        gmailImageButton=view.findViewById(R.id.gmail)
        gmailImageButton.setOnClickListener {
            sendEmail()
        }

        contactMeImageButton = view.findViewById(R.id.contact)
        contactMeImageButton.setOnClickListener {
           /* if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this.requireActivity(),Array(1){Manifest.permission.READ_CONTACTS},111)
            }
            else
                contactMeImageButton.isClickable = false*/
            contactMe()

        }








        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            contactMeImageButton.isClickable = true
            contactMe()
        }
    }

    fun openGitHub() {
        val openIntent = Intent().apply {
            action =Intent.ACTION_VIEW
            data = Uri.parse("https://github.com/MohammedFouadx")
        }
        if (openIntent.resolveActivity(requireActivity().packageManager)!=null){
            startActivity(openIntent)
        }

    }

    fun sendEmail(){
        val sendIntent = Intent().apply {
            action= Intent.ACTION_SEND
            type = "text/plain"
            to("support@shertut.com")


        }
        if (sendIntent.resolveActivity(requireActivity().packageManager)!=null){
            startActivity(sendIntent)
        }
    }


    fun contactMe(){
        val intent = Intent(
            ContactsContract.Intents.SHOW_OR_CREATE_CONTACT,
            ContactsContract.Contacts.CONTENT_URI)
        intent.data = Uri.parse("tel:6476150054")

        intent.putExtra(ContactsContract.Intents.Insert.NAME, "SherTutor Suppport")
//        intent.putExtra(ContactsContract.Intents.Insert.POSTAL,
//                "House Address, Street Name, State/Country")
        startActivity(intent)
        Toast.makeText(this.requireContext(), "Contact Saved Successfully", Toast.LENGTH_SHORT).show()

    }

}





