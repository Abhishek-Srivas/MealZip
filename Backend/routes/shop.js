const router = require("express").Router();

const isAuth = require("../middleware/isAuth");
const shopController = require("../controllers/shop");

router.post("/shopInfo", isAuth, shopController.shopInfo);
router.post("/shop/addItem", isAuth, shopController.addItem);
router.post("/shop/getItem", shopController.getItem);

router.get("/getCollage", shopController.getCollege);
router.get("/getCategory", shopController.getCategory);


module.exports = router;
