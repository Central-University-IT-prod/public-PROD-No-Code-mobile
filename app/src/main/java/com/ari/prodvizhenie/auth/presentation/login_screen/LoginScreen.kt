package com.ari.prodvizhenie.auth.presentation.login_screen

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ari.prodvizhenie.auth.presentation.login_screen.components.GradientBox
import com.ari.prodvizhenie.auth.presentation.login_screen.components.MyTextField
import com.ari.prodvizhenie.auth.presentation.login_screen.components.isSmallScreenHeight
import com.ari.prodvizhenie.auth.presentation.login_screen.components.rememberImeState
import com.ari.prodvizhenie.util.components.LoadingDialog

@Composable
fun LoginScreen() {

    val loginViewModel: LoginViewModel = hiltViewModel()
    val tokenViewModel: TokenViewModel = hiltViewModel()

    val context = LocalContext.current


    val authState by loginViewModel.state.collectAsStateWithLifecycle()

    when (authState.tokens.accessToken) {
        "" -> LoginScreenUi(
            isLoading = authState.isLoading,
            loginUser = { username, password ->
                loginViewModel.loginUser(username, password)
            }
        )

        else -> {

            Toast.makeText(context, "Авторизация успешна", LENGTH_SHORT).show()

            tokenViewModel.saveRefreshToken(authState.tokens.refreshToken)
            tokenViewModel.saveAccessToken(authState.tokens.accessToken)

            loginViewModel.saveAppEntry()

        }

    }

}

@Composable
fun LoginScreenUi(
    isLoading: Boolean,
    loginUser: (String, String) -> Unit
) {
    val isImeVisible by rememberImeState()

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    LoadingDialog(isLoading = isLoading)

    GradientBox(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val animatedUpperSectionRatio by animateFloatAsState(
                targetValue = if (isImeVisible) 0f else 0.35f,
                label = ""
            )
            AnimatedVisibility(visible = !isImeVisible, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(animatedUpperSectionRatio),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Добро пожаловать в Prodвижение",
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        lineHeight = 40.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isSmallScreenHeight()) {
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                } else {
                    Spacer(modifier = Modifier.fillMaxSize(0.1f))
                }

                Text(
                    text = "Вход",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )

                if (isSmallScreenHeight()) {
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                } else {
                    Spacer(modifier = Modifier.fillMaxSize(0.1f))
                }

                MyTextField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = "Логин",
                    keyboardOptions = KeyboardOptions(),
                    keyboardActions = KeyboardActions(),
                    trailingIcon = null,
                    text = username,
                    onTextChange = { newText -> username = newText }
                )

                Spacer(modifier = Modifier.height(20.dp))

                MyTextField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = "Пароль",
                    keyboardOptions = KeyboardOptions(),
                    keyboardActions = KeyboardActions(),
                    trailingIcon = Icons.Default.Lock,
                    text = password,
                    onTextChange = { newText -> password = newText }
                )
                if (isImeVisible) {
                    Button(
                        onClick = { loginUser(username, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0D4C92),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Войти", style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(500)
                            )
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp), contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                loginUser(username.trim(), password.trim())
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0D4C92),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Войти", style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(500)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}