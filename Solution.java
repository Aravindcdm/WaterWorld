import java.util.*;
class code {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int minWaterNeeded = 0;
        int days = sc.nextInt();
        if (days == 0) {
            System.out.println(minWaterNeeded);
            return;
        }
        int n = sc.nextInt();
        // Cluster mapping: name → index
        Map<String, Integer> clusterIndexMap = new HashMap<>();

        List<List<String>> clusters = new ArrayList<>();
        int[] RemainingWater = new int[n];

        for (int i = 0; i < n; i++) {
            List<String> cluster = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                String str = sc.next();
                cluster.add(str);
                if (j == 0) {
                    clusterIndexMap.put(str, i); // store cluster name to index
                }
                if (j == 2) {
                    int capacity = Integer.parseInt(str);
                    RemainingWater[i] = capacity;
                    minWaterNeeded += capacity;
                }
            }
            clusters.add(cluster);
        }

        int NumOfpipeLink = sc.nextInt();

        // Pipe mapping: destination cluster → [source, destination]
        Map<String, String[]> pipeMap = new HashMap<>();
        for (int i = 0; i < NumOfpipeLink; i++) {
            String[] parts = sc.next().split("_");
            pipeMap.put(parts[1], parts);
        }

        while (days-- > 0) {
            for (int i = n - 1; i >= 0; i--) {
                int dailyUsage = Integer.parseInt(clusters.get(i).get(1));
                if (RemainingWater[i] < dailyUsage) {
                    String clusterName = clusters.get(i).get(0);
                    String[] pipeline = pipeMap.get(clusterName);
                    System.out.println(clusterName + " " + Arrays.toString(pipeline));
                    // Traverse path back to F
                    while (pipeline[0].charAt(0) != 'F') {
                        int currentIndex = clusterIndexMap.get(pipeline[1]);
                        int capacity = Integer.parseInt(clusters.get(currentIndex).get(2));
                        minWaterNeeded += capacity;
                        RemainingWater[currentIndex] = capacity;

                        String nextCluster = pipeline[0];
                        pipeline = pipeMap.get(nextCluster);
                    }

                    // Final refill from F
                    int federalTargetIndex = clusterIndexMap.get(pipeline[1]);
                    int capacity = Integer.parseInt(clusters.get(federalTargetIndex).get(2));
                    minWaterNeeded += capacity;
                    RemainingWater[federalTargetIndex] = capacity;
                }

                RemainingWater[i] -= dailyUsage;
            }
        }

        System.out.println(minWaterNeeded);
    }
}
