package net.adrouet.broceliande.struct;

import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.*;

public class NodeTest {

    class TestData {
        private String sex;
        private int age;
        private boolean result;

        public TestData(String sex, int age, boolean result) {
            this.sex = sex;
            this.age = age;
            this.result = result;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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

    @Test
    public void testNodeTransition(){
        Node<TestData, Boolean> root = new Node<>();
        Node<TestData, Boolean> child = new Node<>();

        Predicate<TestData> isMale = t-> t.getSex().equals("M");
        Predicate<TestData> isFemale = isMale.negate();

        child.setPredicate(isMale);
        child.setResult(true);
        root.getChildren().add(child);

        child = new Node<>();
        child.setResult(false);
        child.setPredicate(isFemale);
        root.getChildren().add(child);

        assertTrue(root.getChild(new TestData("M",12,true)).getResult());
        assertFalse(root.getChild(new TestData("F",12,true)).getResult());
    }
}