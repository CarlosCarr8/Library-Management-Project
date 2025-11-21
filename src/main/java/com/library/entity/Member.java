package com.library.entity;

import jakarta.persistence.* ;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("MEMBER")

public class Member extends User {
private int memberId;
private String name;
private String email;

@Transient
private List<Book> borrowedBooks = new ArrayList<>();
	 
public Member() {} 
	 
public Member(int memberId, String username, String password, String name, String email) {
super(username, password);
this.memberId=memberId;
this.name=name;
this.email=email;
		
}
	
public int getMemberId() {
return memberId;
}

public void setMmberId(int memberId) {
this.memberId=memberId;
}

	
public String getName() {
return name;
}
	
	public void setName(String name) {
		this.name=name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public List<Book> getBorrowedBooks(){
		return borrowedBooks;
	}
	public void borrowBook(Book book) {
		borrowedBooks.add(book);	
		}
	public void returnBook(Book book) {
		borrowedBooks.remove(book);
	}
	@Override
	public String toString() {
        return "Member{" + "id=" + getId() + ", memberId=" + memberId + ", name='" + name + '\'' +
                ", email='" + email + '\'' +  ", username='" + getUsername() + '\'' + '}';
	}
	}
	

