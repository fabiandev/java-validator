# java-validator

A simple and extensible validation utility for Java.

# Usage

```java
import io.fabiandev.validator.contracts.Validator;
import io.fabiandev.validator.contracts.Bag;
import io.fabiandev.validator.core.StandardValidator;

Validator validator = new StandardValidator(request);

validator
    .rule("email", "max:35|email")
    .rule("phone", "required_unless:email|min:9|digits")
        .message("phone.min", ":field must be at least :min characters long.")
        .message("phone.digits", "For :field only digits are allowed")
    .rule("name", "required|min:6")
        .message("name.min", "Please fill in your name with at least :min characters.")
    .rule("tos_accepted", "required")
        .message("tos_accepted.required", "Please accept the Terms of Service.");

if (validator.fails()) {
    System.out.println("There were errors!");
    Bag errorBag = validator.errors();
    return;
}

System.out.println("Validation passed!");
```

# Extending

Find two basic examples of how to create custom rules below, and add them to the `RulesManage`

```java
import io.fabiandev.validator.core.RulesManager;

RulesManager.addRule(MyRule.class);
```

## Standard Rule

```java
import io.fabiandev.validator.core.BaseRule;

public class MyRule extends BaseRule {

  @Override
    protected void validate() {
    // Get the input field's value: this.inputField.getValue()
    // Get all input fields: this.inputFields
    // Get the rule parameter: this.ruleValue
    // Call this.fail() if validation doesn't pass
    }

    @Override
    protected String defaultMessage() {
        return ":field can include :my_rule placeholders for the parameter.";
    }

}
```

## Regex Rule

```java
import io.fabiandev.validator.rules.Regex;

public class Everything extends Regex {

    @Override
    protected String regex() {
        return "^.*$";
    }

    @Override
    protected String defaultMessage() {
        return ":field did not pass the everything rule.";
    }

}
```

# Full Example

```java
import io.fabiandev.validator.core.BaseRule;

public class ExactNumber extends BaseRule {

  @Override
    protected void validate() { 
      int expected = Integer.parseInt(this.ruleValue);
      int actual = Integer.parseInt(this.inputField.getValue());
      
      if (actual != expected)
      {
          this.fail();
      }
    }

    @Override
    protected String defaultMessage() {
        return ":field must contain the number :exact_number.";
    }

}
```

```java
import io.fabiandev.validator.core.RulesManager;

RulesManager.addRule(ExactNumber.class);
```

```java
import io.fabiandev.validator.contracts.Validator;
import io.fabiandev.validator.core.StandardValidator;

Validator validator = new StandardValidator(data);

validator
    .rule("inputField", "exact_number:10|required")
    .messsage("inputField.exact_number", "Field ':field should' contain the number :exact_number.")
    

validator.validate();
validator.errors(); // contains "Field 'inputField' should contain the number 10."

```
