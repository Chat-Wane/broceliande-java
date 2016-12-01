package net.adrouet.broceliande.data;

import net.korriganed.broceliande.data.Feature;
import net.korriganed.broceliande.data.FeatureType;
import net.korriganed.broceliande.data.Target;

public class TestData {

	private String gender;
	private Integer age;
	private String result;

	public TestData(String sex, Integer age, String result) {
		this.gender = sex;
		this.age = age;
		this.result = result;
	}

	@Feature(FeatureType.CATEGORICAL)
	public String getGender() {
		return gender;
	}

	public void setGender(String sex) {
		this.gender = sex;
	}

	@Feature(FeatureType.ORDERED)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Target
	public String isResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}