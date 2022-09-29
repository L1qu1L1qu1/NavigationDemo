package com.example.navigationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
mainScreen()
        }
    }
}
@Composable
fun mainScreen(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route,
    ) {
        composable(NavRoutes.Home.route) {

            Home(navController = navController)
        }

        composable(NavRoutes.Welcome.route+"/{userName}") {
            val userName = it.arguments?.getString("userName")
            Welcome(navController = navController, userName)
        }

        composable("profile") {
//            val userName = it.arguments?.getString("userName")
            Profile("userName")
        }
    }
}
sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Welcome : NavRoutes("welcome")
    object Profile : NavRoutes("profile")
}
@Composable
fun Home(navController: NavHostController) {

    var userName by remember { mutableStateOf("") }
    val onUserNameChange = { text : String ->
        userName = text
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CustomTextField(
                title = "Enter your name",
                textState = userName,
                onTextChange = onUserNameChange
            )

            Spacer(modifier = Modifier.size(30.dp))

            Button(onClick = { navController.navigate(NavRoutes.Welcome.route+"/$userName") }) {
                Text(text = "Register")
            }
        }
    }
}

@Composable
fun CustomTextField(
    title: String,
    textState: String,
    onTextChange: (String) -> Unit,

) {

    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        singleLine = true,
        label = { Text(title)},
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 30.sp)
    )
}
@Composable
fun Welcome(navController: NavHostController, userName: String?) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome "+userName, style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.size(30.dp))

            Button(onClick = { navController.navigate(NavRoutes.Profile.route)}) {
                Text(text = "Set up your Profile")
            }
        }
    }
}
@Composable
fun Profile(userName: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Hello $userName", style = MaterialTheme.typography.h5)
    }
}


