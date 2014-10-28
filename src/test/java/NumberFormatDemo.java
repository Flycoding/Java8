import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class NumberFormatDemo {

	static public void displayNumber(Locale currentLocale) {
		final Integer quantity = new Integer(123456);
		final Double amount = new Double(345987.246);
		NumberFormat numberFormatter;
		String quantityOut;
		String amountOut;

		numberFormatter = NumberFormat.getNumberInstance(currentLocale);
		quantityOut = numberFormatter.format(quantity);
		amountOut = numberFormatter.format(amount);
		System.out.println(quantityOut + "   " + currentLocale.toString());
		System.out.println(amountOut + "   " + currentLocale.toString());
	}

	static public void displayCurrency(Locale currentLocale) {
		final Double currencyAmount = new Double(9876543.21);
		final Currency currentCurrency = Currency.getInstance(currentLocale);
		final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
		System.out.println(currentLocale.getDisplayName() + ", " + currentCurrency.getDisplayName() + ": " + currencyFormatter.format(currencyAmount));
	}

	static public void displayPercent(Locale currentLocale) {
		final Double percent = new Double(0.75);
		NumberFormat percentFormatter;
		String percentOut;

		percentFormatter = NumberFormat.getPercentInstance(currentLocale);
		percentOut = percentFormatter.format(percent);
		System.out.println(percentOut + "   " + currentLocale.toString());
	}

	static public void main(String[] args) {
		final ArrayList<Locale> locales = new ArrayList<>();
		locales.add(0, new Locale.Builder().setLanguage("fr").setRegion("FR").build());
		locales.add(1, new Locale.Builder().setLanguage("de").setRegion("DE").build());
		locales.add(2, new Locale.Builder().setLanguage("en").setRegion("US").build());

		for (int i = 0; i < locales.size(); i++) {
			displayNumber(locales.get(i));
		}
		for (int i = 0; i < locales.size(); i++) {
			displayCurrency(locales.get(i));
		}
		for (int i = 0; i < locales.size(); i++) {
			displayPercent(locales.get(i));
		}
	}
}