package daniellopes.io.app_mobile.extensions

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(
        requireContext(),
        message,
        duration
    ).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun formatForDateBr(date: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale("PT", "BR"))
    val data: Date? = format.parse(date)
    format.applyPattern("dd/MM/yyyy")
    return if (data != null) {
        format.format(data)
    } else {
        ""
    }
}