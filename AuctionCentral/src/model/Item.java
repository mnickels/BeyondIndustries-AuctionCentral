package model;

import java.io.Serializable;
import java.lang.Math;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import model.users.Bidder;

/**
 * The Item class stores all of the items information, such as name, donor, starting bid, and 
 * other things. It also is responsible for adding bids to itself, and searching for bidders who
 * may have bid.
 * 
 * @author Matthew Subido
 * @version November 9 2016
 */
public class Item implements Serializable {
	private static final int DAYS_IN_ADVANCE = 2;
	private static final long serialVersionUID = 1L;
	private String myName;
	private String myDonor;
	private int myQuantity;
	private String myCondition;
	private String mySize;
	private String myLocation;
	private BigDecimal myStartingBid;
	private String myDescription;
	private Map<Bidder, BigDecimal> myBids;
	private int myItemID;
	
	public Item(final String theName, final String theDonor, final int theQuantity,
			final String theCondition, final String theSize, final String theLocation,
			final BigDecimal theStartingBid, final String theDescription) {
		myName = theName;
		myDonor = theDonor;
		myQuantity = Math.max(theQuantity, 1); //Quantity cannot be negative or zero.
		myCondition = theCondition;
		mySize = theSize;
		myLocation = theLocation;
		myStartingBid = BigDecimal.ZERO.max(theStartingBid); // Starting bid cannot be negative.
		myDescription = theDescription;
		myBids = new HashMap<Bidder, BigDecimal>();
		myItemID = Data.getItemID();
	}
	/**
	 * @return true if the bidder has bid upon this item already, false if not.
	 */
	public boolean checkBidder(final Bidder theBidder) {
		return myBids.containsKey(theBidder);
	}
	/**
	 * @return the bid put upon the item by the given bidder.
	 */
	public BigDecimal getBid(final Bidder theBidder) {
		return myBids.get(theBidder);
	}
	/**
	 * If the Bidder has yet to place a bid on this Item, and the Bid's amount is greater
	 * than the Starting Bid amount, then the Bidder and their Bid are added to the Map
	 * of Bidders and Bids.
	 */
	public void addBid(final Bidder theBidder, final BigDecimal theBid) {
		if (!checkBidder(theBidder) && (theBid.doubleValue() >= myStartingBid.doubleValue())) {
			myBids.put(theBidder, theBid);
		}
	}
	
	/**
	 * @return null if Bidder did not bid on item or if theAuctionDate is more than two days away, or
	 * the amount theBidder bid if neither of those are true.
	 */
	public BigDecimal removeBid(final Bidder theBidder, final LocalDateTime theAuctionDate) {
		LocalDate check = LocalDateTime.now().toLocalDate().plusDays(DAYS_IN_ADVANCE);
		if (check.isBefore(theAuctionDate.toLocalDate())) {
			return myBids.remove(theBidder);
		} else {
			return null;
		}
	}
	
	public String getName() {
		return myName;
	}
	public void setName(final String theName) {
		myName = theName;
	}
	
	public String getDonor() {
		return myDonor;
	}
	public void setDonor(final String theDonor) {
		myDonor = theDonor;
	}
	
	public int getQuantity() {
		return myQuantity;
	}
	public void setQuantity(final int theQuantity) {
		myQuantity = theQuantity;
	}

	public String getCondition() {
		return myCondition;
	}
	public void setCondition(final String theCondition) {
		myCondition = theCondition;
	}

	public String getSize() {
		return mySize;
	}
	public void setSize(final String theSize) {
		mySize = theSize;
	}

	public String getLocation() {
		return myLocation;
	}
	public void setLocation(final String theLocation) {
		myLocation = theLocation;
	}
	
	public BigDecimal getStartingBid() {
		return myStartingBid.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
	public void setStartingBid(final BigDecimal theStartingBid) {
		myStartingBid = theStartingBid;
	}

	public String getDescription() {
		return myDescription;
	}
	public void setDescription(final String theDescription) {
		myDescription = theDescription;
	}
	
	public Map<Bidder, BigDecimal> getBids() {
		return myBids;
	}
	
	public int getItemID() {
		return myItemID;
	}
	
	public boolean equals(Item theItem) {
		if (theItem == null) {
			return false;
		}
		return myItemID == theItem.getItemID();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ");
		sb.append(myName);
		if (myDonor != null && !myDonor.equals("")) {
			sb.append("\nDonor: ");
			sb.append(myDonor);
		}
		sb.append("\nQuantity: ");
		sb.append(myQuantity);
		sb.append("\nCondition: ");
		sb.append(myCondition);
		sb.append("\nSize: ");
		sb.append(mySize);
		sb.append("\nLocation: ");
		sb.append(myLocation);
		sb.append("\nStarting Bid: ");
		sb.append(getStartingBid().toString());
		if (myDescription != null && !myDescription.equals("")) {
			sb.append("\n");
			sb.append(myDescription);
		}
		return sb.toString();
	}
}
