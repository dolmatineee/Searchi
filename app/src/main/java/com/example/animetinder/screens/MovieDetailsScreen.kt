package com.example.animetinder.screens


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.animetinder.data.Countries
import com.example.animetinder.data.Genres
import com.example.animetinder.data.Movie
import com.example.animetinder.viewmodels.MovieDetailsViewModel
import com.example.animetinder.data.Persons
import com.example.animetinder.data.Watchability
import com.example.animetinder.ui.theme.BackgroundColor
import com.example.animetinder.ui.theme.PrimaryColor
import com.example.animetinder.ui.theme.SecondaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movie: Movie,

    ) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    val viewModel: MovieDetailsViewModel = viewModel()
    val isLoading = viewModel.isLoading.value

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = PrimaryColor)
        }
    } else {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = (screenHeight / 3).dp,
            sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            sheetContainerColor = BackgroundColor,
            sheetContent = {
                SheetContent(movie = movie)
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = movie.poster.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }



}


@Composable
fun SheetContent(movie: Movie) {
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp , bottom = 120.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {

            Text(
                modifier = Modifier.padding(bottom = 16.dp, start = 6.dp),
                text = movie.name,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

        }

        item {
            ShortInformation(movie = movie)
        }

        item {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SecondaryColor)
            )
        }

        item {

            Text(
                text = "Страны",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

        }

        item {
            Countries(countries = movie.countries)
        }

        item {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SecondaryColor)
            )
        }

        item {

            Text(
                text = "Жанры",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

        }

        item {
            MovieGenres(genres = movie.genres)
        }

        item {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SecondaryColor)
            )
        }

        item {

            Text(
                text = "Краткая информация",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

        }

        item {
            Description(description = movie.description)
        }



        item {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SecondaryColor)
            )
        }

        item {

            Text(
                text = "Актеры",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

        }

        item {
            PersonsFromMovie(persons = movie.persons)
        }


        item {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SecondaryColor)
            )
        }

        item {

            Text(
                text = "Где посмотреть",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

        }

        item {
            WatchabilityCards(watchability = movie.watchability)
        }


    }
}


@Composable
fun ShortInformation(
    movie: Movie
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .wrapContentSize()
                    .background(PrimaryColor)
                    .padding(vertical = 6.dp, horizontal = 16.dp),

                ) {
                Text(
                    text = "${movie.rating.imdb}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(color = SecondaryColor, width = 1.dp, shape = RoundedCornerShape(15.dp))
                    .padding(vertical = 6.dp, horizontal = 16.dp),

                ) {
                Text(
                    text = "фильм",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SecondaryColor
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(color = SecondaryColor, width = 1.dp, shape = RoundedCornerShape(15.dp))
                    .padding(vertical = 6.dp, horizontal = 16.dp),

                ) {
                Text(
                    text = "${movie.year}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SecondaryColor
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(color = SecondaryColor, width = 1.dp, shape = RoundedCornerShape(15.dp))
                    .padding(vertical = 6.dp, horizontal = 16.dp),

                ) {
                Text(
                    text = "+${movie.ageRating}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SecondaryColor
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(color = SecondaryColor, width = 1.dp, shape = RoundedCornerShape(15.dp))
                    .padding(vertical = 6.dp, horizontal = 16.dp),

                ) {
                Text(
                    text = "${movie.movieLength} мин.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SecondaryColor
                )
            }
        }
    }
}


@Composable
fun Countries(countries: List<Countries>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(countries) { country ->
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(color = SecondaryColor, width = 1.dp, shape = RoundedCornerShape(15.dp))
                    .padding(vertical = 6.dp, horizontal = 16.dp),
            ) {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = SecondaryColor
                )
            }
        }
    }
}


@Composable
fun MovieGenres(genres: List<Genres>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(genres) { genre ->
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(color = SecondaryColor, width = 1.dp, shape = RoundedCornerShape(15.dp))
                    .padding(vertical = 6.dp, horizontal = 16.dp),
            ) {
                Text(
                    text = genre.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = SecondaryColor
                )
            }
        }
    }
}

@Composable
fun Description(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        color = SecondaryColor
    )
}


@Composable
fun PersonsFromMovie(persons: List<Persons>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(persons) { person ->
            if (person.photo != null && person.name != null && person.profession != null) {
                PersonCard(
                    photo = person.photo,
                    name = person.name,
                    profession = person.profession
                )
            } else {

            }
        }
    }
}


@Composable
fun PersonCard(
    photo: String,
    name: String,
    profession: String
) {
    Column(
        modifier = Modifier.width(100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(15.dp)),
            model = photo,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            text = profession,
            style = MaterialTheme.typography.bodySmall,
            color = SecondaryColor
        )

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryColor
        )
    }
}

@Composable
fun WatchabilityCards(watchability: Watchability) {
    LazyRow(
        modifier = Modifier.padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(watchability.items) { item ->
            val logoUrl = when (item.name) {
                "Okko" -> "https://sun9-68.userapi.com/impg/L77Prc0jooyikER-p-awq-wA9gEJcwm35ID4mA/7cY4o-fINh4.jpg?size=1079x1079&quality=96&sign=495ac7a994428a5fb89acc417c227b7e&c_uniq_tag=nvyvSc2C51p3eYsdeuhBZ-dszmtmHcI6hYMpT3l7Sq0&type=album"
                "Иви" -> "https://telegra.ph/file/21895b3bdd8c93da278b4.jpg"
                "KION" -> "https://static.pepper.ru/threads/raw/1gfSE/323620_1/re/1024x1024/qt/60/323620_1.jpg"
                "Wink" -> "https://sun9-39.userapi.com/impg/DLzNogQOkJJ6mVnOS8sAqBSNV6m1nYIim6dqxA/UbI8dIEUHtg.jpg?size=750x420&quality=96&sign=df10c922373e14e140b51f9c63579f15&c_uniq_tag=mYdCp1DInRHD2ib04h3l5F6IeNYYtBUdbCa9Q-8Duo4&type=album"
                "VK Видео" -> "https://sun9-48.userapi.com/impf/OjvV7nB8ioxFIHQeh-abU8ZbcWdkEYUBWwGRjg/sFRPG9bWbm0.jpg?size=1920x768&quality=95&crop=22,0,1000,399&sign=b26534060412b176d142d114620ebfc8&type=cover_group"
                else -> item.logo.url
            }
            WatchabilityCard(
                logoUrl = logoUrl,
                platformName = item.name,
                url = item.url
            )
        }
    }
}

@Composable
fun WatchabilityCard(
    logoUrl: String,
    platformName: String,
    url: String
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(15.dp)),
            model = logoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            text = platformName,
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryColor
        )
    }
}
