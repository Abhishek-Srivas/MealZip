const jwt = require("jsonwebtoken");
const createError = require('http-errors')
const dotenv = require('dotenv')
dotenv.config()


module.exports = (req, res, next) => {
    const authHeader = req.get("Authorization");
    if (!authHeader) {
      const error = new Error("Not authenticated.");
      error.statusCode = 401;
      error.message = "not Authenticated"
      throw error;
    }
    const token = authHeader.split(" ")[1];
    console.log(token)
    // let decodedToken;
    try {
        /*decodedToken =*/ jwt.verify(token, process.env.ACCESS_TOKEN_KEY,(err,payload) => {
          console.log(payload) 
          if(err){
               if(err.name === 'JsonWebTokenError'){
                   return next(createError.Unauthorized())
               } else{
                   return next(createError.Unauthorized(err.message))
               }
           }
           req.User = payload;
           
        });
      } catch (err) {
        err.statusCode = 500;
        throw err;
      }
      
      next();
};