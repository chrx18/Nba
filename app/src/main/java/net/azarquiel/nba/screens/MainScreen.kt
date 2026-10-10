package net.azarquiel.nba.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import net.azarquiel.nba.R
import net.azarquiel.nba.model.Player
import net.azarquiel.nba.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    Scaffold(
        topBar = { CustomTopBar(textState) },
        content = { padding ->
            CustomContent(padding, viewModel, textState)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(textState: MutableState<TextFieldValue>) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchView(textState)
            }
        },
        colors = topAppBarColors(
            containerColor = colorResource(R.color.naranja),
            titleContentColor = colorResource(R.color.griso)
        )
    )
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    BasicTextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(colorResource(R.color.griso))
            .padding(16.dp, 3.dp),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = colorResource(R.color.naranja),
            fontSize = 20.sp
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.value.text.isEmpty()) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier.wrapContentSize(),
                        tint = colorResource(R.color.naranja)
                    )
                    Text(
                        text = "Buscar jugador...",
                        color = colorResource(R.color.naranja)
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun CustomContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    textState: MutableState<TextFieldValue>
) {
    val players by viewModel.players.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(colorResource(R.color.griso))
            .padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.naranja))
        ) {
            items(
                players.filter {
                    it.firstName.contains(
                        textState.value.text,
                        ignoreCase = true
                    ) || it.lastName.contains(
                        textState.value.text,
                        ignoreCase = true
                    )
                }
            ) { player ->
                CardPlayer(player)
            }
        }
    }
}

@Composable
fun CardPlayer(player: Player) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 7.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.griso),
            contentColor = colorResource(R.color.naranja)
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = player.headShotUrl,
                contentDescription = "${player.firstName} ${player.lastName}",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = "${player.firstName} ${player.lastName}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(text = "Equipo: ${player.team}")
                Text(text = "Posición: ${player.position}")
                Text(text = "Nacimiento: ${player.dateOfBirth}")
            }
        }
    }
}