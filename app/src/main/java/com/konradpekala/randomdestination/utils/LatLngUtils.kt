package com.konradpekala.randomdestination.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import java.util.*

object LatLngUtils {
    fun interpolate(fraction: Float, a: LatLng, b: LatLng): LatLng {
        val lat = (b.latitude - a.latitude) * fraction + a.latitude
        var lngDelta = b.longitude - a.longitude

        // Take the shortest path across the 180th meridian.
        if (Math.abs(lngDelta) > 180) {
            lngDelta -= Math.signum(lngDelta) * 360
        }
        val lng = lngDelta * fraction + a.longitude
        return LatLng(lat, lng)
    }

    fun getRandomLocation(point: LatLng, radius: Int): LatLng {
        //https://stackoverflow.com/questions/33976732/generate-random-latlng-given-device-location-and-radius xD
        val myLocation = Location("")
        myLocation.latitude = point.latitude
        myLocation.longitude = point.longitude


        val x0 = point.latitude
        val y0 = point.longitude

        val random = Random()

        // Convert radius from meters to degrees
        val radiusInDegrees = (radius / 111000f).toDouble()

        val u = random.nextDouble()
        val v = random.nextDouble()
        val w = radiusInDegrees * Math.sqrt(u)
        val t = 2.0 * Math.PI * v
        val x = w * Math.cos(t)
        val y = w * Math.sin(t)

        // Adjust the x-coordinate for the shrinking of the east-west distances
        val new_x = x / Math.cos(y0)

        val foundLatitude = new_x + x0
        val foundLongitude = y + y0
        val randomLatLng = LatLng(foundLatitude, foundLongitude)

        return randomLatLng
    }

    fun getDistanceInMeters(l1: LatLng, l2: LatLng): Float{
        val distance = floatArrayOf(0f,0f)
        Location.distanceBetween(l1.latitude,l1.longitude,l2.latitude,l2.longitude,distance)
        return distance[0]
    }

}