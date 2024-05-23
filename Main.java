import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Secretary secretary = new Secretary();

    public static void main(String[] args) throws IOException {
        secretary.loadData();

        while (true) {
            displayMainMenu();
        }
    }

    private static void displayMainMenu() throws IOException {
        System.out.println("=== Hospital Management System ===");
        System.out.println("1. Doctor Management");
        System.out.println("2. Patient Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Medical Record Management");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            doctorManagementMenu();
        } else if ("2".equals(choice)) {
            patientManagementMenu();
        } else if ("3".equals(choice)) {
            appointmentManagementMenu();
        } else if ("4".equals(choice)) {
            medicalRecordManagementMenu();
        } else if ("5".equals(choice)) {
            secretary.saveData();
            System.out.println("Data saved. Exiting...");
            System.exit(0);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void doctorManagementMenu() {
        System.out.println("=== Doctor Management ===");
        System.out.println("1. Add Doctor");
        System.out.println("2. List Doctors");
        System.out.println("3. Back to Main Menu");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            addDoctor();
        } else if ("2".equals(choice)) {
            secretary.listDoctors();
        } else if ("3".equals(choice)) {
            return;
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void patientManagementMenu() {
        System.out.println("=== Patient Management ===");
        System.out.println("1. Add Patient");
        System.out.println("2. List Patients");
        System.out.println("3. Manage Patient History");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            addPatient();
        } else if ("2".equals(choice)) {
            secretary.listPatients();
        } else if ("3".equals(choice)) {
            managePatientHistoryMenu();
        } else if ("4".equals(choice)) {
            return;
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void managePatientHistoryMenu() {
        System.out.print("Enter patient's phone number: ");
        String phoneNumber = scanner.nextLine();
        Patient patient = secretary.getPatientByPhoneNumber(phoneNumber);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("=== Manage Patient History ===");
        System.out.println("1. View Medical History");
        System.out.println("2. View Surgical History");
        System.out.println("3. Add Medical History Entry");
        System.out.println("4. Add Surgical History Entry");
        System.out.println("5. Add Observation");
        System.out.println("6. Remove Medical History Entry");
        System.out.println("7. Remove Surgical History Entry");
        System.out.println("8. Remove Observation");
        System.out.println("9. Back to Patient Management");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        try {
            if ("1".equals(choice)) {
                viewMedicalHistory(patient);
            } else if ("2".equals(choice)) {
                viewSurgicalHistory(patient);
            } else if ("3".equals(choice)) {
                addMedicalHistoryEntry(patient);
            } else if ("4".equals(choice)) {
                addSurgicalHistoryEntry(patient);
            } else if ("5".equals(choice)) {
                addObservation(patient);
            } else if ("6".equals(choice)) {
                removeMedicalHistoryEntry(patient);
            } else if ("7".equals(choice)) {
                removeSurgicalHistoryEntry(patient);
            } else if ("8".equals(choice)) {
                removeObservation(patient);
            } else if ("9".equals(choice)) {
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewMedicalHistory(Patient patient) {
        System.out.println("=== Medical History ===");
        for (String entry : patient.getMedicalHistory()) {
            System.out.println(entry);
        }
    }

    private static void viewSurgicalHistory(Patient patient) {
        System.out.println("=== Surgical History ===");
        for (String entry : patient.getSurgicalHistory()) {
            System.out.println(entry);
        }
    }

    private static void appointmentManagementMenu() {
        System.out.println("=== Appointment Management ===");
        System.out.println("1. Schedule Appointment");
        System.out.println("2. Cancel Appointment");
        System.out.println("3. List Appointments");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            scheduleAppointment();
        } else if ("2".equals(choice)) {
            cancelAppointment();
        } else if ("3".equals(choice)) {
            secretary.listAppointments();
        } else if ("4".equals(choice)) {
            return;
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void medicalRecordManagementMenu() throws IOException {
        System.out.println("=== Medical Record Management ===");
        System.out.println("1. Load Medical History");
        System.out.println("2. Load Surgical History");
        System.out.println("3. Remove Medical History Entry");
        System.out.println("4. Remove Surgical History Entry");
        System.out.println("5. Search Medical History");
        System.out.println("6. Search Surgical History");
        System.out.println("7. Back to Main Menu");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            loadMedicalHistory();
        } else if ("2".equals(choice)) {
            loadSurgicalHistory();
        } else if ("3".equals(choice)) {
            removeMedicalHistoryEntry();
        } else if ("4".equals(choice)) {
            removeSurgicalHistoryEntry();
        } else if ("5".equals(choice)) {
            searchMedicalHistory();
        } else if ("6".equals(choice)) {
            searchSurgicalHistory();
        } else if ("7".equals(choice)) {
            return;
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addDoctor() {
        System.out.print("Enter doctor's first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter doctor's last name: ");
        String lastName = scanner.nextLine();
        Doctor doctor = new Doctor(firstName, lastName);
        secretary.addDoctor(doctor);
        secretary.saveDoctor(doctor);
        System.out.println("Doctor added successfully.");
    }

    private static void addPatient() {
        System.out.print("Enter patient's first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter patient's last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter patient's phone number: ");
        String phoneNumber = scanner.nextLine();
        Patient patient = new Patient(firstName, lastName, phoneNumber);
        secretary.addPatient(patient);
        secretary.savePatient(patient);
        System.out.println("Patient added successfully.");
    }

    private static void scheduleAppointment() {
        try {
            System.out.println("Select a doctor:");
            secretary.listDoctors();
            System.out.print("Enter doctor index: ");
            int doctorIndex = Integer.parseInt(scanner.nextLine()) - 1;

            System.out.println("Select a patient:");
            secretary.listPatients();
            System.out.print("Enter patient index: ");
            int patientIndex = Integer.parseInt(scanner.nextLine()) - 1;

            System.out.print("Enter appointment date (yyyy-MM-dd HH:mm): ");
            String dateString = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date appointmentDate = dateFormat.parse(dateString);

            secretary.scheduleAppointment(doctorIndex, patientIndex, appointmentDate);
            System.out.println("Appointment scheduled successfully.");
        } catch (Exception e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        try {
            System.out.println("Select an appointment to cancel:");
            secretary.listAppointments();
            System.out.print("Enter appointment index: ");
            int appointmentIndex = Integer.parseInt(scanner.nextLine()) - 1;

            secretary.cancelAppointment(appointmentIndex);
            System.out.println("Appointment cancelled successfully.");
        } catch (Exception e) {
            System.out.println("Error cancelling appointment: " + e.getMessage());
        }
    }

    private static void addMedicalHistoryEntry(Patient patient) throws IOException {
        System.out.print("Enter medical history entry: ");
        String entry = scanner.nextLine();
        patient.addMedicalHistory(entry);
        patient.saveMedicalHistory();
        System.out.println("Medical history entry added successfully.");
    }

    private static void addSurgicalHistoryEntry(Patient patient) throws IOException {
        System.out.print("Enter surgical history entry: ");
        String entry = scanner.nextLine();
        patient.addSurgicalHistory(entry);
        patient.saveSurgicalHistory();
        System.out.println("Surgical history entry added successfully.");
    }

    private static void addObservation(Patient patient) throws IOException {
        System.out.print("Enter observation: ");
        String observation = scanner.nextLine();
        patient.addObservation(observation);
        patient.saveObservations();
        System.out.println("Observation added successfully.");
    }

    private static void removeMedicalHistoryEntry(Patient patient) throws IOException {
        System.out.println("Select a medical history entry to remove:");
        for (int i = 0; i < patient.getMedicalHistory().size(); i++) {
            System.out.println((i + 1) + ". " + patient.getMedicalHistory().get(i));
        }
        System.out.print("Enter entry index: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        patient.removeMedicalHistoryEntry(index);
        System.out.println("Medical history entry removed successfully.");
    }

    private static void removeSurgicalHistoryEntry(Patient patient) throws IOException {
        System.out.println("Select a surgical history entry to remove:");
        for (int i = 0; i < patient.getSurgicalHistory().size(); i++) {
            System.out.println((i + 1) + ". " + patient.getSurgicalHistory().get(i));
        }
        System.out.print("Enter entry index: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        patient.removeSurgicalHistoryEntry(index);
        System.out.println("Surgical history entry removed successfully.");
    }

    private static void removeObservation(Patient patient) throws IOException {
        System.out.println("Select an observation to remove:");
        for (int i = 0; i < patient.getObservations().size(); i++) {
            System.out.println((i + 1) + ". " + patient.getObservations().get(i));
        }
        System.out.print("Enter observation index: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        patient.removeObservation(index);
        System.out.println("Observation removed successfully.");
    }

    private static void loadMedicalHistory() {
        try {
            for (Patient patient : secretary.getPatients()) {
                secretary.loadMedicalHistoryForPatient(patient);
            }
            System.out.println("Medical history loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading medical history: " + e.getMessage());
        }
    }

    private static void loadSurgicalHistory() {
        try {
            for (Patient patient : secretary.getPatients()) {
                secretary.loadSurgicalHistoryForPatient(patient);
            }
            System.out.println("Surgical history loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading surgical history: " + e.getMessage());
        }
    }

    private static void removeMedicalHistoryEntry() {
        try {
            System.out.print("Enter patient's phone number: ");
            String phoneNumber = scanner.nextLine();
            Patient patient = secretary.getPatientByPhoneNumber(phoneNumber);
            if (patient != null) {
                System.out.println("Select a medical history entry to remove:");
                for (int i = 0; i < patient.getMedicalHistory().size(); i++) {
                    System.out.println((i + 1) + ". " + patient.getMedicalHistory().get(i));
                }
                System.out.print("Enter entry index: ");
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                secretary.removeMedicalHistoryEntry(patient, index);
                System.out.println("Medical history entry removed successfully.");
            } else {
                System.out.println("Patient not found.");
            }
        } catch (IOException e) {
            System.out.println("Error removing medical history entry: " + e.getMessage());
        }
    }

    private static void removeSurgicalHistoryEntry() {
        try {
            System.out.print("Enter patient's phone number: ");
            String phoneNumber = scanner.nextLine();
            Patient patient = secretary.getPatientByPhoneNumber(phoneNumber);
            if (patient != null) {
                System.out.println("Select a surgical history entry to remove:");
                for (int i = 0; i < patient.getSurgicalHistory().size(); i++) {
                    System.out.println((i + 1) + ". " + patient.getSurgicalHistory().get(i));
                }
                System.out.print("Enter entry index: ");
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                secretary.removeSurgicalHistoryEntry(patient, index);
                System.out.println("Surgical history entry removed successfully.");
            } else {
                System.out.println("Patient not found.");
            }
        } catch (IOException e) {
            System.out.println("Error removing surgical history entry: " + e.getMessage());
        }
    }

    private static void searchMedicalHistory() throws IOException {
        System.out.print("Enter patient's phone number: ");
        String phoneNumber = scanner.nextLine();
        Patient patient = secretary.getPatientByPhoneNumber(phoneNumber);
        if (patient != null) {
            System.out.print("Enter keyword to search: ");
            String keyword = scanner.nextLine();
            boolean found = secretary.searchMedicalHistory(patient, keyword);
            if (found) {
                System.out.println("Keyword found in medical history.");
            } else {
                System.out.println("Keyword not found in medical history.");
            }
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void searchSurgicalHistory() throws IOException {
        System.out.print("Enter patient's phone number: ");
        String phoneNumber = scanner.nextLine();
        Patient patient = secretary.getPatientByPhoneNumber(phoneNumber);
        if (patient != null) {
            System.out.print("Enter keyword to search: ");
            String keyword = scanner.nextLine();
            boolean found = secretary.searchSurgicalHistory(patient, keyword);
            if (found) {
                System.out.println("Keyword found in surgical history.");
            } else {
                System.out.println("Keyword not found in surgical history.");
            }
        } else {
            System.out.println("Patient not found.");
        }
    }
    
}