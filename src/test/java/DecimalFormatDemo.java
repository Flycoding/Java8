import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class DecimalFormatDemo {

	static public void customFormat(String pattern, double value) {
		final DecimalFormat myFormatter = new DecimalFormat(pattern);
		final String output = myFormatter.format(value);
		System.out.println(value + "  " + pattern + "  " + output);
	}

	static public void localizedFormat(String pattern, double value, Locale loc) {
		final NumberFormat nf = NumberFormat.getNumberInstance(loc);
		final DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern(pattern);
		final String output = df.format(value);
		System.out.println(pattern + "  " + output + "  " + loc.toString());
	}

	static public void main(String[] args) {

		customFormat("###,###.###", 123456.789);
		customFormat("###.##", 123456.789);
		customFormat("000000.000", 123.78);
		customFormat("$###,###.###", 12345.67);
		customFormat("\u00a5###,###.###", 12345.67);

		final Locale currentLocale = new Locale("en", "US");

		final DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(currentLocale);
		unusualSymbols.setDecimalSeparator('|');
		unusualSymbols.setGroupingSeparator('^');
		final String strange = "#,##0.###";
		final DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
		weirdFormatter.setGroupingSize(4);
		final String bizarre = weirdFormatter.format(12345.678);
		System.out.println(bizarre);

		final Locale[] locales = { new Locale("en", "US"), new Locale("de", "DE"), new Locale("fr", "FR") };

		for (int i = 0; i < locales.length; i++) {
			localizedFormat("###,###.###", 123456.789, locales[i]);
		}

	}
}