package ru.avalon.javapp.devj120.avalontelecom.models;

public class PersonClientInfo extends ClientInfo {

    private String birth;

    public PersonClientInfo(PhoneNumber phoneNumber, String name, String address, String birth) {
        super(phoneNumber, name, address);
        setBirth(birth);
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
   
    public String getBirth() {
        return birth;
    }

    @Override
    public String getOtherInfo() {
        return birth;
    }
}
