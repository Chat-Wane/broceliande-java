package net.adrouet.broceliande.struct;

import net.adrouet.broceliande.data.TestData;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.*;

public class NodeTest {



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