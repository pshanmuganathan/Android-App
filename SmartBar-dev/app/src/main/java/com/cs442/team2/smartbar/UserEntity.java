package com.cs442.team2.smartbar;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by SumedhaGupta on 17-11-2016.
 */

public class UserEntity implements Serializable {

    private String userId;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String height;
    private String weight;
    private String dob;
    private String age;
    private String location;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Image getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(Image profileImg) {
        this.profileImg = profileImg;
    }

    private Image profileImg;

    public UserEntity(String firstname, String lastName, String email, String password, String age, String height, String weight) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.setFirstName(firstname);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setAge(age);
        this.setHeight(height);
        this.setWeight(weight);

    }

    public UserEntity() {

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


}
