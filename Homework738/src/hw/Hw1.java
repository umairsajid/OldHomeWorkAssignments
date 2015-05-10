package hw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList; 
import java.util.Hashtable;
import java.util.Scanner;

/**
 * @author Jadd Jennings
 * 
 * @description This program will generate a 
 *				frequent Item set on a given 
 *				set of transactions.
 *
 * @input: dataset where all items end with "|" and transactions values are
 * 		  separated with ",". The path of the data file will have to be
 *  	  set with the args[0] String during an execution. If this is not 
 *  	  set the program will may throw exception. Also, items must be 
 *  	  distinct, no item name can be a substring of another item.
 *
 * @ouput: all the frequent itemsets of dataset.
 */
class Hw1
{

	/**
	 * Will hold the value of minimum support count for a set to be considered frequent.
	 *  
	 **/
	int minimumSupportCount = 4;
	/**
	 * Global List of transactions that will hold Item objects
	 */
	ArrayList<Object>[] transactions; 
	/**
	 * Data structure that will current candidate Item set 
	 * based on K. This item set will change after every Apriori Generation iteration.
	 */
	ArrayList<Object>[] canidateSet; 
	
	/**
	 *Will hold the frequent item sets 
	 **/
	ArrayList<Item>  frequentItemSets= new ArrayList<Item>();
	
	/**
	 * The next two ArrayLists will be used to store data scanned from the input file.
	 *
	 **/
	
	ArrayList<String> ItemNameListInput = new ArrayList<String>(); 
    ArrayList<String> transactionsInput;

	/**
	 * Defines the Item class, the object will hold a String and Boolean value
	 *
	 * 
	 */
	private class Item
	{
		/**
		 * Items and associations will be facilitated in ItemNames string
		 */
		private String ItemNames;     
		/**
		 * Boolean exists; // used too determine whether or not item existed in transaction.
		 */
		private Boolean exists;     
 
		public Item(String nm, Boolean existing)
		{
			setItemName(new String(nm));
			setExists(new Boolean(existing));
		}
		/***
		 * Getters and Setters for data types in Item class.
		 ***/
		public void setItemName(String Param_itemNames)
		{
			ItemNames = Param_itemNames;
		}
		public String getItemName()
		{
			return ItemNames;
		}
		public String getLastItem(){
			int index = ItemNames.lastIndexOf("|");
			if (index<0) return ItemNames;
			else return ItemNames.substring(index+1);
			
			
		}
		public void setExists(Boolean Par_exists)
		{
			 exists = Par_exists;
		}
		public Boolean getExists()
		{
			return exists;
		}
		public void setExists(String substring) {
			 if(substring.equals("1")) exists= true;
			 else exists= false;
			
		}
	}
/**
 * This method takes in a candidate item set as a parameter and the minimum support. The method
 * calculates the support of each item in the candidate item set and removes the items with
 * support counts under the minimum support count. 
 * 
 * @param itemset
 * @param minSupport
 * @return itemset
 */
	public ArrayList<Object>[] pruneItemSet(ArrayList<Object>[] itemset,int minSupport ){
		Hashtable<String, Integer> SupportCounts = new Hashtable<String, Integer>();
		int count = 0;
		String[] ItemNameList = new String[itemset[0].size()];
		int[] supportCountItem = new int[itemset[0].size()];
		for (int strCounter = 0; strCounter < ItemNameList.length; strCounter++)
		{
			ItemNameList[strCounter] = new String(((Item)itemset[count].get(strCounter)).getItemName());
			supportCountItem[strCounter] = 0;
			SupportCounts.put(ItemNameList[strCounter], 0);

		}
		while (count < itemset.length)
		{
			for (int counter = 0; counter < ItemNameList.length; counter++)
			{
				if (((Item)itemset[count].get(counter)).getExists())
					SupportCounts.put(ItemNameList[counter], ++supportCountItem[counter]);
			}
			count++;
		}
		count = 0;
		while (count < itemset.length)
		{
			for ( int counter = 0; counter < ItemNameList.length; counter++)
			{
				if (SupportCounts.get(ItemNameList[counter]) < minSupport)
					itemset = removeItemFromCandidate1ItemSet(ItemNameList[counter],itemset);
			}
			count++;
		}

		
		return itemset;
	}

	/**
	 * This method depends on the pruneItemSet(x,y) method. Its purpose is too 
	 * determine the frequent 1-item sets. 
	 * 
	 **/
	public void frequent_1_Itemset()
	{
		transactions = pruneItemSet(transactions, this.minimumSupportCount);
		for (int i = 0; i < transactions.length; i++)
		{
			canidateSet[i] = new ArrayList<Object>();
			for (int j = 0; j < transactions[i].size(); j++)
			{
				canidateSet[i].add((Item)transactions[i].get((j)));
			}

		}
		for(int f=0; f < transactions[0].size();f++)
			frequentItemSets.add((Item)transactions[0].get(f));
		
	}
	@SuppressWarnings("unchecked")
	/**
	 * This is the method for apriori generation, there are no input parameters
	 * because the item set needed is globally defined. Frequent k-itemsets are
	 * stored at the end of this method. 
	 * 
	 **/
	public void aprioriGen()
	{
		ArrayList<Object>[] oldCanidateSet = new ArrayList[canidateSet.length];

		for (int i = 0; i < canidateSet.length; i++)
		{
			oldCanidateSet[i] = new ArrayList<Object>();
			for (int j = 0; j < canidateSet[i].size(); j++)
			{
				oldCanidateSet[i].add((Item)canidateSet[i].get((j)));
			}

		}
		int count = 0;
		
		Item K_item;
		Item K_Minus1_Item;
		Item newItemAssociation;
		while (count < oldCanidateSet.length)
		{
			canidateSet[count].clear();
			for (int i = 0; i < oldCanidateSet[count].size(); i++)
			{
				K_item = (Item)oldCanidateSet[count].get(i);
				for (int j = 0; j < transactions[count].size(); j++)
				{
					K_Minus1_Item = (Item)transactions[count].get(j);
					
				 	if(K_Minus1_Item.getLastItem().compareTo(K_item.getLastItem()) >0)
					{
						if(K_Minus1_Item.getItemName().indexOf(K_item.getItemName())< 0  && K_item.getItemName().indexOf(K_Minus1_Item.getItemName())< 0  )
						{
							newItemAssociation = addTwoItemSets(K_item, K_Minus1_Item);
							canidateSet[count].add(newItemAssociation);
						}
					}
				}
			}
			count++;
		}
		canidateSet = pruneItemSet(canidateSet, this.minimumSupportCount);
		for(int f=0; f < canidateSet[0].size();f++)
			frequentItemSets.add((Item)canidateSet[0].get(f));
	}
	
	/**
	 *This method removes itemsets that do not meet the mininum support count. 
	 */
	public ArrayList<Object>[] removeItemFromCandidate1ItemSet(String Item1, ArrayList<Object>[] itemset )
	{
		int index = -1;
		for (int k = 0; k < itemset[0].size(); k++)
		{
			if (((Item)itemset[0].get(k)).getItemName().equals(Item1))
			{
				index = k;
				break;
			}

		}
		if (index >= 0){ 
			for (int i = 0; i < itemset.length; i++){
				itemset[i].remove(index);
			}
		}
		return itemset;
	}
	
	public Item addTwoItemSets(Item x, Item y)
	{
		String newItemName = new String(x.getItemName() + "|" + y.getItemName());
		Boolean existVal = x.getExists() && y.getExists();
		Item result = new Item(newItemName, existVal);
		return result;
	}	
	public String toString(){
		String output = new String("The frequent itemsets are: ");
		for (int k=0;k< frequentItemSets.size();k++)
			output = output + ("{"+ frequentItemSets.get(k).getItemName()+"}, ");
		return output;
		
	}
	@SuppressWarnings("unchecked")
	private  void readFile(String fileName) {
		
		 	
	       try {
	         File file = new File(fileName);
	         Scanner scanner = new Scanner(file).useDelimiter("\\|");
	         while (scanner.hasNext()) {
	        	 ItemNameListInput.add(scanner.next());
	           
	         }
	         ItemNameListInput.remove(ItemNameListInput.size()-1);
	         scanner.close();
	         scanner = new Scanner(file);
	         
	         transactionsInput= new ArrayList();
	          
	         scanner = new Scanner(file);
	         transactionsInput =new ArrayList();
		     while (scanner.hasNext()) {
		          	 transactionsInput.add(scanner.next());
		          	 System.out.println(transactionsInput.get(transactionsInput.size()-1));
		          	 		      
		    }
		     transactionsInput.remove(0); //we don't need the first line in the data file because it consists of the item names.
		      scanner.close();
	     
	       } catch (FileNotFoundException e) {
	         e.printStackTrace();
	       }
	  }

	@SuppressWarnings("unchecked")
	Hw1()
	{
		
		readFile("C:\\Users\\jadd\\workspace\\Homework738\\src\\hw\\Hw1.txt");
		canidateSet = new ArrayList[transactionsInput.size()];
		transactions = new ArrayList[transactionsInput.size()];
		int i = 0;
	 	Item newItem;
		String ex = new String ();
		while (i < transactions.length)
		{
			transactions[i] = new ArrayList<Object>();
			for(int j=0; j<ItemNameListInput.size(); j++){
				newItem = new Item(ItemNameListInput.get(j),false);
				ex = new String(transactionsInput.get(i).substring(j*2,j*2+1));
				newItem.setExists(ex);
				transactions[i].add(newItem);
			}
			i++;
		}
		frequent_1_Itemset();
	   int k = 0;
		while (k < this.transactions[0].size() - 1)
		{
			aprioriGen();
			k++;
		}
		return;

	}

	@SuppressWarnings("unchecked")
	public Hw1(String string) {
		readFile(string);
		canidateSet = new ArrayList[transactionsInput.size()];
		transactions = new ArrayList[transactionsInput.size()];
		int i = 0;
	 	Item newItem;
		String ex = new String ();
		while (i < transactions.length)
		{
			transactions[i] = new ArrayList<Object>();
			for(int j=0; j<ItemNameListInput.size(); j++){
				newItem = new Item(ItemNameListInput.get(j),false);
				ex = new String(transactionsInput.get(i).substring(j*2,j*2+1));
				newItem.setExists(ex);
				transactions[i].add(newItem);
			}
			i++;
		}
		frequent_1_Itemset();
	   int k = 0;
		while (k < this.transactions[0].size() - 1)
		{
			aprioriGen();
			k++;
		}
		return;
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		if(args.length<=0){
			Hw1 h = new Hw1();
			System.out.println(h.toString());
		}
		else{
			Hw1 h = new Hw1(args[0]);
			System.out.println(h.toString());
		}
		
		return;
	}

}
