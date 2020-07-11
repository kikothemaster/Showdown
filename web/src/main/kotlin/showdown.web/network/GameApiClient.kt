package showdown.web.network

import com.badoo.reaktive.completable.completable
import de.jensklingenberg.showdown.model.ServerResponse
import de.jensklingenberg.showdown.model.ShowdownError
import de.jensklingenberg.showdown.model.getServerResponse
import org.w3c.dom.CloseEvent
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event

class GameApiClient {

    private var socket: WebSocket? = null

    private lateinit var observer: NetworkApiObserver

    fun start(observer: NetworkApiObserver) = completable {
         this.observer = observer
         socket = org.w3c.dom.WebSocket(NetworkPreferences().websocketUrl())

         socket?.onopen = { event: Event ->
             it.onComplete()
         }

         socket?.onmessage = { event: Event ->
             onMessage((event as MessageEvent))
         }

         socket?.onerror = { event: Event ->
             observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
             it.onError(Throwable("NO Connection"))
         }
         socket?.onclose = { event: Event ->
             val even = event as CloseEvent
             observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
         }
     }

    private fun onMessage(messageEvent: MessageEvent) {
        when (val type = getServerResponse(messageEvent.data.toString())) {
            is ServerResponse.PlayerEvent -> {
                observer.onPlayerEventChanged(type.playerResponseEvent)
            }
            is ServerResponse.GameStateChanged -> {
                observer.onGameStateChanged(type.state)
            }
            is ServerResponse.ErrorEvent -> {
                observer.onError(type)
            }

        }


    }

    fun sendMessage(message: String) {
        socket?.send(message)
    }
}