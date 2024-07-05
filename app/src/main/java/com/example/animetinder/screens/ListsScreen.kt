package com.example.animetinder.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.animetinder.data.Genres
import com.example.animetinder.viewmodels.ListsViewModel
import com.example.animetinder.data.Persons
import com.example.animetinder.data.Poster
import com.example.animetinder.data.Rating
import com.example.animetinder.data.Watchability
import com.example.animetinder.data.WatchabilityItem
import com.example.animetinder.data.WatchabilityLogo
import com.example.animetinder.factory.ListsViewModelFactory
import com.example.animetinder.ui.theme.BackgroundColor
import com.example.animetinder.ui.theme.ErrorColor
import com.example.animetinder.ui.theme.OnSecondaryColor
import com.example.animetinder.ui.theme.PrimaryColor
import com.example.animetinder.ui.theme.SecondaryColor
import com.example.animetinder.ui.theme.WhiteColor
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import com.example.animetinder.Movie as Movie


@Composable
fun ListsScreen(
    paddingValues: PaddingValues,
    onLikedMovieItemClickListener: (Int) -> Unit,
    viewModelFactory: ListsViewModelFactory
) {
    val viewModel: ListsViewModel = viewModel(factory = viewModelFactory)
    val likedMovies by viewModel.likedMovies.observeAsState(emptyList())
    val isLoading = viewModel.isLoading.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = PrimaryColor
            )
        } else {
                var refreshing by remember { mutableStateOf(isLoading) }
                LaunchedEffect(refreshing) {
                    if (refreshing) {
                        delay(3000)
                        refreshing = false
                    }
                }
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = isLoading),
                    onRefresh = { viewModel.loadLikedMovies() }
                ) {
                    if (likedMovies.isEmpty()) {
                        EmptyListPlaceholder()
                    } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)
                            .background(BackgroundColor)
                    ) {
                        items(likedMovies) { movie ->
                            SwipeToDismissItem(
                                movie = movie,
                                onDismiss = { viewModel.removeLikedMovie(it) },
                                onLikedMovieItemClickListener = onLikedMovieItemClickListener
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LikedMovieItem(
    movie: Movie,
    onLikedMovieItemClickListener: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)


    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = movie.poster.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onLikedMovieItemClickListener(movie.id) },
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = movie.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Short information
                ShortInformation(movie = movie)

                Spacer(modifier = Modifier.height(16.dp))

                // Genres
                MovieGenresForLikedItem(genres = movie.genres)
            }

        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLikedMovieItem() {
    val movieInstance = Movie(
        id = 1,
        name = "Inception",
        year = 2010,
        rating = Rating(imdb = 8.8),
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/6f631486-e947-487d-94d6-41c2b5a8f5a0/1920x"),
        countries = listOf(Countries(name = "USA")),
        ageRating = "PG-13",
        description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.",
        genres = listOf(
            Genres(name = "Action"),
            Genres(name = "Adventure"),
            Genres(name = "Sci-Fi")
        ),
        movieLength = 148,
        persons = listOf(
            Persons(
                name = "Christopher Nolan",
                profession = "Director",
                photo = "https://example.com/nolan.jpg"
            )
        ),
        watchability = Watchability(
            items = listOf(
                WatchabilityItem(
                    name = "Netflix",
                    logo = WatchabilityLogo(url = "https://example.com/netflix_logo.jpg"),
                    url = "https://www.netflix.com/title/406030"
                )
            )
        )
    )

    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        LikedMovieItem(movie = movieInstance) {

        }
    }
}


@Composable
fun MovieGenresForLikedItem(genres: List<Genres>) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(genres) { genre ->
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(SecondaryColor)
                    .padding(vertical = 6.dp, horizontal = 16.dp),
            ) {
                Text(
                    text = genre.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = OnSecondaryColor
                )
            }
        }
    }


}

@Preview
@Composable
fun EmptyListPlaceholder() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Clear,
            contentDescription = null,
            tint = SecondaryColor
        )
        Text(
            text = "Ваш список пуст",
            style = MaterialTheme.typography.bodyLarge,
            color = SecondaryColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissItem(
    movie: Movie,
    onDismiss: (Movie) -> Unit,
    onLikedMovieItemClickListener: (Int) -> Unit
) {
    val dismissState = rememberDismissState()
    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onDismiss(movie)
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.Default -> ErrorColor
                    DismissValue.DismissedToStart -> ErrorColor
                    DismissValue.DismissedToEnd -> ErrorColor
                }
            )

            val icon = when (direction) {
                DismissDirection.EndToStart -> Icons.Default.Delete
                DismissDirection.StartToEnd -> TODO()
            }

            val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)

            val alignment = when (direction) {
                DismissDirection.EndToStart -> Alignment.CenterEnd
                DismissDirection.StartToEnd -> Alignment.CenterStart
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(end = 24.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    modifier = Modifier.scale(scale),
                    imageVector = icon,
                    contentDescription = null,
                    tint = WhiteColor
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(animateDpAsState(targetValue = if (dismissState.dismissDirection != null) 12.dp else 0.dp).value),
                colors = CardDefaults.cardColors(containerColor = BackgroundColor)
            ) {
                LikedMovieItem(
                    movie = movie,
                    onLikedMovieItemClickListener = onLikedMovieItemClickListener
                )
            }

        }
    )
}


