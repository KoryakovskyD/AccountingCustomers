package ru.avalon.javapp.devj120.avalontelecom.models;

import java.time.LocalDate;

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
    public String getOtherInfo(){
        String year = birth.substring(6, 10);
        System.out.println(year);

        LocalDate currentDate = LocalDate.now();
        int res = currentDate.getYear() - Integer.parseInt(year);
        return String.valueOf(res);
    }
}
