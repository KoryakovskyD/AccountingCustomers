package ru.avalon.javapp.devj120.avalontelecom.models;


public class CompanyClientInfo extends ClientInfo {

    private String contactName;
    private String directorName;

    public CompanyClientInfo(PhoneNumber phoneNumber, String name, String address, String contactName, String directorName) {
        super(phoneNumber, name, address);
        setContactName(contactName);
        setDirectorName(directorName);
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getContactName() {
        return contactName;
    }
    public String getDirectorName() {
       return directorName;
    }

    @Override
    public String getOtherInfo() {
        return directorName + ", " + contactName;
    }
}
