const router = require("express").Router();

const isAuth = require("../middleware/isAuth");
const shopController = require("../controllers/shop");

router.post("/shopInfo", isAuth, shopController.shopInfo);


module.exports = router;
