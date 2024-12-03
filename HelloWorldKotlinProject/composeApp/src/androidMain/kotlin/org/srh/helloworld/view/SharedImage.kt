package org.srh.helloworld.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.srh.helloworld.R

@Preview(showBackground = true)
@Composable
actual fun SharedImage() {

    Image(
        painter = painterResource(R.drawable.cat),
        contentDescription = "Cat Image",
        modifier = Modifier.size(150.dp)
    )
}
