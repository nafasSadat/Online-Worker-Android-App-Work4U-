package com.example.Online_Worker_App;

import android.util.Log;

public class UserSearchData {

    private int id;
    private String s_name;
    private String s_lastname;
    private String s_email;
    private String s_mobile;
    private String s_age;
    private String s_job;
    private String s_experience;
    private String s_salary;
    private String s_address;
    private String s_gender;
    private float s_rating;
    private String s_image;
    private String s_Workerstate;


    public UserSearchData(int id, String name, String lastname, String email, String mobile, String age, String job,
                          String experience, String salary, String address, String gender, String image, float rating, String Workerstate) {
        this.id = id;
        this.s_name = name;
        this.s_lastname=lastname;
        this.s_email = email;
        this.s_mobile = mobile;
        this.s_age=age;
        this.s_job=job;
        this.s_experience=experience;
        this.s_salary=salary;
        this.s_address=address;
        this.s_gender = gender;
        this.s_rating = rating;
        this.s_image = image;
        this.s_Workerstate=Workerstate;
        Log.d("inside",name);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return s_name;
    }

    public void setName(String name) {
        this.s_name = name;
    }

    public String getImage() {
        return s_image;
    }

    public void setImage(String image) {
        this.s_image = image;
    }

    public String getEmail() {
        return s_email;
    }

    public void setEmail(String email) {
        this.s_email = email;
    }

    public float getRating() {
        return s_rating;
    }

    public void setRating(float rating) {
        this.s_rating = rating;
    }


    public String getGender() {
        return s_gender;
    }

    public void setGender(String gender) {
        this.s_gender = gender;
    }
    public String getMobile() {
        return s_mobile;
    }

    public void setMobile(String mobile) {
        this.s_mobile = mobile;
    }

    public String getLastname() {
        return s_lastname;
    }

    public String getAge() {
        return s_age;
    }
    public String getJob() {
        return s_job;
    }public String getExperience() {
        return s_experience;
    }
    public String getSalary() {
        return s_salary;
    }public String getAddress() {
        return s_address;
    }

public String getWorkerState(){
        return s_Workerstate;
}







}

