package sirniy.teremok.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {
    val USER: String = "user"
    val IS_USER: String = "is_user"
    val POSITION: String = "position"
    const val YES: String = "yes"
    const val IS_ADMIN: String = "is_admin"
    const val CATEGORY: String = "category"
    const val PRODUCTS: String = "products"
    const val CATEGORIES: String = "categories"

    const val SHARED_TAG: String = "tag"
    const val USERS: String = "users"
    const val USER_ID: String = "user_id"
    const val USER_DETAIL: String = "user_detail"
    const val READ_STORAGE_CODE: Int = 2
    const val PICK_IMAGE_REQUEST_CODE: Int = 11

    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    const val EMAILADMIN: String = "emailadmin"
    const val PASSWORDADMIN: String = "passwordadmin"
    const val NONE: String = "none"
    const val COMPANY: String = "company"
    const val NAME: String = "name"
    const val TLF: String = "tlf"
    const val PROFILECOMPLETED: String = "profileCompleted"
    const val IMAGE: String = "image"

    fun getFileExtencion(activity: Activity, uri: Uri?): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

    const val CATEGORY_NAME: String = "category_name"
    const val CATEGORY_ID: String = "category_id"
    const val OBJECT: String = "object"
}