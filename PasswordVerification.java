import java.util.ArrayList;
import java.util.List;

import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.WhitespaceRule;

public class PasswordVerification {
	
	
	
	public static boolean isValidPassword(String password) {
		LengthRule lengthRule = new LengthRule(8, 16);
		WhitespaceRule whitespaceRule = new WhitespaceRule();
		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(lengthRule);
		ruleList.add(whitespaceRule);
		PasswordValidator validator = new PasswordValidator(ruleList);
		PasswordData passwordData = new PasswordData(new Password(password));
		RuleResult result = validator.validate(passwordData);

		if (result.isValid()) 
			return true;
		return false;
	}

}
