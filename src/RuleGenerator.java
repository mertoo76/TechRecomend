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
		checkMinConf(minConf);

		Set<Association<I>> sonucSet = new HashSet<>();

		for (Set<I> techset : data.getFrequentItemsetList()) {
			if (techset.size() < 2) {
				//Association rule için en az 2 tech ihtiyacımız var, biri sağda biri solda
				continue;
			}
			//oncelikle consequent listesi sadece bir elemandan oluşan bir liste oluşturuyoruz
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
		//girdideki consequent item sayısı = m
		int m = ruleSet.iterator().next().getDevam().size();
		if (k > m + 1) {
			Set<Association<I>> nextRules = yeniEkle(itemset, ruleSet, data);

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

	private Set<Association<I>> yeniEkle(Set<I> itemset, Set<Association<I>> ruleSet,
			FrekansListeli<I> data) {
		Set<Association<I>> output = new HashSet<>();
		Set<I> ilkler = new HashSet<>();
		Set<I> devamlar = new HashSet<>();
		double itemsetSupportCount = data.getSupportCountMap().get(itemset);
		// her kural için
		for (Association<I> rule : ruleSet) {
			ilkler.clear();
			devamlar.clear();
			ilkler.addAll(rule.getIlk());
			devamlar.addAll(rule.getDevam());
			//her antecentten sadece birini consequnte atıyoruz
			for (I item : rule.getIlk()) {
				ilkler.remove(item);
				devamlar.add(item);

				int antecedentSupportCount = data.getSupportCountMap().get(ilkler);
				Association<I> newRule = new Association<>(ilkler, devamlar,
						itemsetSupportCount / antecedentSupportCount);

				output.add(newRule);

				ilkler.add(item);
				devamlar.remove(item);
			}
		}

		return output;
	}

	private Set<Association<I>> generateBasic(Set<I> itemset, FrekansListeli<I> data) {
		Set<Association<I>> ilkAssociatedList = new HashSet<>(itemset.size());

		Set<I> ilk = new HashSet<>(itemset);
		Set<I> devam = new HashSet<>(1);

		for (I item : itemset) {
			ilk.remove(item);
			devam.add(item);

			int itemsetSupportCount = data.getSupportCountMap().get(itemset);
			int ilkSupportCount = data.getSupportCountMap().get(ilk);

			double confidence = 1.0 * itemsetSupportCount / ilkSupportCount;

			ilkAssociatedList.add(new Association(ilk, devam, confidence));
			ilk.add(item);
			devam.remove(item);
		}

		return ilkAssociatedList;
	}

	private void checkMinConf(double minConf) {
		if (Double.isNaN(minConf)) {
			throw new IllegalArgumentException("Min confidence hatasi");
		}

		if (minConf < 0.0) {
			throw new IllegalArgumentException(
					"Min conf 0 dan buyuk olmali");
		}

		if (minConf > 1.0) {
			throw new IllegalArgumentException(
					"Min conf 1 den kucuk olmali");
		}
	}
}