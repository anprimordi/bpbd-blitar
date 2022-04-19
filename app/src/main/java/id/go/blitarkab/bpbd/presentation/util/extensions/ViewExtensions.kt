package id.go.blitarkab.bpbd.presentation.util.extensions

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import id.go.blitarkab.bpbd.R
import timber.log.Timber

fun ShimmerFrameLayout.show() {
    isVisible = true
    showShimmer(true)
}

fun ShimmerFrameLayout.hide() {
    stopShimmer()
    hideShimmer()
    isVisible = false
}

//region Material TextView
fun MaterialTextView.startDrawable(@DrawableRes drawableRes: Int) {

    setCompoundDrawablesRelativeWithIntrinsicBounds(drawableRes, 0, 0, 0)
}

fun MaterialTextView.startDrawable(drawable: Drawable?) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
}

fun MaterialTextView.endDrawable(@DrawableRes drawableRes: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawableRes, 0)
}
//endregion Material TextView

//region Material CardView
fun MaterialCardView.setCardBackgroundColorFromResources(@ColorRes colorRes: Int) {
    val color = ResourcesCompat.getColor(resources, colorRes, context.theme)
    setCardBackgroundColor(color)
}
//endregion Material CardView

//region Material Button
fun MaterialButton.setBackgroundTintFromResources(@ColorRes colorRes: Int) {
    val colorStateList = ColorStateList.valueOf(
        ResourcesCompat.getColor(
            resources,
            colorRes,
            context.theme
        )
    )
    backgroundTintList = colorStateList
}
//endregion Material Button

//region ImageView
fun ImageView.setImageRemoteSource(
    srcRemote: String?,
    srcPlaceholderRes: Int? = R.drawable.placeholder_image,
    srcErrorRes: Int? = null,
    roundedRadius: Float? = null,
    targetScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    errorCallback: (() -> Unit)? = null
) {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    if (srcRemote == null) {
        if (srcErrorRes != null) {
            setImageResource(srcErrorRes)
        } else {
            if (errorCallback != null) errorCallback()
            else {
                setBackgroundResource(R.drawable.placeholder_image)
                setImageResource(R.drawable.ic_baseline_image_24)
            }
        }
    } else {
        val builder = Glide.with(this)
            .load(srcRemote)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    scaleType = targetScaleType
                    if (isFirstResource) target?.onResourceReady(resource, null)
                    return true
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Timber.e(e)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    if (srcErrorRes != null) {
                        setImageResource(srcErrorRes)
                    } else {
                        setBackgroundResource(R.drawable.placeholder_image)
                        setImageResource(R.drawable.ic_baseline_image_24)
                    }
                    if (errorCallback != null) errorCallback()
                    return true
                }
            })

        if (srcPlaceholderRes != null) builder.placeholder(srcPlaceholderRes)

        if (roundedRadius != null) builder.transform(
            when (targetScaleType) {
                ImageView.ScaleType.CENTER_INSIDE -> CenterInside()
                ImageView.ScaleType.FIT_CENTER -> FitCenter()
                ImageView.ScaleType.CENTER -> CenterInside()
                else -> CenterCrop()
            },
            RoundedCorners(roundedRadius.toInt())
        )
        builder.into(this)
    }
}

fun ImageView.setImageRemoteSource(
    srcRemote: String?,
    srcPlaceholderDrawable: Drawable?,
    srcErrorDrawable: Drawable? = null,
    roundedRadius: Float? = null,
    targetScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    errorCallback: (() -> Unit)? = null
) {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    if (srcRemote == null) {
        if (srcErrorDrawable != null) {
//            //setBackgroundResource(0)
            setImageDrawable(srcErrorDrawable)
        } else {
            setBackgroundResource(R.drawable.placeholder_image)
            setImageResource(R.drawable.ic_baseline_image_24)
        }
    } else {
        val builder = Glide.with(this)
            .load(srcRemote)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    scaleType = targetScaleType
                    if (isFirstResource) target?.onResourceReady(resource, null)
                    return true
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Timber.e(e)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    if (srcErrorDrawable != null) {
                        //setBackgroundResource(0)
                        setImageDrawable(srcErrorDrawable)
                    } else {
                        setBackgroundResource(R.drawable.placeholder_image)
                        setImageResource(R.drawable.ic_baseline_image_24)
                    }
                    if (errorCallback != null) errorCallback()
                    return true
                }
            })

        if (srcPlaceholderDrawable != null) builder.placeholder(srcPlaceholderDrawable)
        else builder.placeholder(R.drawable.placeholder_image)

        if (roundedRadius != null) builder.transform(
            when (targetScaleType) {
                ImageView.ScaleType.CENTER_INSIDE -> CenterInside()
                ImageView.ScaleType.FIT_CENTER -> FitCenter()
                ImageView.ScaleType.CENTER -> CenterInside()
                else -> CenterCrop()
            },
            RoundedCorners(roundedRadius.toInt())
        )
        builder.into(this)
    }
}

fun ImageView.setCircleImageRemoteSource(
    srcRemote: String?,
    srcPlaceholderRes: Int = R.drawable.placeholder_image_circle,
    srcErrorRes: Int? = null,
    targetScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    errorCallback: (() -> Unit)? = null
) {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    if (srcRemote == null) {
        if (srcErrorRes != null) {
            //setBackgroundResource(0)
            setImageResource(srcErrorRes)
        } else {
            setBackgroundResource(R.drawable.placeholder_image_circle)
            setImageResource(R.drawable.ic_baseline_image_24)
        }
    } else {
        Glide.with(this)
            .load(srcRemote)
            .placeholder(srcPlaceholderRes)
            .transform(CircleCrop())
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    scaleType = targetScaleType
                    if (isFirstResource) target?.onResourceReady(resource, null)
                    return true
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Timber.e(e)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    if (srcErrorRes != null) {
                        //setBackgroundResource(0)
                        setImageResource(srcErrorRes)
                    } else {
                        setBackgroundResource(R.drawable.placeholder_image_circle)
                        setImageResource(R.drawable.ic_baseline_image_24)
                    }
                    if (errorCallback != null) errorCallback()
                    return true
                }
            })
            .into(this)
    }
}

fun ImageView.setCircleImageRemoteSource(
    srcRemote: String?,
    srcPlaceholderDrawable: Drawable? = null,
    srcErrorDrawable: Drawable? = null,
    targetScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    errorCallback: (() -> Unit)? = null
) {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    if (srcRemote == null) {
        if (srcErrorDrawable != null) {
            //setBackgroundResource(0)
            setImageDrawable(srcErrorDrawable)
        } else {
            setBackgroundResource(R.drawable.placeholder_image_circle)
            setImageResource(R.drawable.ic_baseline_image_24)
        }
    } else {
        val builder = Glide.with(this)
            .load(srcRemote)
            .transform(CircleCrop())
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    scaleType = targetScaleType
                    if (isFirstResource) target?.onResourceReady(resource, null)
                    return true
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Timber.e(e)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    if (srcErrorDrawable != null) {
                        //setBackgroundResource(0)
                        setImageDrawable(srcErrorDrawable)
                    } else {
                        setBackgroundResource(R.drawable.placeholder_image_circle)
                        setImageResource(R.drawable.ic_baseline_image_24)
                    }
                    if (errorCallback != null) errorCallback()
                    return true
                }
            })

        if (srcPlaceholderDrawable != null) builder.placeholder(srcPlaceholderDrawable)
        else builder.placeholder(R.drawable.placeholder_image_circle)

        builder.into(this)
    }
}

fun ImageView.setImageLocalSource(
    srcLocal: ByteArray,
    srcPlaceholderRes: Int = R.drawable.placeholder_image,
    srcErrorRes: Int? = null,
    roundedRadius: Float? = null,
    targetScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    errorCallback: (() -> Unit)? = null
) {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    val builder = Glide.with(this)
        .load(srcLocal)
        .placeholder(srcPlaceholderRes)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                scaleType = targetScaleType
                if (isFirstResource) target?.onResourceReady(resource, null)
                return true
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Timber.e(e)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                if (srcErrorRes != null) {
                    //setBackgroundResource(0)
                    setImageResource(srcErrorRes)
                } else {
                    setBackgroundResource(R.drawable.placeholder_image)
                    setImageResource(R.drawable.ic_baseline_image_24)
                }
                if (errorCallback != null) errorCallback()
                return true
            }
        })

    if (roundedRadius != null) builder.transform(
        when (targetScaleType) {
            ImageView.ScaleType.CENTER_INSIDE -> CenterInside()
            ImageView.ScaleType.FIT_CENTER -> FitCenter()
            ImageView.ScaleType.CENTER -> CenterInside()
            else -> CenterCrop()
        },
        RoundedCorners(roundedRadius.toInt())
    )

    builder.into(this)
}

fun ImageView.setImageLocalSource(
    srcLocal: Uri,
    srcPlaceholderRes: Int = R.drawable.placeholder_image,
    srcErrorRes: Int? = null,
    roundedRadius: Float? = null,
    targetScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    errorCallback: (() -> Unit)? = null
) {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    val builder = Glide.with(this)
        .load(srcLocal)
        .placeholder(srcPlaceholderRes)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                scaleType = targetScaleType
                if (isFirstResource) target?.onResourceReady(resource, null)
                return true
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Timber.e(e)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                if (srcErrorRes != null) {
                    //setBackgroundResource(0)
                    setImageResource(srcErrorRes)
                } else {
                    setBackgroundResource(R.drawable.placeholder_image)
                    setImageResource(R.drawable.ic_baseline_image_24)
                }
                if (errorCallback != null) errorCallback()
                return true
            }
        })

    if (roundedRadius != null) builder.transform(
        when (targetScaleType) {
            ImageView.ScaleType.CENTER_INSIDE -> CenterInside()
            ImageView.ScaleType.FIT_CENTER -> FitCenter()
            ImageView.ScaleType.CENTER -> CenterInside()
            else -> CenterCrop()
        },
        RoundedCorners(roundedRadius.toInt())
    )

    builder.into(this)
}

fun ImageView.setImageLocalSource(
    srcLocal: Uri,
    srcPlaceholderDrawable: Drawable?,
    srcErrorDrawable: Drawable? = null,
    roundedRadius: Float? = null,
    targetScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    errorCallback: (() -> Unit)? = null
) {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    val builder = Glide.with(this)
        .load(srcLocal)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                scaleType = targetScaleType
                if (isFirstResource) target?.onResourceReady(resource, null)
                return true
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Timber.e(e)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                if (srcErrorDrawable != null) {
                    //setBackgroundResource(0)
                    setImageDrawable(srcErrorDrawable)
                } else {
                    setBackgroundResource(R.drawable.placeholder_image)
                    setImageResource(R.drawable.ic_baseline_image_24)
                }
                if (errorCallback != null) errorCallback()
                return true
            }
        })

    if (srcPlaceholderDrawable != null) builder.placeholder(srcPlaceholderDrawable)
    else builder.placeholder(R.drawable.placeholder_image)

    if (roundedRadius != null) builder.transform(
        when (targetScaleType) {
            ImageView.ScaleType.CENTER_INSIDE -> CenterInside()
            ImageView.ScaleType.FIT_CENTER -> FitCenter()
            ImageView.ScaleType.CENTER -> CenterInside()
            else -> CenterCrop()
        },
        RoundedCorners(roundedRadius.toInt())
    )

    builder.into(this)
}

fun ImageView.setCircleImageLocalSource(
    srcLocal: Uri,
    srcPlaceholderRes: Int = R.drawable.placeholder_image_circle,
    srcErrorRes: Int? = null,
    targetScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    errorCallback: (() -> Unit)? = null
) {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    Glide.with(this)
        .load(srcLocal)
        .placeholder(srcPlaceholderRes)
        .transform(CircleCrop())
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                scaleType = targetScaleType
                if (isFirstResource) target?.onResourceReady(resource, null)
                return true
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Timber.e(e)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                if (srcErrorRes != null) {
                    //setBackgroundResource(0)
                    setImageResource(srcErrorRes)
                } else {
                    setBackgroundResource(R.drawable.placeholder_image_circle)
                    setImageResource(R.drawable.ic_baseline_image_24)
                }
                if (errorCallback != null) errorCallback()
                return true
            }
        })
        .into(this)
}

fun ImageView.setCircleImageLocalSource(
    srcLocal: Uri,
    srcPlaceholderDrawable: Drawable? = null,
    srcErrorDrawable: Drawable? = null,
    targetScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    errorCallback: (() -> Unit)? = null
) {
    scaleType = ImageView.ScaleType.CENTER_INSIDE
    val builder = Glide.with(this)
        .load(srcLocal)
        .transform(CircleCrop())
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                scaleType = targetScaleType
                if (isFirstResource) target?.onResourceReady(resource, null)
                return true
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Timber.e(e)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                if (srcErrorDrawable != null) {
                    //setBackgroundResource(0)
                    setImageDrawable(srcErrorDrawable)
                } else {
                    setBackgroundResource(R.drawable.placeholder_image_circle)
                    setImageResource(R.drawable.ic_baseline_image_24)
                }
                if (errorCallback != null) errorCallback()
                return true
            }
        })

    if (srcPlaceholderDrawable != null) builder.placeholder(srcPlaceholderDrawable)
    else builder.placeholder(R.drawable.placeholder_image_circle)

    builder.into(this)
}
//endregion ImageView
