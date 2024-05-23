import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private String firstName;
    private String lastName;
    private List<Appointment> appointments;
    private List<String> medicalHistory;
    private List<String> surgicalHistory;
    private List<String> observations;
    private List<String> medications;
    private List<String> treatments;

    // Constructor
    public Doctor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.appointments = new ArrayList<>();
        this.medicalHistory = new ArrayList<>();
        this.surgicalHistory = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.medications = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<String> getSurgicalHistory() {
        return surgicalHistory;
    }

    public void setSurgicalHistory(List<String> surgicalHistory) {
this.surgicalHistory = surgicalHistory;
    }

    public List<String> getObservations() {
        return observations;
    }

    public void setObservations(List<String> observations) {
        this.observations = observations;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<String> treatments) {
        this.treatments = treatments;
    }

    // Additional Methods
    public void loadMedicalHistory() throws IOException {
        this.medicalHistory = new ArrayList<>();
        File file = new File("medicalHistory.txt");
        if (file.exists()) {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fileReader.readLine()) != null) {
                this.medicalHistory.add(line);
            }
            fileReader.close();
        }
    }

    public void loadSurgicalHistory() throws IOException {
        this.surgicalHistory = new ArrayList<>();
        File file = new File("surgicalHistory.txt");
        if (file.exists()) {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fileReader.readLine()) != null) {
                this.surgicalHistory.add(line);
            }
            fileReader.close();
        }
    }

    public void loadObservations() throws IOException {
        this.observations = new ArrayList<>();
        File file = new File("observations.txt");
        if (file.exists()) {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fileReader.readLine()) != null) {
                this.observations.add(line);
            }
            fileReader.close();
        }
    }

    public void loadMedications() throws IOException {
        this.medications = new ArrayList<>();
        File file = new File("medications.txt");
        if (file.exists()) {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fileReader.readLine()) != null) {
                this.medications.add(line);
            }
            fileReader.close();
        }
    }

    public void loadTreatments() throws IOException {
        this.treatments = new ArrayList<>();
        File file = new File("treatments.txt");
        if (file.exists()) {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fileReader.readLine()) != null) {
                this.treatments.add(line);
            }
            fileReader.close();
        }
    }

    public void removeMedicalHistoryEntry(int index) throws IOException {
        if (index >= 0 && index < this.medicalHistory.size()) {
            this.medicalHistory.remove(index);
            saveMedicalHistory();
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void removeSurgicalHistoryEntry(int index) throws IOException {
        if (index >= 0 && index < this.surgicalHistory.size()) {
            this.surgicalHistory.remove(index);
            saveSurgicalHistory();
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void removeObservation(int index) throws IOException {
        if (index >= 0 && index < this.observations.size()) {
            this.observations.remove(index);
            saveObservations();
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void removeMedication(int index) throws IOException {
        if (index >= 0 && index < this.medications.size()) {
            this.medications.remove(index);
            saveMedications();
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void removeTreatment(int index) throws IOException {
        if (index >= 0 && index < this.treatments.size()) {
            this.treatments.remove(index);
            saveTreatments();
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void saveMedicalHistory() throws IOException {
        File file = new File("medicalHistory.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        for (String entry : this.medicalHistory) {
            fileWriter.write(entry + "\n");
        }
        fileWriter.close();
    }

    public void saveSurgicalHistory() throws IOException {
        File file = new File("surgicalHistory.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        for (String entry : this.surgicalHistory) {
            fileWriter.write(entry + "\n");
        }
        fileWriter.close();
    }

    public void saveObservations() throws IOException {
        File file = new File("observations.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        for (String entry : this.observations) {
            fileWriter.write(entry + "\n");
        }
        fileWriter.close();
    }

    public void saveMedications() throws IOException {
        File file = new File("medications.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        for (String entry : this.medications) {
            fileWriter.write(entry + "\n");
        }
        fileWriter.close();
    }

    public void saveTreatments() throws IOException {
        File file = new File("treatments.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        for (String entry : this.treatments) {
            fileWriter.write(entry + "\n");
        }
        fileWriter.close();
    }

    public boolean searchMedicalHistory(String keyword) {
        return this.medicalHistory.stream().anyMatch(entry -> entry.contains(keyword));
    }

    public boolean searchSurgicalHistory(String keyword) {
        return this.surgicalHistory.stream().anyMatch(entry -> entry.contains(keyword));
    }

    public boolean searchObservations(String keyword) {
        return this.observations.stream().anyMatch(entry -> entry.contains(keyword));
    }

    public boolean searchMedications(String keyword) {
        return this.medications.stream().anyMatch(entry -> entry.contains(keyword));
    }

    public boolean searchTreatments(String keyword) {
        return this.treatments.stream().anyMatch(entry -> entry.contains(keyword));
    }

    public void sortMedicalHistoryByDate() {
        this.medicalHistory.sort((a, b) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateA = LocalDate.parse(a.split(" ")[1], formatter);
            LocalDate dateB = LocalDate.parse(b.split(" ")[1], formatter);
            return dateA.compareTo(dateB);
        });
    }

    public void sortSurgicalHistoryByDate() {
        this.surgicalHistory.sort((a, b) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateA = LocalDate.parse(a.split(" ")[1], formatter);
            LocalDate dateB = LocalDate.parse(b.split(" ")[1], formatter);
            return dateA.compareTo(dateB);
});
    }

    public void sortObservationsByDate() {
        this.observations.sort((a, b) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateA = LocalDate.parse(a.split(" ")[1], formatter);
            LocalDate dateB = LocalDate.parse(b.split(" ")[1], formatter);
            return dateA.compareTo(dateB);
        });
    }

    public void sortMedicationsByDate() {
        this.medications.sort((a, b) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateA = LocalDate.parse(a.split(" ")[1], formatter);
            LocalDate dateB = LocalDate.parse(b.split(" ")[1], formatter);
            return dateA.compareTo(dateB);
        });
    }

    public void sortTreatmentsByDate() {
        this.treatments.sort((a, b) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateA = LocalDate.parse(a.split(" ")[1], formatter);
            LocalDate dateB = LocalDate.parse(b.split(" ")[1], formatter);
            return dateA.compareTo(dateB);
        });
    }

    public void mergePatient(Patient other) {
        this.surgicalHistory.addAll(other.surgicalHistory);
        this.observations.addAll(other.observations);
        this.medications.addAll(other.medications);
        this.treatments.addAll(other.treatments);
    }

    public void savePatientData() throws IOException {
        saveMedicalHistory();
        saveSurgicalHistory();
        saveObservations();
        saveMedications();
        saveTreatments();
    }
}