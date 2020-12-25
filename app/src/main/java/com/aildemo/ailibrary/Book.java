package com.aildemo.ailibrary;

public class Book {

    private String BookID;
    private String Title;
    private String Author;
    private String Status;
    private String Loc;
    private String Edition;

    public Book() {
    }

    public Book(String bookID, String title, String author, String status, String loc, String edition) {
        BookID = bookID;
        Title = title;
        Author = author;
        Status = status;
        Loc = loc;
        Edition = edition;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLoc() {
        return Loc;
    }

    public void setLoc(String loc) {
        Loc = loc;
    }

    public String getEdition() {
        return Edition;
    }

    public void setEdition(String edition) {
        Edition = edition;
    }

    public String toString(){
        return this.BookID + " - "  + Title + " - " + Author + " - " + Status;
    }

}
