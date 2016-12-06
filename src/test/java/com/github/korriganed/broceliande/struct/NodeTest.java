package com.github.korriganed.broceliande.struct;

import static org.junit.Assert.assertEquals;

import java.util.function.Predicate;

import com.github.korriganed.broceliande.algo.BestSplit;
import org.junit.Test;

import com.github.korriganed.broceliande.data.TestData;

public class NodeTest {

	@Test
	public void testNodeTransition() {
		Node<TestData, String> root = new Node<>();

		Predicate<TestData> isMale = t -> ((TestData) t).getGender().equals("M");
		BestSplit splitIsMale = new BestSplit(null, isMale, null);

		root.setSplit(splitIsMale);

		Node<TestData, String> child = new Node<>();
		child.setResult("YES");
		root.setLeft(child);

		child = new Node<>();
		child.setResult("NO");
		root.setRight(child);

		assertEquals("YES", root.getChild(new TestData("M", 12, "YES")).getResult());
		assertEquals("NO", root.getChild(new TestData("F", 12, "NO")).getResult());
	}
}