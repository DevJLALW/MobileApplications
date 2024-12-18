package org.srh.fda.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.srh.fda.model.ProductPreviewState
import org.srh.fda.theme.AppTheme
import org.srh.fda.R
import org.srh.fda.model.ProductHighlightState
import org.srh.fda.model.ProductHighlightType

@Composable
fun ProductPreviewSection(
    modifier: Modifier = Modifier,
    state: ProductPreviewState
) {
    Box(
        modifier = modifier.height(IntrinsicSize.Max)
    ) {
        ProductBackground(
            Modifier.padding(bottom = 16.dp)
        )
        Content(
            state = state,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 24.dp)
        )
    }
}

//@Composable
//private fun ProductBackground(
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier.fillMaxSize()
//    ) {
//        Image(
//            painter = painterResource(R.drawable.backgroundimg1), // Replace with your background image
//            contentDescription = "Background image",
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
//    }
//}

@Composable
private fun ProductBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = AppTheme.colors.secondarySurface,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    state: ProductPreviewState
) {
    ConstraintLayout(
        modifier = modifier.height(IntrinsicSize.Max)
    ) {
        val (actionBar, highlights, productImg) = createRefs()

        ActionBar(
            headline = "NomNomNow",
            modifier = Modifier
                .padding(horizontal = 19.dp)
                .constrainAs(actionBar) {
                    top.linkTo(parent.top)
                }
        )

        Image(
            painter = painterResource(id = state.productImg),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(256.dp)
                .constrainAs(productImg) {
                    end.linkTo(parent.end)
                    top.linkTo(anchor = actionBar.bottom, margin = 20.dp)
                }
        )

        ProductHighlights(
            highlights = state.highlights,
            modifier = Modifier.constrainAs(highlights) {
                start.linkTo(anchor = parent.start, margin = 19.dp)
                top.linkTo(productImg.top)
            }
        )
    }
}

@Composable
private fun ActionBar(
    modifier: Modifier = Modifier,
    headline: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = headline,
            style = AppTheme.typography.headline,
            color = AppTheme.colors.onActionSurface
            //color = androidx.compose.ui.graphics.Color.Magenta
        )
        CloseButton()
    }
}

@Composable
private fun CloseButton(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(44.dp),
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.colors.actionSurface,
        contentColor = AppTheme.colors.secondarySurface
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProductPreviewSectionPreview() {
//    // Wrap the preview in AppTheme
//    AppTheme {
//        ProductPreviewSection(
//            state = ProductPreviewState(
//                productImg = R.drawable.ic_placeholder, // Replace with your image resource
//                highlights = listOf(
//                    ProductHighlightState(
//                        text = "High-quality ingredients",
//                        type = ProductHighlightType.PRIMARY
//                    ),
//                    ProductHighlightState(
//                        text = "No preservatives",
//                        type = ProductHighlightType.SECONDARY
//                    ),
//                    ProductHighlightState(
//                        text = "Tasty!",
//                        type = ProductHighlightType.PRIMARY
//                    )
//                )
//            ),
//            modifier = Modifier
//        )
//    }
//}

