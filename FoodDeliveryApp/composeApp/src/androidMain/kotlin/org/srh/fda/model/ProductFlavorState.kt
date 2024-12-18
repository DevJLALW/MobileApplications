package org.srh.fda.model

import androidx.annotation.DrawableRes
import org.srh.fda.R

data class ProductFlavorState(
    val name: String,
    val price: String,
    @DrawableRes val imgRes: Int
)

val ProductFlavorsData = listOf(
    ProductFlavorState(
        imgRes = R.drawable.img_cheese,
        name = "Chedder",
        price = "€0.79"
    ),
    ProductFlavorState(
        imgRes = R.drawable.img_bacon,
        name = "Bacon",
        price = "€0.52"
    ),
    ProductFlavorState(
        imgRes = R.drawable.img_onion,
        name = "Onion",
        price = "€0.28"
    )
)
