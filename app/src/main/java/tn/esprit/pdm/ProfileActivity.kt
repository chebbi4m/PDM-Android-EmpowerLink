package tn.esprit.pdm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.databinding.ActivityProfilBinding
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.uikotlin.skills.SkillsFragment
import tn.esprit.pdm.uikotlin.login.EditProfileActivity
import tn.esprit.pdm.uikotlin.login.LoginActivity
import tn.esprit.pdm.uikotlin.login.NewsFragment
import tn.esprit.pdm.utils.Apiuser
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class ProfileActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityProfilBinding
    private lateinit var apiUser: Apiuser
    private lateinit var currentPhotoPath: String
    private val REQUEST_TAKE_PHOTO = 1
    private val REQUEST_PICK_IMAGE = 2
    private val REQUEST_STORAGE_PERMISSION = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiUser = Apiuser.create()

        binding.tiedit.setOnClickListener() {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        sessionManager = SessionManager(this)
        val token = sessionManager.getUserName().toString()
        val decodedToken = sessionManager.decodeToken(token)
        binding.tiusername.text = decodedToken.username
        val profileImageUrl = decodedToken.image

// Utiliser Picasso pour charger l'image dans votre ImageView
        if (profileImageUrl != null) {
            if (profileImageUrl.isNotEmpty()) {
                Picasso.get().load(profileImageUrl).into(binding.idUrlImg)
            } else {
                // Si l'URL de l'image n'est pas disponible, vous pouvez afficher une image par défaut ou gérer d'une autre manière.
            }
        }
        // binding
        binding.linkedin.setOnClickListener {
            changeFragment(NewsFragment(), "")
        }
        binding.github.setOnClickListener {
            changeFragment(SkillsFragment(), "")
        }
        binding.btnUpdateProfilePhoto.setOnClickListener() {

        }
        binding.updatephoto.setOnClickListener(){
            selectImage()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, NewsFragment())
            .commit()
    }

    private fun changeFragment(fragment: Fragment, name: String) {
        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .addToBackStack("").commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item2 -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Logout")
                builder.setMessage("Are you sure you want to logout ?")
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    logout()
                }
                builder.setNegativeButton("No") { dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                builder.create().show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        sessionManager = SessionManager(this)
        sessionManager.logout()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun selectImage() {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo")
        builder.setItems(items) { dialog, item ->
            when {
                items[item] == "Take Photo" -> dispatchTakePictureIntent()
                items[item] == "Choose from Gallery" -> dispatchGalleryIntent()
                items[item] == "Cancel" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: Exception) {
                Log.e("ProfileActivity", ex.message ?: "Unknown error")
                null
            }
            photoFile?.also {
                val photoURI: Uri = getUriForFile(it)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    private fun dispatchGalleryIntent() {
        // Vérifiez si vous avez la permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si vous ne l'avez pas, demandez la permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_STORAGE_PERMISSION
            )
        } else {
            // Si vous avez déjà la permission, lancez l'intent de la galerie
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, REQUEST_PICK_IMAGE)
        }
    }

    private fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(
            this,
            "tn.esprit.pdm.fileprovider",
            file
        )
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir("Pictures")
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                // Vérifiez si les permissions ont été accordées
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Les permissions ont été accordées, vous pouvez maintenant accéder au stockage
                    // Réessayez le téléchargement de l'image ici
                    dispatchGalleryIntent()
                } else {
                    // Les permissions n'ont pas été accordées, informez l'utilisateur
                    Toast.makeText(
                        this,
                        "Permissions denied, cannot access storage.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
            // Gérez d'autres demandes de permissions si nécessaire
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_TAKE_PHOTO -> {
                    uploadImage(File(currentPhotoPath))
                }

                REQUEST_PICK_IMAGE -> {
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let { uri ->
                        val selectedImageFile = File(getRealPathFromURI(uri))
                        uploadImage(selectedImageFile)
                    }
                }
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
        val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val imagePath: String = cursor?.getString(columnIndex!!) ?: ""
        cursor?.close()
        return imagePath
    }

    private fun uploadImage(imageFile: File) {
        val userId = "6550bce0a0d1a744ea94d641"
        val requestFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("profilePhoto", imageFile.name, requestFile)

        apiUser.updateProfilePhoto(userId, body).enqueue(object : Callback<LoginRequest> {
            override fun onResponse(call: Call<LoginRequest>, response: Response<LoginRequest>) {
                if (response.isSuccessful) {
                    Log.d("NetworkResponse", "Successful response: ${response.body()}")
                    Toast.makeText(applicationContext, "Image updated successfully", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.d(
                        "NetworkResponse",
                        "Failed response: ${response.code()}, ${response.message()}"
                    )
                    Toast.makeText(applicationContext, "Failed to update image", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<LoginRequest>, t: Throwable) {
                Log.e("NetworkError", "Error: ${t.message}")
                Toast.makeText(applicationContext, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
