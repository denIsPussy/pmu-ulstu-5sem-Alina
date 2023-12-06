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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.flowershopapp.ComposeUI.AppViewModelProvider
import com.example.flowershopapp.Entities.Model.Bouquet
import com.example.flowershopapp.Entities.Model.CartModel
import com.example.flowershopapp.Entities.Model.FavoriteModel
import com.example.flowershopapp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BouquetCatalog(
    navController: NavController,
    viewModel: BouquetCatalogViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
//    val context = LocalContext.current
//    val bouquets = remember { mutableStateListOf<Bouquet>() }
//    LaunchedEffect(Unit) {
//        withContext(Dispatchers.IO) {
//            AppDatabase.getInstance(context).bouquetDao().getAll().collect { data ->
//                bouquets.clear()
//                bouquets.addAll(data)
//            }
//        }
//    }

    val bouquetListUiState = viewModel.bouquetListUiState.collectAsLazyPagingItems()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        bouquetListUiState.refresh()
        refreshing = false
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.pullRefresh(state)) {
        Text(text = "Каталог букетов", fontFamily = FontFamily.Serif, fontSize = 40.sp,fontWeight = FontWeight.W600)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(count = bouquetListUiState.itemCount, key = bouquetListUiState.itemKey(), contentType = bouquetListUiState.itemContentType()) { index ->
                val bouquet = bouquetListUiState[index]
                Row(modifier = Modifier.padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        BouquetCard(bouquet = bouquet!!)
                }
            }
        }
    }
}

@Composable
fun BouquetCard(bouquet: Bouquet) {
    var heart by remember { mutableStateOf(FavoriteModel.containsBouquet(bouquet)) }
    Card(
        modifier = Modifier
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.TopEnd) {
                bouquet.image?.let { imageData ->
                    val decodedBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                    val imageBitmap = decodedBitmap.asImageBitmap()
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = null,
                        modifier = Modifier
                            .height(250.dp)
                            .width(250.dp)
                            .clip(shape = RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
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
                            .clickable {
                                heart = FavoriteModel.addBouquet(bouquet)
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = bouquet.name, fontFamily = FontFamily.Serif, fontSize = 25.sp)
            //Spacer(modifier = Modifier.height(1.dp))
            Text(text = "${bouquet.quantityOfFlowers} цветов", fontFamily = FontFamily.Serif, fontSize = 19.sp)
            //Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${bouquet.price}", fontFamily = FontFamily.Serif, fontSize = 19.sp)
            Row(horizontalArrangement = Arrangement.Center) {
                Button(modifier = Modifier
                    .width(115.dp), shape = RoundedCornerShape(8.dp), onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.button)
                    )) {
                    Text("Купить")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(modifier = Modifier
                    .width(115.dp), shape = RoundedCornerShape(8.dp), onClick = { CartModel.addBouquet(bouquet) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.button)
                    )) {
                    Text("В корзину")
                }
            }
        }
    }
}