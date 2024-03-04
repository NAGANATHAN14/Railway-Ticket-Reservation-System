# Railway-Ticket-Reservation-System

Create a console application for a railway ticket reservation system with the following functionalities:

1. Book Ticket:
  - Obtain passenger details such as Name, Age, Gender, and Berth Preference.
  - There are 63 confirmed ticket berths, 18 RAC ticket berths, and a waiting list with a limit of 10 tickets.
  - No tickets should be allocated for children below 5 years, but their details should be stored.
  - Lower berths should be allocated for persons above 60 years and ladies with children if available.
  - Side-lower berths should be allocated for RAC passengers.
  - Print "No tickets available" if the waiting-list ticket count exceeds 10.

2. Cancel Ticket:
   - When a ticket is canceled, a ticket from RAC should be confirmed, and a waiting-list ticket should move to RAC.

3. Print Booked Tickets:
   - Print details of all booked tickets, including passenger details. Provide a summary at the end with the total number of booked tickets.

4. Print Available Tickets:
   - Print details of all unoccupied tickets. Provide a summary at the end with the total number of unoccupied tickets.
