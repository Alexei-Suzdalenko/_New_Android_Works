package sirniy.teremok.utils
import android.net.Uri
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.App
import sirniy.teremok.user.UserProfilEdit
class FirestoreUser{

    fun uploadUserImage(activity: UserProfilEdit, imageUri: Uri){
        activity.showProgressDialog(activity.resources.getString(R.string.please_wait))
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(Constants.IMAGE + System.currentTimeMillis() + "." + Constants.getFileExtencion(activity, imageUri))
        sRef.putFile(imageUri)
            .addOnSuccessListener {task ->
               // Log.d("none", "=>" + task.metadata!!.reference!!.downloadUrl.toString())
                task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    activity.imageUploadRegisterSuccess(uri.toString())
                }
            }
            .addOnFailureListener {
                   activity.hideProgressDialog()
            }
    }


    fun registerNewUser(activity: UserProfilEdit, user: User){
        activity.showProgressDialog(activity.resources.getString(R.string.please_wait))
        FirebaseFirestore.getInstance().collection(Constants.USERS).document(user.id).set(user)
            .addOnSuccessListener {
                activity.newUserRegisterSuccess(user)
            }
            .addOnFailureListener { e->
                activity.hideProgressDialog()
            }
    }


    fun uploadUserImageAndDeleteOldImage(activity: UserProfilEdit, selectedImageUri: Uri, userImage: String?){
        FirebaseStorage.getInstance().getReferenceFromUrl(userImage.toString()).delete()

        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(Constants.IMAGE + System.currentTimeMillis() + "." + Constants.getFileExtencion(activity, selectedImageUri))
        sRef.putFile(selectedImageUri)
            .addOnSuccessListener { task ->
                task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    activity.imageRefreshDataOldUser(uri.toString())
                }
            }
            .addOnFailureListener {
                activity.hideProgressDialog()
            }
    }

    fun updateUserData(activity: UserProfilEdit, userHashMap: HashMap<String, Any>){
        val userId = App.sharedPreferences.getString(Constants.USER_ID, "").toString()
        FirebaseFirestore.getInstance().collection(Constants.USERS).document(userId).update(userHashMap)
            .addOnSuccessListener {
                activity.hideProgressDialog()
                activity.refreshActivity(userHashMap)
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Toast.makeText(activity.applicationContext, activity.resources.getString(R.string.error_ocurried), Toast.LENGTH_LONG).show()
            }
    }




}
