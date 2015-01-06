package forest.rice.field.k.amazon.response;

import java.util.Calendar;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

public class Item {

	public String detailPageURL = "";
	public String largeImage = "";
	public String mediumImage = "";
	public String title = "";
	public String releaseDate = "";
	public String productGroup = "";
	public String artist = "";

	public Item(Node item) {

		NodeList childNodes = item.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i) instanceof Element) {
				Element element = (Element) childNodes.item(i);

				switch (element.getTagName()) {
				case "DetailPageURL":
					detailPageURL = element.getFirstChild().getNodeValue();
					break;
				case "LargeImage":
					largeImage = parseImage(element);
					break;
				case "MediumImage":
					mediumImage = parseImage(element);
					break;
				case "ItemAttributes":
					parseItemAttributes(element);
					break;
				}
			}
		}
	}

	private String parseImage(Element element) {
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i) instanceof Element) {
				Element childElement = (Element) childNodes.item(i);
				switch (childElement.getTagName()) {
				case "URL":
					return childElement.getFirstChild().getNodeValue();
				}
			}
		}
		return null;
	}

	private void parseItemAttributes(Element element) {
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i) instanceof Element) {
				Element childElement = (Element) childNodes.item(i);
				switch (childElement.getTagName()) {
				case "ReleaseDate":
					releaseDate = childElement.getFirstChild().getNodeValue();
					break;
				case "Title":
					title = childElement.getFirstChild().getNodeValue();
					break;
				case "ProductGroup":
					productGroup = childElement.getFirstChild().getNodeValue();
					break;
				case "Artist":
					artist = childElement.getFirstChild().getNodeValue();
					break;
				}
			}
		}
	}

	public Intent createCalendarIntent() throws CalendarDataException {
		Intent intent = new Intent(Intent.ACTION_INSERT)
				.setData(Events.CONTENT_URI)
				.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
						getReleaseDate(releaseDate).getTimeInMillis())
				.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
						getReleaseDate(releaseDate).getTimeInMillis())
				.putExtra(Events.TITLE, createEventTitle());
		return intent;

	}

	private Calendar getReleaseDate(String releaseDate)
			throws CalendarDataException {
		Calendar cal;
		try {
			String year = releaseDate.substring(0, 4);
			String month = releaseDate.substring(5, 7);
			String day = releaseDate.substring(8, 10);

			cal = Calendar.getInstance();
			cal.set(Integer.valueOf(year), Integer.valueOf(month) - 1,
					Integer.valueOf(day), 0, 0);
		} catch (Exception e) {
			throw new CalendarDataException("カレンダーデータの作成に失敗しました");
		}
		return cal;
	}

	private String createEventTitle() {
		StringBuilder titleString = new StringBuilder();
		titleString.append("【発売日】");
		titleString.append(title);
		if (!artist.equals("")) {
			titleString.append("/").append(artist);
		}
		return titleString.toString();
	}

	public class CalendarDataException extends Exception {

		private static final long serialVersionUID = 7748699340420566735L;

		private String message;

		public CalendarDataException(String message) {
			this.message = message;
		}

		@Override
		public String getMessage() {
			return message;
		}
	}
}
