package com.example.onlineshop.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.onlineshop.R

class ProfileFragment : Fragment() {

    private lateinit var ivProfilePicture: ImageView
    private lateinit var btnChangeProfilePicture: Button
    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etHomeAddress: EditText
    private lateinit var btnUpdateProfile: Button
    private lateinit var btnCancel: Button

    // Activity result launcher for image selection
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                ivProfilePicture.setImageURI(selectedImageUri) // Set the selected image to ImageView
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture)
        btnChangeProfilePicture = view.findViewById(R.id.btnChangeProfilePicture)
        etFullName = view.findViewById(R.id.etFullName)
        etEmail = view.findViewById(R.id.etEmail)
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber)
        etHomeAddress = view.findViewById(R.id.etHomeAddress)
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile)
        btnCancel = view.findViewById(R.id.btnCancel)

        // Set click listeners
        btnChangeProfilePicture.setOnClickListener {
            openGalleryForImage()
        }

        btnUpdateProfile.setOnClickListener {
            // Handle update profile logic here
            val fullName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val homeAddress = etHomeAddress.text.toString()

            // Perform update profile action
            updateProfile(fullName, email, phoneNumber, homeAddress)
        }

        btnCancel.setOnClickListener {
            // Handle cancel action
            requireActivity().supportFragmentManager.popBackStack() // Close the fragment
        }

        return view
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun updateProfile(fullName: String, email: String, phoneNumber: String, homeAddress: String) {
        // Implement your update profile logic here
        // For example, you can send this data to a server or save it locally
    }
}