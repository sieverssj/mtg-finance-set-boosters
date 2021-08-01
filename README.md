# mtg-finance-set-boosters
Little program to compute percentages of card combinations in Magic: The Gathering set boosters.

## Sources
Based on:
1. https://cardgamebase.com/mtg-set-booster-contents/
1. https://magic.wizards.com/en/articles/archive/making-magic/set-boosters-2020-07-25
1. My own findings in opening set boosters (comments in code outline these cases)

## Methodology

The approach used in this tool is to use the aforementioned sources and create a list of potential results for each slot
or set of slots in a set booster and the probability (as a percentage) for each of those options within a given slot or
set of slots. With those options defined, we produce a cartesian product of all options across all slots and compute the
probability of that particular combination of options by multiplying the probability percentage of each option. We then
collapse options that result in the same numbers of cards at each rarity and compute the total probability of receiving
that many cards at each rarity by summing the probability percentages for each analogous option. We then output the
result in a simple CSV format to stdout for further analysis in your favorite spreadsheet program.

As of this writing some slots are ignored (e.g. signed vs unsigned art card or foil vs non-fail basic land) in order to
simplify the result set. If you care about the variability in these slots you can certainly compute them, but it will
mean the output will be significantly larger.

## Reading the Output

Each row in the output represents a particular combination of rarities of cards (plus the list) that one can expect to
open in a set booster along with the likelihood of that occurrence as a percentage. The output is sorted from most
likely to least likely options.

For instance, take this line from the example output below:

`1 Rares, 3 Uncommons, 7 Commons, 0 The List",1,3,7,0,0.178403`

We can expect to receive 1 rare, 3 uncommons, 7 commons, and no cards from the list for ~17.8% of set booster packs we open.

By contrast consider the last line:

`"4 Rares, 7 Uncommons, 0 Commons, 1 The List",4,7,0,1,0.000006`

We can expect to receive 4 rares, 7 uncommons, 0 commons, and 1 card from the list only ~0.0006% of the time or 1 in
every 55 set booster boxes. :)

### Example Output
```
Label,Rares,Uncommons,Commons,The List,Percentage
"1 Rares, 3 Uncommons, 7 Commons, 0 The List",1,3,7,0,0.178403
"1 Rares, 4 Uncommons, 6 Commons, 0 The List",1,4,6,0,0.136309
"1 Rares, 2 Uncommons, 8 Commons, 0 The List",1,2,8,0,0.091838
"2 Rares, 3 Uncommons, 6 Commons, 0 The List",2,3,6,0,0.070589
"1 Rares, 5 Uncommons, 5 Commons, 0 The List",1,5,5,0,0.068709
"1 Rares, 3 Uncommons, 7 Commons, 1 The List",1,3,7,1,0.059468
"1 Rares, 4 Uncommons, 6 Commons, 1 The List",1,4,6,1,0.045436
"2 Rares, 4 Uncommons, 5 Commons, 0 The List",2,4,5,0,0.043761
"2 Rares, 2 Uncommons, 7 Commons, 0 The List",2,2,7,0,0.042060
"1 Rares, 6 Uncommons, 4 Commons, 0 The List",1,6,4,0,0.032853
"1 Rares, 2 Uncommons, 8 Commons, 1 The List",1,2,8,1,0.030613
"2 Rares, 3 Uncommons, 6 Commons, 1 The List",2,3,6,1,0.023530
"1 Rares, 5 Uncommons, 5 Commons, 1 The List",1,5,5,1,0.022903
"2 Rares, 5 Uncommons, 4 Commons, 0 The List",2,5,4,0,0.019885
"1 Rares, 7 Uncommons, 3 Commons, 0 The List",1,7,3,0,0.017129
"2 Rares, 4 Uncommons, 5 Commons, 1 The List",2,4,5,1,0.014587
"2 Rares, 2 Uncommons, 7 Commons, 1 The List",2,2,7,1,0.014020
"1 Rares, 6 Uncommons, 4 Commons, 1 The List",1,6,4,1,0.010951
"2 Rares, 6 Uncommons, 3 Commons, 0 The List",2,6,3,0,0.009782
"3 Rares, 3 Uncommons, 5 Commons, 0 The List",3,3,5,0,0.008919
"2 Rares, 5 Uncommons, 4 Commons, 1 The List",2,5,4,1,0.006628
"1 Rares, 8 Uncommons, 2 Commons, 0 The List",1,8,2,0,0.006502
"3 Rares, 2 Uncommons, 6 Commons, 0 The List",3,2,6,0,0.006306
"1 Rares, 7 Uncommons, 3 Commons, 1 The List",1,7,3,1,0.005710
"2 Rares, 7 Uncommons, 2 Commons, 0 The List",2,7,2,0,0.005256
"3 Rares, 4 Uncommons, 4 Commons, 0 The List",3,4,4,0,0.004208
"2 Rares, 6 Uncommons, 3 Commons, 1 The List",2,6,3,1,0.003261
"3 Rares, 3 Uncommons, 5 Commons, 1 The List",3,3,5,1,0.002973
"1 Rares, 8 Uncommons, 2 Commons, 1 The List",1,8,2,1,0.002167
"3 Rares, 2 Uncommons, 6 Commons, 1 The List",3,2,6,1,0.002102
"3 Rares, 5 Uncommons, 3 Commons, 0 The List",3,5,3,0,0.001873
"2 Rares, 7 Uncommons, 2 Commons, 1 The List",2,7,2,1,0.001752
"2 Rares, 8 Uncommons, 1 Commons, 0 The List",2,8,1,0,0.001587
"3 Rares, 4 Uncommons, 4 Commons, 1 The List",3,4,4,1,0.001403
"1 Rares, 9 Uncommons, 1 Commons, 0 The List",1,9,1,0,0.001293
"3 Rares, 6 Uncommons, 2 Commons, 0 The List",3,6,2,0,0.000973
"3 Rares, 5 Uncommons, 3 Commons, 1 The List",3,5,3,1,0.000624
"3 Rares, 7 Uncommons, 1 Commons, 0 The List",3,7,1,0,0.000532
"2 Rares, 8 Uncommons, 1 Commons, 1 The List",2,8,1,1,0.000529
"1 Rares, 9 Uncommons, 1 Commons, 1 The List",1,9,1,1,0.000431
"4 Rares, 3 Uncommons, 4 Commons, 0 The List",4,3,4,0,0.000346
"3 Rares, 6 Uncommons, 2 Commons, 1 The List",3,6,2,1,0.000324
"4 Rares, 2 Uncommons, 5 Commons, 0 The List",4,2,5,0,0.000302
"3 Rares, 7 Uncommons, 1 Commons, 1 The List",3,7,1,1,0.000177
"2 Rares, 9 Uncommons, 0 Commons, 0 The List",2,9,0,0,0.000172
"4 Rares, 3 Uncommons, 4 Commons, 1 The List",4,3,4,1,0.000115
"4 Rares, 4 Uncommons, 3 Commons, 0 The List",4,4,3,0,0.000108
"4 Rares, 2 Uncommons, 5 Commons, 1 The List",4,2,5,1,0.000101
"1 Rares, 10 Uncommons, 0 Commons, 0 The List",1,10,0,0,0.000100
"3 Rares, 8 Uncommons, 0 Commons, 0 The List",3,8,0,0,0.000098
"4 Rares, 5 Uncommons, 2 Commons, 0 The List",4,5,2,0,0.000060
"2 Rares, 9 Uncommons, 0 Commons, 1 The List",2,9,0,1,0.000057
"4 Rares, 4 Uncommons, 3 Commons, 1 The List",4,4,3,1,0.000036
"1 Rares, 10 Uncommons, 0 Commons, 1 The List",1,10,0,1,0.000033
"3 Rares, 8 Uncommons, 0 Commons, 1 The List",3,8,0,1,0.000033
"4 Rares, 6 Uncommons, 1 Commons, 0 The List",4,6,1,0,0.000030
"4 Rares, 5 Uncommons, 2 Commons, 1 The List",4,5,2,1,0.000020
"4 Rares, 7 Uncommons, 0 Commons, 0 The List",4,7,0,0,0.000017
"4 Rares, 6 Uncommons, 1 Commons, 1 The List",4,6,1,1,0.000010
"4 Rares, 7 Uncommons, 0 Commons, 1 The List",4,7,0,1,0.000006
```
