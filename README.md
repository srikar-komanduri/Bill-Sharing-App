# Bill-Sharing-App

## What is this application about?
This is a Java Application where users can create expenses, add other users to it to settle their shares

## Example
there are 4 users A,B,C,D. they all went to a restaurant where A spent $100 , B spent $200 , C spent $105.5, D spent $0.<br/> A paid $405.5 in total to restaurant. <br/>
Therefore,<br/>
B owes A $200 <br/>
C owes A $105.5

So, A creates a Expense group with B and C in it. A assigns B a share of $200 , C a share of $105.5.
As long as B,C don't pay their contributions, expense will be in **PENDING** state.
when they do pay, the expense goes to **SETTLED** state.


## Features : 
* We can create multiple users
* A user can create/be part of multiple expenses
* All users who have share for an expense can be made into a group
* Users can make multiple transactions to repay a share
* Get list of all pending expenses

## Features planning to be added
- [ ] Multiple contributions by different users for same expense
- [ ] Get Transaction History of a user
- [ ] Add Equal/Percentage/exact Mode to split an expense 

## Where is the main function? 
src/bill/share/BillShare.java
