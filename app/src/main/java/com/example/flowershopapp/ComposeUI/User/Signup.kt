package com.example.flowershopapp.ComposeUI.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flowershopapp.ComposeUI.Navigation.Screen
import com.example.flowershopapp.R

@Composable
fun Signup(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.backgroundWindow)),
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "", Modifier.size(180.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.signup_title),
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Имя пользователя") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(id = R.color.textFieldContainer),
                    focusedContainerColor = colorResource(id = R.color.textFieldContainer)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(id = R.color.textFieldContainer),
                    focusedContainerColor = colorResource(id = R.color.textFieldContainer)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Подтвердите пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(id = R.color.textFieldContainer),
                    focusedContainerColor = colorResource(id = R.color.textFieldContainer)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(shape = RoundedCornerShape(5.dp), onClick = { navController.navigate(Screen.Login.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button)
            )) {
                Text("Создать")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                Text(text = "У меня есть учетная запись")
            }
        }
    }
}
