package com.nhatle.workmangement.until

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException
import kotlin.math.log

class SocketManager {
    private val TAG= "SocketManager"
    private  var socket: Socket?=null
     fun connect(){
        try {
            socket = IO.socket(URL_SOCKET)
            socket?.on(Socket.EVENT_CONNECT) {
                Log.d(TAG, "EVENT_CONNECT :$it")
                socket?.emit("connected",""+CommonData.getInstance().profile!!.profileId )
            }
            socket?.on(Socket.EVENT_DISCONNECT) {
                Log.d(TAG, "EVENT_DISCONNECT : $it")
            }
            socket?.on(Socket.EVENT_CONNECT_ERROR){
                Log.d(TAG, "EVENT_CONNECT_ERROR : $it")
            }
        }catch (e:URISyntaxException){
            e.printStackTrace()
        }
    }
    fun disconnect(){
        if (socket!=null){
            socket!!.disconnect()
            socket=null
        }
    }
   companion object{
       private  var  instance :SocketManager?=null
       @JvmStatic
       fun getInstance():SocketManager = instance?: SocketManager().also { instance =it }
   }
}