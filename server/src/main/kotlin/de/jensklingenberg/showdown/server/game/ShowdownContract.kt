package de.jensklingenberg.showdown.server.game

import de.jensklingenberg.showdown.model.Player
interface GameServer {
    fun sendBroadcast(data: String)
    fun sendData(sessionId: String, data: String)
    fun onPlayerAdded(sessionId: String, player: Player)
    fun createNewRoom(roomName: String) :Game
    fun closeRoom()
}