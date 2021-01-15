//Schemas
const User = require("../models/shops");
const Otp = require("../models/otp");
//
const bcrypt = require("bcryptjs");
const { validationResult } = require("express-validator");
const otpGenerator = require("otp-generator");
const emailSender = require("../utils/mailsender");
const JWT = require("jsonwebtoken");
const dotenv = require("dotenv");
dotenv.config();
// const { authSchema } = require('../helpers/validation_Schema');

exports.shopSignup = (req, res, next) => {
  const errors = validationResult(req);
  console.log(errors);
  if (!errors.isEmpty()) {
    const error = new Error("Validation Failed");
    error.statusCode = 422;
    error.data = errors.array();
    throw error;
  }

  const email = req.body.email;
  const password = req.body.password;
  const number = req.body.number;

  console.log("1");
  bcrypt
    .hash(password, 12)
    .then((hashedPass) => {
      const user = new User({
        isverified: "false",
        email: email,
        password: hashedPass,
        number: number,
      });

      user
        .save()
        .then((result) => {
          console.log("User Saved");
          //  res.status(200).json({message:"User Saved",result:result})
        })
        .catch((err) => {
          res.status(400).json({ message: "User Not Saved", error: err });
        });

      const OTP = otpGenerator.generate(4, {
        upperCase: false,
        specialChars: false,
        alphabets: false,
      });

      const otp = new Otp({
        otp: OTP,
        email: email,
      });

      otp
        .save()
        .then((result) => {
          console.log("Otp saved in database");
        })
        .catch((err) => {
          res.json("Otp not Saved in database");
        });

      res.send("OTP SEND CHECK YOUR EMAIL");
      return emailSender.sendemail(email, OTP);
    })
    .catch((err) => {
      console.log("here");
      next(err);
    });
};

//OTP Check
exports.checkOTP = (req, res, next) => {
  console.log("here at OTP check");
  const email = req.body.email;
  const checkOtp = req.body.otp;
  console.log("1" + email + checkOtp);
  Otp.findOne({ email: email })
    .then((otpResult) => {
      console.log("2");
      if (otpResult.otp === checkOtp) {
        console.log("3");
        User.findOne({ email: email })
          .then((user) => {
            user.isverified = "True";

            // const signAccessToken = signAccessToken(user.email,user._id.toString());
            const signAccessToken = JWT.sign(
              {
                email: user.email,
                userId: user._id.toString(),
              },
              process.env.ACCESS_TOKEN_KEY,
              { expiresIn: "1s" }
            );

            const verifyAccessToken = JWT.sign(
              {
                email: user.email,
                userId: user._id.toString(),
              },
              process.env.REFRESH_TOKEN_KEY,
              { expiresIn: "1y" }
            );

            user.save();

            res.json({
              message: "Otp Verified",
              signAccessToken,
              refreshToken: verifyAccessToken,
              userId:user._id
            });
          })
          .catch((err) => {
            res.json({ message: "Provide a registered Email" });
          });
      } else {
        res.json("otp Entered is incorrect");
      }
    })
    .catch((err) => {
      res.json("Otp expire, Please resend the email");
    });
};
//Refresh Token
exports.refreshToken = (req, res, next) => {
  const refreshToken = req.body.refreshToken;

  const payload = JWT.verify(refreshToken, process.env.REFRESH_TOKEN_KEY);

  const signAccessToken = JWT.sign(
    {
      email: payload.email,
      userId: payload.userId,
    },
    process.env.ACCESS_TOKEN_KEY,
    { expiresIn: "1h" }
  );

  const verifyAccessToken = JWT.sign(
    {
      email: payload.email,
      userId: payload.userId,
    },
    process.env.REFRESH_TOKEN_KEY,
    { expiresIn: "1y" }
  );

  res.json({ signAccessToken, refreshToken: verifyAccessToken });
};
//Login Controller
exports.login = (req, res, next) => {
  console.log("here at Login");
  const errors = validationResult(req);
  console.log(errors);
  if (!errors.isEmpty()) {
    const error = new Error("Validation Failed");
    error.statusCode = 422;
    error.data = errors.array();
    throw error;
  }
  const email = req.body.email;
  const password = req.body.password;

  User.findOne({ email: email })
    .then((user) => {
      console.log(user);
      if (user.isverified === "false") {
        //Checking if user is verified or not
        let OTP = otpGenerator.generate(4, {
          upperCase: false,
          specialChars: false,
          alphabets: false,
        });
        res.json(
          "This Email is not verified,OTP has been sent to your email please verify"
        );
        return emailSender.sendemail(email, OTP);
      }

      bcrypt
        .compare(password, user.password) // to compare the stored and entered password, returning because this will give us a promise
        .then((equal) => {
          //will get a true or false
          if (!equal) {
            const error = new Error("wrong password");
            res.status(401).json({ message: "wrong password" });
            error.statusCode = 401;
            throw error;
          }

          const signAccessToken = JWT.sign(
            {
              email: user.email,
              userId: user._id.toString(),
            },
            process.env.ACCESS_TOKEN_KEY,
            { expiresIn: "1h" }
          );

          const verifyAccessToken = JWT.sign(
            {
              email: user.email,
              userId: user._id.toString(),
            },
            process.env.REFRESH_TOKEN_KEY,
            { expiresIn: "1y" }
          );

          res.json({
            message: "User loggedin",
            signAccessToken,
            refreshToken: verifyAccessToken,
            userId:user._id
          });
        })
        .catch((err) => {
          if (!err.statusCode) {
            err.statusCode = 500;
          }
          next(err);
        });
    })
    .catch((err) => {
      res.json({ message: "Email Not Registered", error: err });
    });
};

//Resending the OTP
exports.resendOTP = (req, res, next) => {
  // extra measure's taken if, password valnerability occurs.........

  const email = req.body.email;

  let OTP = otpGenerator.generate(4, {
    upperCase: false,
    specialChars: false,
    alphabets: false,
  });

  Otp.findOneAndDelete({ email: email })
    .then((result) => {
      console.log("OTP Doc Deleted");
      const otp = new Otp({
        otp: OTP,
        email: email,
      });

      otp
        .save()
        .then((result) => {
          res.json("OTP sent to your Email");
          return emailSender.sendemail(email, OTP);
        })
        .catch((err) => {
          res.json("Otp not Saved in database");
        });
      return emailSender.sendemail(email, OTP);
    })
    .catch((err) => {
      res.json("Something went wrong");
    });
};
//PASSWORD REST CONTROLLERS
exports.sendResetOtp = (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    const error = new Error("Email not registered");
    error.statusCode = 422;
    error.data = {
      message: "Email not registered",
    };
    throw error;
  }

  const email = req.body.email;
  console.log(email);

  let OTP = otpGenerator.generate(4, {
    upperCase: false,
    specialChars: false,
    alphabets: false,
  });

  const otp = new Otp({
    otp: OTP,
    email: email,
  });

  otp
    .save()
    .then((result) => {
      res.json({ message: "OTP Send,Check Your Email" });
    })
    .catch((err) => {
      res.json({ message: "Otp not saved ", error: err });
    });
  return emailSender.sendemail(email, OTP);
};

exports.checkResetOtp = (req, res, next) => {
  const otp = req.body.otp;
  const email = req.body.email;
  console.log(otp);
  Otp.findOne({ email: email }).then((data) => {
    if (!(data.otp === otp)) {
      res.status(400).json("Otp incorrect");
    } else {
      res.status(200).json("Otp correct");
    }
  });
};

exports.resetPassword = (req, res, next) => {
  const email = req.body.email;
  const newPassword = req.body.newPassword;
  const confirmPassword = req.body.confirmPassword;

  if (newPassword != confirmPassword) {
    const error = new Error("reset failed,fields do no match");
    error.statusCode = 422;
    error.data = {
      message: "Confirm password and new password do not match",
      param: "confirmPassword",
    };
    throw error;
  }

  bcrypt.hash(newPassword, 12).then((hashedPass) => {
    User.findOne({ email: email })
      .then((user) => {
        user.password = hashedPass;
        user
          .save()
          .then((result) => {
            res.json({ messsage: "new password saved", updatedUser: result });
          })
          .catch((err) => {
            res.json(err);
          });
      })
      .catch((err) => {
        res.json({ error: err, message: "password not saved" });
      });
  });
};
