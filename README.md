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
