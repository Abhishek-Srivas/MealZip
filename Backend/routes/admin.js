const router = require("express").Router();
const adminController = require("../controllers/admin");

router.post("/add-collage", adminController.addCollage);
router.post("/add-category", adminController.addCategory);
router.post("/delete-category", adminController.deleteCategory);

module.exports = router;
