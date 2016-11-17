package net.adrouet.broceliande.data;

public class TestData {
        private String gender;
        private int age;
        private boolean result;

        public TestData(String sex, int age, boolean result) {
            this.gender = sex;
            this.age = age;
            this.result = result;
        }

        public String getSex() {
            return gender;
        }

        public void setSex(String sex) {
            this.gender = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }