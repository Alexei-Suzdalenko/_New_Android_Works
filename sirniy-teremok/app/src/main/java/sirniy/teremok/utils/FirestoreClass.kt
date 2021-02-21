package sirniy.teremok.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.App
import sirniy.teremok.first_youtube.LoginActivity
import sirniy.teremok.user.UserProfilEdit

class FirestoreClass {

    fun registerUser(activity: UserProfilEdit, userInfo: User){
        FirebaseFirestore.getInstance().collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo) // SetOptions.merge()
            .addOnSuccessListener {
               // activity.userRegisterSuccess(userInfo)
            }
            .addOnFailureListener { e->
                activity.hideProgressDialog()
            }
    }

    fun getCurrentUserId(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = "jui"
        if(currentUser != null){ currentUserId = currentUser.uid }
        return currentUserId
    }

    fun getUserDetails(activity: Activity){
        Toast.makeText(activity, getCurrentUserId(), Toast.LENGTH_LONG).show()
        FirebaseFirestore.getInstance().collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)!!
                val sharedPreferences: SharedPreferences = activity.getSharedPreferences(Constants.SHARED_TAG, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString(Constants.USER_ID, user.id)
                editor.apply()
                when(activity){
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                when(activity){
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                }
            }
    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>, userId: String, user: User){
        val user_id = App.sharedPreferences.getString(Constants.USER_ID, "none").toString()
        FirebaseFirestore.getInstance().collection(Constants.USERS).document(user_id).update(userHashMap)
            .addOnSuccessListener {
                when(activity){
                    is UserProfilEdit -> {
                      //  activity.userUpdateSuccess(userId, user);
                    }
                }
            }
            .addOnFailureListener { e ->
                when(activity){
                    is UserProfilEdit -> {
                        activity.hideProgressDialog()
                        Toast.makeText(activity.applicationContext, activity.resources.getString(R.string.error_ocurried), Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    fun uploadImagetoCloudStorage(activity: Activity, imageUri: Uri){
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.IMAGE + System.currentTimeMillis() + "." + Constants.getFileExtencion(activity, imageUri)
        )
        sRef.putFile(imageUri)
            .addOnSuccessListener {task ->
                Log.d("none", "=>" + task.metadata!!.reference!!.downloadUrl.toString())
                task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    when(activity){
                        is UserProfilEdit -> {
                          //  activity.imageUploadSucess(uri.toString())
                        }
                    }
                }
            }
            .addOnFailureListener {
                when(activity){
                    is UserProfilEdit -> {
                        activity.hideProgressDialog()
                    }
                }
            }
    }


}









