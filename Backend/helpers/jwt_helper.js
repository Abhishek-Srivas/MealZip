const JWT = require('jsonwebtoken')
const createError = require('http-errors')

module.exports = {
    signAccessToken: (userId) =>{
        return new Promise( (resolve,reject) => {
            const payload = {
                name:"yours tuly"
            }
            console.log("4")
            const secret = "some super secret"
            const options = {}
            JWT.sign(payload,secret,options, (err, token)=>{
                if(err){
                    console.log("2")
                   reject(err); 
                }
                console.log("3")
                resolve(token)
            })
        })
    }
}