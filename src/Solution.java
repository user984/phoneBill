import java.util.*;
import java.util.stream.Collectors;

public class Solution
{
    private static int solution(String rawLogData) {
        System.out.println(rawLogData);
        String[] phoneRecords = rawLogData.split("\n");
        Map<Integer, Integer> phoneDurationMap = new HashMap<>();
        for (String phoneRecord : phoneRecords) {
            String[] s = phoneRecord.split(",");
            Integer pn = Integer.parseInt(s[1].replaceAll("-", ""));
            if (s.length > 1) {
                if(phoneDurationMap.containsKey(pn)) {
                    Integer d = phoneDurationMap.get(pn) + Integer.parseInt(s[0]);
                    phoneDurationMap.put(pn, d);
                } else {
                    phoneDurationMap.put(pn, Integer.parseInt(s[0]));
                }
            }
        }
        phoneDurationMap.forEach((k, v) -> System.out.println("Item : " + k + " Count : " + v));
        System.out.println("-----------------------------------------------------------------");
        Map<Integer, Integer> sortedNewMap =  phoneDurationMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        sortedNewMap.forEach((k, v) -> System.out.println("Item : " + k + " Count : " + v));
        Integer longestDurationValue = 0;
        if (!sortedNewMap.values().isEmpty()) {
            longestDurationValue = (Integer) sortedNewMap.values().toArray()[0];
            System.out.println(longestDurationValue);
        }
        Map<Integer, Integer> sortedMapByKey =  sortedNewMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        sortedMapByKey.forEach((k, v) -> System.out.println("Item : " + k + " Count : " + v));
        sortedMapByKey.values().remove(longestDurationValue);
        System.out.println("-----------------------------------------------------------------");
        sortedMapByKey.forEach((k, v) -> System.out.println("Item : " + k + " Count : " + v));

        Integer total = sortedMapByKey.values().stream().map( v -> costofCall(v) ).mapToInt(Integer::intValue).sum();
        System.out.println(total);
        return 0;
    }

    private static Integer costofCall(Integer v) {
        // 3p a minute if under 300s
        if (v < 300) {
            return v * 3;
        } else {
            // else charge per minute for extra time used at 150p for every started minute
            // 360s is 6 minutes
            // 361s is 7 minutes
            Integer minutes = v / 60;
            if ((v % 60) != 0) {
                minutes += 1;
            }
            return 150 * minutes;
        }
    }

    public static void main(String [] args) {

        final String d = "300,400-234-090\n" +
                "900,701-080-080\n" +
                "600,400-234-090\n" +
                "123,301-240-567\n" +
                "361,123-456-789\n";

        int t = Solution.solution(d);
        System.out.println(t);
    }
}
