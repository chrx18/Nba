package net.azarquiel.nba.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.azarquiel.nba.model.Player
import net.azarquiel.nba.view.MainActivity
import java.net.URL

class MainViewModel(mainActivity: MainActivity) : ViewModel() {

    val mainActivity by lazy { mainActivity }

    private val _players = MutableStateFlow(emptyList<Player>())
    val players: StateFlow<List<Player>> = _players

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val jsontxt = URL(
                    "http://www.ies-azarquiel.es/paco/apinba/players"
                ).readText(Charsets.UTF_8)

                val listadeplayers = Gson().fromJson(
                    jsontxt,
                    Array<Player>::class.java
                ).toList()

                withContext(Dispatchers.Main) {
                    Log.d("Chris", jsontxt)
                    _players.value = listadeplayers
                }

            } catch (e: Exception) {
                Log.e("Chris", "Error al cargar los jugadores", e)

                withContext(Dispatchers.Main) {
                    _error.value = "No se han podido cargar los jugadores"
                }
            }
        }
    }
}