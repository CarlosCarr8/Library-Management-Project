package com.library.entity;

import jakarta.persistence.* ;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("MEMBER")

public class Member extends User {
	
	@Column(name = "memberId")
	private int memberId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;

@OneToMany(mappedBy = "member")
private List<Loan> memberLoans = new ArrayList<>();

	 
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

public void setMemberId(int memberId) {
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

	public List<Loan> getMemberLoans(){
		return memberLoans;
	}
	
	@Override
	public String toString() {
        return "Member{" + "id=" + getId() + ", memberId=" + memberId + ", name='" + name + '\'' +
                ", email='" + email + '\'' +  ", username='" + getUsername() + '\'' + '}';
	}
	}
	

