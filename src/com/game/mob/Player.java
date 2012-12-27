package com.game.mob;

import java.util.Date;
import javax.persistence.*;





@Entity
@Table(name="PLAYER")
public class Player extends Mob {
    
        private static final long serialVersionUID = 1L;
        
    
        
        @Column(name = "USERNAME", nullable = false)
	private String username;
        @Column(name = "PASSWORD", nullable = false)
	private String password;
        @Column(name = "REG_NICK", nullable = false)
        private String regNick;
        @Column(name = "CUR_NICK", nullable = false)
        private String curNick;
        
        @Temporal(javax.persistence.TemporalType.TIMESTAMP)
        @Column(name = "REG_DATE", nullable = false)
        private Date regDate;
        @Temporal(javax.persistence.TemporalType.TIMESTAMP)
        @Column(name = "LAST_LOGON", nullable = false)
	private Date lastLogon;
        
        private boolean isAdmin = false;
        
        
        public Player() { }
        
        public Player(  String username,
                        String password,
                        String regNick,
                        String curNick,
                        Date regDate,
                        Date lastLogon,
                        boolean isLoggedIn)
        {
            this.username = username;
            this.password = password;
            this.regNick = regNick;
            this.curNick = curNick;
            this.regDate = regDate;
            this.lastLogon = lastLogon;
        }
        
        
        
	public String getUsername() {
		return username;
	}	
	public void setUsername(String name) {
		this.username = name;
	}
	
        
	public String getPassword() {
		return password;
	}	
	public void setPassword(String pw) {
		password = pw;
	}
        
        
	public String getRegNick() {
		return regNick;
	}	
	public void setRegNick(String nick) {
		this.regNick = nick;
	}
        
        
	public String getCurNick() {
		return curNick;
	}	
	public void setCurNick(String nick) {
		this.curNick = nick;
	}
        
        
	public Date getRegDate() {
		return regDate;
	}	
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
        
	public Date getLastLogon() {
		return lastLogon;
	}	
	public void setLastLogon(Date lastLogon) {
		this.lastLogon = lastLogon;
	}
        
        
        public boolean getIsAdmin() {
		return isAdmin;
	}	
	public void setIsAdmin(boolean admin) {
		this.isAdmin = admin;
	}
	
    
}