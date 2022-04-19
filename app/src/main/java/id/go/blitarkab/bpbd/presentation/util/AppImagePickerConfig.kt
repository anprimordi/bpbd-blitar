package id.go.blitarkab.bpbd.presentation.util

import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode

object AppImagePickerConfig {

    val DEFAULT = ImagePickerConfig {
        mode = ImagePickerMode.SINGLE
        language = "in"
        returnMode = ReturnMode.NONE
        isFolderMode = false
        isIncludeVideo = false
        isOnlyVideo = false
        limit = 1
        isShowCamera = true
    }

    val MULTIPLE_IMAGES = ImagePickerConfig {
        mode = ImagePickerMode.MULTIPLE
        language = "in"
        returnMode = ReturnMode.NONE
        isFolderMode = false
        isIncludeVideo = false
        isOnlyVideo = false
        isShowCamera = true
    }

}