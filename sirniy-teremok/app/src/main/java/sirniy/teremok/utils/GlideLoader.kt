package sirniy.teremok.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import sirniy.teremok.R

class GlideLoader(val context: Context) {

    fun loadPicture(imageIri: Uri, imageView: ImageView){
        try{
            Glide.with(context)
                .load(Uri.parse(imageIri.toString()))
                .centerCrop()
                .placeholder(R.drawable.user)
                .into(imageView)
        }catch (ex: Exception){ }
    }

    fun loadPictureString(path: String, imageView: ImageView){
        try{
            Glide.with(context)
                .load(Uri.parse(path))
                .centerCrop()
                .placeholder(R.drawable.product)
                .into(imageView)
        }catch (ex: Exception){ }
    }


    fun loadUserImage(imageIri: String, imageView: ImageView){
        try{
            Glide.with(context)
                .load(Uri.parse(imageIri))
                .centerCrop()
                .placeholder(R.drawable.user)
                .into(imageView)
        }catch (ex: Exception){ }
    }
}