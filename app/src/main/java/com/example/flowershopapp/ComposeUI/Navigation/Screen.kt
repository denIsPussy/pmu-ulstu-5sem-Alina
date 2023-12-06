package com.example.flowershopapp.ComposeUI.Navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.flowershopapp.R

enum class Screen (
    val route: String,
    @StringRes val resourceId: Int,
    val icon: Int?,
    val showInBottomBar: Boolean = true,
    val showTopBarAndNavBar: Boolean = true
){
    Signup("signup", R.string.signup_title, null, showInBottomBar = false, showTopBarAndNavBar = false),
    Login("login", R.string.login_title, null, showInBottomBar = false, showTopBarAndNavBar = false),
    Boot("boot", R.string.boot_title, null, showInBottomBar = false, showTopBarAndNavBar = false),
    Orders("orders", R.string.boot_title, null, showInBottomBar = false, showTopBarAndNavBar = false),
    BouquetCatalog("bouquet-catalog", R.string.bouquet_catalog_title, R.drawable.icons8_home),
    ShoppingCart("shopping-cart", R.string.shoppingCart_title, R.drawable.icons8_cart),
    Profile("profile", R.string.profile_title, R.drawable.icons8_user),
    Favorite("favorite", R.string.favorite_title, R.drawable.icons8_favorite);
//    Search("search", R.string.search_title, Icons.Filled.Search),
//    MovieView("movie-view/{id}", R.string.movie_view_title, Icons.Filled.List, showInBottomBar = false);
    companion object {
        val bottomBarItems = listOf(
            BouquetCatalog,
            Favorite,
            ShoppingCart,
            Profile
        )

        fun getItem(route: String): Screen? {
            val findRoute = route.split("/").first()
            return values().find { value -> value.route.startsWith(findRoute) }
        }
    }
}