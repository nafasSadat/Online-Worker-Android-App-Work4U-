package com.example.Online_Worker_App;

import android.util.Log;

public class UserData {

    private int id;
    private String name;
    private String lastname;
    private String email;
    private String mobile;
    private String age;
    private String job;
    private String experience;
    private String salary;
    private String address;
    private String gender;
    private float rating;
    private String image;
    private String Workerstate;

    public UserData(int id, String name,String lastname, String email, String mobile,String age,String job,
                    String experience,String salary,String address,String gender, String image, float rating,String Workerstate) {
        this.id = id;
        this.name = name;
        this.lastname=lastname;
        this.email = email;
        this.mobile = mobile;
        this.age=age;
        this.job=job;
        this.experience=experience;
        this.salary=salary;
        this.address=address;
        this.gender = gender;
        this.rating = rating;
        this.image = image;
        this.Workerstate=Workerstate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        Log.d("UserClass",image);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAge() {
        return age;
    }
    public String getJob() {
        return job;
    }public String getExperience() {
        return experience;
    }
    public String getSalary() {
        return salary;
    }public String getAddress() {
        return address;
    }

    public String getWorkerState(){
        return Workerstate;
}
}

