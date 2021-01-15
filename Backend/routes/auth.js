const router = require("express").Router();
const authController = require("../controllers/auth");
const User = require("../models/shops");
const { body } = require("express-validator");
const isAuth = require("../middleware/isAuth");

router.post(
  "/signup",
  [
    body("email")
      .isEmail()
      .withMessage("Please Enter a Valid Email") //Stored in error object which can be retrived.
      .custom((value) => {
        //to check whether the email adress already exist or not.
        return User.findOne({ email: value }).then((UserDoc) => {
          console.log("userdoc = " + UserDoc);
          if (UserDoc) {
            // return a promise if validation done a async task
            return Promise.reject("E-mail Already already Exist");
          }
        });
      })
      .normalizeEmail(), // check for  .. or + - in the email and remove it

    body("password").trim().isLength({ min: 8 }),

    // body('name')
    //     .isEmpty()
    //     .isLength({min:6})
  ],
  authController.shopSignup
);

router.post("/signup/otp-check", authController.checkOTP);

router.post("/refreshToken", authController.refreshToken);

router.post(
  "/login",
  [
    body("email")
      .isEmail()
      .withMessage("Please Enter a Valid Email") //Stored in error object which can be retrived.
      .custom((value) => {
        //to check whether the email adress already exist or not.
        return User.findOne({ email: value }).then((UserDoc) => {
          console.log("userdoc = " + UserDoc);
          if (!UserDoc) {
            // return a promise if validation done a async task
            return Promise.reject("E-mail not Registered Exist");
          }
        });
      })
      .normalizeEmail(), // check for  .. or + - in the email and remove it

    body("password").trim().isLength({ min: 8 }),
  ],
  authController.login
);

router.post("/resendOtp", authController.resendOTP);

router.post(
  "/signup/resetOtp",
  [
    body("email")
      .isEmail()
      .withMessage("Please Enter a Valid Email") //Stored in error object which can be retrived.
      .custom((value, { req }) => {
        //to check whether the email adress already exist or not.
        return User.findOne({ email: value }).then((UserDoc) => {
          if (!UserDoc) {
            // return a promise if validation done a async task
            return Promise.reject("E-mail Does not  Exist");
          }
        });
      }) /* .normalizeEmail() */, // check for  .. or + - in the email and remove it
  ],
  authController.sendResetOtp
);

router.post("/check-Reset-Otp", authController.checkResetOtp);

router.post("/reset-Password", authController.resetPassword);

router.post("/authcheck", isAuth, (req, res, next) => {
  console.log(req.User);
  res.json("you are verfied and here");
});

module.exports = router;
