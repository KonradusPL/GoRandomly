package com.konradpekala.randomdestination.utils

import android.content.Context
import android.graphics.*
import com.konradpekala.randomdestination.R
import android.graphics.BitmapShader
import android.graphics.Bitmap



object BitmapUtils {
    var mBitmapMarkerUser: Bitmap? = null
    var mBitmapMarkerDest: Bitmap? = null


    fun getUserMarkerBitmap(context: Context, size: Int): Bitmap{
        if(mBitmapMarkerUser == null){
            val base = createBaseBitmap(context,R.drawable.user_holder)
            val circleBitmap = toCircle(base)
            mBitmapMarkerUser = getResizedBitmap(circleBitmap,size,size)
            return mBitmapMarkerUser!!

        }
        else
            return mBitmapMarkerUser!!
    }

    fun getDestinationMarkerBitmap(context: Context, size: Int): Bitmap{
        if(mBitmapMarkerDest == null){
            val base = createBaseBitmap(context,R.drawable.flag)
            val circleBitmap = toCircle(base)
            mBitmapMarkerDest = getResizedBitmap(circleBitmap,size,size)
            return mBitmapMarkerDest!!
        }
        else
            return mBitmapMarkerDest!!
    }

    private fun createBaseBitmap(context: Context, resource: Int): Bitmap{
       return BitmapFactory.decodeResource(context.resources, resource)
    }

    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height

        val matrix = Matrix()

        matrix.postScale(scaleWidth, scaleHeight)

        val resizedBitmap = Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false)
        //bm.recycle()
        return resizedBitmap
    }

    fun toCircle(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)

        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            //source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)

        //squaredBitmap.recycle()
        return bitmap
    }
}