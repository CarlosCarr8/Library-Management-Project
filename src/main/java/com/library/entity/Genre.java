package com.library.entity;

import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "genre")
public class Genre {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<Book> books;

    public Genre() {}

    public Genre(String type) {
        this.type = type;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }

    @Override
    public String toString() {
        return "Genre [id=" + id + ", type=" + type + "]";
    }
}
