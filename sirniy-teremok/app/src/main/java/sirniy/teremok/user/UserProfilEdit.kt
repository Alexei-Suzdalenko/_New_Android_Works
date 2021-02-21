package sirniy.teremok.user
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_user_profil.*
import kotlinx.android.synthetic.main.activity_user_profil.user_name_detail
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.App
import sirniy.teremok.first_youtube.BaseActivity
import sirniy.teremok.utils.*
import java.io.IOException
class UserProfilEdit : BaseActivity() { //https://youtu.be/777T2EyM6Zw yay joy
    private var selectedImageUri: Uri? = null
    private var userProfileImageUrlThenImageUpload: String = ""
    var userId: String? = App.sharedPreferences.getString(Constants.USER_ID, null)
    var userImage: String? = App.sharedPreferences.getString(Constants.IMAGE, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profil)

        if(!userId.isNullOrEmpty()){
            company_user_data.setText(App.sharedPreferences.getString(Constants.COMPANY, ""))
            user_name_detail.setText(App.sharedPreferences.getString(Constants.NAME, ""))
            tlf_user_edit_data.setText(App.sharedPreferences.getString(Constants.TLF, ""))
        }
        if(!userImage.isNullOrEmpty()){
            GlideLoader(this).loadUserImage(userImage.toString(), tv_user_photo_data)
        }



     tv_user_photo_data.setOnClickListener {
         if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
             showErrorSnackBar(resources.getString(R.string.you_are_have_permission_to_read), false)
             Constants.showImageChooser(this)
         } else {
             ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_STORAGE_CODE)
         }
     }

      btn_submit_data.setOnClickListener {
            if(userId.isNullOrEmpty()){
                registerNewUser()
            } else {
                updateOldUser()
            }
      }

    }

    private fun registerNewUser(){
        if(ifUserFildsIsCompleted()){
            if(selectedImageUri.toString().length > 11){ // if we get a image
                FirestoreUser().uploadUserImage(this, selectedImageUri!!)
            } else {
                val user = User(System.currentTimeMillis().toString(), company_user_data.text.toString(), user_name_detail.text.toString(), tlf_user_edit_data.text.toString(), "")
                FirestoreUser().registerNewUser(this, user)
            }
        }
    }


    private fun updateOldUser(){
        if(ifUserFildsIsCompleted()){
            if(selectedImageUri.toString().length > 11){ // if we get a image
                showProgressDialog("")
                FirestoreUser().uploadUserImageAndDeleteOldImage(this, selectedImageUri!!, userImage)
            } else {
                updateUserDetails()
            }
        }
    }

    fun ifUserFildsIsCompleted(): Boolean{
        if(company_user_data.text.toString().length > 2 && user_name_detail.text.toString().length > 2 && tlf_user_edit_data.text.toString().length > 2 ){
            return  true
        } else {
            Toast.makeText(this, resources.getString(R.string.need_datas), Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun imageUploadRegisterSuccess(urlImage: String){
        hideProgressDialog()
        val user = User(System.currentTimeMillis().toString(), company_user_data.text.toString(), user_name_detail.text.toString(), tlf_user_edit_data.text.toString(), urlImage)
        FirestoreUser().registerNewUser(this, user)
    }

    fun newUserRegisterSuccess(user: User){
        val editor: SharedPreferences.Editor = App.sharedPreferences.edit()
            editor.putString(Constants.USER_ID, user.id)
            editor.putString(Constants.COMPANY, user.company)
            editor.putString(Constants.NAME, user.name)
            editor.putString(Constants.TLF, user.tlf)
            editor.putString(Constants.IMAGE, user.image)
            editor.apply()
        hideProgressDialog()
        Toast.makeText(this, resources.getString(R.string.saved), Toast.LENGTH_LONG).show()
        startActivity(Intent(this, UserProfil::class.java))
        finish()
    }

    fun imageRefreshDataOldUser(url: String){
        val editor: SharedPreferences.Editor = App.sharedPreferences.edit()
        editor.putString(Constants.IMAGE, url)
        editor.apply()
        hideProgressDialog()
        updateUserDetails()
    }


    private fun updateUserDetails(){
        val userHashMap = HashMap<String, Any>()
        userHashMap[Constants.COMPANY] =  company_user_data.text.toString()
        userHashMap[Constants.NAME] = user_name_detail.text.toString()
        userHashMap[Constants.TLF] = tlf_user_edit_data.text.toString()
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreUser().updateUserData(this, userHashMap)
    }


    fun refreshActivity(userHashMap: HashMap<String, Any>){
        val editor = App.sharedPreferences.edit()
            editor.putString(Constants.COMPANY, userHashMap.get(Constants.COMPANY).toString())
            editor.putString(Constants.NAME, userHashMap.get(Constants.NAME).toString())
            editor.putString(Constants.TLF, userHashMap.get(Constants.TLF).toString())
            editor.apply()
        startActivity(Intent(this, UserProfilEdit::class.java))
        finish()
    }







    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showErrorSnackBar(resources.getString(R.string.you_are_have_permission_to_read), false)
                Constants.showImageChooser(this)
            } else {
                showErrorSnackBar(resources.getString(R.string.you_dont_have), true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if(data != null){
                    try {
                        selectedImageUri = data.data!!
                        GlideLoader(this).loadPicture(selectedImageUri!!, tv_user_photo_data)
                    } catch (ex: IOException){
                        showErrorSnackBar(ex.message.toString(), true)
                    }
                }
            }
        }
    }

  //  private fun updateUserDetails(userId: String){
  //      var user: User
  //      val userHashMap = HashMap<String, Any>()
  //      userHashMap[Constants.COMPANY] =  company_user_data.text.toString()
  //      userHashMap[Constants.NAME] = user_name_detail.text.toString()
  //      userHashMap[Constants.TLF] = tlf_user_edit_data.text.toString()
  //      user = User(userId, company_user_data.text.toString(), user_name_detail.text.toString(),  tlf_user_edit_data.text.toString(), "")
  //      if(userProfileImageUrlThenImageUpload.isNotEmpty()){
  //          userHashMap[Constants.IMAGE] = userProfileImageUrlThenImageUpload
  //          user = User(userId, company_user_data.text.toString(), user_name_detail.text.toString(),  tlf_user_edit_data.text.toString(), userProfileImageUrlThenImageUpload)
  //      }
  //      showProgressDialog(resources.getString(R.string.please_wait))
  //      FirestoreClass().updateUserProfileData(this, userHashMap, userId, user)
  //  }
//
  //  fun registerUser(){
  //      val user = User( System.currentTimeMillis().toString(),
  //          company_user_data.text.toString(),
  //          user_name_detail.text.toString(),
  //          tlf_user_edit_data.text.toString(),
  //          userProfileImageUrlThenImageUpload )
  //      FirestoreClass().registerUser(this, user)
  //  }
//
//
  //  fun imageUploadSucess(imageUrl: String){
  //      hideProgressDialog()
  //      userProfileImageUrlThenImageUpload = imageUrl
  //      registerUser()
  //  }
//
  //  fun userRegisterSuccess(user: User){
  //      val editor: SharedPreferences.Editor = App.sharedPreferences.edit()
  //          editor.putString(Constants.IS_USER, Constants.USER)
  //          editor.putString(Constants.USER_ID, user.id)
  //          editor.putString(Constants.COMPANY, user.company)
  //          editor.putString(Constants.NAME, user.name)
  //          editor.putString(Constants.TLF, user.tlf)
  //          editor.putString(Constants.IMAGE, user.image)
  //          editor.apply()
  //      hideProgressDialog()
  //      Toast.makeText(this, resources.getString(R.string.saved), Toast.LENGTH_LONG).show()
  //      startActivity(Intent(this, TabTopActivity::class.java))
  //      finish()
  //  }
//
  //  fun userUpdateSuccess(userId: String, user: User){
  //      val editor: SharedPreferences.Editor = App.sharedPreferences.edit()
  //      editor.putString(Constants.IS_USER, userId)
  //      editor.putString(Constants.USER_ID, user.id)
  //      editor.putString(Constants.COMPANY, user.company)
  //      editor.putString(Constants.NAME, user.name)
  //      editor.putString(Constants.TLF, user.tlf)
  //      editor.putString(Constants.IMAGE, user.image)
  //      editor.apply()
  //      hideProgressDialog()
  //      Toast.makeText(this, resources.getString(R.string.saved), Toast.LENGTH_LONG).show()
  //      startActivity(Intent(this, TabTopActivity::class.java))
  //      finish()
  //  }
//
  //  fun userUpadatedSuccess(){
  //      hideProgressDialog()
  //      Toast.makeText(this, resources.getString(R.string.saved), Toast.LENGTH_LONG).show()
  //      startActivity(Intent(this, TabTopActivity::class.java))
  //      finish()
  //  }

}