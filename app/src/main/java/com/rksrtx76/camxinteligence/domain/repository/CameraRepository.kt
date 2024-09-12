package com.rksrtx76.camxinteligence.domain.repository

import androidx.camera.view.LifecycleCameraController

interface CameraRepository {

    suspend fun takePhoto(
        controller : LifecycleCameraController
    )

    suspend fun recordVideo(
        controller : LifecycleCameraController
    )
}