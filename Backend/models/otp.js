const mongoose = require("mongoose");

const otpSchema = new mongoose.Schema({
  email: {
    type: String,
    require: true,
    lowercase: true,
    unique: true,
  },
  otp: {
    type: String,
    require: true,
  },
  createdAt: { type: Date, expires: "2m", default: Date.now },
});

module.exports = mongoose.model("otp", otpSchema);
