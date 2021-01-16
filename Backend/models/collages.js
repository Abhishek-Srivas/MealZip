var mongoose = require("mongoose");

const collageSchema = new mongoose.Schema({
  code: {
    type: String,
    require: true,
  },
  name: {
    type: String,
    require: true,
  },
});

module.exports = mongoose.model("collages", collageSchema);
