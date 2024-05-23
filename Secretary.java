import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Secretary {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;

    public Secretary() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void scheduleAppointment(int doctorIndex, int patientIndex, Date appointmentDate) throws Exception {
        if (doctorIndex >= 0 && doctorIndex < doctors.size() && patientIndex >= 0 && patientIndex < patients.size()) {
            Doctor doctor = doctors.get(doctorIndex);
            Patient patient = patients.get(patientIndex);

            // Check if the doctor has an appointment at the given date and time
            for (Appointment appointment : appointments) {
                if (appointment.getDoctor().equals(doctor) && appointment.getAppointmentDate().equals(appointmentDate)) {
                    throw new Exception("Le docteur a déjà un rendez-vous à cette heure.");
                }
            }

            // Check if the patient has an appointment at the given date and time
            for (Appointment appointment : appointments) {
                if (appointment.getPatient().equals(patient) && appointment.getAppointmentDate().equals(appointmentDate)) {
                    throw new Exception("Le patient a déjà un rendez-vous à cette heure.");
                }
            }
        } else {
            throw new Exception("Index de médecin ou patient invalide.");
        }
    }

    public void cancelAppointment(int appointmentIndex) throws Exception {
        if (appointmentIndex >= 0 && appointmentIndex < appointments.size()) {
            appointments.remove(appointmentIndex);
        } else {
            throw new Exception("Index de rendez-vous invalide.");
        }
    }

    public void listAppointments() {
        int appointmentIndex = 1;
        for (Appointment appointment : appointments) {
            System.out.println(appointmentIndex + ". " + appointment);
            appointmentIndex++;
        }
    }

    public void listDoctors() {
        int doctorIndex = 1;
        for (Doctor doctor : doctors) {
            System.out.println(doctorIndex + ". " + doctor);
            doctorIndex++;
        }
    }

    public void listPatients() {
        int patientIndex = 1;
        for (Patient patient : patients) {
            System.out.println(patientIndex + ". " + patient);
            patientIndex++;
        }
    }
    public Patient getPatientByPhoneNumber(String phoneNumber) {
        for (Patient patient : patients) {
            if (patient.getPhoneNumber().equals(phoneNumber)) {
                return patient;
            }
        }
        return null;
    }
 public void loadDoctors() {
        try {
            File file = new File("doctors.txt");
            if (file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    String[] values = line.split(",");
                    Doctor doctor = new Doctor(values[0], values[1]);
                    doctors.add(doctor);
                }
                fileReader.close();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des médecins : " + e.getMessage());
        }
    }
    public void loadPatients() {
        try {
            File file = new File("patients.txt");
            if (file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = fileReader.readLine())!= null) {
                    String[] values = line.split(",");
                    Patient patient = new Patient(values[0], values[1], values[2]) ;
                    patients.add(patient);
                }
                fileReader.close();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des patients : " + e.getMessage());
        }
    }


    public void loadAppointments() {
        try {
            File file = new File("appointments.txt");
            if (file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = fileReader.readLine())!= null) {
                    String[] values = line.split(",");
                    Doctor doctor = null;
                    for (Doctor d : doctors) {
                        if (d.getFirstName().equals(values[0]) && d.getLastName().equals(values[1])) {
                            doctor = d;
                            break;
                        }
                    }
                    Patient patient = getPatient(values[2], values[3], values[4]);
                    if (patient == null) {
                        System.out.println("Erreur lors du chargement des rendez-vous : patient non trouvé");
                        continue;
                    }
                    Date appointmentDate = new Date(Long.parseLong(values[5]));
                    Appointment appointment = new Appointment(patient, doctor, appointmentDate);
                    appointments.add(appointment);
                }
                fileReader.close();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des rendez-vous : " + e.getMessage());
        }
    }
    

 
    public Patient getPatient(String firstName, String lastName, String phoneNumber) {
        for (Patient patient : patients) {
            if (patient.getFirstName().equals(firstName) && patient.getLastName().equals(lastName) && patient.getPhoneNumber().equals(phoneNumber)) {
                return patient;
            }
        }
        return null;
    }
     public void saveDoctor(Doctor doctor) {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("doctors.txt", true));
            fileWriter.write(doctor.getFirstName() + "," + doctor.getLastName() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement du médecin : " + e.getMessage());
        }
    }
    public void savePatient(Patient patient) {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("patients.txt", true));
            fileWriter.write(patient.getFirstName() + "," + patient.getLastName() + "," + patient.getPhoneNumber() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement du patient : " + e.getMessage());
        }
    }

    public void saveAppointment(Appointment appointment) {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("appointments.txt", true));
            fileWriter.write(appointment.getDoctor().getFirstName() + "," + appointment.getDoctor().getLastName() + "," +
                    appointment.getPatient().getFirstName() + "," + appointment.getPatient().getLastName() + "," +
                    appointment.getPatient().getPhoneNumber() + "," + appointment.getAppointmentDate().getTime() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement du rendez-vous : " + e.getMessage());
        }
    }
    public void saveAppointments() {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("appointments.txt", false));
            for (Appointment appointment : appointments) {
                fileWriter.write(appointment.getDoctor().getFirstName() + "," + appointment.getDoctor().getLastName() + "," +
                        appointment.getPatient().getFirstName() + "," + appointment.getPatient().getLastName() + "," +
                        appointment.getPatient().getPhoneNumber() + "," + appointment.getAppointmentDate().getTime() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement des rendez-vous : " + e.getMessage());
        }
    }
    public void saveDoctors() {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("doctors.txt", false));
            for (Doctor doctor : doctors) {
                fileWriter.write(doctor.getFirstName() + "," + doctor.getLastName() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement des médecins : " + e.getMessage());
        }
    }
    private void savePatients() {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("patients.txt", false));
            for (Patient patient : patients) {
                fileWriter.write(patient.getFirstName() + "," + patient.getLastName() + "," + patient.getPhoneNumber() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement des patients : " + e.getMessage());
        }
    }
    public void loadData() {
        loadDoctors();
        loadPatients();
        loadAppointments();
    }
    public void saveData() {
        saveDoctors();
        savePatients();
        saveAppointments();
    }
    public void loadSurgicalHistory() throws IOException {
        for (Patient patient : patients) {
            patient.loadSurgicalHistory();
        }
    }

    public void loadMedicalHistory() throws IOException {
        for (Patient patient : patients) {
            patient.loadMedicalHistory();
        }
    }

    public void removeSurgicalHistoryEntry(Patient patient, int index) throws IOException {
        if (index >= 0 && index < patient.getSurgicalHistory().size()) {
            patient.removeSurgicalHistoryEntry(index);
            savePatientData(patient);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void removeObservation(Patient patient, int index) throws IOException {
        if (index >= 0 && index < patient.getObservations().size()) {
            patient.removeObservation(index);
            savePatientData(patient);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void sortMedicalHistoryByDate(Patient patient) {
        patient.sortMedicalHistoryByDate();
    }

    public void sortSurgicalHistoryByDate(Patient patient) {
        patient.sortSurgicalHistoryByDate();
    }

    public void sortObservationsByDate(Patient patient) {
        patient.sortObservationsByDate();
    }

    public void mergePatient(Patient patient, Patient other) throws IOException {
        patient.mergePatient(other);
        savePatientData(patient);
    }

    private void savePatientData(Patient patient) throws IOException {
        patient.saveMedicalHistory();
        patient.saveSurgicalHistory();
        patient.saveObservations();
    }

    public void removeMedicalHistoryEntry(Patient patient, int index) throws IOException {
        if (index >= 0 && index < patient.getMedicalHistory().size()) {
            patient.removeMedicalHistoryEntry(index);
            savePatientData(patient);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public boolean searchMedicalHistory(Patient patient, String keyword) {
        return patient.searchMedicalHistory(keyword);
    }

    public boolean searchSurgicalHistory(Patient patient, String keyword) {
        return patient.searchSurgicalHistory(keyword);
    }
    public List<Patient> getPatients() {
        return patients;
    }
    
   
    public void loadMedicalHistoryForPatient(Patient patient) throws IOException {
        String filePath = "medical_history_" + patient.getPhoneNumber() + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                patient.addMedicalHistory(line);
            }
            reader.close();
        } else {
            System.out.println("No medical history found for patient: " + patient.getPhoneNumber());
        }
    }

    public void loadSurgicalHistoryForPatient(Patient patient) throws IOException {
        String filePath = "surgical_history_" + patient.getPhoneNumber() + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                patient.addSurgicalHistory(line);
            }
            reader.close();
        } else {
            System.out.println("No surgical history found for patient: " + patient.getPhoneNumber());
        }
    }


    
}