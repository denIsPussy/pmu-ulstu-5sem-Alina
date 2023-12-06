package com.example.flowershopapp.ComposeUI.User

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowershopapp.Entities.Model.AuthModel
import com.example.flowershopapp.Entities.Model.Order
import com.example.flowershopapp.Entities.Model.User
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import com.example.flowershopapp.Entities.Repository.User.UserRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    var userUiState by mutableStateOf(UserUiState())
        private set

    private val userName: String = AuthModel.currentUser.userName

    init {
        viewModelScope.launch {
            userUiState = userRepository.getUserWithOrders(userName)
                .filterNotNull()
                .first()
                .toUiState()
        }
    }
}

data class UserUiState(
    val userDetails: UserDetails = UserDetails()
)

data class UserDetails(
    val user: User? = null,
    val orders: List<Order> = listOf()
)

fun UsersWithOrders.toDetails(): UserDetails = UserDetails(
    user = user,
    orders = orders
)

fun UsersWithOrders.toUiState(): UserUiState = UserUiState(
    userDetails = this.toDetails()
)
