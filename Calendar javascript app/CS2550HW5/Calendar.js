//Michelle Spencer
//CS2550 Assingment 2


function calendar()
{
        var stored = localStorage.getItem("cs2550timestamp");//get local storage info
        if(stored == null)
        {
        document.getElementById("loginInfo").innerHTML =  "Your local storage is: " + "clear";
        }
        else{document.getElementById("loginInfo").innerHTML =  "Your local storage is: " + stored;}
        
        
        var currDate = new Date();
        var currDay = currDate.getDate();
        var currMonth = currDate.getMonth();
       // var currYear = currDate.getYear();
      birthday = new createNewEvent("Dad's Birthday","July",16,"5pm","7pm");
      //  if(currYear<=200)//adjust for get year to get 4 digit value
        //{
          //      currYear += 1900;
        //}
       var currYear = js1();//if needed adjust year to 4 digit value
        //lists the months in order
        //ListOfMonths = new Array('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December');
        
        //DaysPerMonth = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
        //if(currYear%4 == 0 && currYear!=1900)//for leap Year
        //{
          //      DaysPerMonth[1]=29;
        //}
        //grab the number of days in the current month
        CurrMonthNumDays = js3(currMonth);
        //create title for month

        var monthYearLabel =
    js2(currMonth) + " " + currYear;
        firstDayMonth = currDate;
        firstDayMonth.setDate(1);
        firstDayMonth = firstDayMonth.getDay();
       // var HTML = "<table id = 'myTable' border=0 width=100%><tr>";
        HTML1 = '<table class="main_calendar" onload="document.getElementById(\'calendar_title\');">';
        HTML = '<tbody id="calendar_title"><tr><th colspan="7">'+monthYearLabel+'</th></tr>';
       
        HTML +='<tr class="label_weekdays"><th>Sun</th><th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th></tr><tr>';
        tableWeek = 0;//start the week number at 0
        for(i=1;i<=firstDayMonth;i++)//go through a loop to create cells for days before the month
        {
                HTML +='<td class="outsideMonthDays" style = "color:DimGray;background-color:Gainsboro">'+(DaysPerMonth[currMonth-1]-firstDayMonth+i)+'</td>';
                tableWeek++;
        }
        for(i=1;i<=CurrMonthNumDays;i++)//create cells for main month
        {
            if(tableWeek===0)//if end of week start a new week
                {
                        HTML +='<tr>';
                }
           
           
            if(currDay==i)//if it is today then highlight today
                {
                        HTML +='<td class="highlightToday" style = "color:Black;background-color:Brown">'+i+'</td>';
                }     
                else //defult style for regular days
                {
                        HTML +='<td onclick="clickedDay(this.cellIndex, this.parentNode.rowIndex)">'+i+'</td>';
                }
                tableWeek++;//increment to the next day
                if(tableWeek==7)//reset week if back on Sunday(start a new row when completed with column)
                {
                        HTML +='</tr>';
                        tableWeek=0;
                }
        }
        for(i=1;tableWeek!=0;i++)//finish off the table with days of the new month
        {
                HTML +='<td class="outsideMonthDays" style = "color:DimGray;background-color:Gainsboro">'+i+'</td>';
                tableWeek++;
                if(tableWeek==7)
                {
                        HTML +='</tr>';
                        tableWeek=0;
                }
        }
        HTML +='</tbody></table>';
      
         
        
        //document.getElementById('calendar_title');
        
        
        document.getElementById("predemo").innerHTML = HTML1;
        document.getElementById("demo").innerHTML = HTML;
       
        function addToCal(caldat) {
        
    var ex = (caldat + firstDayMonth-1) % 7;
    var r = parseInt(caldat/7)+2;
    var x = document.getElementById("demo").rows[r].cells;
    x[ex].innerHTML += "<br>" + birthday.title +" " + birthday.beginTime + "-" + birthday.endTime;
    //return;
}
        
        addToCal(birthday.eventDay);
        document.getElementById("sunrise").innerHTML = HTML3;
        document.getElementsByTagName("td")[0].innerHTML = "Hello World!";
        var place = document.getElementbyId("demo").getElementsByTagName("td");
        var mine = places.length;
        document.getElementById("sunrise").innerHTML = String(mine);
          
          
        //start at the first cell that contains an actual date
      
        

        //sunriseToday = sunrise();
       //HTML3 = "<br>" + "6:45";
       
       //  myFunction();
  /*      function myFunction() {
    var x = document.getElementById("demo").rows[0].cells;
    x[0].innerHTML = "NEW CONTENT";
}*/
        return false;
}


       function init(){
                var sunriseToday = sunrise();
                var sunsetToday = sunset();
          document.getElementById("sunrise").innerHTML += "Sunrise: " + String(sunriseToday);
    
          document.getElementById("sunset").innerHTML +="Sunset: " + String(sunsetToday) ;
          
         
          
       
        }
        
        
        function editCell(row, col)
{
       // var cell = document.getElementById("demo").rows[row].cells[col];
        //cell.innerHTML += '<br> <input type="text" value="Create Event?"/></input>';
//var newdiv=cell.createElement("div");
//var newtext=cell.createTextNode("Label div :");
//var aTextBox= this.cell.createElement('input');
//aTextBox.type = 'text';
//aTextBox.value = 'New Event 6:00';
//aTextBox.id = 'txt_cell_two_';
//newdiv.appendChild(newtext); //append text to new div
//newdiv.appendChild(aTextBox); //append text to new div
//document.getElementById("test").appendChild(newdiv); //append new div to another 
}
        
        
        function clickedDay(cellnum, rownum){
                var cells1 = document.getElementsByTagName("td");
                var col = cellnum;
                var row = rownum;
                var date = document.getElementById("demo").rows[row].cells[col].innerHTML;
                var finishdate = document.getElementById("demo").rows[0].cells[0].innerHTML
                document.getElementById("predemo").innerHTML = "Selected Date: " + String(date) + "\n" + finishdate;
                
               // var cells = document.getElementsByTagName("td");
                //cells[1].innerHTML += "meep";
                
                var cell = document.getElementById("demo").rows[row].cells[col];
               var customEvent = prompt('Please enter Event Name and Time.');
                cell.innerHTML += '<br>' + customEvent;
               //editCell(row, col);
         return false;
        // document.getElementsByTagName("td")[0].innerHTML = cell.rows.value;
        }
        window.onload = init;
        
