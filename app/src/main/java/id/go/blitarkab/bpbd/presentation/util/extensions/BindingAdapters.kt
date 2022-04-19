package id.go.blitarkab.bpbd.presentation.util.extensions

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import java.io.File

@BindingAdapter("isVisible")
fun View.hideOrShowView(isShown: Boolean) {
    visibility = if (isShown) View.VISIBLE else View.GONE
}

@BindingAdapter("backgroundTint")
fun MaterialButton.setBackgroundTint(color: ColorStateList) {
    backgroundTintList = color
}

@BindingAdapter(
    value = ["srcRemote", "srcRemotePlaceholder", "srcRemoteError", "srcRemoteRadius"],
    requireAll = false
)
fun ImageView.bindImageRemoteSource(
    srcRemote: String?,
    srcRemotePlaceholder: Drawable?,
    srcRemoteError: Drawable?,
    srcRemoteRadius: Float?
) {
    setImageRemoteSource(
        srcRemote = srcRemote,
        srcPlaceholderDrawable = srcRemotePlaceholder,
        srcErrorDrawable = srcRemoteError,
        roundedRadius = srcRemoteRadius
    )
}

@BindingAdapter(
    value = ["srcRemoteCircle", "srcRemoteCirclePlaceholder", "srcRemoteCircleError"],
    requireAll = false
)
fun ImageView.bindCircleImageRemoteSource(
    srcRemoteCircle: String?,
    srcRemoteCirclePlaceholder: Drawable?,
    srcRemoteCircleError: Drawable?
) {
    setCircleImageRemoteSource(
        srcRemote = srcRemoteCircle,
        srcPlaceholderDrawable = srcRemoteCirclePlaceholder,
        srcErrorDrawable = srcRemoteCircleError
    )
}

@BindingAdapter(
    value = ["srcLocal", "srcLocalPlaceholder", "srcLocalError", "srcLocalRadius"],
    requireAll = false
)
fun ImageView.bindImageLocalSource(
    srcLocal: Uri?,
    srcLocalPlaceholder: Drawable?,
    srcLocalError: Drawable?,
    srcLocalRadius: Float?
) {
    setImageLocalSource(
        srcLocal = srcLocal ?: Uri.fromFile(File("-")),
        srcPlaceholderDrawable = srcLocalPlaceholder,
        srcErrorDrawable = srcLocalError,
        roundedRadius = srcLocalRadius
    )
}

@BindingAdapter(
    value = ["srcLocalCircle", "srcLocalCirclePlaceholder", "srcLocalCircleError"],
    requireAll = false
)
fun ImageView.bindCircleImageLocalSource(
    srcLocalCircle: Uri?,
    srcLocalCirclePlaceholder: Drawable?,
    srcLocalCircleError: Drawable?
) {
    setCircleImageLocalSource(
        srcLocal = srcLocalCircle ?: Uri.fromFile(File("-")),
        srcPlaceholderDrawable = srcLocalCirclePlaceholder,
        srcErrorDrawable = srcLocalCircleError
    )
}