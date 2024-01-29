


function loadSyncPost() {
    var name = document.getElementById("name2Txt").value;
    var password = document.getElementById("password").value;
    var data = "userName=" + name + '&password=' + password;
    var localRequest = new XMLHttpRequest();

    // PASSING false AS THE THIRD PARAMETER TO open SPECIFIES SYNCHRONOUS
    localRequest.open("POST", "http://universe.tc.uvu.edu/cs2550/assignments/PasswordCheck/check.php", false);
    localRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    localRequest.send(data);

    // NOTE THAT THE status WILL NOT BE 200 IF THE REQUEST IS FOR A
    // LOCAL FILE.
    if (localRequest.status >0) {
	var dataDiv = document.getElementById("data4Div");

	// FOR MORE INFORMATION ABOUT JSON SEE http://json.org
	var responseJson = JSON.parse(localRequest.responseText);
    if(responseJson["result"] == "invalid")//sends error message if login fails
        {
            alert("Invalid username or password. Please try again.");
        }
        else{//finished logging in if successful
            var loginInfo = responseJson["userName"] + " " + responseJson["timestamp"];
            localStorage.setItem("cs2550timestamp",loginInfo);//store login info in local storage
            
            var stored = localStorage.getItem("cs2550timestamp");//get local storage info
         dataDiv.innerHTML = "Your result is: " + responseJson["result"];
         dataDiv.innerHTML += "<br>" + "Your username is: " + responseJson["userName"];
         dataDiv.innerHTML += "<br>" + "Your timestamp is: " + responseJson["timestamp"];
         window.location = 'calendar.html';
         return stored;
        }
    
    
    
    
    
    }
}
    
    
function init() {
    'use strict';
    
    // Confirm that document.getElementById() can be used:
    if (document && document.getElementById) {
        var loginForm = document.getElementById('loginForm');
        loginForm.onsubmit = validateForm;
    }
    
   

    
    // var loginbtn = document.getElementById('loadBtn');
     //   loginbtn.onclick = validateForm;

} // End of init() function.

// Assign an event listener to the window's load event:
window.onload = init;

window.onclick()