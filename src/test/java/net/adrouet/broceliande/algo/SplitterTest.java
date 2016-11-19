package net.adrouet.broceliande.algo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.adrouet.broceliande.data.TestData;
import net.adrouet.broceliande.data.TestDataSet;
import net.adrouet.broceliande.struct.IData;

public class SplitterTest {

	@Test
	public void findBestSplitSimpleOrdered() throws Exception {
		List<IData> datas = new ArrayList<>();
		datas.add(new TestData("M", 1, "YES"));
		datas.add(new TestData("M", 2, "YES"));
		datas.add(new TestData("M", 3, "YES"));
		datas.add(new TestData("M", 4, "NO"));
		datas.add(new TestData("M", 5, "NO"));
		datas.add(new TestData("M", 6, "NO"));

		List<IData> datasCopy = new ArrayList<>(datas);
		TestDataSet testDataSet = new TestDataSet(datasCopy);

		BestSplit bestSplit = Splitter.findBestSplit(testDataSet);
		assertTrue(bestSplit.getCutPoint().test(datas.get(0)));
		assertTrue(bestSplit.getCutPoint().test(datas.get(1)));
		assertTrue(bestSplit.getCutPoint().test(datas.get(2)));
		assertFalse(bestSplit.getCutPoint().test(datas.get(3)));
		assertFalse(bestSplit.getCutPoint().test(datas.get(4)));
		assertFalse(bestSplit.getCutPoint().test(datas.get(5)));
		assertEquals(TestData.class.getMethod("getAge"), bestSplit.getFeature());
	}

	@Test
	public void findBestSplitSimpleCategorical() throws Exception {
		List<IData> datas = new ArrayList<>();
		datas.add(new TestData("M", 1, "YES"));
		datas.add(new TestData("M", 1, "YES"));
		datas.add(new TestData("M", 1, "YES"));
		datas.add(new TestData("F", 1, "NO"));
		datas.add(new TestData("F", 1, "NO"));
		datas.add(new TestData("F", 1, "NO"));

		List<IData> datasCopy = new ArrayList<>(datas);
		TestDataSet testDataSet = new TestDataSet(datasCopy);

		BestSplit bestSplit = Splitter.findBestSplit(testDataSet);
		assertTrue(bestSplit.getCutPoint().test(datas.get(0)));
		assertTrue(bestSplit.getCutPoint().test(datas.get(1)));
		assertTrue(bestSplit.getCutPoint().test(datas.get(2)));
		assertFalse(bestSplit.getCutPoint().test(datas.get(3)));
		assertFalse(bestSplit.getCutPoint().test(datas.get(4)));
		assertFalse(bestSplit.getCutPoint().test(datas.get(5)));
		assertEquals(TestData.class.getMethod("getGender"), bestSplit.getFeature());
	}

}