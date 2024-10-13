package com.chariot.nexus.Models

class User {
    var image:String?=null
    var name:String?=null
    var email:String?=null
    var senha:String?=null

    constructor()
    constructor(image: String?, name: String?, email: String?, senha: String?){
        this.image = image
        this.name = name
        this.email = email
        this.senha = senha
    }

    constructor(name: String?, email: String?, senha: String?){
        this.name = name
        this.email = email
        this.senha = senha
    }

    constructor(email: String?, senha: String?){
        this.email = email
        this.senha = senha
    }

}