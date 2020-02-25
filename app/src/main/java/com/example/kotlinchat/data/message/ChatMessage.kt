package com.example.kotlinchat.data.message

class ChatMessage constructor(_msgType: Int, _sender:String, _receiver:String, _message:String){
    var msgType: Int = _msgType
    var sender:String = _sender
    var receiver: String = _receiver
    var message: String = _message

    override fun toString(): String {
        return "msgType : $msgType, sender : $sender, receiver : $receiver, message : $message"
    }
//    constructor(msgType:Int, sender:String, receiver:String, message:String){
//        this.msgType = msgType
//        this.sender = sender
//        this.receiver = receiver
//        this.message = message
//    }


}