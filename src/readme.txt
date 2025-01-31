שגיא בלמש 324227271
דור ביליה 215090598

This project is an e-commerce system where sets of sellers and buyers are defined as attributes.
The original array we used is the buyers array defined in the "Manager" class on line 6.
The String attribute we used is the name attribute defined in the "Buyer" class on line 4.

In order to operate the added features run the "Main" file and follow the menu as follows:

Option 99 (section 15):

To operate this option type 99 in the menu.
This option will call the "printNames" function on line 201 in the "MarketFacade" file.
This function uses the "getArrayNames" function to get the buyer's names in and ArrayLIst,
"getArrayNames" is in the "BuyerManager" class on line 70.

Option 100 (section 16):

To operate this option type 100 in the menu.
This option will call the "printNamesNoDoubles" function on line 210 in the "MarketFacade" file.
This function uses the "getLinkedHashmapNames" function to get the buyer's names and the number of times they appeared in the original array as key and values accordingly without duplicates,
"getLinkedHashmapNames" is in the "BuyerManager" class on line 80.

Option 101 (section 17):

To operate this option type 101 in the menu.
This option will call the "printNameOccurrences" function on line 219 in the "MarketFacade" file.
This function uses the "getLinkedHashmapNames" function to get the buyer's names and the number of times they appeared in the original array as key and values accordingly without duplicates,
"getLinkedHashmapNames" is in the "BuyerManager" class on line 80.

Option 102 (section 18):

To operate this option type 102 in the menu.
This option will call the "printReversedDoubles" function on line 230 in the "MarketFacade" file.
This function uses the "getLinkedHashmapNames" function to get the buyer's names and the number of times they appeared in the original array as key and values accordingly without duplicates,
"getLinkedHashmapNames" is in the "BuyerManager" class on line 90.
Also, it uses the functions the "printWithMyIterator" to print the names in order using our special Iterator and the "printWithMyListIterator" functions to print the names once in order and once in reverse order using our special ListIterator.
"printWithMyIterator" is in the "MarketFacade" class on line 249.
"printWithMyListIterator" is in the "MarketFacade" class on line 258.
Both of these functions use the "updateObservers" function the calls all the observers and tells them to print a specific message.
"updateObservers" is in the "MarketFacade" class on line 22.

Option 103 (section 19):

To operate this option type 103 in the menu.
This option will call the "printNamesSortedByLength" function on line 275 in the "MarketFacade" file.
This function uses the "getBuyerTreeSet" function to get the buyer objects from the original array so that they are ordered by the length of their names,
"getBuyerTreeSet" is in the "BuyerManager" class on line 54.