var currDate = new Date();
        var currDay = currDate.getDate();
        var currMonth = currDate.getMonth();
        var currYear = currDate.getYear();

  
      ListOfMonths = new Array('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December');
      DaysPerMonth = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
      
  function js1() {

    
        if(currYear<=200)//adjust for get year to get 4 digit value
        {
                currYear += 1900;
                return currYear;
        }
        else
        {
            
            return currYear;
        }
  }
function js2(currMonth) {
    return ListOfMonths[currMonth];

}

function js3(currMonth) {
    
    if(currYear%4 === 0 && currYear!=1900 && currMonth == 1)//for leap Year
        {
                //DaysPerMonth[1]=29;
                return 29;
        }
        else{
        return DaysPerMonth[currMonth];
        }
       
}


function createNewEvent(title,eventMonth,eventDay,beginTime,endTime){
       
    this.title = title;
    this.eventMonth = eventMonth;
    this.eventDay = eventDay;
    this.beginTime = beginTime;
    this.endTime = endTime;
    

}

function sunrise(){
      
       var sunrisestart = "4:57" + " A.M.";
       return sunrisestart;
}

function sunset(){
        
        var sunsetstart = "8:29" + " P.M.";
        return sunsetstart;
}

function alert1(){
        
        alert("Button Pushed");
}



//<br><b>clearCalendar():
