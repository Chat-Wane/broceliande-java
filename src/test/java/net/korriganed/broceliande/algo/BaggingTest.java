package net.korriganed.broceliande.algo;

import org.junit.Test;

import net.korriganed.broceliande.algo.Bagging;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;


public class BaggingTest {

	@Test
	public void generateSamples() throws Exception {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			list.add(i);
		}


		Bagging b = new Bagging(new Random());
		List<List<Integer>> result = b.generateSamples(list,10);
		assertEquals(10, result.size());
		for (List<Integer> l : result) {
			assertEquals(15, l.size());
		}
	}

}