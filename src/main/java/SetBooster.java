import com.google.common.base.CharMatcher;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SetBooster {
    // Welcome - Art
    private static final List<SlotOption> artSlot = Arrays.asList(
            new SlotOption("A", .95),
            new SlotOption("B", .05) // Signed
    );

    // Welcome - Land
    private static final List<SlotOption> landSlot = Arrays.asList(
            new SlotOption("L", .85),
            new SlotOption("M", .15) // Foil
    );

    // Welcome - Connected
    private static final List<SlotOption> connectedSlot = Arrays.asList(
            new SlotOption("CCCCCU", .35),
            new SlotOption("CCCCUU", .40),
            new SlotOption("CCCUUU", .125),
            new SlotOption("CCUUUU", .07),
            new SlotOption("CUUUUU", .035),
            new SlotOption("UUUUUU", .02)
    );

    // Fireworks - Head-Turning
    // 2021/08/01 - AFR Set Booster Box had this slot as exclusively being uncommons
    private static final List<SlotOption> headTurningSlot = Collections.singletonList(new SlotOption("U", 1)
    );

    // Fireworks - Wild Cards
    private static final List<SlotOption> wildCardSlot = Arrays.asList(
            new SlotOption("CC", .49),
            new SlotOption("CU", .245),
            new SlotOption("CR", .175),
            new SlotOption("UU", .031),
            new SlotOption("UR", .043),
            new SlotOption("RR", .016)
    );

    // Big Finish - Rare Slot
    private static final List<SlotOption> rareSlot = Collections.singletonList(new SlotOption("R", 1));

    // Big Finish - Foil
    private static final List<SlotOption> foilSlot = Arrays.asList(
            new SlotOption("C", .714),
            new SlotOption("U", .214),
            new SlotOption("R", .072)
    );

    // Epilogue - Token
    private static final List<SlotOption> tokenSlot = Arrays.asList(
            new SlotOption("T", .75),
            new SlotOption("V", .25)
    );


    public static void main(String[] args) {
        List<List<SlotOption>> lists = Lists.cartesianProduct(/*artSlot, landSlot,*/ connectedSlot, headTurningSlot, wildCardSlot, rareSlot, foilSlot, tokenSlot);
        Multimap<String, Double> collect = lists.stream()
                .map(packPermutation -> packPermutation.stream().reduce(
                        new SlotOption("", 1),
                        (a, b) -> new SlotOption(a.label() + b.label(), a.percentage() * b.percentage())))
                .map(slotOption -> {
                    char[] labelChars = slotOption.label().toCharArray();
                    Arrays.sort(labelChars);
                    return new PackPermutation(String.valueOf(labelChars), slotOption.percentage());
                })
                .collect(ArrayListMultimap::create,
                        (map, packPermutation) -> map.put(packPermutation.label(), packPermutation.percentage()),
                        (map1, map2) -> map1.putAll(map2));
        List<HumanReadablePack> finalPermutations = Multimaps.asMap(collect)
                .entrySet()
                .stream()
                .map(entry -> new PackPermutation(entry.getKey(), entry.getValue().stream()
                        .reduce(0.00, Double::sum, Double::sum)))
                .sorted(Comparator.comparingDouble(PackPermutation::percentage).reversed())
                .map(packPermutation -> new HumanReadablePack(
                        CharMatcher.is('R').countIn(packPermutation.label()),
                        CharMatcher.is('U').countIn(packPermutation.label()),
                        CharMatcher.is('C').countIn(packPermutation.label()),
                        CharMatcher.is('V').countIn(packPermutation.label()),
                        String.format("%.6f", packPermutation.percentage())))
                .collect(Collectors.toList());

        System.out.println("Label,Rares,Uncommons,Commons,The List,Percentage");
        finalPermutations.forEach(pack -> System.out.printf("%s,%d,%d,%d,%d,%s%n",
                humanReadableLabel(pack),
                pack.rares(),
                pack.uncommons(),
                pack.commons(),
                pack.theList(),
                pack.percentage()));
    }

    private static String humanReadableLabel(HumanReadablePack pack) {
        return String.format("\"%d Rares, %d Uncommons, %d Commons, %d The List\"", pack.rares(), pack.uncommons(), pack.commons(), pack.theList());
    }

    private record SlotOption(String label, double percentage) {
    }

    private record PackPermutation(String label, double percentage) {
    }

    private record HumanReadablePack(int rares, int uncommons, int commons, int theList, String percentage) {
    }
}
