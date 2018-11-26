package com.phimy.model;


import java.io.Serializable;

public class MovieDB implements Serializable{
    private String title;
    private Integer vote_count;
    private Integer id;
    private String poster_path;


    public String getTitle() {
        return title;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public Integer getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    @Override
    public String toString() {
        return "MovieDB{" +
                "title='" + title + '\'' +
                ", vote_count=" + vote_count +
                ", id=" + id +
                ", poster_path='" + poster_path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return (this.id.equals(((MovieDB) obj).id));
    }
}
