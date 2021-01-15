var mongoose = require("mongoose");

const userSchema = new mongoose.Schema({
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
  shopName: {
    type: String,
    require: false,
    min: 3,
  },
  college: {
    type: String,
    require: false,
  },
  inCollege:{
    type:Boolean,
    require:false
  },
  time:{
    type:String,
    default:"not available"
  },
  shopItem: [
    {
      name: {
        type: String,
      },
      imgUrl: {
        type: String,
      },
      itemsAvailable: {
        type: Boolean,
        default: false
      },
      isveg: {
        type: String,
      },
      ratingArray:[
        {
          rating:{
            type:Number,
          },
          consumerId: {
            type:mongoose.Schema.Types.ObjectId,
          }
        }],
      priceArray: [
        {
          size: {
            type: String,
          },
          price: {
            type: Number,
          },
        },
      ],
      category: {
        type: String,
      },
    },
  ],
});

module.exports = mongoose.model("shopkeepers", userSchema); //stored in users collection  and uses user schema
