const shopSchema = require("../models/shops");
const orderSchema = require("../models/orders");
const graphSchema = require('../models/graphModel')
const { json } = require("body-parser");

/*------------------------------Consumer Section-------------------------------------------*/
exports.getShop = async (req, res, next) => {
  const collegeName = req.body.collegeName;
  console.log(collegeName);
  const shops = await shopSchema.find({ college: collegeName });
  // if(shops.isEmpty()){
  //     res.json("No Shops of your Collage are registered")
  // }
  res.json(shops);
};

exports.placeOrder = async (req, res, next) => {
  
  const orderArray = req.body.orderArray; //[{itemId:, itemName:, price: isaccepted orderStatus: isPaid: orderDate: rating:}]
  const shopId = req.body.shopId;
  const imgUrl = req.body.imgUrl;
  const consumerId = req.body.consumerId;
  const date = req.body.date;
  const name = req.body.name;
  
  await orderArray.forEach( async (element) => {
        
    const orderGraph = new graphSchema({
      shopId:shopId,
      orderDate:date,
      itemId: element.itemId,
      itemName: element.itemName,
      price: element.price,
      rating:element.rating
    })

    try{
        const savedData = await orderGraph.save();
        console.log(savedData);
    }
    catch(err){
        res.json(err);
    }
    
});
  
  const order = new orderSchema({
    customerName:name,
    shopId: shopId,
    consumerId: consumerId,
    orderDate:date,
    ordersArray:orderArray,
    imgUrl:imgUrl,
  });

  order
    .save()
    .then((result) => {
      res.json({ message: "Order Placed", result: result });
    })
    .catch((err) => {
      res.status(500).json("Internal Server Error");
    });
};


