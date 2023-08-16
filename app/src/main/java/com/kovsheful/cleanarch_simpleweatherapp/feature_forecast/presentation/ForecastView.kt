package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.flow.SharedFlow
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.PrimaryColor
import coil.compose.AsyncImage
import com.kovsheful.cleanarch_simpleweatherapp.R
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.DescriptionText
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.SecondaryText
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.TopBarColor
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.typography
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


@Composable
internal fun ForecastView() {
    val viewModel: ForecastViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val flow = viewModel.event
    PrivateForecastView(
        forecasts = state.forecastItems,
        flow = flow,
        isRemoteLoading = state.isLoading,
        updateForecast = { viewModel.getRemoteForecast() }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF3B3D49)
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
            )
        ),
        flow = MutableSharedFlow<Event>().asSharedFlow(),
        isRemoteLoading = true,
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
    val isLoading by remember { mutableStateOf(isRemoteLoading) }
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
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            if (!isLoading) {
                TopAppBar(
                    backgroundColor = SecondaryText,
                ) {
                    Text(
                        text = "Forecast in Saint Petersburg",
                        style = typography.h6,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        AsyncImage(
                            model = forecasts[0].icon,
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
                .alpha(if (isLoading) 0.5f else 1f)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = PrimaryColor,
                )
            } else {
                val pickedDay by remember { mutableStateOf(-1) }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn() {
                        itemsIndexed(forecasts) { index, day ->
                            if (index == pickedDay) {
                                //ExpandedDayForecast()
                            } else {
                                CollapsedDayForecast(
                                    dayInfo = day,
                                    onExpand = { /*TODO*/ }
                                )
                            }
                        }
                    }
                }
                Button(
                    onClick = updateForecast,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DescriptionText
                    ),
                    shape = RoundedCornerShape(25)
                ) {
                    Text(
                        text = "Update forecast",
                        style = typography.h2.copy(
                            color = Color.Black,
                        ),
                        textAlign = TextAlign.Center
                    )
                }
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
        icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
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
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
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
            Column(
                modifier = Modifier.weight(0.17f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = dayInfo.date.second,
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = typography.subtitle2.copy(
                        color = Color.White
                    )
                )
                Text(
                    text = dayInfo.date.first,
                    style = typography.body1.copy(
                        color = SecondaryText
                    )
                )
            }
//            AsyncImage(
//                model = dayInfo.icon,
//                contentDescription = "Weather Icon",
//                modifier = Modifier.weight(0.164f),
//            )
            Row(
                modifier = Modifier.weight(0.164f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${dayInfo.avgtempC}°",
                    style = typography.h4.copy(
                        color = Color.White
                    ),
                )
            }

            Column(
                modifier = Modifier.weight(0.341f),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = dayInfo.text,
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = typography.subtitle2.copy(
                            color = DescriptionText
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "${dayInfo.maxwindKph} km/h",
                        style = typography.caption.copy(
                            color = Color.White
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.drop),
                            contentDescription = "Humidity",
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${dayInfo.avgHumidity}%",
                            style = typography.caption.copy(
                                color = Color.White
                            )
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.weight(0.102f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expand",
                )
            }
        }
    }
}

@Composable
fun ExpandedDayForecast() {

}


