package showdown.web.game

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.observable.Observable
import de.jensklingenberg.showdown.model.*

interface GameDataSource {
    fun connectToServer(): Completable
    fun joinRoom(name:String,password:String)
    fun observeGameState(): Observable<GameState>
    fun requestReset()
    fun observeErrors(): Observable<ShowdownError?>
    fun showVotes()
    fun onSelectedVote(voteId: Int)
    fun changeConfig(newGameConfig: NewGameConfig)
    fun changeRoomPassword(password: String)
    fun getPlayerName():String
    fun getRoomPassword():String
}

