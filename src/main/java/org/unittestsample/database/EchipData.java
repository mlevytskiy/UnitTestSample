package org.unittestsample.database;

public class EchipData {

    private final String nameOfHolder;
    private final String documentNumber;
    private final String issuingOrganization;
    private final String nationality;
    private final String dateOfBirth;
    private final String gender;
    private final String dateOfExpiration;
    private final String filename;

    public String getFilename() {
        return filename;
    }

    private final String optionalData;

    public EchipData(String nameOfHolder,
                     String documentNumber,
                     String issuingOrganization,
                     String nationality,
                     String dateOfBirth,
                     String gender,
                     String dateOfExpiration,
                     String filename,
                     String optionalData) {
        this.nameOfHolder = nameOfHolder;
        this.documentNumber = documentNumber;
        this.issuingOrganization = issuingOrganization;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.dateOfExpiration = dateOfExpiration;
        this.filename = filename;
        this.optionalData = optionalData;

    }

    public String getNameOfHolder() {
        return nameOfHolder;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public String getNationality() {
        return nationality;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfExpiration() {
        return dateOfExpiration;
    }

    public String getOptionalData() {
        return optionalData;
    }

}
