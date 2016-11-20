package net.adrouet.broceliande.bean;

import net.adrouet.broceliande.data.Feature;
import net.adrouet.broceliande.data.FeatureType;
import net.adrouet.broceliande.data.Target;
import net.adrouet.broceliande.struct.IData;

public class Passenger implements IData<Integer> {

	private Integer passengerId;
	private Integer survived;
	private Integer pclass;
	private String name;
	private String sex;
	private Integer age;
	private Integer sibSp;
	private Integer parch;
	private String ticket;
	private Double fare;
	private String cabin;
	private String embarked;


	public Integer getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Integer passengerId) {
		this.passengerId = passengerId;
	}

	@Target
	public Integer getSurvived() {
		return survived;
	}

	public void setSurvived(Integer survived) {
		this.survived = survived;
	}

	@Feature(FeatureType.ORDERED)
	public Integer getPclass() {
		return pclass;
	}

	public void setPclass(Integer pclass) {
		this.pclass = pclass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Feature(FeatureType.CATEGORICAL)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Feature(FeatureType.ORDERED)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Feature(FeatureType.ORDERED)
	public Integer getSibSp() {
		return sibSp;
	}

	public void setSibSp(Integer sibSp) {
		this.sibSp = sibSp;
	}

	@Feature(FeatureType.ORDERED)
	public Integer getParch() {
		return parch;
	}

	public void setParch(Integer parch) {
		this.parch = parch;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	@Feature(FeatureType.CATEGORICAL)
	public String getEmbarked() {
		return embarked;
	}

	public void setEmbarked(String embarked) {
		this.embarked = embarked;
	}

	@Override
	public Integer getResult() {
		return getSurvived();
	}
}
