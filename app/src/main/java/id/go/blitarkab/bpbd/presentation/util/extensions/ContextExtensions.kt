package id.go.blitarkab.bpbd.presentation.util.extensions

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.OpenableColumns
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import id.go.blitarkab.bpbd.R
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

//region Context
fun Context.uriToFile(uri: Uri): File? {
    val returnCursor = contentResolver.query(uri, null, null, null, null)
    return if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val queryName = returnCursor.getString(nameIndex)
        returnCursor.close()

        val destinationFile = File(filesDir.path + File.separatorChar + queryName)
        try {
            val inputStream = contentResolver.openInputStream(uri) ?: throw NullPointerException()
            val outputStream = FileOutputStream(destinationFile)
            val buffer = ByteArray(4096)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            destinationFile
        } catch (ex: Exception) {
            Timber.e(ex)
            null
        }
    } else null
}

fun Context.colorOf(@ColorRes colorRes: Int): Int {
    return ResourcesCompat.getColor(resources, colorRes, theme)
}

fun Context.colorAttrOf(@AttrRes attrRes: Int): Int {
    val value = TypedValue()
    theme.resolveAttribute(attrRes, value, true)
    return value.data
}

fun Context.colorStateListOf(@ColorRes colorRes: Int): ColorStateList {
    return ColorStateList.valueOf(ResourcesCompat.getColor(resources, colorRes, theme))
}

fun Context.colorStateListFrom(color: Int): ColorStateList {
    return ColorStateList.valueOf(color)
}

fun Context.colorStateListFromAttrOf(@AttrRes attrRes: Int): ColorStateList {
    return colorStateListFrom(colorAttrOf(attrRes))
}

fun Context.drawableOf(@DrawableRes drawableRes: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, drawableRes, theme)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(messageRes: Int) {
    Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showLongToast(messageRes: Int) {
    Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show()
}

fun Context.showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun Context.showSnackbar(view: View, messageRes: Int) {
    Snackbar.make(view, messageRes, Snackbar.LENGTH_SHORT).show()
}

fun Context.showLongSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}

fun Context.showLongSnackbar(view: View, messageRes: Int) {
    Snackbar.make(view, messageRes, Snackbar.LENGTH_LONG).show()
}

fun Context.showIndefiniteSnackbar(
    view: View,
    message: String,
    actionRes: Int = R.string.action_close,
    actionClick: () -> Unit = { }
) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
    snackbar.setAction(actionRes) {
        snackbar.dismiss()
        actionClick()
    }
    snackbar.show()
}

fun Context.showIndefiniteSnackbar(
    view: View,
    messageRes: Int,
    actionRes: Int = R.string.action_close,
    actionClick: () -> Unit = { }
) {
    val snackbar = Snackbar.make(view, messageRes, Snackbar.LENGTH_INDEFINITE)
    snackbar.setAction(actionRes) {
        snackbar.dismiss()
        actionClick()
    }
    snackbar.show()
}

fun Context.showMessageDialog(message: String): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setMessage(message)
        .setPositiveButton(R.string.action_close) { dialog, _ -> dialog.dismiss() }
        .show()
}

fun Context.showMessageDialog(@StringRes messageRes: Int): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setMessage(messageRes)
        .setPositiveButton(R.string.action_close) { dialog, _ -> dialog.dismiss() }
        .show()
}

fun Context.showActionDialog(
    message: String,
    buttonRes: Int = R.string.action_retry,
    action: () -> Unit
): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setCancelable(false)
        .setMessage(message)
        .setPositiveButton(buttonRes) { dialog, _ ->
            dialog.dismiss()
            action()
        }
        .show()
}

fun Context.showActionDismissableDialog(
    message: String,
    buttonRes: Int = R.string.action_retry,
    action: () -> Unit
): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setCancelable(true)
        .setMessage(message)
        .setPositiveButton(buttonRes) { dialog, _ ->
            dialog.dismiss()
            action()
        }
        .show()
}

fun Context.showConfirmationDialog(message: String, onConfirm: () -> Unit): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setMessage(message)
        .setPositiveButton(R.string.action_yes) { _, _ -> onConfirm() }
        .setNegativeButton(R.string.action_no) { dialog, _ -> dialog.dismiss() }
        .show()
}

fun Context.showConfirmationDialog(@StringRes messageRes: Int, onConfirm: () -> Unit): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setMessage(messageRes)
        .setPositiveButton(R.string.action_yes) { _, _ -> onConfirm() }
        .setNegativeButton(R.string.action_no) { dialog, _ -> dialog.dismiss() }
        .show()
}
//endregion Context

//region FragmentActivity
fun FragmentActivity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/*fun FragmentActivity.showDatePicker(
    date: Date? = null,
    titleRes: Int = R.string.action_choose_date,
    onDateSelected: (date: Date) -> Unit
): MaterialDatePicker<Long> {
    val calendar = Calendar.getInstance()
    if (date != null) calendar.apply { time = date }
    val constraints = CalendarConstraints.Builder()
    val picker = MaterialDatePicker.Builder
        .datePicker()
        .setTheme(R.style.ThemeOverlay_Kema1Smart_DatePicker)
        .setTitleText(titleRes)
        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
        .setCalendarConstraints(constraints.build())
        .build()
    picker.addOnPositiveButtonClickListener { onDateSelected(Date(it)) }
    picker.show(supportFragmentManager, picker.tag)
    return picker
}*/
//endregion Activity

//region Fragment
fun Fragment.hideKeyboard() {
    requireActivity().hideKeyboard()
}

fun Fragment.colorOf(@ColorRes colorRes: Int): Int {
    return requireContext().colorOf(colorRes)
}

fun Fragment.colorAttrOf(@AttrRes attrRes: Int): Int {
    return requireContext().colorAttrOf(attrRes)
}

fun Fragment.colorStateListOf(@ColorRes colorRes: Int): ColorStateList {
    return requireContext().colorStateListOf(colorRes)
}

fun Fragment.colorStateListFrom(color: Int): ColorStateList {
    return requireContext().colorStateListFrom(color)
}

fun Fragment.colorStateListFromAttrOf(@AttrRes attrRes: Int): ColorStateList {
    return requireContext().colorStateListFromAttrOf(attrRes)
}

fun Fragment.drawableOf(@DrawableRes drawableRes: Int): Drawable? {
    return requireContext().drawableOf(drawableRes)
}

fun Fragment.showToast(message: String) {
    requireContext().showToast(message)
}

fun Fragment.showToast(messageRes: Int) {
    requireContext().showToast(messageRes)
}

fun Fragment.showLongToast(message: String) {
    requireContext().showLongToast(message)
}

fun Fragment.showLongToast(messageRes: Int) {
    requireContext().showLongToast(messageRes)
}

fun Fragment.showSnackbar(view: View, message: String) {
    requireContext().showSnackbar(view, message)
}

fun Fragment.showSnackbar(view: View, messageRes: Int) {
    requireContext().showSnackbar(view, messageRes)
}

fun Fragment.showLongSnackbar(view: View, message: String) {
    requireContext().showLongSnackbar(view, message)
}

fun Fragment.showLongSnackbar(view: View, messageRes: Int) {
    requireContext().showLongSnackbar(view, messageRes)
}

fun Fragment.showIndefiniteSnackbar(
    view: View,
    message: String,
    actionRes: Int = R.string.action_close,
    actionClick: () -> Unit = { }
) {
    requireContext().showIndefiniteSnackbar(view, message, actionRes, actionClick)
}

fun Fragment.showIndefiniteSnackbar(
    view: View,
    messageRes: Int,
    actionRes: Int = R.string.action_close,
    actionClick: () -> Unit = { }
) {
    requireContext().showIndefiniteSnackbar(view, messageRes, actionRes, actionClick)
}

fun Fragment.showMessageDialog(message: String): AlertDialog {
    return requireContext().showMessageDialog(message)
}

fun Fragment.showMessageDialog(@StringRes messageRes: Int): AlertDialog {
    return requireContext().showMessageDialog(messageRes)
}

fun Fragment.showActionDialog(
    message: String,
    buttonRes: Int = R.string.action_retry,
    action: () -> Unit
): AlertDialog {
    return requireContext().showActionDialog(message, buttonRes, action)
}

fun Fragment.showActionDismissableDialog(
    message: String,
    buttonRes: Int = R.string.action_retry,
    action: () -> Unit
): AlertDialog {
    return requireContext().showActionDismissableDialog(message, buttonRes, action)
}

fun Fragment.showConfirmationDialog(message: String, onConfirm: () -> Unit): AlertDialog {
    return requireContext().showConfirmationDialog(message, onConfirm)
}

fun Fragment.showConfirmationDialog(
    @StringRes messageRes: Int,
    onConfirm: () -> Unit
): AlertDialog {
    return requireContext().showConfirmationDialog(messageRes, onConfirm)
}

/*fun Fragment.showDatePicker(
    date: Date? = null,
    titleRes: Int = R.string.action_choose_date,
    onDateSelected: (date: Date) -> Unit
): MaterialDatePicker<Long> {
    val calendar = Calendar.getInstance()
    if (date != null) calendar.apply { time = date }
    val constraints = CalendarConstraints.Builder()
    val picker = MaterialDatePicker.Builder
        .datePicker()
        .setTheme(R.style.ThemeOverlay_Kema1Smart_DatePicker)
        .setTitleText(titleRes)
        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
        .setCalendarConstraints(constraints.build())
        .build()
    picker.addOnPositiveButtonClickListener { onDateSelected(Date(it)) }
    picker.show(childFragmentManager, picker.tag)
    return picker
}*/
//endregion Fragment