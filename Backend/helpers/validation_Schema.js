const Joi = require('@hapi/joi')

const authSchema = Joi.object({
    name:  Joi.string().required().min(6),
    email: Joi.string().email().trim().lowercase().required(),
    password:Joi.string().min(8).required()
})

module.exports = {
    authSchema,
}