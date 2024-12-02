package org.srh.helloworld.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.srh.helloworld.R

@Composable
actual fun SharedImage() {
//    Image(
//        painter = painterResource(Res.drawable.compose_multiplatform),
//        contentDescription = null,
//        modifier = Modifier.size(150.dp)
//    )
    Image(
        painter = painterResource(R.drawable.cat),
        contentDescription = "Cat Image",
        modifier = Modifier.size(150.dp)
    )
}
