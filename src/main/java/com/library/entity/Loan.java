package com.library.entity;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="Loan")
public class Loan {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate borrowDate;
	private LocalDate dueDate;
	private boolean returned;
	
	// A loan belongs to ONE member
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
	
	
	// A loan belongs to ONE book
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	public Loan(){}
	
	public Loan(Member member, Book book, LocalDate borrowDate, LocalDate dueDate) {
		this.member = member;
		this.book = book;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
	}

	public int getId() {
		return id;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	@Override
	public String toString() {
		return "Loan{id= " + id + ", borrow date: " + borrowDate + ", due date; " + dueDate+ ", returned: " + returned + 
				", member: " + member + ", book: " + book.getTitle() + "}";
				}
}
