import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class MedicalRecord {
    private Patient patient;
    private List<String> medicalHistory;
    private List<String> surgicalHistory;
    private List<String> observations;

    // Constructor
    public MedicalRecord(Patient patient) {
        this.patient = patient;
        this.medicalHistory = new ArrayList<>();
        this.surgicalHistory = new ArrayList<>();
        this.observations = new ArrayList<>();
        loadFromFile("medicalHistory_" + patient.getPhoneNumber() + ".txt", this.medicalHistory);
        loadFromFile("surgicalHistory_" + patient.getPhoneNumber() + ".txt", this.surgicalHistory);
        loadFromFile("observations_" + patient.getPhoneNumber() + ".txt", this.observations);
    }

    // Getters and Setters
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void addMedicalHistory(String medicalHistoryEntry) throws IOException {
        this.medicalHistory.add(medicalHistoryEntry);
        writeToFile("medicalHistory_" + patient.getPhoneNumber() + ".txt", medicalHistoryEntry);
    }

    public List<String> getSurgicalHistory() {
        return surgicalHistory;
    }

    public void addSurgicalHistory(String surgicalHistoryEntry) throws IOException {
        this.surgicalHistory.add(surgicalHistoryEntry);
        writeToFile("surgicalHistory_" + patient.getPhoneNumber() + ".txt", surgicalHistoryEntry);
    }

    public List<String> getObservations() {
        return observations;
    }

    public void addObservation(String observation) throws IOException {
        this.observations.add(observation);
        writeToFile("observations_" + patient.getPhoneNumber() + ".txt", observation);
    }

    // Load a list from a file
    private void loadFromFile(String fileName, List<String> list) {
        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    list.add(line);
                }
            } catch (IOException e) {
                System.out.println("Error loading from file: " + e.getMessage());
            }
        }
    }

    // Write a single entry to a file
    private void writeToFile(String fileName, String text) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            fileWriter.write(text + "\n");
        }
    }

    // Save the entire medical record to files
    public void saveAll() throws IOException {
        saveListToFile("medicalHistory_" + patient.getPhoneNumber() + ".txt", medicalHistory);
        saveListToFile("surgicalHistory_" + patient.getPhoneNumber() + ".txt", surgicalHistory);
        saveListToFile("observations_" + patient.getPhoneNumber() + ".txt", observations);
    }

    // Save a list to a file
    private void saveListToFile(String fileName, List<String> list) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName, false))) {
            for (String entry : list) {
                fileWriter.write(entry + "\n");
            }
        }
    }

    // Delete an entry from medical history
    public void deleteMedicalHistoryEntry(int index) throws IOException {
        if (index >= 0 && index < medicalHistory.size()) {
            medicalHistory.remove(index);
            saveListToFile("medicalHistory_" + patient.getPhoneNumber() + ".txt", medicalHistory);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    // Delete an entry from surgical history
    public void deleteSurgicalHistoryEntry(int index) throws IOException {
        if (index >= 0 && index < surgicalHistory.size()) {
            surgicalHistory.remove(index);
            saveListToFile("surgicalHistory_" + patient.getPhoneNumber() + ".txt", surgicalHistory);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    // Delete an observation
    public void deleteObservation(int index) throws IOException {
        if (index >= 0 && index < observations.size()) {
            observations.remove(index);
            saveListToFile("observations_" + patient.getPhoneNumber() + ".txt", observations);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }


    public void addMedicalHistoryEntry(String medicalHistoryEntry, Date date) throws IOException {
        this.medicalHistory.add(medicalHistoryEntry + " - " + date.toString());
        writeToFile("medicalHistory_" + patient.getPhoneNumber() + ".txt", medicalHistoryEntry + " - " + date.toString());
    }
    public void addObservation(String observation, Date date) throws IOException {
        this.observations.add(observation + " - " + date.toString());
        writeToFile("observations_" + patient.getPhoneNumber() + ".txt", observation + " - " + date.toString());
    }
     private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return null;
        }
    }
    public List<String> getMedicalHistoryEntries(Date startDate, Date endDate) {
        List<String> entries = new ArrayList<>();
        for (String entry : medicalHistory) {
            Date entryDate = parseDate(entry.split(" - ")[1]);
            if (entryDate.after(startDate) && entryDate.before(endDate)) {
                entries.add(entry);
            }
        }
        return entries;
    }
    public List<String> getObservations(Date startDate, Date endDate) {
        List<String> entries = new ArrayList<>();
        for (String entry : observations) {
            Date entryDate = parseDate(entry.split(" - ")[1]);
            if (entryDate.after(startDate) && entryDate.before(endDate)) {
                entries.add(entry);
            }
        }
        return entries;
    }
    

}
