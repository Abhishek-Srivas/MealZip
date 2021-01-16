//Schemas
const Users = require("../models/shops");
const collageSchema = require("../models/collages");
const categorySchema = require("../models/categories");
const orderSchema = require("../models/orders");
const graphSchema = require("../models/graphModel")

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


