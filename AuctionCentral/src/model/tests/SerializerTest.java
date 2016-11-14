package model.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Test;

import model.Auction;
import model.Data;
import model.Serializer;
import model.users.Nonprofit;

public class SerializerTest {

	@Test
	public void testSerializerReadAndWrite() {
		List<String> test1 = Arrays.asList("up", "down", "strange", "charm", "top", "bottom");
		List<String> test2 = Arrays.asList("up", "down", "left", "right", "centre");
		String fileName = "testSerializableOnList.ser";
		Serializer.writeFile((Serializable) test1, fileName);
		List<String> comparison = (List<String>) Serializer.readFile(fileName);
		assertEquals(test1, comparison);
		assertNotEquals(test2, comparison);
	}
	
	@Test
	public void testSerializableWithData() {
		Data original = Data.getInstance();		
		LocalDateTime ld = original.getCurrentDateTime().plusWeeks(1);
		for (int i = 0; i < 25; i++) {
			String username =  "username" + i;
			Nonprofit np = new Nonprofit("" + i, username, "email" + i, "phonenumber", ld.plusDays(i), "FreePuppies");
			original.addUser(username, np);
			Auction auc = new Auction(np, ld.plusDays(i), "AuctionName", "AuctionDescr");
			original.addAuction(auc);
		}
		String fileName = "testSerializableOnData.ser";
		Serializer.writeFile((Serializable) original, fileName);
		Data read = (Data) Serializer.readFile(fileName);
		assertEquals(original.getAuctions(), read.getAuctions());
		assertEquals(original.getCurrentDateTime(), read.getCurrentDateTime());
		for (int i = 0; i < 25; i++) {
			String u = "username" + i;
			assertTrue(original.getUser(u).getName().equals(read.getUser(u).getName()));
			assertTrue(original.getUser(u).getEmail().equals(read.getUser(u).getEmail()));
			assertTrue(original.getUser(u).getPhoneNumber().equals(read.getUser(u).getPhoneNumber()));
		}
	}
}
