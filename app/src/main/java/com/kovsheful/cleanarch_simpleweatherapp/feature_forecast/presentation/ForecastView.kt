package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kovsheful.cleanarch_simpleweatherapp.R
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.Background
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.DescriptionText
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.PrimaryColor
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.SecondaryText
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.typography
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow


@Composable
internal fun ForecastView() {
    val viewModel: ForecastViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val flow = viewModel.event
    Log.i(
        "internal ForecastView",
        "State updated: loading - ${state.isLoading}, items - ${state.forecastItems}"
    )
    PrivateForecastView(
        forecasts = state.forecastItems,
        flow = flow,
        isRemoteLoading = state.isLoading,
        updateForecast = { viewModel.getRemoteForecast() }
    )
}

//@Preview(backgroundColor = 0xFF3B3D49)
@Composable
fun PrevPrivateForecastView() {
    PrivateForecastView(
        forecasts = listOf(
            ForecastDay(
                date = Pair("Sunday", "22 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 30,
                maxwindKph = 10,
                avgHumidity = 50
            ),
            ForecastDay(
                date = Pair("Monday", "23 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 30,
                maxwindKph = 10,
                avgHumidity = 50
            ),
            ForecastDay(
                date = Pair("Tuesday", "24 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 30,
                maxwindKph = 10,
                avgHumidity = 50
            ),
            ForecastDay(
                date = Pair("Wednesday", "25 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 30,
                maxwindKph = 10,
                avgHumidity = 50
            ),
            ForecastDay(
                date = Pair("Thursday", "26 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 30,
                maxwindKph = 10,
                avgHumidity = 50
            ),
            ForecastDay(
                date = Pair("Friday", "27 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 30,
                maxwindKph = 10,
                avgHumidity = 50
            ),
            ForecastDay(
                date = Pair("Saturday", "28 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 30,
                maxwindKph = 10,
                avgHumidity = 50
            ),
            ForecastDay(
                date = Pair("Sunday", "29 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 30,
                maxwindKph = 10,
                avgHumidity = 50
            ),
            ForecastDay(
                date = Pair("Monday", "30 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 30,
                maxwindKph = 10,
                avgHumidity = 50
            ),
            ForecastDay(
                date = Pair("Tuesday", "31 Aug"),
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                avgtempC = 31,
                maxwindKph = 10,
                avgHumidity = 50
            )
        ),
        flow = MutableSharedFlow<Event>().asSharedFlow(),
        isRemoteLoading = false,
        updateForecast = {}
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun PrivateForecastView(
    forecasts: List<ForecastDay>,
    flow: SharedFlow<Event>,
    isRemoteLoading: Boolean,
    updateForecast: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Log.i("PrivateForecastView", "State updated: loading - $isRemoteLoading")
    LaunchedEffect(key1 = true) {
        flow.collect { event ->
            when (event) {
                is Event.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        backgroundColor = Background,
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            if (!isRemoteLoading) {
                TopAppBar(
                    backgroundColor = Background,
                ) {
                    Text(
                        text = "Forecast in Saint Petersburg",
                        style = typography.h6,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (isRemoteLoading) {
                        CircularProgressIndicator()
                    } else {
                        AsyncImage(
                            model = "https://" + forecasts[0].icon,
                            contentDescription = "Weather Icon",
                            modifier = Modifier.weight(0.164f),
                        )
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .alpha(if (isRemoteLoading) 0.5f else 1f)
        ) {
            val pickedDay = remember { mutableStateOf(-1) }
            if (isRemoteLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        itemsIndexed(forecasts) { index, day ->
                            if (index == pickedDay.value) {
                                //ExpandedDayForecast()
                            } else {
                                CollapsedDayForecast(
                                    dayInfo = day,
                                    onExpand = {
                                        pickedDay.value = index
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Button(
                onClick = {
                    pickedDay.value = -1
                    updateForecast()
                },
                modifier = Modifier.align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DescriptionText
                ),
                shape = RoundedCornerShape(25)
            ) {
                Text(
                    text = "Update forecast",
                    style = typography.h4.copy(
                        color = Background,
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF3B3D49)
@Composable
fun CollapsedDayForecast(
    dayInfo: ForecastDay = ForecastDay(
        date = Pair("Sunday", "22 Aug"),
        text = "Sunny",
        icon = "https://cdn.weatherapi.com/weather/64x64/day/113.png",
        avgtempC = 30,
        maxwindKph = 10,
        avgHumidity = 50
    ),
    onExpand: () -> Unit = {},
) {
    Button(
        onClick = onExpand,
        modifier = Modifier,
        shape = RoundedCornerShape(25),
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DateAndWeekDay(
                datePair = dayInfo.date,
                modifier = Modifier.weight(0.2f)
            )
            AsyncImage(
                model = "https:" + dayInfo.icon,
                contentDescription = "Weather Icon",
                modifier = Modifier.size(40.dp),
            )
            Temperature(
                avgtempC = dayInfo.avgtempC,
                modifier = Modifier
                    .weight(0.11f)
                    .padding(end = 4.dp),
            )
            DescriptionAndAdditionalInfo(
                dayInfo = dayInfo,
                modifier = Modifier.weight(0.341f)
            )
            ExpandIcon(
                icon = Icons.Filled.KeyboardArrowDown,
                modifier = Modifier
                    .weight(0.06f)
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
fun ExpandIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "ExpandIcon",
            tint = SecondaryText
        )
    }

}

@Composable
fun DescriptionAndAdditionalInfo(
    dayInfo: ForecastDay,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = dayInfo.text,
                modifier = Modifier.padding(bottom = 4.dp),
                textAlign = TextAlign.Center,
                style = typography.body1.copy(
                    color = DescriptionText
                )
            )
        }
        AdditionalInfo(dayInfo = dayInfo)
    }
}

@Composable
fun AdditionalInfo(
    dayInfo: ForecastDay,
    textStyle: TextStyle = typography.caption
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "${dayInfo.maxwindKph} km/h",
            style = textStyle.copy(
                color = Color.LightGray,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.drop),
                contentDescription = "Humidity",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${dayInfo.avgHumidity}%",
                style = textStyle.copy(
                    color = Color.LightGray,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        }
    }
}


@Composable
fun DateAndWeekDay(
    datePair: Pair<String, String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = datePair.second, //date
            style = typography.body1.copy(
                color = Color.White
            )
        )
        Text(
            text = datePair.first, // week day
            style = typography.body2.copy(
                color = SecondaryText
            )
        )
    }
}

@Composable
fun Temperature(
    avgtempC: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${avgtempC}°",
            style = typography.subtitle1.copy(
                color = Color.White,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),

            )
    }
}


@Composable
fun ExpandedDayForecast() {

}


