const sendgridTRansport = require("nodemailer-sendgrid-transport");
const nodemailer = require("nodemailer");
const dotenv = require('dotenv')
dotenv.config();

const transporter = nodemailer.createTransport(
    sendgridTRansport({
      auth: {
        api_key: process.env.API_KEY,
      },
    })
  );


  exports.sendemail=(email,otp) =>{
    transporter.sendMail({
        to: email,
        from: "mealzip@gmail.com",
        subject: "TEAM MealZip",
        html:`<h1>Your OTP is ${otp} </h1>`,
    });
}