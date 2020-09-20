const express = require("express")
const app = express()
const path = require('path')
const port = 3000;



//DATABASE STUFF
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
});                     //For apply page
const toQuery = new mongoose.Schema({
    name: String,
    'e-main-id': String,
    phn_no: Number,
    concern: String
});                     //For contact page

// 3). Creating a model
const applying = mongoose.model('newCandidate', toApply);   //Name of collection is newCandidates
const Concern = mongoose.model('newQuery', toQuery);        //Name of collection is newQuerys


//EXPRESS SPECIFIC STUFF/CONFIGRITION
app.use('/static', express.static('static'));
//app.use(express.static(__dirname + '/static'));
app.use(express.urlencoded())                   //To get data fom the page and add mongo database.

//PUG SPECIFIC STUFF
app.set('view engine', 'pug');       //now can use .pug files. for showing html files. whithout mentioning extension.
app.set('views', path.join(__dirname, '/view'));// Set the views directory



//END POINTS
app.get("/", (req, res) => {
    res.render('Index'); //to send pug file. feel free to use pug as it can contain both html and pug files.
});
app.get("/index", (req, res) => {
    res.render('Index');
});
app.get("/apply", (req, res) => {
    res.render('apply', { 'title': 'Apply in Pandey Dance Academy.com' });//to send html file use:- send file(__dirname+'fileName')
});
app.post("/apply", (req, res) => {
//console.log(req.body)//watch console log to see which data is recieved., values come with name attributes not with id or class.
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
});
app.post("/Contact", (req, res) => {
    //console.log(req.body)//watch console log to see which data is recieved, values come with name attributes not with id or class.
// 4). Creating Obects - These objects will be used as a documents
var Concern_ = new Concern(req.body);
    console.log(Concern_);
// 5). Saving in database - Must use new functon/method for every object.
Concern_.save(function (err, doc) {
    if (err) return console.error(err);
    console.log(`Document >${doc}< inserted successfully`)
});
res.render('Contact', { 'title': 'Contact-Paney Dance Academy.com' });
});

// console.log('Successfully applied.\nData saved.')

//START THE SERVER.
app.listen(port, (req, res) => {
    console.log(`Serves has been started on :- localhost:${port} `)
    // const honstname = '127.0.0.1';    console.log(`Serves has been started on :- http://${hostname}:${port}/`))
});