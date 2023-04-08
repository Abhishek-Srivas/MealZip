//Schemas
const Users = require("../models/shops");
const collageSchema = require("../models/collages");
const categorySchema = require("../models/categories");
const orderSchema = require("../models/orders");
const graphSchema = require("../models/graphModel");

/*------------------------------Shop Section-------------------------------------------*/
exports.shopInfo = (req, res, next) => {
  const name = req.body.name;
  const shopName = req.body.Shopname;
  const college = req.body.college;
  const email = req.User.email;
  const inCollege = req.body.inCollege;

  Users.findOne({ email: email }).then((User) => {
    User.shopName = shopName;
    User.college = college;
    User.name = name;
    User.inCollege = inCollege;
    User.save().then((Result) => {
      res.json("User Saved");
    });
  });
};

exports.addItem = (req, res, next) => {
  const itemName = req.body.itemName;
  const imgUrl = req.body.imgUrl;
  const isveg = req.body.isveg;
  const category = req.body.category;
  const email = req.body.email;
  const priceArray = req.body.price; // Object required {price:"60",size:"Half"} in this syntax
  console.log(priceArray, email);

  Users.findOne({ email: email })
    .then((shopSchema) => {
      shopSchema.shopItem.push({
        name: itemName,
        imgUrl: imgUrl,
        isveg: isveg,
        category: category,
        priceArray: priceArray,
      });
      shopSchema.save().then((result) => {
        res.json(result);
      });
    })
    .catch((err) => {
      res.status(500).json("Not saved");
    });
};

exports.getItem = (req, res, next) => {
  const email = req.body.email;
  Users.findOne({ email: email })
    .then((shop) => {
      res.send(shop.shopItem);
    })
    .catch((err) => {
      res.status(500).json("Internal Server Error");
    });
};

exports.getCollege = async (req, res, next) => {
  const colleges = await collageSchema.find();
  res.json(colleges);
};

exports.getCategory = async (req, res, next) => {
  const category = await categorySchema.find();
  res.json(category);
};

exports.shopOrders = async (req, res, next) => {
  //For ShopOwner to check Orders
  const shopId = req.body.shopId;

  const myOrders = await orderSchema.find({ shopId: shopId });

  res.json({ message: "All the Order List", orders: myOrders });
};

exports.verifyOrder = async (req, res, next) => {
  const orderId = req.body.orderId;
  const itemId = req.body.itemId;

  const verifyOrder = await orderSchema.findById(orderId);

  verifyOrder.ordersArray.forEach(async (element) => {
    console.log(element);
    if (element.itemId.toString() === itemId.toString()) {
      element.isaccepted = true;
    }
  });
  console.log();
  const verifiedOrder = await verifyOrder.save();

  res.json({ message: "Order Accepted", result: verifiedOrder });
};

exports.orderStatus = async (req, res, next) => {
  const orderId = req.body.orderId;
  const itemId = req.body.itemId;

  const verifyOrder = await orderSchema.findById(orderId);

  verifyOrder.ordersArray.forEach(async (element) => {
    console.log(element);
    if (element.itemId.toString() === itemId.toString()) {
      element.orderStatus = "Delivered";
    }
  });
  console.log();
  const verifiedOrder = await verifyOrder.save();

  res.json({ message: "Order Done", result: verifiedOrder });
};

exports.todaysTop = async (req, res, next) => {
  const Date = req.body.Date;
  const Id = req.body.shopId;
  console.log(Id);
  try {
    const result = await graphSchema.aggregate([
      {
        $match: {
          $and: [{ orderDate: { $in: [Date] } }, { shopId: { $in: [Id] } }],
        },
      },
      {
        $group: {
          _id: "$itemId",
          total: { $sum: "$price" },
          itemSold: { $sum: 1 },
          name: { $addToSet: "$itemName" },
          rating: { $addToSet: "$rating" },
        },
      },
      { $sort: { total: -1 } },
    ]);

    res.json(result);
  } catch (err) {
    res.json(err);
  }
};

exports.weeklyStat = async (req, res, next) => {
  const Id = req.body.shopId;
  try {
    const result = await graphSchema.aggregate([
      { $match: { $and: [{ shopId: { $in: [Id] } }] } },
      {
        $group: {
          _id: "$orderDate",
          total: { $sum: "$price" },
          itemSold: { $sum: 1 },
          name: { $addToSet: "$itemName" },
        },
      },
      { $sort: { _id: -1 } },
    ]);

    res.json(result);
  } catch (err) {
    res.json(err);
  }
};

exports.shopTiming = (req, res, next) => {
  const email = req.body.email;
  const time = req.body.time;
  console.log(time);
  Users.findOne({ email: email }).then((User) => {
    User.time = time;
    User.save().then((Result) => {
      res.json("User Saved");
    });
  });
};

exports.shopDeatils = (req, res, next) => {
  Users.findOne({ email: req.params.email }).then((User) => {
    return res.status(200).json(User);
  });
};

// available not available route here
exports.availability = (req, res, next) => {
  const email = req.body.email;
  const itemname = req.body.itemname;
  console.log(itemname);
  Users.findOne({ email: email }).then((User) => {
    User.shopItem.forEach((item) => {
      if (item.name === itemname) {
        item.itemsAvailable = true;
      }
    });
    User.save().then((Result) => {
      res.json("User Saved");
    });
  });
};
