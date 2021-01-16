var mongoose = require('mongoose')

const categorySchema = new mongoose.Schema({
    category:{
        type:String,
        require:true
    }
})

module.exports = mongoose.model("categories", categorySchema)