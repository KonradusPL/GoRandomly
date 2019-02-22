package com.konradpekala.randomdestination.utils

import android.os.Handler
import android.os.SystemClock
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker



object MapUtils {
    fun animateMarkerToGB(marker: Marker, finalPosition: LatLng, latLngUtils: LatLngUtils) {
        val startPosition = marker.position
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val interpolator = AccelerateDecelerateInterpolator()
        val durationInMs = 2000f

        handler.post(object : Runnable {
            var elapsed = 0L
            var t= 0f
            var v= 0f

            override fun run() {
                elapsed = SystemClock.uptimeMillis() - start
                t = elapsed / durationInMs
                v = interpolator.getInterpolation(t)

                marker.position = latLngUtils.interpolate(v, startPosition, finalPosition)

                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16)
                }
            }
        })
    }

    fun animateCircleToGB(circle: Circle, finalPosition: LatLng, latLngUtils: LatLngUtils) {
        val startPosition = circle.center
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val interpolator = AccelerateDecelerateInterpolator()
        val durationInMs = 2000f

        handler.post(object : Runnable {
            var elapsed = 0L
            var t= 0f
            var v= 0f

            override fun run() {
                elapsed = SystemClock.uptimeMillis() - start
                t = elapsed / durationInMs
                v = interpolator.getInterpolation(t)

                circle.center = latLngUtils.interpolate(v, startPosition, finalPosition)

                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16)
                }
            }
        })
    }
}