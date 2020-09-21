package com.mehranj73.moviedb.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.mehranj73.moviedb.R
import com.mehranj73.moviedb.util.MessageType
import com.mehranj73.moviedb.util.Response
import com.mehranj73.moviedb.util.StateMessageCallback
import com.mehranj73.moviedb.util.UIComponentType

private const val TAG = "BaseActivity"

abstract class BaseActivity : AppCompatActivity(), UICommunicationListener {

    private var dialogInView: MaterialDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    ) {
        when (response.uiComponentType) {

            is UIComponentType.Toast -> {
                response.message?.let {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                    stateMessageCallback.removeMessageFromStack()
                }
            }

            is UIComponentType.Dialog -> {
                displayDialog(
                    response = response,
                    stateMessageCallback = stateMessageCallback
                )

            }

            is UIComponentType.None -> {
                // This would be a good place to send to your Error Reporting
                // software of choice (ex: Firebase crash reporting)
                Log.i(TAG, "onResponseReceived: ${response.message}")
                stateMessageCallback.removeMessageFromStack()
            }
        }
    }

    abstract override fun displayProgressBar(isLoading: Boolean)

    private fun displayDialog(
        response: Response,
        stateMessageCallback: StateMessageCallback
    ) {
        Log.d(TAG, "displayDialog: ")
        response.message?.let { message ->
            dialogInView = when (response.messageType) {

                is MessageType.Error -> {
                    displayErrorDialog(
                        message = message,
                        stateMessageCallback = stateMessageCallback
                    )
                }

                else -> {
                    // do nothing
                    stateMessageCallback.removeMessageFromStack()
                    null
                }
            }
        } ?: stateMessageCallback.removeMessageFromStack()
    }

    override fun onPause() {
        super.onPause()
        if (dialogInView != null) {
            (dialogInView as MaterialDialog).dismiss()
            dialogInView = null
        }
    }

    private fun displayErrorDialog(
        message: String?,
        stateMessageCallback: StateMessageCallback
    ): MaterialDialog {
        return MaterialDialog(this)
            .show {
                title(R.string.text_error)
                message(text = message)
                positiveButton(R.string.text_ok) {
                    stateMessageCallback.removeMessageFromStack()
                    dismiss()
                }
                onDismiss {
                    dialogInView = null
                }
                cancelable(false)
            }
    }
}