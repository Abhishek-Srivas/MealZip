const router = require("express").Router();
const consumerController = require("../controllers/consumer");

router.post("/shopList", consumerController.getShop);
router.post("/placeOrder", consumerController.placeOrder);
router.put("/rating", consumerController.rating);

module.exports = router;
