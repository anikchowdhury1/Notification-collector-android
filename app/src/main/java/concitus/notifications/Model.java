package concitus.notifications;

import android.graphics.Bitmap;

/**
 * Created by Anik Chowdhury.
 */


//......................... Database model to send data to firebase..............

public class Model {
    String name;
    Bitmap imaBitmap;
    private String title;
    private String INCOMING_PHONE_NUMBER;
    private String date;
    private String time_stamp;
    private String user_Phone_Number;
    private String INCOMING_SPAM_PHONE_NUMBER;
    private String Additional_DATA;

    public Model(String name, Bitmap imaBitmap) {

        this.name = name;
        this.imaBitmap = imaBitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return imaBitmap;
    }

    public void setImage(Bitmap imaBitmap) {
        this.imaBitmap = imaBitmap;
    }

    public Model() {
    }

    public Model(String title, String INCOMING_PHONE_NUMBER, String date, String time_stamp, String user_Phone_Number, String INCOMING_SPAM_PHONE_NUMBER, String Additional_DATA ) {
        this.title = title;
        this.INCOMING_PHONE_NUMBER = INCOMING_PHONE_NUMBER;
        this.date = date;
        this.time_stamp = time_stamp;
        this.user_Phone_Number = user_Phone_Number;
        this.INCOMING_SPAM_PHONE_NUMBER = INCOMING_SPAM_PHONE_NUMBER;
        this.Additional_DATA = Additional_DATA;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getINCOMING_PHONE_NUMBER() {
        return INCOMING_PHONE_NUMBER;
    }

    public void setINCOMING_PHONE_NUMBER(String INCOMING_PHONE_NUMBER) {
        this.INCOMING_PHONE_NUMBER = INCOMING_PHONE_NUMBER;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String ts) {
        this.time_stamp = time_stamp;
    }

    public String getUser_Phone_Number() {
        return user_Phone_Number;
    }

    public void setUser_Phone_Number(String user_Phone_Number) {
        this.user_Phone_Number = user_Phone_Number;
    }

    public String getINCOMING_SPAM_PHONE_NUMBER() {
        return INCOMING_SPAM_PHONE_NUMBER;
    }

    public void setINCOMING_SPAM_PHONE_NUMBER() {
        this.INCOMING_SPAM_PHONE_NUMBER = INCOMING_SPAM_PHONE_NUMBER;
    }

    public String getAdditional_DATA(){
        return  Additional_DATA;
    }

    public void setAdditional_DATA(){
        this.Additional_DATA = Additional_DATA;
    }

}
