package com.example.flowershopapp.ComposeUI.Bouquet


import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flowershopapp.Entities.Model.Bouquet
import com.example.flowershopapp.Entities.Model.CartModel
import com.example.flowershopapp.Entities.Model.FavoriteModel
import com.example.flowershopapp.R

@Composable
fun Favorite(navController: NavController) {
    var bouquets by remember { mutableStateOf(FavoriteModel.bouquets.toList()) }

    val removeBouquet: (List<Bouquet>) -> Unit = { bouquetsNew ->
        bouquets = bouquetsNew
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Избранное", fontFamily = FontFamily.Serif, fontSize = 40.sp,fontWeight = FontWeight.W600)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = bouquets.chunked(2), key = { it.hashCode() }) { bouquetPair ->
           //bouquets.chunked(2).forEach { bouquetPair ->
                LazyRow(modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()) {
                    var k: Float = 0.5f
                    items(items = bouquetPair, key = { it.bouquetId!! }) { bouquet ->
                        Box(modifier = Modifier
                            .fillMaxWidth(k)
                            .padding(start = 10.dp, end = 10.dp)) {
                            BouquetCardUpgrade(bouquet = bouquet, onRemove = removeBouquet) }
                        k += 0.5f;

                    }
                }
            }
        }
    }
}

@Composable
fun BouquetCardUpgrade(bouquet: Bouquet, onRemove: (List<Bouquet>) -> Unit) {
    var heart by remember { mutableStateOf(FavoriteModel.containsBouquet(bouquet)) }
    var expandedName by remember { mutableStateOf(false) }
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.TopEnd) {
                val decodedBitmap = BitmapFactory.decodeByteArray(bouquet.image, 0, bouquet.image!!.size)
                val imageBitmap = decodedBitmap.asImageBitmap()
                Image(
                    bitmap  = imageBitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(Modifier.clip(RoundedCornerShape(10.dp)).clickable {  }) {
                    Icon(
                        painter = painterResource(id = heart),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .clickable {
                                onRemove(FavoriteModel.removeBouquets(bouquet))
                                heart = R.drawable.heart_black
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (expandedName) {
                AlertDialog(
                    onDismissRequest = { expandedName = false },
                    title = {
                        Text(text = bouquet.name, fontFamily = FontFamily.Serif, fontSize = 25.sp)
                    },
                    confirmButton = {
                        Button(
                            onClick = { expandedName = false }
                        ) {
                            Text("Закрыть")
                        }
                    }
                )
            }
            Text(text = bouquet.name,
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .clickable { expandedName = true })
                //Spacer(modifier = Modifier.height(1.dp))
            Text(text = "${bouquet.quantityOfFlowers} цветов", fontFamily = FontFamily.Serif, fontSize = 19.sp)
            //Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${bouquet.price}", fontFamily = FontFamily.Serif, fontSize = 19.sp)
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Button(modifier = Modifier.width(115.dp), shape = RoundedCornerShape(5.dp), onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.button)
                    )) {
                    Text("Купить")
                }
                Button(modifier = Modifier.width(115.dp), shape = RoundedCornerShape(5.dp), onClick = { CartModel.addBouquet(bouquet) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.button)
                    )) {
                    Text("В корзину")
                }
            }
        }
    }
}