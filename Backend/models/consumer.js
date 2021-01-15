var mongoose = require("mongoose");

const consumerSchema = new mongoose.Schema({
  name: {
    type: String,
    require: true,
    min: 6,
  },
  number: {
    type: String,
    require: false,
    max: 10,
    min: 10,
  },
  email: {
    type: String,
    require: true,
    lowercase: true,
    unique: true,
  },
  password: {
    type: String,
    require: true,
  },
  isverified: {
    type: String,
    require: true,
  },
  college: {
    type: String,
    require: false,
  },
  
});

module.exports = mongoose.model("consumers", consumerSchema); 
