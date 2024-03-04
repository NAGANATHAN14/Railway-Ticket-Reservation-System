class Passenger:
    def __init__(self, name, age, gender, berth_preference):
        self.name = name
        self.age = age
        self.gender = gender
        self.berth_preference = berth_preference
        self.allocated_berth = None
        self.allocated_berth_type = None

class TicketReservationSystem:
    def __init__(self):
        self.confirmed_tickets = [None] * 63 
        self.rac_tickets = [None] * 18
        self.waiting_list = [None] * 10
        self.booked_tickets_count = 0

    def allocate_berth(self, passenger):
        if passenger.age > 60 or (passenger.gender == 'F' and passenger.age > 5):
            for i in range(len(self.confirmed_tickets)):
                if self.confirmed_tickets[i] is None and i % 3 == 0:
                    return f'C{i + 1}', "Lower"
        elif passenger.berth_preference == 'SL':
            for i in range(len(self.rac_tickets)):
                if self.rac_tickets[i] is None:
                    return f'R{i + 1}', "Side-Lower"
        elif passenger.berth_preference == 'MW':
            for i in range(len(self.confirmed_tickets)):
                if self.confirmed_tickets[i] is None and i % 3 == 1:
                    return f'C{i + 1}', "Middle"
        else:
            for i in range(len(self.confirmed_tickets)):
                if self.confirmed_tickets[i] is None:
                    berth_type = "Lower" if i % 3 == 0 else "Middle" if i % 3 == 1 else "Upper"
                    return f'C{i + 1}', berth_type
            for i in range(len(self.rac_tickets)):
                if self.rac_tickets[i] is None:
                    return f'R{i + 1}', "Side-Lower"
            for i in range(len(self.waiting_list)):
                if self.waiting_list[i] is None:
                    return f'W{i + 1}', "Waiting List"
        return None, None

    def book_ticket(self, passenger):
        if passenger.age < 5:
            print("No tickets available for children below 5 years.")
            return

        berth, berth_type = self.allocate_berth(passenger)

        if berth is None:
            print("No tickets available.")
            return

        if berth.startswith('C'):
            self.confirmed_tickets[int(berth[1:]) - 1] = passenger
        elif berth.startswith('R'):
            self.rac_tickets[int(berth[1:]) - 1] = passenger
        elif berth.startswith('W'):
            self.waiting_list[int(berth[1:]) - 1] = passenger

        passenger.allocated_berth = berth
        passenger.allocated_berth_type = berth_type

        print(f"Ticket booked successfully. Berth: {berth} ({berth_type})")
        self.booked_tickets_count += 1

    def cancel_ticket(self, berth):
        if berth.startswith('C'):
            self.confirmed_tickets[int(berth[1:]) - 1] = None
        elif berth.startswith('R'):
            self.rac_tickets[int(berth[1:]) - 1] = None
        elif berth.startswith('W'):
            self.waiting_list[int(berth[1:]) - 1] = None

    def print_booked_tickets(self):
        print("Booked Tickets:")
        for i in range(len(self.confirmed_tickets)):
            if self.confirmed_tickets[i] is not None:
                print(f"Berth C{i + 1} ({self.confirmed_tickets[i].allocated_berth_type}): {self.confirmed_tickets[i].__dict__}")

        for i in range(len(self.rac_tickets)):
            if self.rac_tickets[i] is not None:
                print(f"Berth R{i + 1}: {self.rac_tickets[i].__dict__}")

        for i in range(len(self.waiting_list)):
            if self.waiting_list[i] is not None:
                print(f"Berth W{i + 1}: {self.waiting_list[i].__dict__}")

        print(f"Total Booked Tickets: {self.booked_tickets_count}")

    def print_available_tickets(self):
        print("Available Tickets:")
        for i in range(len(self.confirmed_tickets)):
            if self.confirmed_tickets[i] is None:
                print(f"Berth C{i + 1} is available.")

        for i in range(len(self.rac_tickets)):
            if self.rac_tickets[i] is None:
                print(f"Berth R{i + 1} is available.")

        for i in range(len(self.waiting_list)):
            if self.waiting_list[i] is None:
                print(f"Berth W{i + 1} is available.")


if __name__ == "__main__":
    railway_system = TicketReservationSystem()

    while True:
        print("\nIndian Railway Ticket Reservation System")
        print("1. Book Ticket")
        print("2. Cancel Ticket")
        print("3. Print Booked Tickets")
        print("4. Print Available Tickets")
        print("5. Exit")

        choice = input("Enter your choice: ")

        if choice == "1":
            name = input("Enter passenger name: ")
            age = int(input("Enter passenger age: "))
            gender = input("Enter passenger gender (M/F): ").upper()
            berth_preference = input("Enter berth preference (SL/UP/MW/LW): ").upper()

            passenger = Passenger(name, age, gender, berth_preference)
            railway_system.book_ticket(passenger)
        elif choice == "2":
            berth = input("Enter berth to cancel: ")
            railway_system.cancel_ticket(berth)
            print(f"Ticket for berth {berth} cancelled successfully.")
        elif choice == "3":
            railway_system.print_booked_tickets()
        elif choice == "4":
            railway_system.print_available_tickets()
        elif choice == "5":
            print("Exiting the application. Thank you!")
            break
        else:
            print("Invalid choice. Please enter a valid option.")
