package com.lucasrodrigues.pokemonshowcase.framework.impl

import android.app.Activity
import com.lucasrodrigues.pokemonshowcase.framework.AlertService
import com.lucasrodrigues.pokemonshowcase.framework.ResourceService
import com.tapadoo.alerter.Alerter
import java.lang.ref.WeakReference

class AlertServiceImpl(
    activity: Activity,
    private val resourceService: ResourceService
) : AlertService {
    private val activity = WeakReference(activity)

    private fun hideLoadingAlert() {
        activity.get()?.let { activity ->
            activity.runOnUiThread {
                if (Alerter.isShowing)
                    Alerter.hide()
            }
        }
    }

    override fun sendErrorAlert(message: String) {
        hideLoadingAlert()

        activity.get()?.let { activity ->
            activity.runOnUiThread {
                Alerter.create(activity)
                    .setText(message)
                    .setDuration(5000)
                    .setBackgroundColorRes(android.R.color.holo_red_dark)
                    .enableSwipeToDismiss()
                    .show()
            }
        }
    }

    override fun sendErrorAlert(error: Throwable) {
        hideLoadingAlert()

        activity.get()?.let { activity ->
            activity.runOnUiThread {
                Alerter.create(activity)
                    .setText(resourceService.getErrorMessage(error))
                    .setDuration(5000)
                    .setBackgroundColorRes(android.R.color.holo_red_dark)
                    .enableSwipeToDismiss()
                    .show()
            }
        }
    }

    override fun sendSuccessAlert(message: String) {
        hideLoadingAlert()

        activity.get()?.let { activity ->
            activity.runOnUiThread {
                Alerter.create(activity)
                    .setText(message)
                    .setDuration(5000)
                    .setBackgroundColorRes(android.R.color.holo_green_dark)
                    .enableSwipeToDismiss()
                    .show()
            }
        }
    }

    override fun sendInfoAlert(message: String) {
        hideLoadingAlert()

        activity.get()?.let { activity ->
            activity.runOnUiThread {
                Alerter.create(activity)
                    .setText(message)
                    .setDuration(5000)
                    .setBackgroundColorRes(android.R.color.holo_blue_light)
                    .enableSwipeToDismiss()
                    .show()
            }
        }
    }
}