package com.rksrtx76.camxinteligence.presentation

import android.content.Intent
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rksrtx76.camxinteligence.MainActivity

@Composable
fun CameraScreen(
    activity: MainActivity,
){
    val controller =  remember {
        LifecycleCameraController(activity.applicationContext).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE
            )
        }
    }

    val cameraViewModel = hiltViewModel<CameraViewModel>()
    val isRecording by cameraViewModel.isRecording.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        CameraPreview(
            controller = controller,
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 72.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(42.dp)
                    .background(Color.Black)
                    .clickable {
                        // open gallery
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "content://media/internal/images/media"
                            )
                        ).also {
                            activity.startActivity(it)
                        }
                    },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.PhotoLibrary,
                    contentDescription = "Gallery",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(1.dp))

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(64.dp)
                    .background(Color.Black)
                    .clickable {
                        if ((activity as MainActivity).hasRequiredPermissions()) {
                            cameraViewModel.onRecordVideo(controller)
                        }
                    },
                contentAlignment = Alignment.Center
            ){

                if(isRecording){
                    Icon(
                        imageVector = Icons.Default.Stop ,
                        contentDescription = "Stop recording",
                        tint = Color.Red,
                        modifier = Modifier.size(34.dp)
                    )
                }
                else{
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = "Record video",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }

            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(64.dp)
                    .background(Color.Black)
                    .clickable {
                        if ((activity as MainActivity).hasRequiredPermissions()) {
                            cameraViewModel.onTakePhoto(controller)
                        }
                    },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Take Photo",
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(modifier = Modifier.width(1.dp))

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(42.dp)
                    .background(Color.Black)
                    .clickable {
                        // Switch camera
                        controller.cameraSelector = if(controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA){
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        }else{
                            CameraSelector.DEFAULT_BACK_CAMERA
                        }

                    },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Cameraswitch,
                    contentDescription = "Switch Camera",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun CameraScreenPreview() {
    CameraScreen(
        activity = MainActivity()
    )
}