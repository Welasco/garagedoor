var http = require("http");
var https = require('https');
var express = require("express");
var app = express();
var config = require("./config.js")                                                                                         
var rpio = require('rpio');                                                                                                 
console.log("Modules Loaded");                                                                                              
                                                                                                                            
                                                                                                                            
var httpport = config.httpport                                                                                              
                                                                                                                            
                                                                                                                            
app.get("/", function(req,res) {                                                                                            
res.send("<html><body><h1>GarageDoor Opener </h1></body></html>")                                                           
});                                                                                                                         
                                                                                                                            
//Turn Pool Pump On                                                                                                         
app.get("/api/garagedoor", function(req,res) {                                                                              
    garageDoor();                                                                                                               
    res.end();                                                                                                                  
});                                                                                                                         
                                                                                                                            
///////////////////////////////////////                                                                                     
/////       Creating Server       ////                                                                                      
//////////////////////////////////////                                                                                      
                                                                                                                            
var server = http.createServer(app);                                                                                        
server.listen(httpport);                                                                                                    
                                                                                                                            
                                                                                                                            
function garageDoor() {                                                                                                     
                                                                                                                            
 //   var  led = new Gpio(4, 'out')                                                                                         
//    led.writeSync(1)                                                                                                      
    rpio.open(7, rpio.OUTPUT );                                                                                             
    rpio.write(7, rpio.LOW);                                                                                                
    setTimeout(function() {                                                                                                 
       rpio.write(7, rpio.HIGH);  // Turn LED off.                                                                          
    }, 1500);                                                                                                               
    console.log("GarageToggle");                                                                                            
}                   