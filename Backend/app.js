const express = require("express");
const mongoose = require("mongoose"); // importing mongoose
const dotenv = require("dotenv");
const createError = require("http-errors");
const bodyParser = require("body-parser");
//Import Routes
const authRoutes = require("./routes/auth");
const consumerAuthRoutes = require("./routes/auth.consumer");
const shopRoutes = require("./routes/shop");
const adminRoutes = require("./routes/admin");
const consumerRoutes = require("./routes/consumer");

dotenv.config();

const app = express();

const PORT = process.env.PORT || 3000 ;

app.use(bodyParser.json());

// To remove CROS (cross-resource-origin-platform) problem
app.use((req, res, next) => {
  res.setHeader("Access-Control-Allow-Origin", "*"); // to allow all client we use *
  res.setHeader(
    "Access-Control-Allow-Methods",
    "OPTIONS,GET,POST,PUT,PATCH,DELETE"
  ); //these are the allowed methods
  res.setHeader("Access-Control-Allow-Headers", "*"); // allowed headers (Auth for extra data related to authoriaztiom)
  next();
});

//Route Middleware
app.use(authRoutes);
app.use(consumerAuthRoutes);
app.use(shopRoutes);
app.use("/consumer", consumerRoutes);
app.use("/admin", adminRoutes);

//Page Not Found
app.use((req, res, next) => {
  next(createError.NotFound("Page not Found"));
});

//errorHandeling Middleware
app.use((err, req, res, next) => {
  console.log(err);
  console.log("here");
  res.status(err.statusCode || 500);
  res.send({
    error: {
      status: err.statusCode || 500,
      message: err,
    },
  });
});

mongoose
  .connect(process.env.DB_CONNECT, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useCreateIndex: true,
  })
  .then(() => {
    app.listen(PORT);
    console.log("server started");
  })
  .catch((err) => {
    console.log(err);
  });
