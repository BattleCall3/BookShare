package com.lq.entity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
@Entity
@Table(name = "t_tradelog")
public class TradeLog{
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="tableGenerator")
	@TableGenerator(name="tableGenerator",initialValue=1,allocationSize=1)
	private int id;
	private long  deal_time;
	private int period;
	private int	way;
	@Column(length = 32)
	private String manA;
	@Column(length = 32)
	private String manB;
	private BigDecimal money; 
	
	public TradeLog(){}
	public TradeLog(long deal_time,int period,int way,String manA,String manB,BigDecimal monney){
		this.deal_time 	= deal_time;
		this.period    	= period;
		this.way 	   	= way;
		this.manA 	   	= manA;
		this.manB 	   	= manB;
		this.money 		= monney;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(long deal_time) {
		this.deal_time = deal_time;
	}
	public int getWay() {
		return way;
	}
	public void setWay(int way) {
		this.way = way;
	}
	public String getManA() {
		return manA;
	}
	public void setManA(String manA) {
		this.manA = manA;
	}
	public String getManB() {
		return manB;
	}
	public void setManB(String manB) {
		this.manB = manB;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	
	
}
