package com.example.agrokrishiseva.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.agrokrishiseva.R
import java.io.ByteArrayOutputStream
import java.io.InputStream

object ImageUtils {
    
    fun loadProfileImage(context: Context, imageView: ImageView, imageUrl: String?) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_home)
            .error(R.drawable.ic_home)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
        
        if (imageUrl.isNullOrEmpty()) {
            // Show default profile icon
            imageView.setImageResource(R.drawable.ic_home)
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imageView.setPadding(16, 16, 16, 16)
        } else {
            // Load profile image from URL
            Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(0, 0, 0, 0)
        }
    }
    
    fun compressImage(context: Context, imageUri: Uri, maxSizeKB: Int = 500): ByteArray? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            if (originalBitmap == null) return null
            
            // Calculate new dimensions to maintain aspect ratio
            val maxDimension = 800 // Max width or height
            val ratio = minOf(
                maxDimension.toFloat() / originalBitmap.width,
                maxDimension.toFloat() / originalBitmap.height
            )
            
            val newWidth = (originalBitmap.width * ratio).toInt()
            val newHeight = (originalBitmap.height * ratio).toInt()
            
            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)
            
            // Compress to desired size
            var quality = 90
            var compressedData: ByteArray
            
            do {
                val outputStream = ByteArrayOutputStream()
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                compressedData = outputStream.toByteArray()
                quality -= 10
            } while (compressedData.size > maxSizeKB * 1024 && quality > 10)
            
            originalBitmap.recycle()
            resizedBitmap.recycle()
            
            compressedData
        } catch (e: Exception) {
            null
        }
    }
    
    fun isValidImageUri(context: Context, uri: Uri): Boolean {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()
            
            options.outWidth > 0 && options.outHeight > 0
        } catch (e: Exception) {
            false
        }
    }
}