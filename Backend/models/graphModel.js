const mongoose = require("mongoose");

const graphSchema = new mongoose.Schema(
  {
    shopId: {
      type: String,
      require: true,
    },
    orderDate: {
      type: String,
      required: false,
    },
    itemId: {
          type: mongoose.Schema.Types.ObjectId,
          require: true,
          ref: "",
    },
    itemName: {
          type: String,
          required: true,
    },
    price: {
          type: Number,
          required: true,
    },
    rating: {
          type: Number,
          required: false,
        },
       
      
    createdAt: { type: Date, expires: "7d", default: Date.now },
  
    },{ timestamps: true }
);

module.exports = mongoose.model("graphData", graphSchema);

