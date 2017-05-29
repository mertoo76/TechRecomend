import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class globals {
	public static List<Set<String>> itemsetList;
public static  String tech;
public static LinkedList<String> frameRule = new LinkedList<String>();

	public static List<Set<String>> getItemsetList() {
		FrequentFinder<String> generator =
                new FrequentFinder<>();
		long startTime = System.nanoTime();
		itemsetList.remove(0);
        FrekansListeli<String> data = generator.generate(itemsetList, 0.1);
        
        long endTime = System.nanoTime();
       
        int i = 1;

        System.out.println("--- Frequent itemsets ---");

        for (Set<String> itemset : data.getFrequentItemsetList()) {
            System.out.printf("%2d: %9s, support: %1.1f\n",
                              i++, 
                              itemset,
                              data.getSupport(itemset));
        }

        System.out.printf("Mined frequent itemset in %d milliseconds.\n", 
                         (endTime - startTime) / 1_000_000);

        startTime = System.nanoTime();
        List<Association<String>> associationRuleList = 
                new RuleGenerator<String>()
                        .kuralBul(data, 0.5);
        endTime = System.nanoTime();

        i = 1;

        System.out.println();
        System.out.println("--- Association rules ---");

        for (Association<String> rule : associationRuleList) {
        	
        	if(rule.getAntecedent().contains(tech)){
            System.out.printf("%2d: %s\n", i++, rule);
            frameRule.add(rule.toString());
        	}
            if(i==50){//cok fazla yazdiriyor
            	break;
            }
        }

        System.out.printf("Mined association rules in %d milliseconds.\n",
                          (endTime - startTime) / 1_000_000);
		return itemsetList;
		
	}

	public static void setItemsetList(List<Set<String>> itemsetList, String t) {
		System.out.println("Set succesfull" + itemsetList.size());
		tech = t;
		globals.itemsetList = itemsetList;
		
		
	}
	

}
