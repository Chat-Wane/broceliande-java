package net.adrouet.broceliande.struct;

import static org.junit.Assert.assertEquals;

import java.util.function.Predicate;

import org.junit.Test;

import net.adrouet.broceliande.algo.BestSplit;
import net.adrouet.broceliande.data.TestData;

public class NodeTest {

	@Test
	public void testNodeTransition() {
		Node<String> root = new Node<>();

		Predicate<IData> isMale = t -> ((TestData) t).getGender().equals("M");
		BestSplit splitIsMale = new BestSplit(null, isMale, null);

		root.setSplit(splitIsMale);

		Node<String> child = new Node<>();
		child.setResult("YES");
		root.setLeft(child);

		child = new Node<>();
		child.setResult("NO");
		root.setRight(child);

		assertEquals("YES", root.getChild(new TestData("M", 12, "YES")).getResult());
		assertEquals("NO", root.getChild(new TestData("F", 12, "NO")).getResult());
	}
}