package com.rauantasbolat.weathykazdreamcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rauantasbolat.weathykazdreamcompose.MainViewModel
import com.rauantasbolat.weathykazdreamcompose.R
import com.rauantasbolat.weathykazdreamcompose.core.constants.Constants
import com.rauantasbolat.weathykazdreamcompose.core.utils.getImageFromUrl
import com.rauantasbolat.weathykazdreamcompose.core.utils.getUVIndexColor
import com.rauantasbolat.weathykazdreamcompose.ui.theme.Purple
import com.rauantasbolat.weathykazdreamcompose.ui.theme.Vazir

@Composable
fun AstanaScreen(viewModel: MainViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError}
    viewModel.currentWeather(Constants.AstanaLat, Constants.AstanaLong)

    if(!isLoading && loadError.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
                .verticalScroll(scrollState)
        ) {
            LocationBoxAstana()
            LocationWeatherBoxAstana()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.later_this_week),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                )
            }
            DailyWeatherAstana()
        }
    } else if (loadError.isNotEmpty()) {
        RetrySectionAstana(error = loadError) {
            viewModel.dailyWeather(Constants.AstanaLat, Constants.AstanaLong)
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.Cyan)
        }
    }


}

@Composable
fun LocationBoxAstana(viewModel: MainViewModel = hiltViewModel()) {
    val currentDate by remember { viewModel.currentDate }
    val isLoading by remember { viewModel.isLoading }
    val loadingError by remember { viewModel.loadError }
    if (!isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(32.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = currentDate,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(8.dp),
                        fontFamily = Vazir,
                        fontSize = 19.sp
                    )

                }

            }

        }
    } else {

    }

}

@Composable
fun LocationWeatherBoxAstana(viewModel: MainViewModel = hiltViewModel()) {
    val currentTemperature by remember { viewModel.currentTemperature}
    val currentType by remember { viewModel.currentType}
    val currentHumidity by remember {viewModel.currentHumidity}
    val currentUV by remember {viewModel.currentUV}
    val currentIcon by remember {viewModel.currentIcon}
    val isLoading by remember {viewModel.isLoading}
    val uvIndexColor = getUVIndexColor(currentUV)
    if (!isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp, 16.dp, 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Purple)
                    .padding(4.dp)
            ) {
                Column {
                    Text(
                        text = currentTemperature,
                        color = Color.White,
                        fontSize = 72.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        fontFamily = Vazir
                    )
                    Text(
                        text = currentType,
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(0.dp, 0.dp, 24.dp, 8.dp))
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_humidity),
                                    contentDescription = stringResource(R.string.app_name)
                                )
                                Text(
                                    text = "Humidity $currentHumidity",
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(6.dp, 12.dp),
                                    fontSize = 18.sp
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_wind),
                                    contentDescription = stringResource(R.string.app_name)
                                )
                                Text(
                                    text = "UV $currentUV",
                                    color = uvIndexColor,
                                    modifier = Modifier
                                        .padding(6.dp, 12.dp),
                                    fontSize = 18.sp
                                )
                            }
                        }

                        Image(
                            painter = painterResource(id = getImageFromUrl(currentIcon)),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun DailyWeatherAstana(viewModel: MainViewModel = hiltViewModel()) {
    val dailyWeatherList by remember { viewModel.dailyWeatherList    }
    val isLoading by remember { viewModel.isLoading }

    LazyColumn {
        items(dailyWeatherList) {
            if (!isLoading) {
                DailyWeatherListItem(
                    day = it.day,
                    icon = it.img,
                    weatherType = it.weatherType,
                    highestTemperature = it.maxTemp,
                    lowestTemperature = it.minTemp
                )
            }

        }
    }
}


@Composable
fun DailyWeatherListItemAstana(
    day : String,
    icon: String,
    weatherType: String,
    highestTemperature: String,
    lowestTemperature: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .padding(12.dp, 4.dp)
                .fillMaxWidth()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = day,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(getImageFromUrl(icon)),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier.size(56.dp)
                    )
                    Text(
                        text = weatherType,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp, 0.dp)
                    )
                }
                Row{
                    Text(
                        text = highestTemperature,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "/",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = lowestTemperature,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}

@Composable
fun RetrySectionAstana(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.retry), color = Color.White)
        }
    }
}

