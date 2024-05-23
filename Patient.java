import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Patient {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    List<String> medicalHistory;
    List<String> surgicalHistory;
    List<String> observations;
    private String id;
    public Collection<? extends String> medications;
    public Collection<? extends String> treatments;

    // Constructeur
    public Patient(String firstName, String lastName, String phoneNumber  ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.medicalHistory = new ArrayList<>();
        this.surgicalHistory = new ArrayList<>();
        this.observations = new ArrayList<>();
        
    }

    // Getters et setters
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }
     // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addMedicalHistory(String medicalHistoryEntry) throws IOException {
        if (this.medicalHistory.isEmpty()) {
            createFile("medicalHistory.txt");
        }
        this.medicalHistory.add(medicalHistoryEntry);
        writeToFile("medicalHistory.txt", medicalHistoryEntry);
    }

    public List<String> getSurgicalHistory() {
        return surgicalHistory;
    }

    public void addSurgicalHistory(String surgicalHistoryEntry) throws IOException {
        if (this.surgicalHistory.isEmpty()) {
            createFile("surgicalHistory.txt");
        }
        this.surgicalHistory.add(surgicalHistoryEntry);
        writeToFile("surgicalHistory.txt", surgicalHistoryEntry);
    }

    public List<String> getObservations() {
        return observations;
    }

    public void addObservation(String observation) throws IOException {
        if (this.observations.isEmpty()) {
            createFile("observations.txt");
        }
        this.observations.add(observation);
        writeToFile("observations.txt", observation);
    }

    private void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.createNewFile()) {
            System.out.println("Fichier " + fileName + " créé avec succès.");
        } else {
            System.out.println("Le fichier " + fileName + " existe déjà.");
        }
    }

    private void writeToFile(String fileName, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        fileWriter.write(text + "\n");
        fileWriter.close();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        return true;
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
    public void mergePatient(Patient other) {
        this.medicalHistory.addAll(other.medicalHistory);
        this.surgicalHistory.addAll(other.surgicalHistory);
        this.observations.addAll(other.observations);
    }

    void saveMedicalHistory() throws IOException {
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

    void saveSurgicalHistory() throws IOException {
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

    void saveObservations() throws IOException {
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
    public void removeMedicalHistoryEntry(int index) throws IOException {
        if (index >= 0 && index < this.medicalHistory.size()) {
            this.medicalHistory.remove(index);
            saveMedicalHistory();
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }
    public boolean searchMedicalHistory(String keyword) {
        return this.medicalHistory.stream().anyMatch(entry -> entry.contains(keyword));
    }
    public boolean searchSurgicalHistory(String keyword) {
        return this.surgicalHistory.stream().anyMatch(entry -> entry.contains(keyword));
    }
    
    public void savePatientData() throws IOException {
        saveMedicalHistory();
        saveSurgicalHistory();
        saveObservations();
    }

    public boolean searchObservations(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchObservations'");
    }

    public Appointment[] getAppointments() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppointments'");
    }
}

