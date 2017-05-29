import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RuleGenerator<I> {

	public List<Association<I>> kuralBul(FrekansListeli<I> data, double minConf) {
		Objects.requireNonNull(data, "Frekans Listesi Bos.");
		checkMinimumConfidence(minConf);

		Set<Association<I>> sonucSet = new HashSet<>();

		for (Set<I> techset : data.getFrequentItemsetList()) {
			if (techset.size() < 2) {
				continue;
			}
			Set<Association<I>> ilkAssociatedSet = generateBasic(techset, data);
			generateAssociation(techset, ilkAssociatedSet, data, minConf, sonucSet);
		}

		List<Association<I>> ret = new ArrayList<>(sonucSet);
		Collections.sort(ret, (a1, a2) -> {
			return Double.compare(a2.getConfidence(), a1.getConfidence());
		});

		return ret;
	}

	private void generateAssociation(Set<I> itemset, Set<Association<I>> ruleSet, FrekansListeli<I> data,
			double minConf, Set<Association<I>> collector) {
		if (ruleSet.isEmpty()) {
			return;
		}
		int k = itemset.size();
		int m = ruleSet.iterator().next().getConsequent().size();
		if (k > m + 1) {
			Set<Association<I>> nextRules = moveOneItemToConsequents(itemset, ruleSet, data);

			Iterator<Association<I>> iterator = nextRules.iterator();

			while (iterator.hasNext()) {
				Association<I> rule = iterator.next();

				if (rule.getConfidence() >= minConf) {
					collector.add(rule);
				} else {
					iterator.remove();
				}
			}

			generateAssociation(itemset, nextRules, data, minConf, collector);
		}
	}

	private Set<Association<I>> moveOneItemToConsequents(Set<I> itemset, Set<Association<I>> ruleSet,
			FrekansListeli<I> data) {
		Set<Association<I>> output = new HashSet<>();
		Set<I> antecedent = new HashSet<>();
		Set<I> consequent = new HashSet<>();
		double itemsetSupportCount = data.getSupportCountMap().get(itemset);

		// For each rule ...
		for (Association<I> rule : ruleSet) {
			antecedent.clear();
			consequent.clear();
			antecedent.addAll(rule.getAntecedent());
			consequent.addAll(rule.getConsequent());

			// ... move one item from its antecedent to its consequnt.
			for (I item : rule.getAntecedent()) {
				antecedent.remove(item);
				consequent.add(item);

				int antecedentSupportCount = data.getSupportCountMap().get(antecedent);
				Association<I> newRule = new Association<>(antecedent, consequent,
						itemsetSupportCount / antecedentSupportCount);

				output.add(newRule);

				antecedent.add(item);
				consequent.remove(item);
			}
		}

		return output;
	}

	/**
	 * Given a frequent itemset of size {@code n}, generates and returns all
	 * {@code n} possible association rules with consequent of size one.
	 * 
	 * @param itemset
	 *            the itemset.
	 * @return a set of association rules with consequents of size one.
	 */
	private Set<Association<I>> generateBasic(Set<I> itemset, FrekansListeli<I> data) {
		Set<Association<I>> basicAssociationRuleSet = new HashSet<>(itemset.size());

		Set<I> antecedent = new HashSet<>(itemset);
		Set<I> consequent = new HashSet<>(1);

		for (I item : itemset) {
			antecedent.remove(item);
			consequent.add(item);

			int itemsetSupportCount = data.getSupportCountMap().get(itemset);
			int antecedentSupportCount = data.getSupportCountMap().get(antecedent);

			double confidence = 1.0 * itemsetSupportCount / antecedentSupportCount;

			basicAssociationRuleSet.add(new Association(antecedent, consequent, confidence));
			antecedent.add(item);
			consequent.remove(item);
		}

		return basicAssociationRuleSet;
	}

	private void checkMinimumConfidence(double minimumConfidence) {
		if (Double.isNaN(minimumConfidence)) {
			throw new IllegalArgumentException("The input minimum confidence is NaN.");
		}

		if (minimumConfidence < 0.0) {
			throw new IllegalArgumentException(
					"The input minimum confidence is negative: " + minimumConfidence + ". " + "Must be at least zero.");
		}

		if (minimumConfidence > 1.0) {
			throw new IllegalArgumentException(
					"The input minimum confidence is too large: " + minimumConfidence + ". " + "Must be at most 1.");
		}
	}
}