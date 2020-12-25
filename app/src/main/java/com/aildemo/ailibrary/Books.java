package com.aildemo.ailibrary;

public class Books {

    private String BookID;

    public Books() {
    }

    public Books(String bookID) {
        BookID = bookID;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID, String tmp) {
        BookID = bookID;
    }

    public String toString(){
        return this.BookID;
    }
}
