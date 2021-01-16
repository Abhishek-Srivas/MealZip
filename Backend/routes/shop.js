const router = require("express").Router();

const isAuth = require("../middleware/isAuth");
const shopController = require("../controllers/shop");

router.post("/shopInfo", isAuth, shopController.shopInfo);
router.post("/shop/addItem", isAuth, shopController.addItem);
router.post("/shop/getItem", shopController.getItem);
router.post("/shop/shopOrder", shopController.shopOrders);
router.post("/shop/verifyOrder", shopController.verifyOrder);

router.get("/getCollage", shopController.getCollege);
router.get("/getCategory", shopController.getCategory);


module.exports = router;
