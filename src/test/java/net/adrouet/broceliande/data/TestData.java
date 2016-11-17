package net.adrouet.broceliande.data;

public class TestData {

        private String sex;
        private int age;
        private boolean result;

        public TestData(String sex, int age, boolean result) {
            this.sex = sex;
            this.age = age;
            this.result = result;
        }

        @Feature
        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Feature
        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Target
        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }