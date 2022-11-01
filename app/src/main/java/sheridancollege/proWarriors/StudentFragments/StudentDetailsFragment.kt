package sheridancollege.proWarriors.StudentFragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import id.zelory.compressor.Compressor
//import io.grpc.Compressor
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import sheridancollege.proWarriors.R
import sheridancollege.proWarriors.Student.Student
import sheridancollege.proWarriors.Student.StudentActivity
import sheridancollege.proWarriors.Student.StudentEntity
import sheridancollege.proWarriors.Student.stu
import java.io.ByteArrayOutputStream
import java.io.File


class StudentDetailsFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private lateinit var username:String
    private val GALLERY_PICK = 1
    lateinit var imageStore:StorageReference
    private lateinit var database: DatabaseReference
    private lateinit var mImage:ImageView
    private val LOCATION_AND_CONTACTS = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
    private val RC_LOCATION_CONTACTS_PERM = 124
    private lateinit var storageRef: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        val user = Firebase.auth.currentUser

        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }

        database = FirebaseDatabase.getInstance().reference
        imageStore = FirebaseStorage.getInstance().getReference()
        storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$username.jpg")


        var fullName = view.findViewById<TextView>(R.id.fName)
        var first = view.findViewById<TextView>(R.id.firstName)
        var phone = view.findViewById<TextView>(R.id.cNumber)
        var add = view.findViewById<TextView>(R.id.address)
        var id = view.findViewById<TextView>(R.id.email)
        var idInfo = view.findViewById<TextView>(R.id.emailID)
        var back = view.findViewById<TextView>(R.id.backToHome)
        mImage = view.findViewById<ImageView>(R.id.profileImage)

        back.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_studentDetailsFragment_to_studentHomeFragment)
        }

        mImage.setOnClickListener {
            if(hasLocationAndContactsPermission()){
                val galleryIntent = Intent()
                galleryIntent.type = "image/*"
                galleryIntent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_PICK)

            }else{
                EasyPermissions.requestPermissions(
                    this,
                    "This app needs access to your camera.",
                    RC_LOCATION_CONTACTS_PERM,
                    *LOCATION_AND_CONTACTS)
                }
        }

        StudentEntity.getStudentDetails(username)
        var student: Student = stu.student
        if (student != null) {
            first.text = student.firstName
            fullName.text = student.firstName + " " + student.lastName
            phone.text = student.phoneNo
            add.text = student.address
            id.text = student.email
            idInfo.text = student.email
            if(student.isTutor==false){
                view.findViewById<Button>(R.id.setAvailability).visibility = View.GONE
            }
        }
        else{
            Toast.makeText(activity,"Student info not found. Please try again later.", Toast.LENGTH_SHORT).show()
        }

        val localFile = File.createTempFile("tempImage", "jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            mImage.setImageBitmap(bitmap)

        }

        //setHasOptionsMenu(false)
        return view
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.student_details_menu, menu)
    }


    private fun hasLocationAndContactsPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), *LOCATION_AND_CONTACTS)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GALLERY_PICK && resultCode == Activity.RESULT_OK){
            val imageUri = data?.data

            CropImage.activity(imageUri)
                .setAspectRatio(1,1)
                .setMinCropWindowSize(500,500)
                .start(requireContext(), this)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK){
                val resultUri = result.uri
                val thumb_bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, resultUri)
                val baos = ByteArrayOutputStream()
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val thumb_byte = baos.toByteArray()
                val filepath = imageStore.child("profile_images").child(username + ".jpg")
                val thumb_filepath = imageStore.child("profile_images").child("Thumbs").child(username + ".jpg")

                filepath.putFile(resultUri).addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        Log.d("HELLO", "inside first")
                        val download_url = task.result?.storage?.downloadUrl.toString()
                        val uploadTask = thumb_filepath.putBytes(thumb_byte)

                        uploadTask.addOnCompleteListener{
                            val thumb_downUrl = it.result?.storage?.downloadUrl.toString()
                            if(it.isSuccessful){
                                Log.d("HELLO", "inside second")
                                val update_hashMap = HashMap<String, Any>()
                                update_hashMap.put("image", download_url)
                                update_hashMap.put("thumb_image", thumb_downUrl)
                                database.child(username).setValue(update_hashMap)
                            }
                            else{
                                Toast.makeText(requireContext(), "Error uploading profile feature.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else{
                        Toast.makeText(requireContext(), "Error uploading profile feature.", Toast.LENGTH_SHORT).show()
                    }
                }
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                val error = result.error
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}