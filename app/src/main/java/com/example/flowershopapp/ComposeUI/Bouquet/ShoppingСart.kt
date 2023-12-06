package com.example.flowershopapp.Entities.ComposeUI

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flowershopapp.Entities.Model.Bouquet
import com.example.flowershopapp.Entities.Model.CartModel
import com.example.flowershopapp.Entities.Model.FavoriteModel
import com.example.flowershopapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ShoppingCart(navController: NavController) {
    var totalCost by remember { mutableStateOf(getTotalCostOfItemsInCart()) }
    var bouquets by remember { mutableStateOf(CartModel.bouquets.toList()) }
    val removeBouquet: (List<Bouquet>) -> Unit = { bouquetsNew ->
        bouquets = bouquetsNew
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Корзина", fontFamily = FontFamily.Serif, fontSize = 40.sp,fontWeight = FontWeight.W600)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = bouquets, key = { it.bouquetId!! }) { bouquet ->
                Row(modifier = Modifier.padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    CartBouquetCard(bouquet = bouquet, onPriceChanged =  { price -> totalCost += price }, onRemove = removeBouquet)
                }
            }
            item{
                Text(text = "Общая сумма: $totalCost", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Button(
                    modifier = Modifier.padding(bottom = 20.dp),
                    shape = RoundedCornerShape(5.dp), onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.button)
                    )) {
                    Text("Оформить")
                }
            }
        }
    }
}

@Composable
fun CartBouquetCard(bouquet: Bouquet, onPriceChanged: (Int) -> Unit, onRemove: (List<Bouquet>) -> Unit){
    var heart by remember { mutableStateOf(FavoriteModel.containsBouquet(bouquet)) }
    var quantity by remember { mutableStateOf(1) }
    Card(
        modifier = Modifier
            .padding(16.dp),
        //.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
        //modifier = Modifier.padding(start = 5.dp, end = 5.dp),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.TopEnd) {
                val decodedBitmap = BitmapFactory.decodeByteArray(bouquet.image, 0, bouquet.image!!.size)
                val imageBitmap = decodedBitmap.asImageBitmap()
                Image(
                    bitmap  = imageBitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .width(250.dp)
                        .height(250.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(
                    Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { }) {
                    Icon(
                        painter = painterResource(id = heart),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .clickable { heart = FavoriteModel.addBouquet(bouquet) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = bouquet.name, fontFamily = FontFamily.Serif, fontSize = 25.sp)
            //Spacer(modifier = Modifier.height(1.dp))
            Text(text = "${bouquet.quantityOfFlowers} цветов", fontFamily = FontFamily.Serif, fontSize = 19.sp)
            //Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${bouquet.price}", fontFamily = FontFamily.Serif, fontSize = 19.sp)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.height(50.dp)) {
                Box(modifier = Modifier.fillMaxHeight(0.5f)){
                    Icon(
                        painter = painterResource(id = R.drawable.icon_minus_cirlce),
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            if (quantity >= 1) {
                                quantity--
                                onPriceChanged(-bouquet.price)
                            }
                            if (quantity == 0){
                                onRemove(CartModel.removeBouquets(bouquet))
                            }
                        }
                    )
                }
                Box(){
                    Text(text = "$quantity")
                }
                Box(modifier = Modifier.fillMaxHeight(0.5f)){
                    Icon(
                        painter = painterResource(id = R.drawable.icon_add_circle),
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            quantity++
                            onPriceChanged(bouquet.price)
                        }
                    )
                }
            }
        }
    }
}
fun getTotalCostOfItemsInCart(): Int {
    val bouquetsInCart = CartModel.bouquets
    var totalCost = 0
    for (bouquet in bouquetsInCart) {
        totalCost += bouquet.price
    }
    return totalCost
}