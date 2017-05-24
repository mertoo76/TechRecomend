import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

	
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
			+ "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	//dssaasdasfasfsafsafasfa
	//Ozan
	
	/**
	 * This method receives two parameters. The first parameter is the page in
	 * which we have discovered this new url and the second parameter is the new
	 * url. You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic). In this example,
	 * we are instructing the crawler to ignore urls that have css, js, git, ...
	 * extensions and to only accept urls that start with
	 * "http://www.ics.uci.edu/". In this case, we didn't need the referringPage
	 * parameter to make the decision.
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {

		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches() && href.startsWith("https://stackoverflow.com/users");
	}

	List<Set<String>> itemsetList = new ArrayList<>();
	
	//AprioriFrequentItemsetGenerator<String> generator = new AprioriFrequentItemsetGenerator<>();
	
	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
		int parentDocid = page.getWebURL().getParentDocid();

		System.out.println("Docid: " + docid);
		System.out.println("URL: " + url);
		System.out.println("Docid of parent page: " + parentDocid);
		
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();

			/*
			 * System.out.println("Text length: " + text.length());
			 * System.out.println("Html length: " + html.length());
			 * System.out.println("Number of outgoing links: " + links.size());
			 */

			/*
			 * String[] parts = html.split("rel=\"tag\""); html= parts[1]; //
			 * 034556 parts = html.split("</a>"); html= parts[0]; // 034556
			 * System.out.println("Text length: " + html);
			 */

			String[] parts = html.split("rel=\"tag\">");

			// System.out.println("Text length: " + parts.length);
			// String[] tech = parts[1].split("</a>");
			int index;
			int i = 0;
			LinkedList<String> items = new LinkedList<String>();
			for (index = 1; index < parts.length; index++) {
				String[] tech = parts[index].split("</a>");
				tech[0] = tech[0].replace(
						"<img src=\"//i.stack.imgur.com/tKsDb.png\" height=\"16\" width=\"18\" alt=\"\" class=\"sponsor-tag-img\">",
						"");
				tech[0] = tech[0].replace(
						"<img src=\"//i.stack.imgur.com/vobok.png\" height=\"16\" width=\"18\" alt=\"\" class=\"sponsor-tag-img\">",
						"");
				tech[0] = tech[0].replace(
						"<img src=\"//i.stack.imgur.com/aABck.png\" height=\"16\" width=\"18\" alt=\"\" class=\"sponsor-tag-img\">",
						"");
				
				//<img src="//i.stack.imgur.com/vobok.png" height="16" width="18" alt="" class="sponsor-tag-img">
				System.out.println("Teknolojiler: " + tech[0]);
				//items.add(tech[0]);
				items.add(tech[0]);
			}
			
			itemsetList.add(new HashSet<>(items));
			globals.setItemsetList(itemsetList,this.getMyController().tech);
				
		}
		
		
	}

	public List<Set<String>> getItemsetList() {
		return itemsetList;
	}
}