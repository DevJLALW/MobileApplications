package org.srh.fda.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.srh.fda.model.*

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    productPreviewState: ProductPreviewState = ProductPreviewState(),
    productFlavors: List<ProductFlavorState> = ProductFlavorsData,
    productNutritionState: ProductNutritionState = ProductNutritionData,
    productDescription: String = ProductDescriptionData,
    orderState: OrderState = OrderData,
    onAddItemClicked: () -> Unit = {},
    onRemoveItemClicked: () -> Unit = {},
    onCheckOutClicked: () -> Unit = {},
    navController: NavController // Add NavController parameter here
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Content section is scrollable
        Content(
            productDescription = productDescription,
            productFlavors = productFlavors,
            productNutritionState = productNutritionState,
            productPreviewState = productPreviewState,
            modifier = Modifier.padding(bottom = 128.dp) // Leave space for the OrderActionBar
        )

        // OrderActionBar remains fixed at the bottom
        OrderActionBar(

            navController = navController,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 18.dp, vertical = 8.dp) ,
            state = orderState,
            onAddItemClicked = onAddItemClicked,
            onRemoveItemClicked = onRemoveItemClicked,
            onCheckOutClicked = { navController.navigate("checkout") }
            // Use navController to navigate


        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    productPreviewState: ProductPreviewState,
    productFlavors: List<ProductFlavorState>,
    productNutritionState: ProductNutritionState,
    productDescription: String
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        // Preview, Flavors, Nutrition, and Description sections
        ProductPreviewSection(state = productPreviewState)
        Spacer(modifier = Modifier.height(16.dp))

        FlavorSection(
            data = productFlavors,
            modifier = Modifier.padding(horizontal = 18.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        ProductNutritionSection(
            state = productNutritionState,
            modifier = Modifier.padding(horizontal = 18.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        ProductDescriptionSection(
            productDescription = productDescription,
            modifier = Modifier.padding(horizontal = 18.dp)
        )
    }
}