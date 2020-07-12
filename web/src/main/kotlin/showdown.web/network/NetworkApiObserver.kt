package showdown.web.network

import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ServerResponse

interface NetworkApiObserver {

    fun onGameStateChanged(gameState: GameState)
    fun onError(errorEvent: ServerResponse.ErrorEvent)
}