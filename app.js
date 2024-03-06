const express = require("express")
const path = require('path');

const app = express()
const port = process.env.PORT || 3000;
console.log({port});

app.use(express.static(path.join(__dirname, '/assets')));

app.get("/", (req, res) => {
    res.render('Index');
});
app.get("/index", (req, res) => {
    res.render('Index');
});
app.get("/apply", (req, res) => {
    res.render('apply', { 'title': 'Apply in Pandey Dance Academy.com' });//to send html file use:- send file(__dirname+'fileName')
}).post("/apply", (req, res) => {
// 4). Creating Obects - These objects will be used as a documents
    var applying_ = new applying(req.body);
    console.log(applying_)
// 5). Saving in database - Must use new functon/method for every object.
    applying_.save().then(()=>{
        res.render('apply', { 'title': 'Apply in Pandey Dance Academy.com' });//to send html file use:- send file(__dirname+'fileName')
        console.log('Successfully applied.\nData saved.')
    }).catch((err)=>{
        console.log('Data no saved.' +err)
    });
});
app.get("/class_info", (req, res) => {
    res.render('class_info');
});
app.get("/Contact", (req, res) => {
    res.render('Contact', { 'title': 'Contact-Paney Dance Academy.com' });
}).post("/Contact", (req, res) => {
var Concern_ = new Concern(req.body);
Concern_.save(function (err, doc) {
    if (err) return console.error(err);
});
res.render('Contact', { 'title': 'Contact-Paney Dance Academy.com' });
});

app.listen(port, (req, res) => {
    console.log(`Serves has been started on :- http://localhost:${port} `)
});


// 1). Getting started
const mongoose = require('mongoose');
const bodyparser = require('body-parser')
mongoose.connect('mongodb://localhost/Dance_site', { useNewUrlParser: true });
const db = mongoose.connection;
db.on("error", console.error.bind(console, "connection error:"));

db.once("open", function () {
    console.log("Connection Successful!");
});

// 2). Creating a schema
const toApply = new mongoose.Schema({
    name: String,
    gender: String,
    danceForm: String,
    'e-main-id': String,
    phn_no: Number,
    gardien_phone_no: Number,
    address: String,
    'extra detail': String
});
const toQuery = new mongoose.Schema({
    name: String,
    'e-main-id': String,
    phn_no: Number,
    concern: String
});

// 3). Creating a model
const applying = mongoose.model('newCandidate', toApply);   //Name of collection is newCandidates
const Concern = mongoose.model('newQuery', toQuery);        //Name of collection is newQuerys

