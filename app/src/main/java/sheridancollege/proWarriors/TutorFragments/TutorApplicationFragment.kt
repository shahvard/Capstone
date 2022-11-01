package sheridancollege.proWarriors.TutorFragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.gms.tasks.Task
import com.google.common.net.MediaType.PDF
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import sheridancollege.proWarriors.R
import java.io.ByteArrayOutputStream


class TutorApplicationFragment : Fragment() {

    private lateinit var username: String
    private lateinit var name: TextView
    private lateinit var headName: String
    private lateinit var image: ImageView
    private val GALLERY_PICK = 1
    lateinit var imageStore: StorageReference
    private lateinit var database: DatabaseReference
    private val LOCATION_AND_CONTACTS = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA
    )
    private val RC_LOCATION_CONTACTS_PERM = 124
    private lateinit var storageRef: StorageReference
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutor_application, container, false)
        username = arguments?.getString("tutorUserName").toString()
        image = view.findViewById(R.id.dropbox)
        name = view.findViewById(R.id.nameHeading)
        headName = arguments?.getString("name").toString()
        name.text = "Hello " + arguments?.getString("name").toString() + ","
        progressDialog = ProgressDialog(requireContext())

        var fromStudent = arguments?.getBoolean("fromStudent")
        if (fromStudent == true){
            name.visibility = View.GONE
        }
        database = FirebaseDatabase.getInstance().reference.child("Transcripts")
        imageStore = FirebaseStorage.getInstance().getReference()
        storageRef = FirebaseStorage.getInstance().reference.child("transcripts/$username.pdf")

        image.setOnClickListener {
            if (hasLocationAndContactsPermission()) {
                val galleryIntent = Intent()
                galleryIntent.type = "application/pdf"
                galleryIntent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(galleryIntent, "Select pdf"),
                    GALLERY_PICK
                )
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "This app needs access to your files.",
                    RC_LOCATION_CONTACTS_PERM,
                    *LOCATION_AND_CONTACTS
                )
            }
        }
        return view
    }

    private fun hasLocationAndContactsPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), *LOCATION_AND_CONTACTS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        username = arguments?.getString("tutorUserName").toString()

        if (resultCode == Activity.RESULT_OK) {
            val resultUri = data?.data
            progressDialog.setMessage("Uploading PDF...")
            progressDialog.show()

            val filepath = imageStore.child("transcripts").child(username +".pdf")

            filepath.putFile(resultUri!!)
                .addOnSuccessListener { task ->
                    val uriTask: Task<Uri> = task.storage.downloadUrl
                    while (!uriTask.isSuccessful);
                    val uploadedPdfUrl = "${uriTask.result}"
                    val update_hashMap = HashMap<String, Any>()
                    update_hashMap["transcript"] = "${uploadedPdfUrl}"
                    database.child(username).setValue(update_hashMap)
                        .addOnSuccessListener {
                            progressDialog.dismiss()

                            val builder = android.app.AlertDialog.Builder(requireContext())
                            builder.setMessage("File Uploaded Successfully.")
                                .setCancelable(false)
                                .setPositiveButton("Yes") { dialog, id ->

                                    var fromStudent = arguments?.getBoolean("fromStudent")
                                    if(fromStudent==true){
                                        val bundle = Bundle()
                                        bundle.putBoolean("fromStudent", true)
                                        bundle.putString("name", headName)
                                        bundle.putString("tutorUserName", username)
                                        Navigation.findNavController(requireView())
                                            .navigate(R.id.action_tutorApplicationFragment2_to_tutorAvailabilityFragment3, bundle)
                                    }
                                    else{
                                        val bundle = Bundle()
                                        bundle.putString("name", headName)
                                        bundle.putString("tutorUserName", username)
                                        Navigation.findNavController(requireView())
                                            .navigate(R.id.action_tutorApplicationFragment_to_tutorAvailabilityFragment2, bundle)
                                    }
                                }
                                .setNegativeButton("No"){ dialog, id ->
                                    dialog.cancel()
                                }
                                .setTitle("Confirm upload of the pdf?")
                            val alert = builder.create()
                            alert.show()
                        }
                }
                .addOnFailureListener{
                    progressDialog.dismiss()
                }
        }
    }
}