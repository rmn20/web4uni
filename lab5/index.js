const express = require('express')
const app = express()
const fs = require('fs')
app.use(express.static('public'))
app.use(express.urlencoded({extended:true}))

function LoginPageHandler(req, res){
    res.sendFile(__dirname + "/public/index.html")
}

function LoginFormHandler(req, res){
    function FileWriteErrorHandler(error){
        if(error) console.log('Error', error)
    }

    function FileReadHandler(error, data){
        if(error) console.log('Error', error)
        else {
            let users = data.split('\n')
			
            for(let i=0; i<users.length; i++) {
                let userData = users[i].split(' ')
				
                if(userData[0] == req.body.name) {
					
                    if(userData[1] == req.body.password) {
						return res.send("Long time no see, " + req.body.name)
					} else {
						return res.send("Wrong password")
					}
                }
            }
			
            fs.appendFile(__dirname + "/users.txt", req.body.name + ' ' + req.body.password + '\n', FileWriteErrorHandler)
			return res.send("It looks like you are new here, " + req.body.name)
        }
    }

    fs.readFile(__dirname + "/users.txt", 'utf8', FileReadHandler)
}

function AdminHandler(req, res){
    function FileReadHandler(error, data){
        if(error) console.log('Error', error)
        else {
            let response = "";
            let users = data.split('\n')
			
            for(let i=0; i<users.length; i++) {
                response += (users[i].split(' '))[0] + '\n'
            }
			
            res.send(response)
        }
    }
    if(req.headers.secret == 'I know the secret') {
        fs.readFile(__dirname + "/users.txt", 'utf8', FileReadHandler)
    } else {
        res.sendStatus(403)
    }
}

app.get('/login', LoginPageHandler)
app.post('/login', LoginFormHandler)
app.get('/admin', AdminHandler)

app.listen(3000)