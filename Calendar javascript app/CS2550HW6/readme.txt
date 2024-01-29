For this assignment I used Json in a relative location called "resources/event.js" which is used to store random or default event data .
This is then called in the Calendar.js file by JSON.parse which is given the method name of "obj."
The information is then processed through a for loop to define each event in the create event method. It is the
promptly added to the calendar if the event occurs that month using the addToCal. Other events can be viewed using
the next and prev buttons located right below the calendar.