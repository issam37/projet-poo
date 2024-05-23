import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private Date appointmentDate;
    private boolean completed;
    private static List<Appointment> appointments = new ArrayList<>();

    // Constructeur
    public Appointment(Patient patient, Doctor doctor, Date appointmentDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.completed = false;
        appointments.add(this);
    }

    // Méthode pour marquer le rendez-vous comme terminé
    public void completeAppointment() {
        this.completed = true;
        System.out.println("Le rendez-vous avec " + patient.getFirstName() + " " + patient.getLastName() +
                " pour le " + appointmentDate + " avec le Dr. " + doctor.getFirstName() + " " + doctor.getLastName() +
                " a été terminé.");
        // Tu pourrais ajouter d'autres actions ici, comme générer une facture, etc.
    }

    // Méthode pour vérifier si le rendez-vous est terminé
    public boolean isCompleted() {
        return completed;
    }

    // Méthode pour vérifier si le rendez-vous est prévu pour aujourd'hui
    public boolean isToday() {
        Date today = new Date(); // Obtient la date actuelle
        return appointmentDate.getDate() == today.getDate() &&
               appointmentDate.getMonth() == today.getMonth() &&
               appointmentDate.getYear() == today.getYear();
    }

    // Méthode pour enregistrer le rendez-vous dans un fichier
    public void saveToFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        fileWriter.write(patient.getFirstName() + "," + patient.getLastName() + "," +
                doctor.getFirstName() + "," + doctor.getLastName() + "," +
                appointmentDate.getDate() + "," + (appointmentDate.getMonth() + 1) + "," + (appointmentDate.getYear() + 1900) + "," +
                completed + "\n");
        fileWriter.close();
    }

    // Méthode pour charger le rendez-vous à partir d'un fichier
    public static Appointment loadFromFile(String fileName, int index) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        String line;
        String[] values;
        for (int i= 0; i < index; i++) {
            line = fileReader.readLine();
            if (line == null) {
                throw new IOException("Index invalide.");
            }
        }
        line = fileReader.readLine();
        if (line == null) {
            throw new IOException("Index invalide.");
        }
        values = line.split(",");
        Patient patient = new Patient(values[0], values[1], "");
        Doctor doctor = new Doctor(values[2], values[3]);
        Date appointmentDate = new Date(Integer.parseInt(values[4]), Integer.parseInt(values[5]) - 1, Integer.parseInt(values[6]));
        boolean completed = Boolean.parseBoolean(values[7]);
        Appointment appointment = new Appointment(patient, doctor, appointmentDate);
        appointment.completed = completed;
        return appointment;
    }

    // Méthode pour charger tous les rendez-vous à partir d'un fichier
    public static void loadAppointments(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(",");
            Patient patient = new Patient(values[0], values[1], "");
            Doctor doctor = new Doctor(values[2], values[3]);
            Date appointmentDate = new Date(Integer.parseInt(values[4]), Integer.parseInt(values[5]) - 1, Integer.parseInt(values[6]));
            boolean completed = Boolean.parseBoolean(values[7]);
            Appointment appointment = new Appointment(patient, doctor, appointmentDate);
            appointment.completed = completed;
            appointments.add(appointment);
        }
        bufferedReader.close();
    }

    // Méthode pour enregistrer tous les rendez-vous dans un fichier
    public static void saveAppointments(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        for (Appointment appointment : appointments) {
            fileWriter.write(appointment.patient.getFirstName() + "," + appointment.patient.getLastName() + "," +
                    appointment.doctor.getFirstName() + "," + appointment.doctor.getLastName() + "," +
                    appointment.appointmentDate.getDate() + "," + (appointment.appointmentDate.getMonth() + 1) + "," + (appointment.appointmentDate.getYear() + 1900) + "," +
                    appointment.completed + "\n");
        }
        fileWriter.close();
    }

    // Méthode pour rechercher des rendez-vous dans la liste
    public static List<Appointment> searchAppointments(String keyword) {
        List<Appointment> searchResults = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.patient.getFirstName().contains(keyword) ||
                appointment.patient.getLastName().contains(keyword) ||
                appointment.doctor.getFirstName().contains(keyword) ||
                appointment.doctor.getLastName().contains(keyword) ||
                appointment.appointmentDate.toString().contains(keyword)) {
                searchResults.add(appointment);
            }
        }
        return searchResults;
    }

    // Méthode pour trier les rendez-vous par date
    public static void sortAppointmentsByDate() {
        appointments.sort((a1, a2) -> a1.appointmentDate.compareTo(a2.appointmentDate));
    }

    // Méthode pour trier les rendez-vous par docteur
    public static void sortAppointmentsByDoctor() {
        appointments.sort((a1, a2) -> a1.doctor.getLastName().compareTo(a2.doctor.getLastName()));
    }

    // Méthode pour supprimer un rendez-vous de la liste
    public static void removeAppointment(int index) {
        if (index >= 0 && index < appointments.size()) {
           appointments.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Index invalide.");
        }
    }

    // Getters et setters
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Planifier un rendez-vous pour un patient à une date donnée
    public static void scheduleAppointment(Patient patient, Doctor doctor, Date appointmentDate) throws Exception {
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().equals(patient) && appointment.getAppointmentDate().equals(appointmentDate)) {
                throw new Exception("Le patient a déjà un rendez-vous à cette heure.");
            }
        }
        Appointment appointment = new Appointment(patient, doctor, appointmentDate);
        appointments.add(appointment);
    }

    // Méthode pour récupérer tous les rendez-vous pour un médecin donné
    public static List<Appointment> getAppointmentsForDoctor(Doctor doctor) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().equals(doctor)) {
                doctorAppointments.add(appointment);
            }
        }
        return doctorAppointments;
    }
}