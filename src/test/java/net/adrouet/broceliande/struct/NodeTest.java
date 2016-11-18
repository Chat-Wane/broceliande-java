package net.adrouet.broceliande.struct;

import net.adrouet.broceliande.data.TestData;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.*;

public class NodeTest {



    @Test
    public void testNodeTransition(){
        Node<TestData, String> root = new Node<>();
        Node<TestData, String> child = new Node<>();

        Predicate<TestData> isMale = t-> t.getGender().equals("M");
        Predicate<TestData> isFemale = isMale.negate();

        child.setPredicate(isMale);
        child.setResult("YES");
        root.setLeft(child);

        child = new Node<>();
        child.setResult("NO");
        child.setPredicate(isFemale);
        root.setRight(child);

        assertEquals("YES",root.getChild(new TestData("M",12,"YES")).getResult());
        assertEquals("NO",root.getChild(new TestData("F",12,"NO")).getResult());
    }
}