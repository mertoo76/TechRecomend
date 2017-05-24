import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
	public static void main(String[] args) throws Exception {
		String crawlStorageFolder = "/data/crawl/root";
		int numberOfCrawlers = 1;
		
		CrawlConfig config = new CrawlConfig();
		
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setPolitenessDelay(1000);
		config.setMaxDepthOfCrawling(5);
		config.setMaxPagesToFetch(40);
		config.setResumableCrawling(false);
		//
		//
		/*
		 * Instantiate the controller for this crawl.
		 */
		//
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		
		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		controller.tech=args[2];
		controller.addSeed("https://stackoverflow.com/tags/"+args[2]+"/topusers");

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(MyCrawler.class, numberOfCrawlers);
		
		
		AprioriFrequentItemsetGenerator<String> generator =
                new AprioriFrequentItemsetGenerator<>();
		long startTime = System.nanoTime();
		List<Set<String>> itemsetList2 = new ArrayList<>();
		itemsetList2 = globals.getItemsetList();
		//BURAYA !!!!!!
		System.out.println("Hello darkness2");
        
	}
}