package sirniy.teremok.admin_activitys.utiles
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import sirniy.teremok.admin_activitys.IndexAdminActivity
import sirniy.teremok.admin_activitys.ui.main.PlaceholderFragment
import sirniy.teremok.admin_activitys.utiles.App.Companion.reference

class AdminFirebaseCategory{

    fun saveOneCategory(category: String){
        val key: String? = reference.push().key
        if (key != null) {
            reference.child(key).setValue(category).addOnSuccessListener { it ->
                // activity.loging("asdf")
            }
        }
    }


    fun deleteCategory(id: String, activity: IndexAdminActivity){
        reference.child(id).removeValue().addOnSuccessListener {
           //  activity.gotoAdminCreateCategories()
        }
    }


   fun getMeListCategories(fragment: PlaceholderFragment) {
       val postListener = object : ValueEventListener {
           override fun onDataChange(dataSnapshot: DataSnapshot) {
               val listCategories: MutableList<Category> = arrayListOf()
               for(data in dataSnapshot.children){
                   val key = data.key.toString()
                   val name = data.getValue<String>().toString()
                   val category = Category(key, name)
                   listCategories.add(category)
               }
               fragment.getMelistCategoriesReturn(listCategories)
           }
           override fun onCancelled(databaseError: DatabaseError) {
               // Getting Post failed, log a message
               Log.d("tag", "loadPost:onCancelled", databaseError.toException())
           }
       }
       reference.addValueEventListener(postListener)
   }


}





































