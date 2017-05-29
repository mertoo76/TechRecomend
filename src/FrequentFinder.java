import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class FrequentFinder<I> {

	public FrekansListeli<I> generate(List<Set<I>> techList, double minSupp) {
		Objects.requireNonNull(techList, "TechSet Empty");
		chck(minSupp);

		if (techList.isEmpty()) {
			return null;
		}
		
		Map<Set<I>, Integer> supportCounter = new HashMap<>();
		List<Set<I>> frekansList = findFrequentItems(techList, supportCounter, minSupp);
		Map<Integer, List<Set<I>>> map = new HashMap<>();
		map.put(1, frekansList);
		int p = 1;

		do {
			++p;
			List<Set<I>> adayList = adayOlustur(map.get(p - 1));

			for (Set<I> temp : techList) {
				List<Set<I>> adayList2 = subset(adayList, temp);

				for (Set<I> itemset : adayList2) {
					supportCounter.put(itemset, supportCounter.getOrDefault(itemset, 0) + 1);
				}
			}

			map.put(p, getTechNext(adayList, supportCounter, minSupp, techList.size()));
			//System.out.println("cikmadan once");
		} while (!map.get(p).isEmpty());

		return new FrekansListeli<>(extractFrequentItemsets(map), supportCounter, minSupp, techList.size());
	}
	private List<Set<I>> extractFrequentItemsets(Map<Integer, List<Set<I>>> map) {
		List<Set<I>> ret = new ArrayList<>();

		for (List<Set<I>> techSetList : map.values()) {
			ret.addAll(techSetList);
		}

		return ret;
	}
	private List<Set<I>> getTechNext(List<Set<I>> adayList, Map<Set<I>, Integer> supportCountMap,
			double minimumSupport, int tmp) {
		List<Set<I>> ret = new ArrayList<>(adayList.size());

		for (Set<I> itemset : adayList) {
			if (supportCountMap.containsKey(itemset)) {
				int supportCount = supportCountMap.get(itemset);
				double support = 1.0 * supportCount / tmp;

				if (support >= minimumSupport) {
					ret.add(itemset);
				}
			}
		}

		return ret;
	}
	private List<Set<I>> subset(List<Set<I>> adayList, Set<I> transaction) {
		List<Set<I>> ret = new ArrayList<>(adayList.size());

		for (Set<I> aday : adayList) {
			if (transaction.containsAll(aday)) {
				ret.add(aday);
			}
		}

		return ret;
	}
	private List<Set<I>> adayOlustur(List<Set<I>> techList) {

		List<List<I>> list = new ArrayList<>(techList.size());

		for (Set<I> itemset : techList) {
			List<I> l = new ArrayList<>(itemset);
			Collections.<I>sort(l, ITEM_COMPARATOR);
			list.add(l);
		}

		int listSize = list.size();

		List<Set<I>> ret = new ArrayList<>(listSize);

		for (int i = 0; i < listSize; ++i) {
			for (int j = i + 1; j < listSize; ++j) {
				Set<I> aday = denemeBirlestirme(list.get(i), list.get(j));

				if (aday != null) {
					ret.add(aday);
				}
			}
		}

		return ret;
	}
	private Set<I> denemeBirlestirme(List<I> techSet1, List<I> techSet2) {
		int length = techSet1.size();

		for (int i = 0; i < length - 1; ++i) {
			if (!techSet1.get(i).equals(techSet2.get(i))) {
				return null;
			}
		}

		if (techSet1.get(length - 1).equals(techSet2.get(length - 1))) {
			return null;
		}

		Set<I> ret = new HashSet<>(length + 1);

		for (int i = 0; i < length - 1; ++i) {
			ret.add(techSet1.get(i));
		}

		ret.add(techSet1.get(length - 1));
		ret.add(techSet2.get(length - 1));
		return ret;
	}

	private static final Comparator ITEM_COMPARATOR = new Comparator() {

		@Override
		public int compare(Object o1, Object o2) {
			return ((Comparable) o1).compareTo(o2);
		}

	};
	private List<Set<I>> findFrequentItems(List<Set<I>> techList, Map<Set<I>, Integer> supportCountMap,
			double minimumSupport) {
		Map<I, Integer> map = new HashMap<>();

		// Count the support counts of each item.
		for (Set<I> itemset : techList) {
			for (I item : itemset) {
				Set<I> tmp = new HashSet<>(1);
				tmp.add(item);

				supportCountMap.put(tmp, supportCountMap.getOrDefault(tmp, 0) + 1);

				map.put(item, map.getOrDefault(item, 0) + 1);
			}
		}

		List<Set<I>> frekansTech = new ArrayList<>();

		for (Map.Entry<I, Integer> entry : map.entrySet()) {
			if (1.0 * entry.getValue() / techList.size() >= minimumSupport) {
				Set<I> itemset = new HashSet<>(1);
				itemset.add(entry.getKey());
				frekansTech.add(itemset);
			}
		}

		return frekansTech;
	}

	private void chck(double support) {
		if (Double.isNaN(support)) {
			throw new IllegalArgumentException("Support error.");
		}

		if (support > 1.0) {
			throw new IllegalArgumentException(
					" Support error : " + support + " max : 1.0");
		}

		if (support < 0.0) {
			throw new IllegalArgumentException(
					" Support error : " + support + " min : 1.0");
		}
	}
}