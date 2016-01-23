package com.example.himanshijain.booksquare;

/**
 * Created by himanshi jain on 04-11-2015.
 */
public class BookDetails {

    String title;
    String  authors;
    int id;
    int edition;
    BookDetails(String title,String authors,int edition,int id){
        this.title=title;
        this.id=id;
        this.authors=authors;
        this.edition=edition;
    }
}
