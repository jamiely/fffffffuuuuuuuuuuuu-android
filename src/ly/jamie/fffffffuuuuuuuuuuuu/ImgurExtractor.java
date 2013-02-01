package ly.jamie.fffffffuuuuuuuuuuuu;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.*;

public class ImgurExtractor {
	private String text = "";
	public ImgurExtractor(String text) {
		this.text = text;
	}
	public URL extract() {
		try {
			return new URL(this.normalizeLink(linkInText(this.text)));
		}
		catch(MalformedURLException ex) {
			return null;
		}
	}
	private Pattern imgurPattern() {
		final int flags = Pattern.CASE_INSENSITIVE;
		return Pattern.compile("(https?://imgur.com/[A-Z0-9]+)(.png)?", flags);
	}
	private Matcher matcherFor(String text) {
		return imgurPattern().matcher(text);
	}
	private String normalizeLink(String baseLink) {
		return baseLink + ".png";
	}
	private String linkInText(String text) {
		Matcher m = matcherFor(text);
		return m.find() ? m.group(1) : "";
	}
}
