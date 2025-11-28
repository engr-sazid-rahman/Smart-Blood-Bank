import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

// 1. Parent Class (Inheritance & Encapsulation)
class Person {
    private String name;
    private String phoneNumber;
    private String bloodGroup;

    public Person(String name, String phoneNumber, String bloodGroup) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bloodGroup = bloodGroup;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Phone: " + phoneNumber + " | Group: " + bloodGroup;
    }
}

// 2. Child Class (Inheritance & Polymorphism)
class Donor extends Person {
    private LocalDate lastDonationDate;

    public Donor(String name, String phoneNumber, String bloodGroup, String lastDate) {
        super(name, phoneNumber, bloodGroup);
        this.lastDonationDate = LocalDate.parse(lastDate);
    }

    // Logic: Check if 3 months have passed
    public boolean isEligible() {
        LocalDate today = LocalDate.now();
        Period period = Period.between(lastDonationDate, today);
        long totalMonths = period.toTotalMonths();
        return totalMonths >= 3;
    }

    @Override
    public String toString() {
        String status = isEligible() ? "[Ready to Donate ✅]" : "[Not Eligible ❌]";
        return super.toString() + " | Last Date: " + lastDonationDate + " " + status;
    }
}

// 3. Manager Class (Abstraction of List)
class BloodBank {
    private ArrayList<Donor> donorList = new ArrayList<>();

    public void addDonor(Donor donor) {
        donorList.add(donor);
        System.out.println(">> Success: Donor added to the database.");
    }

    public void searchDonor(String group) {
        boolean found = false;
        System.out.println("\n=== Search Results for '" + group + "' ===");
        for (Donor d : donorList) {
            if (d.getBloodGroup().equalsIgnoreCase(group)) {
                System.out.println(d);
                found = true;
            }
        }
        if (!found) {
            System.out.println(">> No donors found with this blood group.");
        }
    }

    public void displayAll() {
        System.out.println("\n=== All Registered Donors ===");
        if (donorList.isEmpty()) {
            System.out.println(">> Database is empty.");
        } else {
            for (Donor d : donorList) {
                System.out.println(d);
            }
        }
    }
}

// 4. Main Class (Entry Point)
public class BloodDonationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BloodBank bank = new BloodBank();

        // Adding some dummy data for presentation
        bank.addDonor(new Donor("Sazid Rahman", "01700000000", "O+", "2023-01-01"));
        bank.addDonor(new Donor("Rahim Uddin", "01800000000", "AB-", "2025-10-01"));

        while (true) {
            System.out.println("\n========================================");
            System.out.println("    SMART BLOOD DONATION SYSTEM (SBDS)   ");
            System.out.println("========================================");
            System.out.println("1. Add New Donor");
            System.out.println("2. Search Donor (by Blood Group)");
            System.out.println("3. View All Donors");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); 
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Phone Number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter Blood Group (e.g. O+, A-): ");
                    String group = scanner.nextLine();
                    System.out.print("Last Donation Date (yyyy-MM-dd): ");
                    String date = scanner.nextLine();

                    try {
                        Donor newDonor = new Donor(name, phone, group, date);
                        bank.addDonor(newDonor);
                    } catch (DateTimeParseException e) {
                        System.out.println(">> Error: Invalid Date Format! Please use yyyy-MM-dd (e.g., 2025-11-28)");
                    }
                    break;

                case 2:
                    System.out.print("Enter Blood Group to Search: ");
                    String searchGroup = scanner.nextLine();
                    bank.searchDonor(searchGroup);
                    break;

                case 3:
                    bank.displayAll();
                    break;

                case 4:
                    System.out.println(">> Exiting System. Stay Healthy!");
                    return;

                default:
                    System.out.println(">> Invalid Choice! Try again.");
            }
        }
    }
}