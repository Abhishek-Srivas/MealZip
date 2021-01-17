const categorySchema = require("../models/categories");
const collageSchema = require("../models/collages");
const collageList = require("../utils/collageList");

exports.addCategory = (req, res, next) => {
  const category = req.body.category;
  let categoryList = [];
  for (const i in category) {
    obj = {
      category: category[i],
    };
    categoryList.push(obj);
  }
  console.log(categoryList);
  categorySchema
    .insertMany(categoryList)
    .then((result) => {
      res.status(200).json({ message: "Category Added", result });
    })
    .catch((err) => {
      res.json("Error Found" + err);
    });
};

exports.deleteCategory = (req, res, next) => {
  const category = req.body.category;

  categorySchema
    .findOneAndDelete({ category: category })
    .then((result) => {
      res.status(200).json({ message: "Category Deleted", result });
    })
    .catch((err) => {
      res.status(500).json("Oops!!category Not Deleted");
    });
};

exports.addCollage = (req, res, next) => {
  const code = req.body.collageCode;
  const name = req.body.collageName;

  let collageObject = {
    code: code,
    name: name,
  };
  collageSchema
    .insertMany(collageObject)
    .then((result) => {
      res.json("Collages Inserted!!");
    })
    .catch((err) => {
      res.status(500).json("Collage Not Stored");
    });
  /*-------------------------------------------Code For Adding Collage Through Util list---------------------------------------*/
  // const collageListArray = [];
  // for(const i in collageList){
  //     let collageObject = {
  //         code:i,
  //         name:collageList[i],
  //     }
  //     collageListArray.push(collageObject);
  // }

  // collageSchema.insertMany(collageListArray).then(result =>{
  //     res.json("Collages Inserted!!")
  // })
  /*----------------------------------------------------------------------------------------------------------------------------*/
};
