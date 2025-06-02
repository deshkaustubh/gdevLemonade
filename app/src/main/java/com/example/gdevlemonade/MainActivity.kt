package com.example.gdevlemonade

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.gdevlemonade.ui.theme.GdevLemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GdevLemonadeTheme {
                val view = LocalView.current
                SideEffect {
                    val window = (view.context as Activity).window
                    window.statusBarColor = android.graphics.Color.parseColor("#FFFFFF") // change this color
                    WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = true
                }

                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        var stage by remember { mutableStateOf(1) }

        var squeezeCount by remember { mutableStateOf(0) }
        var requiredSqueezes by remember { mutableStateOf(1) }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Lemonade", fontWeight = FontWeight.Bold)
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        ) { innerPadding ->

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                color = MaterialTheme.colorScheme.background
            ) {
                when (stage) {
                    1 -> LemonadeImageAndText(
                        {
                            stage = 2
                            requiredSqueezes = (2..4).random()
                            squeezeCount = 0
                        }, R.drawable.lemon_tree,
                        R.string.cd_text_1,
                        R.string.text_1
                    )

                    2 -> LemonadeImageAndText(
                        {
                            squeezeCount++
                            if (squeezeCount > requiredSqueezes) stage = 3
                        },
                        R.drawable.lemon_squeeze,
                        R.string.cd_text_2,
                        R.string.text_2
                    )

                    3 -> LemonadeImageAndText(
                        { stage = 4 },
                        R.drawable.lemon_drink,
                        R.string.cd_text_3,
                        R.string.text_3
                    )

                    4 -> LemonadeImageAndText(
                        { stage = 1 },
                        R.drawable.lemon_restart,
                        R.string.cd_text_4,
                        R.string.text_4
                    )
                }
            }
        }
    }
}

@Composable
fun LemonadeImageAndText(
    onImageClick: () -> Unit,
    imageResource: Int,
    imageDescription: Int,
    textBelowImage: Int
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = stringResource(imageDescription),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .border(
                    BorderStroke(2.dp, Color(105, 205, 216)),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { onImageClick() }
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(stringResource(textBelowImage), fontSize = 18.sp)
    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    GdevLemonadeTheme {
        LemonadeApp()
    }
}