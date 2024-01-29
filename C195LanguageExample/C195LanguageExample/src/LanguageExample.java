import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageExample {

	public static void main(String[] args) {
	
		Locale.setDefault(new Locale("no", "NO"));
		ResourceBundle rb = ResourceBundle.getBundle("language_files/rb");
		System.out.println(Locale.getDefault());
		
		System.out.println(rb.getString("intro"));
	}

}
