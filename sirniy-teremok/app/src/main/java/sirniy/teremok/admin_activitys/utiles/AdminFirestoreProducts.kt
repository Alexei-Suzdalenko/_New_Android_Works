package sirniy.teremok.admin_activitys.utiles
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import sirniy.teremok.admin_activitys.AdminCreateProduct
import sirniy.teremok.admin_activitys.AdminListProducts
import sirniy.teremok.admin_activitys.IndexAdminActivity
import sirniy.teremok.admin_activitys.ui.main.PlaceholderFragment
import sirniy.teremok.utils.Constants
import sirniy.teremok.utils.User

class AdminFirestoreProducts {
    private val fireStore = FirebaseFirestore.getInstance()
    private val listProduct: MutableList<Product> = arrayListOf()
    private val users = FirebaseFirestore.getInstance().collection(Constants.USERS)
    private val firebaseProducts = FirebaseFirestore.getInstance().collection(Constants.PRODUCTS)

    fun saveProduct(activity: AdminCreateProduct, product: Product){
        fireStore.collection(Constants.PRODUCTS)
            .document(product.id)
            .set(product) // SetOptions.merge()
            .addOnSuccessListener {
                activity.gotoListProduct(product.category)
            }
            .addOnFailureListener { e-> }
    }

    fun getListProductFromCategory(product_category: String, activity: AdminListProducts){
        fireStore.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.CATEGORY, product_category).get()
            .addOnSuccessListener { documentSnapshot ->
                for (document in documentSnapshot.documents){
                    val product = document.toObject<Product>()
                    if (product != null) {
                        listProduct.add(product)
                    }
                }
                activity.getMeListProductFromCategory(listProduct)
            }
    }

    fun saveImageProduct(imageUri: Uri, activity: AdminCreateProduct){
        FirebaseStorage.getInstance().reference.child(System.currentTimeMillis().toString() + "." + Constants.getFileExtencion(activity, imageUri))
            .putFile(imageUri)
            .addOnSuccessListener {task ->
                Log.d("none", "=>" + task.metadata!!.reference!!.downloadUrl.toString())
                task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    when(activity){
                        is AdminCreateProduct -> {
                            activity.imageUploadSucess(uri.toString())
                        }
                    }
                }
            }
            .addOnFailureListener {
                when(activity){
                    is AdminCreateProduct -> {
                        activity.hideProgressDialog()
                    }
                }
            }
    }

    fun deleteProductAndImage(product: Product, activity: AdminListProducts){
        FirebaseStorage.getInstance().getReferenceFromUrl(product.image).delete()
        firebaseProducts.document(product.id).delete()
            .addOnSuccessListener {
                activity.finishMeAndOpenMe()
            }
    }

    fun getListUsers(fragment: PlaceholderFragment){
        users.get().addOnSuccessListener { task ->
            val listUsers: MutableList<User> = arrayListOf()
            for (document in task.documents) {
                val user = document.toObject<User>()
                if (user != null) {
                    listUsers.add(user)
                }
            }
            fragment.renewListUserTab(listUsers)
        }
    }

    fun deleteUserData(id: String, activity: IndexAdminActivity){
        users.document(id).delete().addOnSuccessListener {
            activity.refreshPageWhenUserDeleted()
        }
    }



}