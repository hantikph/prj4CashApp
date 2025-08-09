package com.ciicc.ict2;

public class UserAccount {
    private final int id;
    private String name;
    private String email;
    private String number;
    private int pIdNum;
//    private boolean isAdmin;

    // setters, id is final and cannot be changed
    // value id can be randomly generated
//    public void setId(int id) {
//        this.id = id;
//    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setPIdNum(int pIdNum) {
        this.pIdNum = pIdNum;
    }
    // getters for all fields
    public int getPIdNum() {
        return pIdNum;
    }
    public String getNumber() {
        return number;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    // random id generator
    public static int generateId() {
        int idLength = 6;
        StringBuilder nums = new StringBuilder();
        for(int i=0; i<idLength; i++) {
            nums.append((int) Math.floor(Math.random()*10)); // number from 0 - 9
        }
        int acct = Integer.parseInt(String.valueOf(nums));
        System.out.println(acct);
        return acct;
    }

//    public UserAccount() {
//    }

    public UserAccount(String name, String email, String number, int pIdNum) {
        this.id = generateId();
        this.name = name;
        this.email = email;
        this.number = number;
        this.pIdNum = pIdNum;
    }

    public UserAccount(int id, String name, String email, String number, int pIdNum) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.pIdNum = pIdNum;
    }


}
