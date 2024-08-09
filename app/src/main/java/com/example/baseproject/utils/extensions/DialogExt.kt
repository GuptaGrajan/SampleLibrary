package com.example.baseproject.utils.extensions


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.CountDownTimer
import android.provider.Settings
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.baseproject.R
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.baseproject.utils.Const
import java.util.*



fun View.openTimePickerDialog(
    calendar: Calendar = Calendar.getInstance(),
    is24Hour: Boolean = false,
    onTimeSelect: (calendar: Calendar) -> Unit
) {
    val timePickerDialog = TimePickerDialog(
        this.context, { view, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            onTimeSelect(calendar)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24Hour
    )

    timePickerDialog.show()
    timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setBackgroundResource(0)
    timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE)
        .setTextColor(ContextCompat.getColor(this.context, R.color.colorPrimary))
    timePickerDialog.getButton(Dialog.BUTTON_POSITIVE).setBackgroundResource(0)
    timePickerDialog.getButton(Dialog.BUTTON_POSITIVE)
        .setTextColor(ContextCompat.getColor(this.context, R.color.colorPrimary))

}



fun showLocationSettingsAlert(mContext: Context) {
    var locationAlertDialog: AlertDialog? = null
    try {
        if (!(mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            ) && !(mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
        ) {

            val alertDialogPrepare = AlertDialog.Builder(mContext)

            // Setting Dialog Title
            alertDialogPrepare.setTitle(mContext.resources.getString(R.string.location_setting))
            alertDialogPrepare.setCancelable(false)

            // Setting Dialog Message
            alertDialogPrepare.setMessage(mContext.resources.getString(R.string.location_setting_enable_message))

            // On pressing Settings button
            alertDialogPrepare.setPositiveButton(
                "Settings"
            ) { dialog, which ->
                dialog.dismiss()
                try {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    mContext.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            locationAlertDialog = alertDialogPrepare.create()
            if (locationAlertDialog != null && locationAlertDialog.isShowing) {
                locationAlertDialog.dismiss()
            }
            // Showing Alert Message
            locationAlertDialog.show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }


}

/**
 * Function to show settings alert dialog
 * On pressing Settings button will lauch Settings Options
 */



