package com.example.flowershopapp.ComposeUI.Order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flowershopapp.ComposeUI.AppViewModelProvider
import com.example.flowershopapp.ComposeUI.User.OrderViewModel
import com.example.flowershopapp.Entities.Model.Order

@Composable
fun Orders(viewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    //val context = LocalContext.current
    //val (userWithOrders, setUserWithOrders) = remember { mutableStateOf<UsersWithOrders?>(null) }
    var user = viewModel.userUiState.userDetails.user
    var orders = viewModel.userUiState.userDetails.orders
    LazyColumn {
        //var orders = userWithOrders?.orders
        orders?.let{
            items(items = orders, key = { it.orderId!! }) { order ->
                OrderItem(order = order)
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "ID: ${order.orderId}", fontWeight = FontWeight.Bold)
        Text(text = "Date: ${order.date}")
        Text(text = "Sum: ${order.sum}")
    }
}